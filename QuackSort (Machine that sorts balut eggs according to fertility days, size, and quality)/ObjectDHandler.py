from time import sleep
from ultralytics import YOLO
import cv2
import asyncio
import websockets
import threading
import logging
import json
from detection_debounce import DetectionDebouncer as db
countR = 0
count = ""
response = ""
response1 = ""
response2 = ""
maxListAggregate = 6
list_posL = []
list_posR = []
list_posSize = []
list_aggregate = [0]*7
list_aggregateCrack = [0]*7
sortedListPosSize = []
size_dict = {"Medium": {"from":4.024, "to":4.098},"Large":{"from":4.099,"to":4.305},
             "XLarge":{"from":4.305,"to":4.496} }
ratio = 10/199
size = "None"
oldCount = 0
sendDetectedData = 1

logging.getLogger("ultralytics").disabled = True

async def send(websocket):
    global response
    #response = input("Resonse: ")
    await websocket.send(response)
    
async def send1(websocket):
    global response
    #response = input("Resonse: ")
    await websocket.send(response1)
    
async def send2(websocket):
    global response
    #response = input("Resonse: ")
    await websocket.send(response2)
        
async def receive(websocket):
    global countR
    global count
    global sendDetectedData
    global oldCount
    global flagCount
    count = await websocket.recv()
    if count == "":
        pass
    else:
        countR = int(count[0])
        #if countR == 0:
            #print("")
        #print(count)
        #countR = 1
        if(int(count[1])==1):
            #print("Enters")
            sendDetectedData = 0
        else:
            #print("enters")
            sendDetectedData = 1
        oldCount = countR
        pass

async def hello(websocket):
    await asyncio.gather(send(websocket),receive(websocket))
       
async def hello1(websocket):
    await send1(websocket)
    
async def hello2(websocket):
    await send2(websocket)
        
async def main():
    await websockets.serve(hello, "192.168.0.1",8000)
    await websockets.serve(hello1, "192.168.0.1",8001)
    await websockets.serve(hello2, "192.168.0.1",8002)
    await asyncio.Future()

       
def threadWebsocket():
    asyncio.run(main())
    
t1 = threading.Thread(target=threadWebsocket)
t1.start()

def x_referrence(sortedList):
    if len(sortedList) != 3:
        return
    print("[0]x2: ", int(sortedList[0]["posx2"]))
    print("[1]x2-[0]x2: ", int(sortedList[1]["posx2"] - sortedList[0]["posx2"]))
    print("[1]x2: ", int(sortedList[1]["posx1"]))
    print("[1]x2: ", int(sortedList[1]["posx2"]))
    print("[2]x1: ", int(sortedList[2]["posx1"]))
    print("[2]x2: ", int(sortedList[2]["posx2"]))
    sleep(30)
    


cv2.namedWindow("frame",cv2.WINDOW_NORMAL)
cv2.namedWindow("frame1",cv2.WINDOW_NORMAL)
cv2.namedWindow("frame2",cv2.WINDOW_NORMAL)
cv2.namedWindow("frame3",cv2.WINDOW_NORMAL)
cv2.namedWindow("frame4",cv2.WINDOW_NORMAL)

cap = cv2.VideoCapture(1, cv2.CAP_DSHOW)
cap1 = cv2.VideoCapture(0,cv2.CAP_DSHOW)
cap2 = cv2.VideoCapture(2,cv2.CAP_DSHOW)
cap3 = cv2.VideoCapture(3,cv2.CAP_DSHOW)
cap4 = cv2.VideoCapture(4,cv2.CAP_DSHOW)

#0.75 autoexposure 0.25 manual
cap.set(cv2.CAP_PROP_AUTO_EXPOSURE, 0.75)
cap1.set(cv2.CAP_PROP_AUTO_EXPOSURE, 0.75)
cap2.set(cv2.CAP_PROP_AUTO_EXPOSURE, 0.25)
cap2.set(cv2.CAP_PROP_EXPOSURE, -3)
cap3.set(cv2.CAP_PROP_AUTO_EXPOSURE, 0.25)
cap3.set(cv2.CAP_PROP_EXPOSURE, -9)
cap4.set(cv2.CAP_PROP_AUTO_EXPOSURE, 0.25)
cap4.set(cv2.CAP_PROP_EXPOSURE, -9)



