import json
import gensim
import numpy as np
import jieba
import enchant
import spacy
import os
import sys
import pickle




class Similarity(object):
    def __init__(self,string,word_embedding_path,stopword_path):

        self.string = string                        #json string
        self.data = self.getJsonData(self.string)  #data dict
        self.threshold = self.data['threshold']    #0.6  threshold
        self.params = self.data['Params']            #all the item names for each item in data
        self.weights = self.data['weights']         #the weights for each item in data
        self.approximates = self.data['approximates']   #need to deal with the synonym or items in data or not
        
        self.standard = self.data['standard']           #the standard data to compare
        self.similarity = self.data['similarity']       #the synonyms user define
        self.datas = self.data['data']                  #the data need to be compared
        self.stopwords = self.load_stopwords(stopword_path)
        if not os.path.exists('model.pkl'):
            self.model = self.load_word_embedding(word_embedding_path)
        else:
            with open('model.pkl','rb') as f:
                self.model = pickle.load(f)

    def getJsonData(self,string):
        data = json.loads(string)
        # with open(string,'rb') as f:
        #     data = json.load(f)
        return data

    def load_stopwords(self,path):
        stopwords = []
        with open(path, 'r') as f:
            for line in f:
                stopwords.append(line)
        return stopwords

    def load_word_embedding(self,path):
        model = gensim.models.KeyedVectors.load_word2vec_format(path)
        return model


    def calculateSimilarity(self,model,target,source,default='chinese'):
        similarity = 1/(1+model.wmdistance(target, source))
        return similarity

    def calculateDataSimilarity(self):
        def compare(x,y):
            return 1 if x==y else 0

        result=[]
        approx = np.nonzero(np.array(self.approximates))[0]

        # # genetic method
        # for d in self.datas:
        #     dict_sim = {}
        #     dict_sim['id'] = d['id']
        #     sim = 0.0
        #     for i,param in enumerate(self.params):
        #         #不需要用同义词的情况
        #         if i not in approx:
        #             sim += self.weights[i] * compare(self.standard[param],d[param])
        #         else:
        #             target = [w for w in jieba.cut(d[param])]
        #             source = [w for w in jieba.cut(self.standard[param])]
        #             sim += self.weights[i] * self.calculateSimilarity(self.model,target,source)
        #
        #     dict_sim['similarity'] = sim
        #     result.append(dict_sim)

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




if __name__ == '__main__':
    string ='''
    {
    "threshold":0.6,
    "Params":[
        "length",
        "type",
        "nameEn",
        "nameCn"
    ],
    "weights":[
        0.1,
        0.2,
        0.3,
        0.4
    ],
    "approximates":[
        0,
        0,
        1,
        1
    ],
    "standard":{
        "length":10,
        "type":1,
        "nameEn":"mc",
        "nameCn":"名称"
    },
    "similarity":{
        "nameEn":{
            "xm":0.5,
            "mz":0.8
        },
        "nameCn":{
            "姓名":0.6,
            "名字":0.8
        }
    },
    "data":[
        {
            "id":1,
            "length":10,
            "type":1,
            "nameEn":"xm",
            "nameCn":"姓名"
        },
        {
            "id":2,
            "length":18,
            "type":2,
            "nameEn":"sfz",
            "nameCn":"身份证"
        },
        {
            "id":3,
            "length":1,
            "type":3,
            "nameEn":"sex",
            "nameCn":"性别"
        }
    ]
}'''
    sim = Similarity(string,'resource/sgns.baidubaike.bigram-char','resource/stopword_china.txt')
    result = sim.calculateDataSimilarity()
    print(result)

