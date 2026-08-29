import numpy as np
import math
np.set_printoptions(threshold=np.inf)

class model:
    __perHeadTokenWidth = 0 
    __tokenSize = 0
    __Ein = np.array(0)
    __Head1 = np.array(0)
    __Head2 = np.array(0)
    __Head3 = np.array(0)
    __Head4 = np.array(0)
    __WQ1 = np.array(0)
    __WQ2 = np.array(0)
    __WQ3 = np.array(0)
    __WQ4 = np.array(0)
    __WK1 = np.array(0)
    __WK2 = np.array(0)
    __WK3 = np.array(0)
    __WK4 = np.array(0)
    __WV1 = np.array(0)
    __WV2 = np.array(0)
    __WV3 = np.array(0)
    __WV4 = np.array(0)
    
    def __init__(self,*,modelFile = None,tokenSize = 4, widthOfEmbeddingMatrix = 20):
        self.__Ein = np.random.randn(tokenSize+2,widthOfEmbeddingMatrix)
        perHeadTokenSize = widthOfEmbeddingMatrix//4
        self.__perHeadTokenWidth = perHeadTokenSize
        self.__WQ1 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WQ2 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WQ3 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WQ4 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WK1 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WK2 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WK3 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WK4 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WV1 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WV2 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WV3 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WV4 = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__Head1 = self.__Ein[0:(tokenSize+2), 0:perHeadTokenSize]
        self.__Head2 = self.__Ein[0:(tokenSize+2), perHeadTokenSize:2*perHeadTokenSize]
        self.__Head3 = self.__Ein[0:(tokenSize+2), 2*perHeadTokenSize:3*perHeadTokenSize]
        self.__Head4 = self.__Ein[0:(tokenSize+2), 3*perHeadTokenSize:4*perHeadTokenSize]
        self.__tokenSize = tokenSize

    def trainModel(self,*,forwardPassedData=None,tokenListIDDict=None):
        previousOutputsList = ["▁am", "▁batang", "▁Rommel"]
        povD = np.array(0)
        wkD = np.array(0)
        hvD = np.array(0)
        wqD = np.array(0)
        povD, wkD, hvD, wqD = self.doAttention(previousOutputsList=previousOutputsList,
                              tokenListIDDict=tokenListIDDict, HeadEmbeddingMatrix=self.__Head1,
                              outputCount=2, WQ=self.__WQ1, WK=self.__WK1, WV = None)
        print(povD) 
        print(wkD) 
        print(hvD) 
        print(wqD) 
    #DONT FORGET TO ADD POSITIONAL ENCODING 
    def doAttention(self,*,previousOutputsList,tokenListIDDict, 
                    HeadEmbeddingMatrix = None, outputCount, WQ,
                    WK, WV):
        indices = [tokenListIDDict[token] for token in previousOutputsList]
        previousOutputsVectors = HeadEmbeddingMatrix[indices]
        previousOutputsK = previousOutputsVectors @ WK
        # Derivatives in relation to the values of previousOutputsVectors:
        # The sum of the first row of WK is the derivative of all the values
        # in the first column of previousOutputsVectors. 
        # The sum of the second row is to the second column, and so on and so forth.
        # Derivatives in relation to WK is column to row.
        povDerivatives = np.sum(WK,axis=1)
        wkDerivatives = np.sum(previousOutputsVectors,axis=0)
        hiddenVector = HeadEmbeddingMatrix[tokenListIDDict[previousOutputsList[outputCount]]]
        #Derivatives in relation to values of hiddenVector is row to column.
        #Derivatives in relation to WQ is column to row.
        hvDerivatives = np.sum(WQ,axis=1)
        wqDerivatives = hiddenVector
        hiddenVector = hiddenVector @ WQ
        hiddenVector = hiddenVector @ previousOutputsK.T
        hiddenVector = hiddenVector/math.sqrt(self.__perHeadTokenWidth)
        return povDerivatives, wkDerivatives, hvDerivatives, wqDerivatives 
        
        
    
               
                       
    def getEin(self):
        return self.__Ein
    
    def getEmbeddingHead(self,*,HeadIndex = 1):
        if HeadIndex:
            return self.__Head1
        elif HeadIndex == 2:
            return self.__Head2
        elif HeadIndex == 3:
            return self.__Head3
        else:
            return self.__Head4
    
    def getWQHead(self,*,HeadIndex = 1):
        if HeadIndex:
            return self.__WQ1
        elif HeadIndex == 2:
            return self.__WQ2
        elif HeadIndex == 3:
            return self.__WQ3
        else:
            return self.__WQ4
    
    def getWKHead(self,*,HeadIndex = 1):
        if HeadIndex:
            return self.__WK1
        elif HeadIndex == 2:
            return self.__WK2
        elif HeadIndex == 3:
            return self.__WK3
        else:
            return self.__WK4
    
    def getWVHead(self,*,HeadIndex = 1):
        if HeadIndex:
            return self.__WV1
        elif HeadIndex == 2:
            return self.__WV2
        elif HeadIndex == 3:
            return self.__WV3
        else:
            return self.__WV4
                    
    
        
# Test Cases:
# A = np.array([[1,2],[3,4]])
# B = np.array([[5,6],[7,8]])
# print(A)
# print(B)
# print(A @ B)
# print(np.sum(A,axis=0))