model = YOLO('./runs/detect/train19/weights/best.pt')
#model = YOLO('./best.pt')
modelSize = YOLO('./runs-3/detect/train4/weights/best.pt')
modelCrack = YOLO('./runs-2/detect/train12/weights/best.pt')
# modelCrack = YOLO('./bestCrack.pt')
threshold = 0.55
threshold1 = 0.6
flag=0

balut18Frame = db()
balut16Frame = db()
penoyFrame = db()
balut18Frame1 = db()
balut16Frame1 = db()
penoyFrame1 = db()
crackFrame1_1 = db(on_thr=0.7,off_thr=0.6)
crackFrame1_2 = db(on_thr=0.7,off_thr=0.6)
crackFrame1_3 = db(on_thr=0.7,off_thr=0.6)
crackFrame2_1 = db(on_thr=0.7,off_thr=0.6)
crackFrame2_2 = db(on_thr=0.7,off_thr=0.6)
crackFrame2_3 = db(on_thr=0.7,off_thr=0.6)

count_firstx2 = 0
count_secondx2 = 0
count_thirdx2 = 0
setMode = 1

while True:

    ret, frame = cap.read()
    ret1, frame1 = cap1.read()
    ret2, frame2 = cap2.read()
    ret3, frame3 = cap3.read()
    ret4, frame4 = cap4.read()
    
    
    
#------------------------------------------------------------
#FIRST CAMERA FERTILITY DETECT
#------------------------------------------------------------
    count_firstx2 = 0
    count_secondx2 = 0
    count_thirdx2 = 0
    results = model(frame)[0]
    ListDetected = results.boxes.data.tolist()
    for result in results.boxes.data.tolist():    
        x1, y1, x2, y2, score, class_id = result
        
        if results.names[int(class_id)] == "Balut18":
            presentScore = balut18Frame.update(score)
        elif results.names[int(class_id)] == "Balut16":
            presentScore = balut16Frame.update(score)
        elif results.names[int(class_id)] == "Penoy":
            presentScore = penoyFrame.update(score)
        
        if x2 < 290:
            count_firstx2 = count_firstx2 + 1
            if count_firstx2 > 1: 
                continue
        if x2 < 460 and x2 > 290:
            count_secondx2 = count_secondx2 + 1
            if count_secondx2 > 1:
                continue
        if x2 < 650 and x2 > 460 and x2 > 290:
            count_thirdx2 = count_thirdx2 + 1
            if count_thirdx2 > 1:
                continue
        if x2 > 650 and x2 > 460 and x2 > 290:
            continue
            
        if presentScore:
            list_posL.append({"name":results.names[int(class_id)], "posx2":x2, "posx1":x1, 
                              "size":None, "content": "full","integrity":"whole"})
            cv2.rectangle(frame, (int(x1), int(y1)), (int(x2), int(y2)), (0, 255, 0), 4)
            cv2.putText(frame, results.names[int(class_id)].upper(), (int(x1), int(y1 - 10)),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 3, cv2.LINE_AA) 
    sortedListPosL = sorted(list_posL,key=lambda x: x["posx2"])
    
    if ListDetected != [] and sortedListPosL!=[] and len(sortedListPosL)!=3:
        if sortedListPosL[0]["posx2"] > 265:
            sortedListPosL.insert(0,{"content":"empty"})
        if (len(sortedListPosL)!=1 and sortedListPosL[0]["content"]!="empty" and 
            (sortedListPosL[1]["posx2"] - sortedListPosL[0]["posx2"])>161
                and len(sortedListPosL)>1):
            sortedListPosL.insert(1,{"content":"empty"})
        if (len(sortedListPosL) == 1 and sortedListPosL[0]["posx2"]<265):
            sortedListPosL.append({"content":"empty"})
            sortedListPosL.append({"content":"empty"})
        if (len(sortedListPosL) == 2 and sortedListPosL[1]["posx1"]>450):
            sortedListPosL.insert(1,{"content":"empty"})
        if (len(sortedListPosL) == 2):
            sortedListPosL.append({"content":"empty"})
    
    if sortedListPosL == []:
        sortedListPosL = [{"content":"empty"},
                          {"content":"empty"},
                          {"content":"empty"}]
#------------------------------------------------------------
#END FIRST CAMERA FERTILITY DETECT
#------------------------------------------------------------

