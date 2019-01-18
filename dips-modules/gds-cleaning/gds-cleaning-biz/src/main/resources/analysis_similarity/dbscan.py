import numpy as np
import json
import gensim
import os
import sys
import pickle
import enchant
import spacy
import jieba
import time
from multiprocessing import Pool
import multiprocessing
from itertools import repeat
# from pathos.multiprocessing import ProcessingPool as Pool

import functools


def dbscan(dataset, distanceMatrix, eps, minPts):
    labels = [0] * len(dataset)
    c = 0
    for i, d in enumerate(dataset):
        if labels[i] != 0:
            continue
        else:
            neighbors = getNeighbor(distanceMatrix, i, eps)
            if len(neighbors) < minPts:
                labels[i] = -1
            else:
                c += 1
                print('c:',c)
                expandCluster(eps, minPts, i, neighbors, labels, c, distanceMatrix)
    return labels


def expandCluster(eps, minPts, currentData, neighbors, labels, c, distanceMatrix):
    labels[currentData] = c
    i = 0
    while i < len(neighbors):
        if labels[neighbors[i]] == -1:
            labels[neighbors[i]] = c
        elif labels[neighbors[i]] == 0:
            labels[neighbors[i]] = c
            _neighbor = getNeighbor(distanceMatrix, neighbors[i], eps)
            if len(_neighbor) >= minPts:
                neighbors.extend(_neighbor)
        i += 1


def getNeighbor(distanceMatrix, currentData, eps):
    indexs = np.nonzero(np.asarray(distanceMatrix[currentData] > eps))[0]
    return indexs.tolist()


def getDistanceMatrix_parallel(i, dataList, matrix, distance):
    # print('current process id:',os.getpid())
    print('i:', i)
    dataListLen = len(dataList)
    print('dataListLen:', dataListLen)
    for j in range(i, dataListLen):
        if i == j:
            matrix[i, j] = 0
        else:
            matrix[i, j] = distance(i, j)
            matrix[j, i] = matrix[i, j]



class JsonData(object):
    def __init__(self,jsonPath):
        self.path = jsonPath
        self._data = self.getJson()
        self._threshold = self._data['threshold']
        self._params = self._data['Params']
        self._weights = self._data['weights']
        self._approximates = self._data['approximates']
        self._dataList = self._data['data']

    def getJson(self):
        with open(self.path, 'rb') as f:
            data = json.load(f)
        return data

    @property
    def threshold(self):
        return self._threshold

    @property
    def params(self):
        return self._params

    @property
    def weights(self):
        return self._weights

    @property
    def approximates(self):
        return self._approximates

    @property
    def dataList(self):
        return self._dataList

def preCutPhrase(data,d_):
    if not data:
        return None
    preList = [w for w in jieba.cut(data)]
    preList_chinese = [w for w in preList if not d_.check(w)]
    preList_english = ''.join([w for w in preList if d_.check(w)])
    return (preList_chinese, preList_english)


def getPrePhrase(approximates,dataList,params,d_):
        allSemanticWords = []
        nonZeroIndex = np.nonzero(np.array(approximates) > 0)[0]
        if nonZeroIndex.size == 0:
            return None
        else:
            for d in dataList:
                record = []
                for i in nonZeroIndex:
                    record.append(preCutPhrase(d[params[i]],d_))
                allSemanticWords.append(record)
        return allSemanticWords

def load_word_embedding(path):
    model = gensim.models.KeyedVectors.load_word2vec_format(path)
    return model

def get_stopword_path(path):
    stopwords = []
    with open(path, 'r') as f:
        for d in f:
            stopwords.append(d.rstrip())
    return stopwords

def phraseSimilarity(model, target, source):
    similarity = 1 / (1 + model.wmdistance(target, source))
    return similarity

def calculateDataSimilarity(self):
    def compare(x,y):
        return 1 if x==y else 0

    result=[]
    approx = np.nonzero(np.array(self.approximates))[0]
    #improved method
    directComparisonList = list(set(range(len(self.params))).difference(set(approx)))
    threshold_local = self.threshold - np.sum(np.array(self.weights)[approx])
    for d in self.datas:
        dict_sim = {}
        dict_sim['id'] = d['id']
        sim = 0.0
        for i in directComparisonList:
            sim += self.weights[i] * compare(self.standard[self.params[i]],d[self.params[i]])
        if sim > threshold_local:
            synonym_params = self.similarity.keys()
            d_ = enchant.Dict('en_US')
            for index in approx:
                if self.params[index] in synonym_params:
                    if d[self.params[index]] in self.similarity[self.params[index]]:
                        sim += self.similarity[self.params[index]][d[self.params[index]]]*self.weights[index]
                    else:
                        target_chinese = [w for w in jieba.cut(d[self.params[index]]) if not d_.check(w)]
                        target_english = [w for w in jieba.cut(d[self.params[index]]) if d_.check(w)]
                        source_chinese = [w for w in jieba.cut(self.standard[self.params[index]]) if not d_.check(w)]
                        source_english = [w for w in jieba.cut(self.standard[self.params[index]]) if d_.check(w)]
                        if len(target_english) == 0:
                            sim += self.weights[index] * self.calculateSimilarity(self.model,target_chinese,source_chinese)
                        elif len(source_english) >0:
                            nlp = spacy.load('en_core_web_md')
                            sim_english = nlp(target_english).similarity(nlp(source_english))
                            sim += 0.5*(self.weights[index] * self.calculateSimilarity(self.model,target_chinese,source_chinese))+\
                                0.5*sim_english
        # print('sim:',sim)
        if sim > self.threshold:
            dict_sim['similarity'] = sim
            result.append(dict_sim)
    return {'result':result}


