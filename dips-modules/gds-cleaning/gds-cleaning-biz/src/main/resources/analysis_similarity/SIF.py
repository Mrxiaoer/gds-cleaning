import math

import numpy as np
import json
import gensim
import os
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

from sklearn.decomposition import PCA


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

def preCutPhrase(data):
    if not data:
        return None
    preList = [w for w in jieba.cut(data)]
    return preList




def getPrePhrase(dataList,attibute):
        allSemanticWords = [preCutPhrase(data[attibute]) for data in dataList]
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

#TODO 自己训练一个词向量并顺便统计词频
def get_word_frequency(word_text):
    return 0.0001


def sentence_to_vec(sentence_list,embedding_size,model,a=1e-3):
    sentence_set = []
    for sentence in sentence_list:
        vs = np.zeros(embedding_size)
        sentence_length = len(sentence)
        for word in sentence:
            if word in model:
                a_value = a/(a + get_word_frequency(word))
                vs = np.add(vs,np.multiply(a_value,model[word]))
        vs = np.divide(vs,sentence_length)
        sentence_set.append(vs)

    pca = PCA()
    pca.fit(np.array(sentence_set))
    u = pca.components_[0]
    u = np.multiply(u,np.transpose(u))

    if len(u) < embedding_size:
        for i in range(embedding_size - len(u)):
            u = np.append(u,0)

    sentence_vecs = []
    for vs in sentence_set:
        sub = np.multiply(u,vs)
        sentence_vecs.append(np.subtract(vs,sub))
    return sentence_vecs

def l2_dist(v1, v2):
    sum = 0.0
    if len(v1) == len(v2):
        for i in range(len(v1)):
            delta = v1[i] - v2[i]
            sum += delta * delta
        return math.sqrt(sum)

if __name__ == '__main__':
    # jsonPath = '/usr/local/data-cleaning/drs_element.json'
    # jsondata = JsonData(jsonPath)
    # threshold = jsondata.threshold
    # params = jsondata.params
    # dataList = jsondata.dataList
    # weights = jsondata.weights
    # approximates = jsondata.approximates
    # attribute = 'name_cn'
    # model_path = 'resource/sgns.baidubaike.bigram-char'
    # # print('path:',os.path.abspath(model_path))
    # allSemanticWords = getPrePhrase(dataList,attribute)
    # # model = load_word_embedding(os.path.abspath(model_path))
    with open('model.pkl','rb') as f:
        model = pickle.load(f)
    # # print(result)
    # # print(len(result))
    # sentence_vecs = sentence_to_vec(allSemanticWords,100,model)
    # np.save('sentence_vec.npy',sentence_vecs)
    data = np.load('sentence_vec.npy')
    print(1/(1+l2_dist(data[52],data[53])))
    print(1/(1+model.wmdistance(['转出','单位'],['转出','单位','名称'])))
    print(model.similarity('腾讯','网易'))


