#------------------------------------------------------------
#SECOND CAMERA FERTILITY DETECT
#------------------------------------------------------------    
    countFrame1_firstx2 = 0
    countFrame1_secondx2 = 0
    countFrame1_thirdx2 = 0
    results = model(frame1)[0]
    ListDetected1 = results.boxes.data.tolist()
    for result in results.boxes.data.tolist():    
        x1, y1, x2, y2, score, class_id = result
        if results.names[int(class_id)] == "Balut18":
            presentScore1 = balut18Frame1.update(score)
        elif results.names[int(class_id)] == "Balut16":
            presentScore1 = balut16Frame1.update(score)
        elif results.names[int(class_id)] == "Penoy":
            presentScore1 = penoyFrame1.update(score)
            
        if x2 < 300:
            countFrame1_firstx2 = countFrame1_firstx2 + 1
            if countFrame1_firstx2 > 1:
                continue
        if x2 < 420 and x2 > 300: 
            countFrame1_secondx2 = countFrame1_secondx2 + 1
            if countFrame1_secondx2 > 1: 
                continue
        if x2 < 580 and x2 > 420 and x2 > 300:
            countFrame1_thirdx2 = countFrame1_thirdx2 + 1
            if countFrame1_thirdx2 > 1:
                continue
        if x2 > 580 and x2 > 420 and x2 > 300:
            continue
            
        if presentScore1:
            list_posR.append({"name":results.names[int(class_id)], "posx2":x2, "posx1":x1, 
                              "size":None, "content": "full","integrity":"whole"})
            cv2.rectangle(frame1, (int(x1), int(y1)), (int(x2), int(y2)), (0, 255, 0), 4)
            cv2.putText(frame1, results.names[int(class_id)].upper(), (int(x1), int(y1 - 10)),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 3, cv2.LINE_AA)
    sortedListPosR = sorted(list_posR,key=lambda x: x["posx2"],reverse=True)
    
    if ListDetected1 != [] and sortedListPosR!=[] and len(sortedListPosR)!=3:
        if sortedListPosR[0]["posx2"] < 450:
            sortedListPosR.insert(0,{"content":"empty"})
        if (len(sortedListPosR)!=1 and sortedListPosR[0]["content"]!="empty" and 
            (sortedListPosR[1]["posx2"] - sortedListPosR[0]["posx2"])<-196
                and len(sortedListPosR)>1):
            sortedListPosR.insert(1,{"content":"empty"})
        if (len(sortedListPosR) == 1 and sortedListPosR[0]["posx2"]>450):
            sortedListPosR.append({"content":"empty"})
            sortedListPosR.append({"content":"empty"})
        if (len(sortedListPosR) == 2 and sortedListPosR[1]["posx1"]<227):
            sortedListPosR.insert(1,{"content":"empty"})
        if (len(sortedListPosR) == 2):
            sortedListPosR.append({"content":"empty"})
    
    if sortedListPosR == []:
        sortedListPosR = [{"content":"empty"},
                          {"content":"empty"},
                          {"content":"empty"}]
#------------------------------------------------------------
#END SECOND CAMERA FERTILITY DETECT
#------------------------------------------------------------ 