def distance(target, source,params,approximates,weights,dataList,allSemanticWords,model,approx,directList,threshold_local,nlp=spacy.load('en_core_web_md')):
    def directDistance(a, b):
        return 1 if a == b else 0

    distance = 0.0
    index = 0

    # approx = np.nonzero(np.array(approximates))[0]
    # directComparisonList = list(set(range(len(params))).difference(set(approx)))
    # threshold_local = threshold - np.sum(np.array(weights)[approx])

    for i, p in enumerate(params):
        if approximates[i] == 0:
            distance += weights[i] * directDistance(dataList[target][p], dataList[source][p])
        else:
            if not dataList[target][p] or not dataList[source][p]:
                index += 1
                distance += weights[i] * directDistance(dataList[target][p], dataList[source][p])
            else:
                words_list_target = allSemanticWords[target][index]
                words_list_source = allSemanticWords[source][index]
                target_chinese = words_list_target[0]
                target_english = words_list_target[1]
                source_chinese = words_list_source[0]
                source_english = words_list_source[1]
                index += 1
                # distance += self.weights[i] * self.phraseSimilarity(self.model, words_list_target, words_list_source)

                if len(target_english) == 0:
                    distance += weights[i] * phraseSimilarity(model, target_chinese, source_chinese)
                elif len(source_english) > 0:
                    sim_english = nlp(target_english).similarity(nlp(source_english))
                    distance += 0.5 * (weights[i] * phraseSimilarity(model, target_chinese,
                                                                               source_chinese)) + 0.5 * sim_english
    return distance

def getDistanceMatrix_parallel(i,params,approximates,weights,dataList,allSemanticWords,model):
    print('i:',i)
    dataListLen = len(dataList)
    arr = np.zeros(dataListLen)
    for j in range(i,dataListLen):
        if i == j:
            arr[i] = 0
        else:
            arr[j] = distance(i,j,params,approximates,weights,dataList,allSemanticWords,model)
    return {'data':arr,'index':i}
    # return {i:arr}


def parallel(modelPath,stopwordPath):
    string = '/Users/tian/Downloads/drs_element.json'
    jsonData = JsonData(string)
    weights = jsonData.weights
    approximates = jsonData.approximates
    dataList = jsonData.dataList
    params = jsonData.params
    if not os.path.exists('model.pkl'):
        model = load_word_embedding(modelPath)
    else:
        with open('model.pkl', 'rb') as f:
            model = pickle.load(f)
    # model = load_word_embedding(modelPath)
    stopwords = get_stopword_path(stopwordPath)
    d_ = enchant.Dict('en_US')
    # nlp = spacy.load('en_core_web_md')
    allSemanticWords = getPrePhrase(approximates,dataList,params,d_)
    cores = multiprocessing.cpu_count()
    pool = Pool(processes=cores)
    # getDistanceMatrix_parallel_ = functools.partial(getDistanceMatrix_parallel, params=params,approximates=approximates,\
    #                                                 weights=weights,dataList=dataList,allSemanticWords=allSemanticWords,\
    #                                                 model=model,nlp=nlp)
    print(123)
    result = pool.starmap(getDistanceMatrix_parallel,zip(range(len(dataList)),repeat(params),repeat(approximates),\
                                                         repeat(weights),repeat(dataList),repeat(allSemanticWords),\
                                                         repeat(model)))
    # res = [pool.apply_async(target=self.getDistanceMatrix_parallel,(i,)) for i in range(len(self.dataList))]
    pool.close()
    pool.join()

    return result,dataList

    # with open('alias.pkl','wb') as f:
    #     pickle.dump(result,f)


