import os
import jieba
import sys
# from pathos.multiprocessing import ProcessingPool as Pool
from scipy.spatial import distance
import scipy.cluster.hierarchy as hcluster
import functools
from sklearn.cluster import DBSCAN
from util import *




def preCutPhrase(data,stopwords):
    if not data:
        return None
    preList = [w for w in jieba.cut(data) if w not in stopwords]
    return preList


#得到所有短语对应的向量
def adjustDataList(dataList,attribute,stopwords,model,vector_length=100):
    # data_simplified_list = {}
    vectors = []
    for i,d in enumerate(dataList):
        cutted_list = preCutPhrase(d[attribute],stopwords)
        vector = calcVector(cutted_list,model,vector_length)
        vectors.append(vector)
    return vectors

# def dbscan(dataList):
#     X = np.array(dataList)
#     clustering = DBSCAN(eps=0.35,min_samples=2,metric='cosine').fit(X)
#     return clustering.labels_



#得到权重最大的短语语义参数名
def getMainAttribute(weights,approximates,params):
    approx = np.nonzero(np.asarray(approximates))[0]
    weights_post = np.asarray(weights)[approx]
    max_index = np.argmax(weights_post)
    return params[approx[max_index]]



#准备工作，得到字典，停用词，词向量模型，短语对应的向量
def prepare(dataList,stopwords_path,model_path,attribute):
    index2id,id2index = getDataDict(dataList)
    stopwords = get_stopword_path(stopwords_path)
    model = load_word_embedding(model_path)
    vectors = adjustDataList(dataList, attribute, stopwords, model)
    return index2id,id2index,stopwords,model,vectors





def cluster(vectors,data_dict,threshold):
    z = hcluster.linkage(np.asarray(vectors), 'complete', metric='cosine')
    cluster = hcluster.fcluster(z, 1-threshold, criterion='distance')
    cluster_dict = {}
    for i,c in enumerate(cluster):
        if c not in cluster_dict:
            cluster_dict[c] = [data_dict[i]]
        else:
            cluster_dict[c].append(data_dict[i])
    cluster_result = dict(filter(lambda x: len(x[1]) > 1, cluster_dict.items()))
    largest_id_list = [getMostFreq(data[1]) for data in cluster_result.items()]

    # old version
    # result = {}
    # for i,j in zip(largest_id_list,list(cluster_result.items())):
    #     result[i] = j[1]
    # return result

    #new version
    result = []
    result_local = {}
    for i,j in zip(largest_id_list,list(cluster_result.items())):
        result_local['id'] = i
        result_local['group'] = j[1]
        result.append(result_local)
        result_local = {}
    return result


def adjustOutput(result,vectors,id2index):
    #old
    # similarity = {}
    # for k,v_list in result.items():
    #     for v in v_list:
    #         similarity[v] = 1-distance.cosine(vectors[id2index[k]],vectors[id2index[v]])
    #     result[k] = similarity
    #     similarity = {}

    #new
    output = []
    similarity = {}
    for v_list in result:
        group_list_local = []
        group_list = v_list['group']
        for v in group_list:
            dict_local = {}
            dict_local['id'] = v
            dict_local['similarity'] = 1 - distance.cosine(vectors[id2index[v_list['id']]],vectors[id2index[v]])
            group_list_local.append(dict_local)
            dict_local = {}
        similarity['id'] = v_list['id']
        similarity['group'] = group_list_local
        output.append(similarity)
        similarity = {}
    return output


def summary(json_path):
    stopwords_path = '/usr/local/stopword_china.txt'
    #stopwords_path = 'resource/stopword_china.txt'
    #stopwords_path = os.path.abspath(stopwords_path)
    model_path = '/usr/local/zhwiki_embedding_t2s.model'
    #model_path = 'resource/zhwiki_embedding_t2s.model'
    #model_path = os.path.abspath(model_path)
    jsonData = JsonData(json_path)
    dataList = jsonData.dataList
    threshold = jsonData.threshold
    params = jsonData.params
    weights = jsonData.weights
    approximates = jsonData.approximates
    attribute = getMainAttribute(weights, approximates, params)
    index2id, id2index, stopwords, model, vectors = prepare(dataList, stopwords_path, model_path, attribute)
    result = cluster(vectors, index2id, threshold)
    result = adjustOutput(result, vectors, id2index)
    return json.dumps(result)







#print(summary('/usr/local/data-cleaning/1.json'))
print(summary(sys.argv[1]))



