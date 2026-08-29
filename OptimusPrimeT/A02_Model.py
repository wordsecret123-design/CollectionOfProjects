import numpy as np
import math
np.set_printoptions(threshold=np.inf)

class model:
 
    def __init__(self,*,modelFile = None,VocabSize = 4, widthOfEmbeddingMatrix = 20, NumberHeads = 4):
        self.__Ein = np.random.randn(VocabSize,widthOfEmbeddingMatrix)
        perHeadTokenSize = widthOfEmbeddingMatrix//NumberHeads
        self.__perHeadTokenWidth = perHeadTokenSize
        self.__WQ = np.zeros((NumberHeads,perHeadTokenSize,perHeadTokenSize))
        for i in range(NumberHeads):
            self.__WQ[i] = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WK = np.zeros((NumberHeads,perHeadTokenSize,perHeadTokenSize))
        for i in range(NumberHeads):
            self.__WK[i] = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__WV = np.zeros((NumberHeads,perHeadTokenSize,perHeadTokenSize))
        for i in range(NumberHeads):
            self.__WV[i] = np.random.randn(perHeadTokenSize,perHeadTokenSize)
        self.__Heads = np.zeros(NumberHeads, dtype=object)
        for i in range(NumberHeads):
            self.__Heads[i] = self.__Ein[0:(VocabSize),i*perHeadTokenSize:(i+1)*perHeadTokenSize]
        self.__VocabSize = VocabSize
        

    def trainModel(self,*,forwardPassedData=None,tokenListIDDict=None):
        sequenceFullLength = len(forwardPassedData)
        print(sequenceFullLength)
        for outputCount in range(sequenceFullLength):
            previousOutputsList = [token for token in forwardPassedData[0:outputCount+1]] 
            print("enters")
            (povD, wkD, hvDQ, wqD, povTD, 
             hvDQK, hvDSoftmax, hvdScaledDown,
             ) = self.doAttention(previousOutputsList=previousOutputsList,
                                tokenListIDDict=tokenListIDDict, HeadEmbeddingMatrix=self.__Heads[0],
                                outputCount=outputCount, WQ=self.__WQ[0], WK=self.__WK[0], WV = None)
            
    
    #DONT FORGET TO GET DERIVATIVES FOR THE SOFTMAX OF HIDDEN VECTOR
    def doAttention(self,*,previousOutputsList,tokenListIDDict, 
                    HeadEmbeddingMatrix = None, outputCount, WQ,
                    WK, WV):
        indices = [tokenListIDDict[token] for token in previousOutputsList]
        previousOutputsVectors = HeadEmbeddingMatrix[indices]
        PE = self.positional_encoding(outputCount+1,self.__perHeadTokenWidth)
        previousOutputsVectors = previousOutputsVectors + PE
        previousOutputsK = previousOutputsVectors @ WK
        # Derivatives in relation to the values of previousOutputsVectors:
        # The sum of the first row of WK is the derivative of all the values
        # in the first column of previousOutputsVectors. 
        # The sum of the second row is to the second column, and so on and so forth.
        # Derivatives in relation to WK is column to row.
        povDerivatives = np.sum(WK,axis=1)
        wkDerivatives = np.sum(previousOutputsVectors,axis=0)
        hiddenVector = HeadEmbeddingMatrix[tokenListIDDict[previousOutputsList[outputCount]]]
        print(outputCount)
        #Derivatives in relation to values of hiddenVectorQ <- WQ is row to column.
        #Derivatives in relation to WQ is column to row.
        #Derivatives in relation to hiddenVectorQK <- POK.T row to column 
        hvDerivativesQ = np.sum(WQ,axis=1)
        wqDerivatives = hiddenVector
        hiddenVector = hiddenVector @ WQ
        hvDerivativesQK = hiddenVector
        POV_TDerivative = np.sum(previousOutputsK.T)
        hiddenVector = hiddenVector @ previousOutputsK.T
        hiddenVector = hiddenVector/math.sqrt(self.__perHeadTokenWidth)
        hvDScaledDown = math.sqrt(self.__perHeadTokenWidth)
        hiddenVector = hiddenVector - hiddenVector.max()
        hvDSoftMax = ((np.power(np.e,hiddenVector)/np.sum(np.power(np.e,hiddenVector)))*
                      (1-(np.power(np.e,hiddenVector)/np.sum(np.power(np.e,hiddenVector)))))
        hiddenVector = np.power(np.e,hiddenVector) / np.sum(np.power(np.e,hiddenVector))
        print(np.sum(hiddenVector))
        return (povDerivatives, wkDerivatives, hvDerivativesQ,
                wqDerivatives, POV_TDerivative, hvDerivativesQK,
                hvDSoftMax, hvDScaledDown) 
        
    def positional_encoding(self, sequence_length, dk):
        pos = np.arange(sequence_length)[:,np.newaxis]
        i = np.arange(0,dk,2)
        angle = pos / np.power(10000,i/dk) 
        PE = np.zeros((sequence_length,dk))
        
        PE[:,0::2] = np.sin(angle)
        PE[:,1::2] = np.cos(angle)
        return PE
                 
    def getEin(self):
        return self.__Ein
    
    def getEmbeddingHead(self,):
        return self.__Heads
    
    def getWQHead(self,*,HeadIndex = 1):
        return self.__WQ
    
    def getWKHead(self,*,HeadIndex = 1):
        return self.__WK
    
    def getWVHead(self,*,HeadIndex = 1):
        return self.__WV
                    
    
        
# Test Cases:
# A = np.array([[1,2],[3,4]])
# B = np.array([[5,6],[7,8]])
# print(A)
# print(B)
# print(A @ B)
# print(np.sum(A,axis=0))