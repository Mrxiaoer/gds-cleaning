import json
import pandas as pd
import sys

def loadJson(jsonData):
    return json.loads(jsonData)

def loadMatrix(pd_path):
    pd_matrix = pd.read_pickle(pd_path)
    return pd_matrix

def summary(jsonData):
    try:
        dictData = loadJson(jsonData)
        pd_matrix_filename = dictData['fileId'] + '.pkl'
        centerId = dictData['centerId']
        threshold = dictData['threshold']
        pd_matrix = loadMatrix(pd_matrix_filename)
        pd_series = pd_matrix.loc[centerId]
        pd_series_thres = pd_series[pd_series<1-threshold]
        output = {'id':centerId,'group':[]}
        local_dict = {}
        for i in pd_series_thres.index:
            local_dict['id'] = i
            local_dict['similarity'] = 1-pd_series_thres[i]
            output['group'].append(local_dict)
            local_dict = {}
        return [output]
    except Exception as e:
        print('failure')
        print(e)





# jsonData = '''
# {
# 	"fileId": "12",
# 	"centerId": 28,
# 	"threshold": 0.6
# }'''
# print(summary(jsonData))
print(summary(sys.argv[1]))