#------------------------------------------------------------
#FIRST CAMERA SIZE DETECT
#------------------------------------------------------------           
    results = modelSize(frame2)[0]
    ListDetected2 = results.boxes.data.tolist()
    for result in results.boxes.data.tolist():    
        x1, y1, x2, y2, score, class_id = result
        if score > threshold1:
            if (x2-x1)*ratio > size_dict["Medium"]["from"] and (x2-x1)*ratio < size_dict["Medium"]["to"]:
                size = "Medium" 
            elif (x2-x1)*ratio > size_dict["Large"]["from"] and (x2-x2)*ratio < size_dict["Large"]["to"]:
                size = "Large"
            elif (x2-x1)*ratio > size_dict["XLarge"]["from"] and (x2-x2)*ratio < size_dict["XLarge"]["to"]: 
                size = "XLarge"
            list_posSize.append({"name": size,"posx1":x1, "posx":x2, "posy":y2,"content":"full"})
            cv2.rectangle(frame2, (int(x1), int(y1)), (int(x2), int(y2)), (0, 255, 0), 4)
            cv2.putText(frame2, results.names[int(class_id)].upper()+str(int(y2)), (int(x1), int(y1 - 10)),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 3, cv2.LINE_AA)
            
    sortedListPosSize = sorted(list_posSize,key=lambda x: x["posy"])
    
    k = 0 
    sizeY_sort = [198,388,550]
    if ListDetected2!=[]:
        # for res in sortedListPosSize: 
        #     print(res["posx"],",",res["posy"])
        # sleep(1000)
        for i in range(len(sortedListPosSize)): 
            
            if i+1<len(sortedListPosSize)-1:  
                # print(sortedListPosSize[i]["posx"] > sortedListPosSize[i+1]["posx"]) 
                if (sortedListPosSize[i]["posx"] > sortedListPosSize[i+1]["posx"] and 
                    (True if (k==0 and sortedListPosSize[i]["posy"]<sizeY_sort[k] and 
                    sortedListPosSize[i+1]["posy"]<sizeY_sort[k]) else 
                    (sortedListPosSize[i]["posy"]>sizeY_sort[k] and 
                    sortedListPosSize[i]["posy"]<sizeY_sort[k+1] and 
                    sortedListPosSize[i+1]["posy"]>sizeY_sort[k] and 
                    sortedListPosSize[i+1]["posy"]<sizeY_sort[k+1]))):
                    sortedListPosSize[i], sortedListPosSize[i+1] = sortedListPosSize[i+1], sortedListPosSize[i]
                    k = k + 1
                    
    
    i = 0 
    k = 0
    c = 0
    d = 0
    f = 0
    xRefSize = 327
    yRefSize = [198,388]
    flag = False
    flag1 = False               
    while True: 
        if i >= len(sortedListPosSize) or len(sortedListPosSize)==6:
            break 
        
        
        if (True if i==0 and sortedListPosSize[i]["posx"]>xRefSize else 
            (((sortedListPosSize[i-2]["content"]!="empty" and 
            sortedListPosSize[i-2]["posy"]<yRefSize[k]) 
            if sortedListPosSize[i-1]["content"]=="empty" else  
            sortedListPosSize[i-1]["posy"]<yRefSize[k]) and 
            sortedListPosSize[i]["posx"]>xRefSize and
            sortedListPosSize[i]["posy"]>yRefSize[k])):
            sortedListPosSize.insert(i,{"content":"empty"})
            if i!=0:
                k = k + 1
            i = i + 1
        for j in range(2):        
            if ((f==0 and sortedListPosSize[i]["posy"]>yRefSize[j]) or 
                ((f==1 or f==2) and sortedListPosSize[i]["posy"]>yRefSize[1]) and 
                ((sortedListPosSize[i-2]["content"]!="empty" and
                sortedListPosSize[i-2]["posy"]<yRefSize[0]) if 
                sortedListPosSize[i-1]["content"]=="empty" else 
                sortedListPosSize[i-1]["posy"]<yRefSize[0])):
                
                sortedListPosSize.insert(i,{"content":"empty"})
                i = i + 1
                sortedListPosSize.insert(i,{"content":"empty"})
                i = i + 1
                if ((f==1 or f==2) and sortedListPosSize[i]["posy"]>yRefSize[1]):
                    break    
        if (i<len(sortedListPosSize)-1 and 
            sortedListPosSize[i]["posy"]<yRefSize[1] and 
            sortedListPosSize[i]["posx"]<xRefSize and 
            (sortedListPosSize[i+1]["posy"]>yRefSize[0] if i==0 else 
            sortedListPosSize[i+1]["posy"]>yRefSize[1])):
            sortedListPosSize.insert(i+1,{"content":"empty"})
            if i!=0: 
                k = k + 1 
            i = i + 1
            c = c + 1   
        flag1 = False         
        i = i + 1
        f = f + 1

        if (i > 1 and i<len(sortedListPosSize) and
            sortedListPosSize[i]["content"]!="empty" and
            sortedListPosSize[i-1]["content"]!="empty" and 
            sortedListPosSize[i]["posx"]>xRefSize and
            sortedListPosSize[i]["posy"]>yRefSize[0] and 
            sortedListPosSize[i]["posy"]<yRefSize[1] and 
            sortedListPosSize[i-1]["posy"]>yRefSize[0] and 
            sortedListPosSize[i-1]["posy"]<yRefSize[1]):
            k = k + 1        
        
    if len(sortedListPosSize) != 6: 
        for i in range(6 - len(sortedListPosSize)):
            sortedListPosSize.append({"content":"empty"})
            
    
    # if ListDetected2!=[]:
    #     for res in sortedListPosSize: 
    #         print(res["posx"],",",res["posy"])
    #     sleep(1000)
