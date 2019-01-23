import numpy as np
import json
import gensim
import os
import pickle
import sys
import enchant
import spacy
import jieba
import time
from multiprocessing import Pool
import multiprocessing
from itertools import repeat
# from pathos.multiprocessing import ProcessingPool as Pool
import pandas as pd
from scipy.spatial import distance
import scipy.cluster.hierarchy as hcluster
import scipy.spatial.distance as ssd

import functools
# from quickAnalyse import JsonData,load_word_embedding,get_stopword_path,calcVector,adjustDataList,getMostFreq,prepare,getDataDict
from util import *


#对一个句子进行切分，得到中英文
# def preCutPhrase(data,stopwords,model,vector_length=100,d_=enchant.Dict('en_US')):
#     if not data:
#         return None
#     preList = [w for w in jieba.cut(data) if w not in stopwords]
#     preList_chinese = [w for w in preList if not d_.check(w)]
#     preList_english = ''.join([w for w in preList if d_.check(w)])
#     chinese_vector = calcVector(preList_chinese,model,vector_length)
#     return (chinese_vector,preList_english)
def preCutPhrase(data,stopwords,model,vector_length=100,d_=enchant.Dict('en_US')):
    if not data:
        return None
    preList = [w for w in jieba.cut(data) if w not in stopwords]
    preList_chinese = [w for w in preList if not d_.check(w) or w.isdigit()]
    preList_english = ''.join([w for w in preList if d_.check(w) and not w.isdigit()])
    chinese_vector = calcVector(preList_chinese,model,vector_length)
    return (chinese_vector,preList_english)

#对所有句子进行切分，得到一个[[数据1的短语1(中文向量，英文字符串)，数据1的短语2.....]]
def getPrePhrase(approximates,dataList,params,stopwords,model):
        allSemanticWords = []
        nonZeroIndex = np.nonzero(np.array(approximates) > 0)[0]
        if nonZeroIndex.size == 0:
            return None
        else:
            for d in dataList:
                record = []
                for i in nonZeroIndex:
                    record.append(preCutPhrase(d[params[i]],stopwords,model))
                allSemanticWords.append(record)
        return allSemanticWords


# 计算target和source两条数据之间的距离
# def _distance(target, source,params,approximates,weights,dataList,allSemanticWords,nlp=spacy.load('en_core_web_md')):
#     def directDistance(a, b):
#         return 1 if a == b else 0
#     sim = 0.0
#     index = 0
#     for i, p in enumerate(params):
#         if approximates[i] == 0:
#             sim += weights[i] * directDistance(dataList[target][p], dataList[source][p])
#         else:
#             if not dataList[target][p] or not dataList[source][p]:
#                 index += 1
#                 sim += weights[i] * directDistance(dataList[target][p], dataList[source][p])
#             else:
#                 words_list_target = allSemanticWords[target][index]
#                 words_list_source = allSemanticWords[source][index]
#                 target_chinese = words_list_target[0]

#                 target_english = words_list_target[1]
#                 source_chinese = words_list_source[0]
#                 source_english = words_list_source[1]
#                 index += 1
#                 # distance += self.weights[i] * self.phraseSimilarity(self.model, words_list_target, words_list_source)

#                 if len(target_english) == 0:
#                     sim += weights[i] * (1 - distance.cosine(target_chinese,source_chinese))
#                 elif len(source_english) > 0:
#                     sim_english = nlp(target_english).similarity(nlp(source_english))
#                     sim += 0.5 * (weights[i] * (1 - distance.cosine(target_chinese,source_chinese))) + 0.5 * weights[i]*sim_english
#     return 1-sim

def _distance(target, source,params,approximates,weights,dataList,allSemanticWords,nlp=spacy.load('en_core_web_md')):
    def directDistance(a, b):
        return 1 if a == b else 0
    sim = 0.0
    index = 0
    for i, p in enumerate(params):
        if approximates[i] == 0:
            sim += weights[i] * directDistance(dataList[target][p], dataList[source][p])
        else:
            if not dataList[target][p] or not dataList[source][p]:
                index += 1
                sim += weights[i] * directDistance(dataList[target][p], dataList[source][p])
            else:
                words_list_target = allSemanticWords[target][index]
                words_list_source = allSemanticWords[source][index]
                target_chinese = words_list_target[0]

                target_english = words_list_target[1]
                source_chinese = words_list_source[0]
                source_english = words_list_source[1]
                index += 1

                # if len(target_english) == 0:
                #     sim += weights[i] * (1 - distance.cosine(target_chinese,source_chinese))
                # elif len(source_english) > 0:
                #     sim_english = nlp(target_english).similarity(nlp(source_english))
                #     sim += 0.5 * (weights[i] * (1 - distance.cosine(target_chinese,source_chinese))) + 0.5 * weights[i]*sim_english
                if not target_english and not source_english:
                    sim += weights[i] * (1 - distance.cosine(target_chinese, source_chinese))
                else:
                    sim_english = nlp(target_english).similarity(nlp(source_english))
                    sim += 0.8 * (weights[i] * (1 - distance.cosine(target_chinese,source_chinese))) + 0.2 * weights[i]*sim_english
    return 1-sim


