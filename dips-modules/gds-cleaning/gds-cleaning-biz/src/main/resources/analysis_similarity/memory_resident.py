import pickle
import redis
from gensim.models.keyedvectors import KeyedVectors
from gensim.models.word2vec import Word2Vec
import bz2

def load_word2vec_in_redis():
    rs = redis.StrictRedis(host='localhost',port=6379)
    model = KeyedVectors.load('/usr/local/data-cleaning/analysis_similarity/resource/zhwiki_embedding_t2s.model')
    for word in model.wv.vocab:
        rs.set(word,bz2.compress(pickle.dumps(model.wv[word])))


load_word2vec_in_redis()