#------------------------------------------------------------
#END FIRST CAMERA SIZE DETECT
#------------------------------------------------------------ 


#------------------------------------------------------------
#FIRST CAMERA CRACK DETECT
#frame3 - frame1
#------------------------------------------------------------
    list_posRCrack = []
    results = modelCrack(frame3)[0]
    ListDetected3 = results.boxes.data.tolist()
    crack1DLimit = 0
    for result in results.boxes.data.tolist():    
        x1, y1, x2, y2, score, class_id = result
        if crack1DLimit == 0:
            presentScore3 = crackFrame1_1.update(score)
        elif crack1DLimit == 1:
            presentScore3 = crackFrame1_2.update(score)
        elif crack1DLimit == 2:
            presentScore3 = crackFrame1_3.update(score)
        crack1DLimit = crack1DLimit + 1      
        if presentScore3:
            list_posRCrack.append({"integrity":"crack","content":"full","posx2":x2,"posx1":x1})
            cv2.rectangle(frame3, (int(x1), int(y1)), (int(x2), int(y2)), (0, 255, 0), 4)
            cv2.putText(frame3, results.names[int(class_id)].upper(), (int(x1), int(y1 - 10)),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 3, cv2.LINE_AA) 
            
    sortedListPosRCrack = sorted(list_posRCrack,key=lambda x: x["posx2"],reverse=True)
    
    if ListDetected3 != [] and sortedListPosRCrack!=[] and len(sortedListPosRCrack)!=3:
        if sortedListPosRCrack[0]["posx2"] < 500:
            sortedListPosRCrack.insert(0,{"content":"empty"})
        if (len(sortedListPosRCrack)!=1 and sortedListPosRCrack[0]["content"]!="empty" and 
            (sortedListPosRCrack[1]["posx2"] - sortedListPosRCrack[0]["posx2"])<-202
                and len(sortedListPosRCrack)>1):
            sortedListPosRCrack.insert(1,{"content":"empty"})
        if (len(sortedListPosRCrack) == 1 and sortedListPosRCrack[0]["posx2"]>500):
            sortedListPosRCrack.append({"content":"empty"})
            sortedListPosRCrack.append({"content":"empty"})
        if (len(sortedListPosRCrack) == 2 and sortedListPosRCrack[1]["posx1"]<100):
            sortedListPosRCrack.insert(1,{"content":"empty"})
        if (len(sortedListPosRCrack) == 2):
            sortedListPosRCrack.append({"content":"empty"})
            
    if sortedListPosRCrack == []:
        sortedListPosRCrack = [{"content":"empty"},
                          {"content":"empty"},
                          {"content":"empty"}]
        
    # print(sortedListPosRCrack)
#------------------------------------------------------------
#END FIRST CAMERA CRACK DETECT
#------------------------------------------------------------  

#------------------------------------------------------------
#SECOND CAMERA CRACK DETECT
#frame4 - frame
#------------------------------------------------------------
    results = modelCrack(frame4)[0]
    ListDetected4 = results.boxes.data.tolist()
    list_posLCrack = []
    crack2DLimit = 0 
    for result in results.boxes.data.tolist():    
        x1, y1, x2, y2, score, class_id = result
        if crack2DLimit == 0:
            presentScore4 = crackFrame2_1.update(score)
        elif crack2DLimit == 1:
            presentScore4 = crackFrame2_2.update(score)
        elif crack2DLimit == 2:
            presentScore4 = crackFrame2_3.update(score)
        crack2DLimit = crack2DLimit + 1   
        if presentScore4:
            list_posLCrack.append({"integrity":"crack","content":"full","posx2":x2,"posx1":x1})
            cv2.rectangle(frame4, (int(x1), int(y1)), (int(x2), int(y2)), (0, 255, 0), 4)
            cv2.putText(frame4, results.names[int(class_id)].upper(), (int(x1), int(y1 - 10)),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 3, cv2.LINE_AA) 
    sortedListPosLCrack = sorted(list_posLCrack,key=lambda x: x["posx2"])


    if ListDetected4 != [] and sortedListPosLCrack!=[] and len(sortedListPosLCrack)!=3:
            if sortedListPosLCrack[0]["posx2"] > 265:
                sortedListPosLCrack.insert(0,{"content":"empty"})
            if (len(sortedListPosLCrack)!=1 and sortedListPosLCrack[0]["content"]!="empty" and 
                (sortedListPosLCrack[1]["posx2"] - sortedListPosLCrack[0]["posx2"])>240
                    and len(sortedListPosLCrack)>1):
                sortedListPosLCrack.insert(1,{"content":"empty"})
            if (len(sortedListPosLCrack) == 1 and sortedListPosLCrack[0]["posx2"]<265):
                sortedListPosLCrack.append({"content":"empty"})
                sortedListPosLCrack.append({"content":"empty"})
            if (len(sortedListPosLCrack) == 2 and sortedListPosLCrack[1]["posx1"]>360):
                sortedListPosLCrack.insert(1,{"content":"empty"})
            if (len(sortedListPosLCrack) == 2):
                sortedListPosLCrack.append({"content":"empty"})
    if sortedListPosLCrack == []:
        sortedListPosLCrack = [{"content":"empty"},
                          {"content":"empty"},
                          {"content":"empty"}]
