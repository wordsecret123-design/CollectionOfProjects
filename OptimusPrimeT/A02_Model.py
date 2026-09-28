import numpy as np
import math
np.set_printoptions(threshold=np.inf)

class model:
   
    def __init__(self,*,modelFile = None,VocabSize = 4, widthOfEmbeddingMatrix = 16, NumberHeads = 4):
        self.__Ein = np.random.randn(VocabSize,widthOfEmbeddingMatrix)
        perHeadTokenSize = widthOfEmbeddingMatrix//NumberHeads
        self.__perHeadTokenWidth = perHeadTokenSize
        self.__numberHeads = NumberHeads
        self.__widthOfEmbeddingMatrix = widthOfEmbeddingMatrix
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
        self.__WO = np.random.randn(widthOfEmbeddingMatrix,widthOfEmbeddingMatrix)
        self.__learnedScaleLN = np.random.randn(widthOfEmbeddingMatrix)
        self.__learnedBiasLN = np.random.randn(widthOfEmbeddingMatrix)
        self.__learnedBias1FFN = np.random.randn(widthOfEmbeddingMatrix*4)
        self.__learnedBias2FFN = np.random.randn(widthOfEmbeddingMatrix)
        self.__WZ1 = np.random.randn(widthOfEmbeddingMatrix,widthOfEmbeddingMatrix*4)
        self.__WZ2 = np.random.randn(widthOfEmbeddingMatrix*4,widthOfEmbeddingMatrix)
        
    def trainModel(self,*,forwardPassedData=None,tokenListIDDict=None):
        sequenceFullLength = len(forwardPassedData)
        previousOutputsVectors, hiddenVector = self.doMultiHeadAttention(
            previousOutputsList=forwardPassedData,
            tokenListIDDict=tokenListIDDict
        )
        hiddenVector = self.doOutputProjection(hiddenVector)
        hiddenVector = self.doResidualAndLayerNorm(hiddenVector,previousOutputsVectors)
        hiddenVector = self.doFeedForwardNetwork(hiddenVector) 
               
    def doAttention(self,*,previousOutputsList,tokenListIDDict, 
                    HeadEmbeddingMatrix, WQ, WK, WV):
        indices = [tokenListIDDict[token] for token in previousOutputsList]
        previousOutputsVectors = HeadEmbeddingMatrix[indices]
        PE = self.positional_encoding(len(previousOutputsList),self.__perHeadTokenWidth)
        previousOutputsVectors = previousOutputsVectors + PE
        POV_WKDerivatives = self.calcDrvForMatrixMultL(previousOutputsVectors,WK)
        WK_POVDerivatives = self.calcDrvForMatrixMultR(previousOutputsVectors,WK)
        previousOutputsK = previousOutputsVectors @ WK
        hiddenVector = previousOutputsVectors.copy()
        HV_WQDerivatives = self.calcDrvForMatrixMultL(hiddenVector,WQ)
        WQ_HVDerivatives = self.calcDrvForMatrixMultR(hiddenVector,WQ) 
        hiddenVector = hiddenVector @ WQ
        HV_POKDerivatives = self.calcDrvForMatrixMultL(hiddenVector,previousOutputsK.T)
        POK_HVDerivatives = self.calcDrvForMatrixMultL(previousOutputsK,hiddenVector.T)
        hiddenVector = hiddenVector @ previousOutputsK.T
        mask = np.triu(
            np.ones((len(previousOutputsVectors), len(previousOutputsVectors))),
            k=1
        )
        hiddenVector[mask==1] = -np.inf
        hiddenVector = hiddenVector/math.sqrt(self.__perHeadTokenWidth)
        softmaxDerivative = self.calcDrvForSoftMax(hiddenVector)
        hiddenVector = self.doSoftmax(hiddenVector)
        previousOutputsV = previousOutputsVectors @ WV
        hiddenVector = hiddenVector @ previousOutputsV
        return previousOutputsVectors, hiddenVector
    
    def doSoftmax(self,hiddenVector):
        hiddenVector = hiddenVector - np.max(hiddenVector,axis=1,keepdims=True)
        hiddenVector = np.power(np.e,hiddenVector) / np.sum(np.power(np.e,hiddenVector),axis=1,keepdims=True)
        return hiddenVector
    
    def doMultiHeadAttention(self,previousOutputsList,tokenListIDDict):
        hiddenVector = np.zeros((self.__numberHeads),dtype=object)
        previousOutputsVectors = np.zeros((self.__numberHeads),dtype=object)
        for i in range(self.__numberHeads):
            previousOutputsVectors[i], hiddenVector[i] = self.doAttention(
                    previousOutputsList=previousOutputsList, tokenListIDDict=tokenListIDDict, 
                    HeadEmbeddingMatrix=self.__Heads[i], WQ=self.__WQ[i], WK=self.__WK[i], 
                    WV = self.__WV[i])
            if i != 0:
                hiddenVector[0] = np.concatenate((hiddenVector[0],hiddenVector[i]),axis=1)
                previousOutputsVectors[0] = np.concatenate((
                        previousOutputsVectors[0], previousOutputsVectors[i]                                            
                    ),
                    axis=1                                       
                )
        return previousOutputsVectors[0], hiddenVector[0]
    
    def doOutputProjection(self,hiddenVector):
        return hiddenVector @ self.__WO
    
    def doResidualAndLayerNorm(self, hiddenVector, previousOutput):
        hiddenVector = hiddenVector + previousOutput
        epsilon = 10**(-5)
        mean = np.mean(hiddenVector,axis=1,keepdims=True)
        variance = np.var(hiddenVector,axis=1,keepdims=True)
        hiddenVector = (hiddenVector - mean)/np.sqrt(variance+epsilon)
        hiddenVector = (self.__learnedScaleLN*hiddenVector) + self.__learnedBiasLN
        return hiddenVector    
    
    def doFeedForwardNetwork(self,hiddenVector):
        hiddenVector = (hiddenVector @ self.__WZ1) + self.__learnedBias1FFN
        hiddenVector = np.maximum(0,hiddenVector)
        hiddenVector = (hiddenVector @ self.__WZ2) + self.__learnedBias2FFN
        return hiddenVector
    
    def positional_encoding(self, sequence_length, dk):
        pos = np.arange(sequence_length)[:,np.newaxis]
        i = np.arange(0,dk,2)
        angle = pos / np.power(10000,i/dk) 
        PE = np.zeros((sequence_length,dk))
        
        PE[:,0::2] = np.sin(angle)
        PE[:,1::2] = np.cos(angle)
        return PE
    
    def calcDrvForMatrixMultL(self,leftMatrix,rightMatrix):
        derivative = np.array([rightMatrix])
        forBroadcastingDerivative = np.ones((
            len(leftMatrix),
            rightMatrix.shape[0],
            rightMatrix.shape[1]
        ))
        return derivative*forBroadcastingDerivative

    def calcDrvForMatrixMultR(self,leftMatrix,rightMatrix):
        derivative = np.array(leftMatrix.T[:,np.newaxis])
        forBroadcastingDerivative = np.ones((
            leftMatrix.shape[1], 
            rightMatrix.shape[1], 
            leftMatrix.shape[0]
        ))
        return derivative*forBroadcastingDerivative

    def calcDrvForSoftMax(self,hiddenVector):
        hiddenVector = hiddenVector - np.max(hiddenVector,axis=1,keepdims=True)
        powerOfE = np.power(np.e,hiddenVector)
        summation = np.sum(powerOfE,axis=1,keepdims=True)
        derivative = np.zeros((len(hiddenVector)*len(hiddenVector),len(hiddenVector)))
        for i in range(len(hiddenVector)):
            rowStart = i*len(hiddenVector)
            rowEnd = (i+1)*len(hiddenVector)
            derivative[rowStart:rowEnd,i] = 1
            derivative[rowStart:rowEnd] = (
                (
                    (derivative[rowStart:rowEnd])*((np.exp(hiddenVector[:,i:i+1]))/
                    (np.sum(np.exp(hiddenVector),axis=1,keepdims=True)))
                ) -
                (
                    (
                        np.exp(hiddenVector)/
                        np.sum(np.exp(hiddenVector),axis=1,keepdims=True)
                    ) *
                    (
                        (np.exp(hiddenVector[:,i:i+1]))/
                        (np.sum(np.exp(hiddenVector),axis=1,keepdims=True))
                    )
                )            
            )
        return derivative
    
    def getEin(self):
        return self.__Ein
    
    def getEmbeddingHead(self):
        return self.__Heads
    
    def getWQHead(self):
        return self.__WQ
    
    def getWKHead(self):
        return self.__WK
    
    def getWVHead(self):
        return self.__WV
     
    def getWidthOfEmbedding(self):                
        return self.__widthOfEmbeddingMatrix
    
    def getNumberOfHeads(self):
        return self.__numberHeads
        