#计算target i和所有数据之间的距离
def getDistanceMatrix_parallel(i,params,approximates,weights,dataList,allSemanticWords):
    '''
    :param i:  一个数据target
    :param params:
    :param approximates:
    :param weights:
    :param dataList: 一行数据
    :param allSemanticWords:
    :param model:
    :return:
    '''
    dataListLen = len(dataList)
    arr = np.zeros(dataListLen)
    for j in range(i,dataListLen):
        if i == j:
            arr[i] = 0
        else:
            arr[j] = _distance(i,j,params,approximates,weights,dataList,allSemanticWords)
    return {'data':arr,'index':i}
    # return {i:arr}


def parallel(weights,approximates,dataList,params,allSemanticWords):
    print('parallel starts')
    # if not os.path.exists('model.pkl'):
    #     model = load_word_embedding(modelPath)
    # else:
    #     with open('model.pkl', 'rb') as f:
    #         model = pickle.load(f)
    cores = multiprocessing.cpu_count()
    pool = Pool(processes=cores)
    # getDistanceMatrix_parallel_ = functools.partial(getDistanceMatrix_parallel, params=params,approximates=approximates,\
    #                                                 weights=weights,dataList=dataList,allSemanticWords=allSemanticWords,\
    #                                                 model=model,nlp=nlp)
    result = pool.starmap(getDistanceMatrix_parallel,zip(range(len(dataList)),repeat(params),repeat(approximates),\
                                                         repeat(weights),repeat(dataList),repeat(allSemanticWords)))
    # res = [pool.apply_async(target=self.getDistanceMatrix_parallel,(i,)) for i in range(len(self.dataList))]
    pool.close()
    pool.join()

    return result

    # with open('alias.pkl','wb') as f:
    #     pickle.dump(result,f)


#得到相似度（距离）矩阵
def aggregate(weights,approximates,dataList,params,allSemanticWords):
    t0 = time.time()
    result = parallel(weights,approximates,dataList,params,allSemanticWords)
    t1 = time.time()
    print('time consumed:',t1-t0)
    # result,dataList = parallel('resource/sgns.baidubaike.bigram-char', 'resource/stopword_china.txt')
    # with open('alias.pkl','rb') as f:
    #     result = pickle.load(f)
    result = sorted(result,key=lambda x:x['index'])
    # with open('parallel_result.pkl','wb') as f:
    #     pickle.dump(result,f)

    # matrix = result[0][0]
    # for i,data in enumerate(result):
    #     if i == 0:
    #         continue
    #     matrix = np.vstack((matrix,data[i]))

    matrix = []
    for i,data in enumerate(result):
        matrix.append(data['data'])
    matrix = np.array(matrix).T +np.array(matrix)
    return matrix

def pd_matrix(matrix,index2id,dataListLen):
    indexArray = np.arange(dataListLen)
    columns = [index2id[index] for index in indexArray]
    return pd.DataFrame(matrix,columns=columns,index=columns)


def cluster(pd_matrix,threshold):
    matrix = pd_matrix.values
    matrix_condense = ssd.squareform(matrix) #压缩矩阵
    z = hcluster.linkage(matrix_condense, 'complete') #层次聚类
    cluster = hcluster.fcluster(z, 1-threshold, criterion='distance')
    # return cluster,pd_matrix.index.values
    return cluster


#输出规定格式的结果
def output(cluster,pd_matrix):
    # pd_index = pd_matrix.index.values.astype(np.int32)
    pd_index = list(map(lambda x:int(x),pd_matrix.index.values))

    cluster_dict = {}
    for i, c in enumerate(cluster):
        if c not in cluster_dict:
            cluster_dict[c] = [pd_index[i]]
        else:
            cluster_dict[c].append(pd_index[i])
    #过滤掉clusters中个数等于1的cluster
    cluster_result = dict(filter(lambda x: len(x[1]) > 1, cluster_dict.items()))
    largest_id_list = [getMostFreq(data[1]) for data in cluster_result.items()]

    # old version
    # result = {}
    # for i,j in zip(largest_id_list,list(cluster_result.items())):
    #     result[i] = j[1]
    # return result

    # new version
    output = []
    result_local = {}
    for i, j in zip(largest_id_list, list(cluster_result.items())):
        result_local['id'] = i
        result_local['group'] = similarityList(j[1],pd_matrix,i)
        output.append(result_local)
        result_local = {}
    return json.dumps(output)

#辅助函数
def similarityList(oneClusterList,pd_matrix,targetIndex):
    returnList = []
    local_dict = {}
    for index in oneClusterList:
        local_dict['id'] = index
        local_dict['similarity'] = 1 - pd_matrix.loc[targetIndex,index]
        returnList.append(local_dict)
        local_dict = {}
    return returnList