#------------------------------------------------------------
#END SECOND CAMERA CRACK DETECT
#------------------------------------------------------------    
    if setMode:
        for i in range(3):
            if(sortedListPosL[i]["content"]!="empty" and 
               sortedListPosR[i]["content"]!="empty" and 
               (sortedListPosR[i]["name"]=="Balut16" or 
                sortedListPosL[i]["name"]=="Balut16")):
                sortedListPosL[i]["name"] = "Balut16"
                
            if(sortedListPosL[i]["content"]!="empty" and 
               sortedListPosR[i]["content"]!="empty" and 
               (sortedListPosR[i]["name"]=="Penoy" or 
                sortedListPosL[i]["name"]=="Penoy")):
                sortedListPosL[i]["name"] = "Penoy"
           
        sortedListPosR.clear()
        sortedListPosR = [{"content":"empty"},
                          {"content":"empty"},
                          {"content":"empty"}]
    
    if setMode:
        for i in range(3):
            if(sortedListPosLCrack[i]["content"]!="empty" and 
               sortedListPosRCrack[i]["content"]!="empty" and 
               (sortedListPosRCrack[i]["integrity"]=="Crack" or 
                sortedListPosLCrack[i]["integrity"]=="Crack")):
                sortedListPosLCrack[i]["integrity"] = "Crack"
                
        sortedListPosRCrack.clear()
        sortedListPosRCrack = [{"content":"empty"},
                          {"content":"empty"},
                          {"content":"empty"}]
    
    sortedListPosSize.insert(0,1)
    if sortedListPosL!=[] or sortedListPosR!=[]:
        list_aggregate.append(0)
        for i in range(4):
            if i < len(sortedListPosL):
                list_aggregate.append(sortedListPosL[i])
            if i < len(sortedListPosR):
                list_aggregate.append(sortedListPosR[i])
    
    if sortedListPosLCrack!=[] or sortedListPosRCrack!=[]:
        list_aggregateCrack.append(2)
        for i in range(4):
            if i < len(sortedListPosLCrack):
                list_aggregateCrack.append(sortedListPosLCrack[i])
            if i < len(sortedListPosRCrack):
                list_aggregateCrack.append(sortedListPosRCrack[i])

    if(sendDetectedData==0):
        response = "[]"
        response1 = "[]"
        response2 = "[]"
        pass   
    elif(sendDetectedData==1):
        response = json.dumps(list_aggregate)
        response1 = json.dumps(sortedListPosSize)
        response2 = json.dumps(list_aggregateCrack)
        

    
    list_posSize.clear()
    list_aggregate.clear()
    list_aggregateCrack.clear()
    list_posL.clear() 
    list_posR.clear()
    list_posLCrack.clear() 
    list_posRCrack.clear() 
    sortedListPosR.clear()
    sortedListPosL.clear() 
    sortedListPosRCrack.clear()
    sortedListPosLCrack.clear()
    sortedListPosSize.clear() 
    
    #sleep(2)
    if ret:
        cv2.imshow('frame',frame)
    if ret1:
        cv2.imshow('frame1',frame1)
    if ret2:
        cv2.imshow('frame2',frame2)   
    if ret3:
        cv2.imshow('frame3',frame3)
    if ret4:
        cv2.imshow('frame4',frame4)
    
    if flag>2:    
        #sleep(40)
        pass
    
    #flag = flag + 1
    cv2.waitKey(1)
        
        
cap.release()
cv2.destroyAllWindows()