def aggregate():
    # result,dataList = parallel('resource/sgns.baidubaike.bigram-char', 'resource/stopword_china.txt')
    with open('alias.pkl','rb') as f:
        result = pickle.load(f)
    # result = sorted(result,key=lambda x:x['index'])

    # matrix = result[0][0]
    # for i,data in enumerate(result):
    #     if i == 0:
    #         continue
    #     matrix = np.vstack((matrix,data[i]))

    matrix = []
    for i,data in enumerate(result):
        matrix.append(data[i])
    matrix = np.array(matrix).T +np.array(matrix)
    return matrix


    # return matrix,dataList


def reformatClustering(labels,matrix,threshold):
    labels_set = set(labels)
    clustering = {}
    if len(labels_set) == 1:
        return 'there is no cluster'
    else:
        for i,value in enumerate(labels):
            if value != -1:
                if value not in clustering:
                    clustering[value] = [i]
                else:
                    clustering[value].append(i)
    clustering_centrold = {}
    for clustering_list in clustering.values():
        clustering_centrold[centroid(clustering_list,matrix,threshold)] = clustering_list

    return clustering_centrold


def centroid(l1,matrix,threshold):
    if len(l1) == 2:
        return l1[0]
    else:
        matrix_local = matrix[l1]
        matrix_local = matrix_local[:,l1]
        index = np.argmax(np.sum(matrix_local>threshold,axis=1))
        return l1[index]










if __name__ == '__main__':
    # parallel('resource/sgns.baidubaike.bigram-char', 'resource/stopword_china.txt')
    matrix = aggregate()

    with open('/Users/tian/Downloads/drs_element.json','rb') as f:
        data = json.load(f)['data']
    print(1)
    labels = dbscan(data,matrix,0.8,1)
    print(labels)
    print(2)
    clustering = reformatClustering(labels)
    print(clustering)







