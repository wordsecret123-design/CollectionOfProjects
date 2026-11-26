from tokenizers import Tokenizer
from tokenizers.models import BPE
from tokenizers.pre_tokenizers import ByteLevel
from tokenizers.pre_tokenizers import Metaspace
from tokenizers.trainers import BpeTrainer
from tokenizers.decoders import ByteLevel as ByteLevelDecoder 
from tokenizers.decoders import Metaspace as MetaspaceDecoder
from tokenizers.processors import TemplateProcessing




class tokenizer:
    __content = []
    __TokenListVocabDict = {}
    __VocabSize = []
    
    def __init__(self, filename:str):
        tokenizer = Tokenizer(BPE(unk_token="<UNK>"))
        tokenizer.pre_tokenizer = Metaspace()
        trainer = BpeTrainer(vocab_size=10000,special_tokens=[
                            "<UNK>","<BOS>","<EOS>"])
        tokenizer.train([filename],trainer)
        tokenizer.decoder = MetaspaceDecoder()
        
        tokenizer.post_processor = TemplateProcessing(
            single="<BOS> $A <EOS>",
            special_tokens=[
                ("<BOS>", tokenizer.token_to_id("<BOS>")),
                ("<EOS>", tokenizer.token_to_id("<EOS>")),
            ],
        )
        file = self.OpenFile(filename)
        self.__content = self.SetContentToList(file,tokenizer)
        self.__TokenListVocabDict = self.SetVocabDict(tokenizer)
        self.__VocabSize = self.SetVocabSize(tokenizer)

    def OpenFile(self, filename: str):
        with open(filename,"r") as file:
            content = file.read()
        return content

    def SetContentToList(self, file, tokenizer):
        output = tokenizer.encode(file)
        return output.tokens 
    
    def SetVocabDict(self, tokenizer):
        return tokenizer.get_vocab()
    
    def SetVocabSize(self, tokenizer):
        return tokenizer.get_vocab_size()
    
    def GetContentList(self):
        return self.__content
    
    def GetVocabDict(self):
        return self.__TokenListVocabDict
    
    def GetVocabSize(self):
        return self.__VocabSize









# # test case:
# tokenizers = Tokenizer(BPE(unk_token="<UNK>"))
# tokenizers.pre_tokenizer = Metaspace()
# trainer = BpeTrainer(vocab_size=10000,special_tokens=[
#                     "<UNK>","<BOS>","<EOS>"])

# tokenizers.train(["00_DataSet.txt"],trainer)
# tokenizers.decoder = MetaspaceDecoder()
# with open("00_DataSet.txt","r") as file:
#     content = file.read()

# output = tokenizers.encode(content)
# print(tokenizers.get_vocab_size())
# # print(tokenizers.decode(output.ids))