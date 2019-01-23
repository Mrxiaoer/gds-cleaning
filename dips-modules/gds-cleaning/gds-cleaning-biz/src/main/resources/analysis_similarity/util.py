import gensim
import json
import numpy as np
import pickle,os
import bz2
import redis
from collections import Counter
from gensim.models.keyedvectors import KeyedVectors
general_dict_path = '/usr/local/data-cleaning/general_dict.pkl'

#装载词向量模型
def load_word_embedding(path):
    # model = gensim.models.Word2Vec.load(path)
    model = RedisKeyedVectore()
    return model

#得到停用词
def get_stopword_path(path):
    stopwords = []
    with open(path, 'rb') as f:
        for d in f:
            stopwords.append(d.rstrip().decode('utf-8'))
    # print(stopwords)
    return stopwords

# #计算短语对应的向量
# def calcVector(data,model,vector_length=100):
#     vector = np.zeros([vector_length])
#     num = 0
#     for d in data:
#         if d in model:
#             num += 1
#             vector = vector + model[d]
#     if num == 0:
#         return np.ones([vector_length])
#     else:
#         return vector/num

#计算短语对应的向量
def calcVector(data,model,vector_length=100):
    vector = np.zeros([vector_length])
    num = 0
    for d in data:
        if d in model:
            num += 1
            vector = vector + model[d]
        else:
            #对数字和姓名等处理方式一致，故写在一起
            if os.path.exists(general_dict_path):
                with open(general_dict_path,'rb') as f:
                    general_dict = pickle.load(f)
                    if d in general_dict:
                        vector = vector + general_dict[d]
                        if not d.isdigit():
                            num += 1
                    else:
                        if d.isdigit():
                            #general_dict[d] = np.random.randn(vector_length)
                            general_dict[d] = np.random.uniform(-0.005,0.005,vector_length)
                        else:
                            general_dict[d] = np.random.randn(vector_length)
                        vector = vector + general_dict[d]
                        if not d.isdigit():
                            num += 1
                        with open(general_dict_path,'wb') as f2:
                            pickle.dump(general_dict,f2)
            else:
                general_dict = {}
                if d.isdigit():
                    general_dict[d] = np.random.randn(vector_length) / 10
                else:
                    general_dict[d] = np.random.randn(vector_length)
                vector = vector + general_dict[d]
                if not d.isdigit():
                    num += 1
                with open(general_dict_path,'wb') as f2:
                    pickle.dump(general_dict,f2)

    if num == 0:
        return np.ones([vector_length])
        # return np.random.randn(vector_length)
    else:
        return vector/num

#得到一个聚类中出现次数最多的词
def getMostFreq(data):
    word_count = Counter(data)
    return word_count.most_common(1)[0][0]


#得到dataList里所有索引对应的id的字典
def getDataDict(dataList):
    index2id = {}
    id2index = {}
    for i,data in enumerate(dataList):
        index2id[i] = data['id']
        id2index[data['id']] = i
    return index2id,id2index


class JsonData(object):
    def __init__(self,jsonPath):
        self.path = jsonPath
        self._data = self.getJson()
        self._threshold = self._data['threshold']
        self._params = self._data['params']
        self._weights = self._data['weights']
        self._approximates = self._data['approximates']
        self._dataList = self._data['data']
        self._needReAnalyse = self._data.get('needReanalysis',0)

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

    @property
    def needReAnalyse(self):
        return self._needReAnalyse

class RedisKeyedVectore(KeyedVectors):
    def __init__(self):
        self.rs = redis.StrictRedis(host='localhost',port=6379)

    def word_vec(self, word):
        try:
            return pickle.loads(bz2.decompress(self.rs.get(word)))
        except TypeError:
            return None

    @classmethod
    def load_word2vec_format(cls, **kwargs):
        raise NotImplementedError("You can't load a word model that way. It needs to pre-loaded into redis")

    def save(self, *args, **kwargs):
        raise NotImplementedError("You can't write back to Redis that way.")

    def save_word2vec_format(self, **kwargs):
        raise NotImplementedError("You can't write back to Redis that way.")

    def __getitem__(self, word):
        if isinstance(word,str):
            return self.word_vec(word)

    def __contains__(self, word):
        return self.rs.exists(word)