# class DBSCANALG(object):
#     def __init__(self, path, word_embedding_path, stopword_path):
#         self.path = path
#         self.data = self.getJson()
#         self.threshold = self.data['threshold']
#         self.Params = self.data['Params']
#         self.weights = self.data['weights']
#         self.approximates = self.data['approximates']
#         self.dataList = self.data['data']
#         if not os.path.exists('model.pkl'):
#             self.model = self.load_word_embedding(word_embedding_path)
#         else:
#             with open('model.pkl', 'rb') as f:
#                 self.model = pickle.load(f)
#         self.stopwords = self.get_stopword_path(stopword_path)
#         self.nlp = spacy.load('en_core_web_md')
#         self.d_ = enchant.Dict('en_US')
#         self.matrix = np.zeros((len(self.dataList), len(self.dataList)), dtype=np.int16)
#         # self.cuttedList = Pool.map()
#
#     def getPrePhrase(self):
#         allSemanticWords = []
#         nonZeroIndex = np.nonzero(np.array(self.approximates) > 0)[0]
#         if nonZeroIndex.size == 0:
#             return None
#         else:
#             for d in self.dataList:
#                 record = []
#                 for i in nonZeroIndex:
#                     record.append(self.preCutPhrase(d[self.Params[i]]))
#                 allSemanticWords.append(record)
#         return allSemanticWords
#
#         # return Pool.map(self.preCutPhrase,)
#
#     def preCutPhrase(self, data):
#         if not data:
#             return None
#         preList = [w for w in jieba.cut(data)]
#         preList_chinese = [w for w in preList if not self.d_.check(w)]
#         preList_english = ''.join([w for w in preList if self.d_.check(w)])
#         return (preList_chinese, preList_english)
#         # return preList
#
#     def getJson(self):
#         with open(self.path, 'rb') as f:
#             data = json.load(f)
#         return data
#
#     def load_word_embedding(self, path):
#         model = gensim.models.KeyedVectors.load_word2vec_format(path)
#         return model
#
#     def get_stopword_path(self, path):
#         stopwords = []
#         with open(path, 'r') as f:
#             for d in f:
#                 stopwords.append(d.rstrip())
#         return stopwords
#
#     def phraseSimilarity(self, model, target, source):
#         similarity = 1 / (1 + model.wmdistance(target, source))
#         return similarity
#
#     def distance(self, target, source):
#         def directDistance(a, b):
#             return 1 if a == b else 0
#
#         distance = 0.0
#         index = 0
#         for i, p in enumerate(self.Params):
#             if self.approximates[i] == 0:
#                 distance += self.weights[i] * directDistance(self.dataList[target][p], self.dataList[source][p])
#             else:
#                 if not self.dataList[target][p] or not self.dataList[source][p]:
#                     index += 1
#                     distance += self.weights[i] * directDistance(self.dataList[target][p], self.dataList[source][p])
#                 else:
#                     words_list_target = self.allSemanticWords[target][index]
#                     words_list_source = self.allSemanticWords[source][index]
#                     target_chinese = words_list_target[0]
#                     target_english = words_list_target[1]
#                     source_chinese = words_list_source[0]
#                     source_english = words_list_source[1]
#                     index += 1
#                     # distance += self.weights[i] * self.phraseSimilarity(self.model, words_list_target, words_list_source)
#
#                     if len(target_english) == 0:
#                         distance += self.weights[i] * self.phraseSimilarity(self.model, target_chinese, source_chinese)
#                     elif len(source_english) > 0:
#                         sim_english = self.nlp(target_english).similarity(self.nlp(source_english))
#                         distance += 0.5 * (self.weights[i] * self.phraseSimilarity(self.model, target_chinese,
#                                                                                    source_chinese)) + 0.5 * sim_english
#         return distance
#
#     # def getDistanceMatrix_parallel(self, i):
#     #     # print('current process id:',os.getpid())
#     #     print('i:', i)
#     #     dataListLen = len(self.dataList)
#     #     # print('dataListLen:',dataListLen)
#     #     for j in range(i, dataListLen):
#     #         if i == j:
#     #             self.matrix[i, j] = 0
#     #         else:
#     #             self.matrix[i, j] = self.distance(i, j)
#     #             self.matrix[j, i] = self.matrix[i, j]
#     #     # print('cls:',cls.matrix)
#
#     def getDistanceMatrix_parallel(self,i):
#         print('i:',i)
#         dataListLen = len(self.dataList)
#         arr = np.zeros(dataListLen)
#         for j in range(i,dataListLen):
#             if i == j:
#                 arr[i] = 0
#             else:
#                 arr[j] = self.distance(i,j)
#         return {i:arr}
#
#
#
#
#     def say(self, msg):
#         print('msg:', msg)
#
#     def getDistanceMatrix(self):
#         self.allSemanticWords = self.getPrePhrase()
#         dataListLen = len(self.dataList)
#         distanceMatrix = np.zeros((dataListLen, dataListLen))
#         for i in range(dataListLen):
#             print('i:', i)
#             t0 = time.time()
#             for j in range(i, dataListLen):
#                 if i == j:
#                     distanceMatrix[i, j] = 0
#                 else:
#                     # distanceMatrix[i,j] = self.distance(self.dataList[i],self.dataList[j])
#                     # t0 = time.time()
#                     distanceMatrix[i, j] = self.distance(i, j)
#                     distanceMatrix[j, i] = distanceMatrix[i, j]
#                     # t1 = time.time()
#                     # print('time consumed:',t1-t0)
#             t1 = time.time()
#             print('time consumed:', t1 - t0)
#         np.save('distanceMatrix.npy', distanceMatrix)
#         return distanceMatrix
#
#
#     def parallel(self):
#         self.allSemanticWords = self.getPrePhrase()
#         cores = multiprocessing.cpu_count()
#         pool = Pool(processes=cores)
#         result = pool.map(self.getDistanceMatrix_parallel,range(len(self.dataList)))
#         # res = [pool.apply_async(target=self.getDistanceMatrix_parallel,(i,)) for i in range(len(self.dataList))]
#         pool.close()
#         pool.join()
#         with open('alias.pkl','wb') as f:
#             pickle.dump(result,f)
#
#
#     # def parallel(self):
#     #     self.allSemanticWords = self.getPrePhrase()
#     #     dataListLen = len(self.dataList)
#     #     # self.matrix = np.zeros((dataListLen,dataListLen))
#     #     cores = multiprocessing.cpu_count()
#     #     pool = Pool(processes=cores)
#     #     # pool.map(self.getDistanceMatrix_parallel,args=(i,))
#     #     # pool.map(self.say,[1,2,3])
#     #     # getDistanceMatrix_parallel_ = functools.partial(getDistanceMatrix_parallel,dataList=self.dataList,matrix=self.matrix,\
#     #     #                                                 distance=self.distance)
#     #     pool.map(self.getDistanceMatrix_parallel, range(dataListLen))
#     #     pool.close()
#     #     pool.join()
#     #     np.save('alias.npy',self.matrix)
#
#
# if __name__ == '__main__':
#     string = '/Users/tian/Downloads/drs_element.json'
#     sim = DBSCANALG(string, 'resource/sgns.baidubaike.bigram-char', 'resource/stopword_china.txt')
#     print(1)
#     sim.parallel()
#     # print(1)
#     # pool = Pool(4)
#     # dataListLen = len(sim.dataList)
#     # matrxi = np.zeros((dataListLen,dataListLen))
#     # pool.apply_async(sim.getDistanceMatrix_parallel,args=(0,500,matrxi))
#     # # pool.apply_async(sim.getDistanceMatrix_parallel, args=(500, 1000,matrxi))
#     # # pool.apply_async(sim.getDistanceMatrix_parallel, args=(1000, 1500,matrxi))
#     # # pool.apply_async(sim.getDistanceMatrix_parallel, args=(1500, 2000,matrxi))
#     # distance = sim.getDistanceMatrix()
#     print(2)