# Test Cases:
# A = np.array([[1,2],[3,4]])
# B = np.array([[5,6],[7,8]])
# print(A)
# print(B)
# print(A @ B)
# print(np.sum(A,axis=1))

# arr = np.array([[6,7],[8,9],[10,11]])
# newarr = np.array([0,1,2])

arr = np.array([[6,7],[8,9],[4,5]])
newarr = np.array([[0,1],[2,3],[4,5]])

# newarr = np.array([5,6,7,8])
# arr = np.array([1,2,3,4])

# newarr = np.array([5])
# arr = np.array([1,2,3,4])

# arr = np.array([arr])
# print(newarr*arr)

# def calcDrvForMatrixMultL(leftMatrix,rightMatrix):
#     derivative = np.array([rightMatrix])
#     forBroadcastingDerivative = np.ones((
#         len(leftMatrix),
#         rightMatrix.shape[0],
#         rightMatrix.shape[1]
#     ))
#     return derivative*forBroadcastingDerivative

# def calcDrvForMatrixMultR(leftMatrix,rightMatrix):
#     derivative = np.array(leftMatrix.T[:,np.newaxis])
#     forBroadcastingDerivative = np.ones((
#         leftMatrix.shape[1], 
#         rightMatrix.shape[1], 
#         leftMatrix.shape[0]
#     ))
#     return derivative*forBroadcastingDerivative


