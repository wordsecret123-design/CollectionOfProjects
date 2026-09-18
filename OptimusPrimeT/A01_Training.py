#--------------
from time import sleep
import A02_Model as md
from A03_Tokenizer import tokenizer 
import numpy as np
#--------------


#Test Cases:  
# print(GetWordList(OpenFile("00 DataSet.txt")))
# print(OpenFile("00 DataSet.txt"))
# GetWordList(OpenFile("00 DataSet.txt"))
# print(ContentToList(OpenFile("00 DataSet.txt")))
# print(GetWordList(ContentToList(OpenFile("00 DataSet.txt"))))

tokens = tokenizer("00_DataSet2.txt")
modelA = md.model(VocabSize=tokens.GetVocabSize(),widthOfEmbeddingMatrix=16,NumberHeads=4)


# previousOutputsVectors = modelA.trainModel(
#     forwardPassedData=tokens.GetContentList(),
#     tokenListIDDict=tokens.GetVocabDict()
# )
# PE = modelA.positional_encoding(len(tokens.GetContentList()),4)
# PE = np.concatenate((PE,PE),axis=1)
# PE = np.concatenate((PE,PE),axis=1)
# indices = [tokens.GetVocabDict()[token] for token in tokens.GetContentList()]
# previousOutputsVectors2 = modelA.getEin()[indices]
# previousOutputsVectors2 = previousOutputsVectors2 + PE

# print(previousOutputsVectors2)
# print("here")
# print(previousOutputsVectors)

# modelA.trainModel(forwardPassedData=tokens.GetContentList(),tokenListIDDict=tokens.GetVocabDict())
# print(modelA.getEin()[0])
# array1 = [x for i in range(tokens.GetVocabSize()) for obj in modelA.getEmbeddingHead() for x in obj[i].tolist()]
# array2 = [obj for i in range(tokens.GetVocabSize()) for obj in modelA.getEin()[i].tolist()]
# print(array1 == array2)
# print(tokens.GetContentList())
# previousOutputs = [" David", " Gardiner"]
# modelA = md.model(tokenSize=len(tokens.GetTokenList()),widthOfEmbeddingMatrix=4)
# ArrayRef = modelA.getEmbeddingHead()[[5,6]]
# ArrayTest = modelA.doAttention(previousOutputsList=previousOutputs,tokenListIDDict=tokens.GetTokenListIDsDict())
# print(tokens.GetTokenListIDsDict())
# print(tokens.GetTokenList())
# print (np.array_equal(ArrayRef,ArrayTest))


# print(tokens.GetTokenListIDsDict())
# modelA = md.model(tokenSize=5,widthOfEmbeddingMatrix=4)

# modelA.trainModel()
# print(modelA.getEmbeddingHead(HeadIndex=1))
# print()
# print(modelA.getWQHead(HeadIndex=1))
# print()
# print(modelA.getTokensQ(HeadIndex=1))
# print()
# print(modelA.getTokensK(HeadIndex=1))
# print()
# print(modelA.getTokensV(HeadIndex=1))

