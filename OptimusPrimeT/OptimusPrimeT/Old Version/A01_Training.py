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
modelA = md.model(tokenSize=tokens.GetVocabSize(),widthOfEmbeddingMatrix=16)
modelA.trainModel(tokenListIDDict=tokens.GetVocabDict())
print(tokens.GetContentList())
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

