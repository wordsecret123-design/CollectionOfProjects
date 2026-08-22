#--------------
from time import sleep
import A02_Model as md
import numpy as np
#--------------


class tokenizer:
    __content = []
    __TokenList = []
    __TokenListIDsDict = {"\n":1}
    
    def __init__(self,filename: str):
        self.__content = self.SetContentToList(self.OpenFile(filename))
        self.__TokenList = self.SetTokenList(self.__content,tokenIDsDict = self.__TokenListIDsDict)
    
    def GetContent(self):
        return self.__content
    
    def GetTokenList(self):
        return self.__TokenList
    
    def GetTokenListIDsDict(self):
        return self.__TokenListIDsDict
    
    def OpenFile(self, filename: str):
        with open(filename,"r") as file:
            content = file.read()
        return content

    def SetContentToList(self, file):
        ContentList = []
        placeholderString = ""
        countNewLineOccur = 0
        for i, letter in enumerate(file):
            if (letter == " " or letter == "\n"):
                if (letter == "\n"):
                    countNewLineOccur = countNewLineOccur + 1
                else:
                    countNewLineOccur = 0
                if countNewLineOccur < 2:
                    ContentList.append(placeholderString)
                    placeholderString = " " if not countNewLineOccur else ""
            if letter == "\n":
                ContentList.append(letter)
            elif (i==0 and letter ==" "):
                placeholderString = " "
            else:
                placeholderString = (placeholderString + 
                                     (letter if letter!=" " else ""))
        return ContentList
                
    def SetTokenList(self, content: list[str],*,tokenIDsDict: dict[str,int] | None = None):
        tokenlist = ["\n"]
        placeholderString = ""
        wordNotInList = 0
        iterateID = 1
        for word in content:
            for word1 in tokenlist: 
                if word == word1:
                    wordNotInList = 0
                    break
                else:
                    wordNotInList = 1
            if wordNotInList:
                iterateID = iterateID + 1
                if tokenIDsDict:
                    tokenIDsDict[word] = iterateID
                tokenlist.append(word)
        return tokenlist

#Test Cases:  
# print(GetWordList(OpenFile("00 DataSet.txt")))
# print(OpenFile("00 DataSet.txt"))
# GetWordList(OpenFile("00 DataSet.txt"))
# print(ContentToList(OpenFile("00 DataSet.txt")))
# print(GetWordList(ContentToList(OpenFile("00 DataSet.txt"))))

tokens = tokenizer("00_DataSet2.txt")
modelA = md.model(tokenSize=len(tokens.GetTokenList()),widthOfEmbeddingMatrix=16)
modelA.trainModel(tokenListIDDict=tokens.GetTokenListIDsDict())
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