# Don't delete this is important. 
# Basically left@right.T is equal to
# (right@left.T).T
# print(arr.T)
# print(newarr)
# print(calcDrvForMatrixMultR(newarr,arr.T))
# print()
# print(arr)
# print(newarr.T)
# print(calcDrvForMatrixMultL(arr,newarr.T))
#END-----------------


# hiddenVector = np.array([
#     [1,2,3,4,5,6],
#     [7,8,9,10,11,12],
#     [13,14,15,16,17,18],
#     [19,20,21,22,23,24],
#     [25,26,27,28,29,30],
#     [31,32,33,34,35,36]
# ])
# derivative = np.zeros((len(hiddenVector)*len(hiddenVector),len(hiddenVector)))




# def calcDrvForSoftMax(hiddenVector):
#     hiddenVector = hiddenVector - np.max(hiddenVector,axis=1,keepdims=True)
#     powerOfE = np.power(np.e,hiddenVector)
#     summation = np.sum(powerOfE,axis=1,keepdims=True)
#     derivative = np.zeros((len(hiddenVector)*len(hiddenVector),len(hiddenVector)))
#     for i in range(len(hiddenVector)):
#         rowStart = i*len(hiddenVector)
#         rowEnd = (i+1)*len(hiddenVector)
#         derivative[rowStart:rowEnd,i] = 1
#         derivative[rowStart:rowEnd] = (
#             (
#                 (derivative[rowStart:rowEnd])*((np.exp(hiddenVector[:,i:i+1]))/
#                 (np.sum(np.exp(hiddenVector),axis=1,keepdims=True)))
#             ) -
#             (
#                 (
#                     np.exp(hiddenVector)/
#                     np.sum(np.exp(hiddenVector),axis=1,keepdims=True)
#                 ) *
#                 (
#                     (np.exp(hiddenVector[:,i:i+1]))/
#                     (np.sum(np.exp(hiddenVector),axis=1,keepdims=True))
#                 )
#             )            
#         )
#     return derivative

# print(calcDrvForSoftMax(hiddenVector))

# newvector = ((np.exp(hiddenVector)/(np.sum(np.exp(hiddenVector),axis=1,keepdims=True)))*
#              (1-(np.exp(hiddenVector)/np.sum(np.exp(hiddenVector),axis=1,keepdims=True))))

# print(newvector)

# print(newarr[:,np.newaxis])