def reAnalyse(jsonPath,pd_matrix,fileName,stopword_path,model_path):
    '''
    add column to dataframe b: b = b.assign(e=pd.Series(np.random.randn(2)).values)
    add row to dataframe b: b.loc['e'] = np.random.randn(3)
    :param jsonPath:
    :return:
    '''
    jsonData = JsonData(jsonPath)
    dataList = jsonData.dataList
    threshold = jsonData.threshold
    params = jsonData.params
    weights = jsonData.weights
    approximates = jsonData.approximates
    needReAnalyse = jsonData.needReAnalyse

    stopwords = get_stopword_path(stopword_path)
    model = load_word_embedding(model_path)

    if needReAnalyse == 1:
        # 根据一系列数据重新计算矩阵，进行聚类，返回结果
        return allProcess(jsonPath,fileName,stopword_path,model_path)

    id_list_new = [d['id'] for d in dataList]
    id_list_old = pd_matrix.index.values.tolist()
    id_added = list(set(id_list_new).difference(set(id_list_old)))
    id_intersection = list(set(id_list_new).intersection(set(id_list_old)))
    #没有新数据加入
    if len(id_added) == 0:
        pd_matrix_new = pd_matrix.loc[id_list_new,id_list_new]
        new_cluster = cluster(pd_matrix_new,threshold)
        new_output = output(new_cluster,pd_matrix_new)
        return new_output
    #有新数据加入
    else:
        allSemanticWords = getPrePhrase(approximates, dataList, params, stopwords, model)
        for id_new in id_added:
            id_list_old = pd_matrix.index.values.tolist()
            result_distance = np.zeros([len(id_list_old)])
            # index_insert = np.nonzero(id_list_old == id_new)[0][0]#得到插入的索引
            # for i_,item in enumerate(id_list_old):
            #     if item in id_list_new:
            #         result_distance[i_] = _distance()
            item_index = id_list_new.index(id_new)
            for i_,item in enumerate(id_intersection):
                result_distance[id_list_old.index(item)]=_distance(item_index,id_list_new.index(item),params,approximates,weights,dataList,allSemanticWords)
            # pd_matrix = pd_matrix.assign(id_new=result_distance) #增加一列
            pd_matrix.loc[:,id_new] = result_distance
            pd_matrix.loc[id_new] = np.append(result_distance,0)
            id_intersection.append(id_new)


        pd_matrix.to_pickle('/usr/local/data-cleaning/'+fileName+'.pkl')
        pd_matrix_new = pd_matrix.loc[id_list_new,id_list_new]
        new_cluster = cluster(pd_matrix_new,threshold)
        new_output = output(new_cluster,pd_matrix_new)
        return new_output

def allProcess(json_path,fileName,stopwords_path,model_path):
    jsonData = JsonData(json_path)
    dataList = jsonData.dataList
    dataListLen = len(dataList)
    threshold = jsonData.threshold
    params = jsonData.params
    weights = jsonData.weights
    approximates = jsonData.approximates
    index2id, id2index = getDataDict(dataList)
    stopwords = get_stopword_path(stopwords_path)
    model = load_word_embedding(model_path)

    allSemanticWords = getPrePhrase(approximates, dataList, params, stopwords, model)
    matrix = aggregate(weights, approximates, dataList, params, allSemanticWords)
    pd_m = pd_matrix(matrix,index2id,dataListLen)
    # data_store_path = '/usr/local/data-cleaning/'
    # if not os.path.exists(data_store_path):
    #     os.makedirs(data_store_path)
    pd_m.to_pickle('/usr/local/data-cleaning/'+fileName + '.pkl')
    clusters = cluster(pd_m,threshold)
    format_result = output(clusters, pd_m)
    return format_result


def summary(json_path):
    stopwords_path = '/usr/local/stopword_china.txt'
    #stopwords_path = 'resource/stopword_china.txt'
    #stopwords_path = os.path.abspath(stopwords_path)
    model_path = '/usr/local/zhwiki_embedding_t2s.model'
    #model_path = 'resource/zhwiki_embedding_t2s.model'
    #model_path = os.path.abspath(model_path)

    #在Linux和macos下
    if '/' in json_path:
        json_file_name = json_path[(json_path.rindex('/')+1):].split('.')[0]
    #在Windows下
    else:
        json_path = '%r'%json_path
        json_file_name = json_path[(json_path.rindex('/')+1):].split('.')[0]
    if not os.path.exists(json_file_name+'.pkl'):
        format_result = allProcess(json_path,json_file_name,stopwords_path,model_path)
        return format_result
    else:

        pd_matrix = pd.read_pickle('/usr/local/data-cleaning/'+json_file_name+'.pkl')
        return reAnalyse(json_path,pd_matrix,json_file_name,stopwords_path,model_path)



#print(summary('/usr/local/data-cleaning/1.json'))
print(summary(sys.argv[1]))



