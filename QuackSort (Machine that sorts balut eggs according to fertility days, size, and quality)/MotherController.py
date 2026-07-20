import asyncio
import websockets
import threading
import json
import cv2
from gpiozero import LED, Button
from time import sleep
from adafruit_servokit import ServoKit
from multiprocessing import Process, Array, Lock
import lgpio
import serial
arduino = serial.Serial(port='/dev/ttyUSB0', timeout = 1,baudrate=9600)
sleep(2)

bit1 = LED(14)
bit1.off()
bit2 = LED(15)
bit2.off()
bitH = LED(23)
signalStopH = Button(25)
#bitH 23
signalStopV = Button(18)

resetterV = Button(26)
resetterVOut = LED(19)

resetterH = Button(13)
resetterHOut = LED(6)

toy = ServoKit(channels=16, address=0x41)
toy2 = ServoKit(channels=16, address=0x40)
#14,15 V 
name = ""
max_count = 50
count = 0
sub_count = [0]*10
pause_count = 0
mother_list = [0] * max_count
key_pressed = ""
counter_tuyok = 0
greeting = ""
greeting1 = ""
greeting2 = ""
checkIfLoopDone = 1
hasReceived = 0
hasReceived1 = 0
hasReceived2 = 0


gate1Finished = 0
gate2Finished = 0
gate3Finished = 0
gate4Finished = 0
gate5Finished = 0
gate6Finished = 0
gate7Finished = 0
gate8Finished = 0
gate9Finished = 0
gate10Finished = 0
startGates = 0
             
lock = threading.Lock()


#even 0 odd 180

async def gate1():
    door1 = 3
    door2 = 2
    toy2.servo[door1].set_pulse_width_range(600,2600)
    toy2.servo[door2].set_pulse_width_range(500,2500)
    countGate1 = 0
    locker = 0
    global gate1Finished
    global sub_count
    global startGates
    while True:
        if sub_count[0] == 2:
            locker = 1
        if countGate1 == 0:
            i = 2
        elif countGate1 > 0:
            i = 3
        if gate1Finished==0 and mother_list[count-i]!=0 and startGates==1 and locker == 1:
            print(sub_count)
            print(count)
            print(mother_list[count-i][sub_count[0]-1])
            print(mother_list[count-i][sub_count[0]])
            if (mother_list[count-i][sub_count[0]-1]["content"]!="empty" and
                mother_list[count-i][sub_count[0]-1]["size"] == "Medium" and
                mother_list[count-i][sub_count[0]-1]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[0]-1]["name"] == "Balut18"):
                print("Enters1")
                toy2.servo[door2].angle = 0
                await asyncio.sleep(1)
                toy2.servo[door2].angle = 100
                await asyncio.sleep(1)
            if (mother_list[count-i][sub_count[0]]["content"]!="empty" and
                mother_list[count-i][sub_count[0]]["size"] == "Medium" and
                mother_list[count-i][sub_count[0]]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[0]]["name"] == "Balut18"):
                print("Enters2")
                toy2.servo[door1].angle = 180
                await asyncio.sleep(1)
                toy2.servo[door1].angle = 70
                await asyncio.sleep(1)
            countGate1 = countGate1 + 1
            if countGate1>2:
                locker = 0
                countGate1 = 0
#             gate1Finished = 1
        #else:
        with lock:
            #print(1)
            gate1Finished = 1
        await asyncio.sleep(0.5)
                
                
    
async def gate2():
    door3 = 1
    door4 = 0
    toy2.servo[door3].set_pulse_width_range(500,2500)
    toy2.servo[door4].set_pulse_width_range(500,2700)
    countGate2 = 0
    i=3
    global gate2Finished
    global sub_count
    global startGates
    while True:

#         if countGate2 == 0:
#             i = 3
#         elif countGate2 > 0:
#             i = 4
        if gate2Finished==0 and mother_list[count-i]!=0 and startGates==1:
            print(sub_count)
            print(count)
            print(mother_list[count-i][sub_count[1]-1])
            print(mother_list[count-i][sub_count[1]])
            if (mother_list[count-i][sub_count[1]-1]["content"]!="empty" and
                mother_list[count-i][sub_count[1]-1]["size"] == "Medium" and
                mother_list[count-i][sub_count[1]-1]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[1]-1]["name"] == "Balut16"):
                print("Enters1")
                toy2.servo[door4].angle = 5
                await asyncio.sleep(1)
                toy2.servo[door4].angle = 90
                await asyncio.sleep(1)
            if (mother_list[count-i][sub_count[1]]["content"]!="empty" and
                mother_list[count-i][sub_count[1]]["size"] == "Medium" and
                mother_list[count-i][sub_count[1]]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[1]]["name"] == "Balut16"):
                print("Enters2")
                toy2.servo[door3].angle = 180
                await asyncio.sleep(1)
                toy2.servo[door3].angle = 95
                await asyncio.sleep(1)
#             countGate2 = countGate2 + 1
#             if countGate2>3:
#                 countGate2 = 0
#             gate2Finished = 1
        #else:
        with lock:
            gate2Finished = 1
            #print(2)
        await asyncio.sleep(0.5)
    

async def gate3():
    door5 = 15
    door6 = 14
    toy.servo[door5].set_pulse_width_range(500,2500)
    toy.servo[door6].set_pulse_width_range(500,2500)
    countGate3 = 0
    locker = 0
    global gate3Finished
    global sub_count
    global startGates
    while True:
        if sub_count[2] == 2:
            locker = 1
        if countGate3 == 0:
            i = 3
        elif countGate3 > 1:
            i = 4
        if gate3Finished==0 and mother_list[count-i]!=0 and startGates==1 and locker == 1:
            print(sub_count)
            print(count)
            print(mother_list[count-i][sub_count[2]-1])
            print(mother_list[count-i][sub_count[2]])
            if (mother_list[count-i][sub_count[2]-1]["content"]!="empty" and
                mother_list[count-i][sub_count[2]-1]["size"] == "Medium" and
                mother_list[count-i][sub_count[2]-1]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[2]-1]["name"] == "Penoy"):
                print("Enters1")
                toy.servo[door6].angle = 0
                await asyncio.sleep(1)
                toy.servo[door6].angle = 110
                await asyncio.sleep(1)
            if (mother_list[count-i][sub_count[2]]["content"]!="empty" and
                mother_list[count-i][sub_count[2]]["size"] == "Medium" and
                mother_list[count-i][sub_count[2]]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[2]]["name"] == "Penoy"):
                print("Enters2")
                toy.servo[door5].angle = 180
                await asyncio.sleep(1)
                toy.servo[door5].angle = 80
                await asyncio.sleep(1)
            countGate3 = countGate3 + 1
            if countGate3>2:
                locker = 0
                countGate3 = 0
#             gate3Finished = 1
        #else:
        with lock:
            #print(3)
            gate3Finished = 1
        await asyncio.sleep(0.5)
#ibaylo ang  ubang  mga angles sa mga gates kay imo mang gibaylo ang doors
async def gate4():
    door7 = 13
    door8 = 12
    toy.servo[door7].set_pulse_width_range(500,2900)
    toy.servo[door8].set_pulse_width_range(500,2700)
    countGate4 = 0
    locker = 0
    global gate4Finished
    global sub_count
    global startGates
    while True:
        if sub_count[3] == 2:
            locker = 1
        if countGate4 == 0:
            i = 3
        elif countGate4 > 0:
            i = 4
        if gate4Finished==0 and mother_list[count-i]!=0 and startGates==1 and locker == 1:
            print(sub_count[3]," gate4")
            print(count)
            print(mother_list[count-i][sub_count[3]-1])
            print(mother_list[count-i][sub_count[3]])
            if (mother_list[count-i][sub_count[3]-1]["content"]!="empty" and
                mother_list[count-i][sub_count[3]-1]["size"] == "Large" and
                mother_list[count-i][sub_count[3]-1]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[3]-1]["name"] == "Balut18"):
                print("Enters1")
                toy.servo[door8].angle = 0 
                await asyncio.sleep(1)
                toy.servo[door8].angle = 85
                await asyncio.sleep(1)
            if (mother_list[count-i][sub_count[3]]["content"]!="empty" and
                mother_list[count-i][sub_count[3]]["size"] == "Large" and
                mother_list[count-i][sub_count[3]]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[3]]["name"] == "Balut18"):
                print("Enters2")
                toy.servo[door7].angle = 100
                await asyncio.sleep(1)
                toy.servo[door7].angle = 90
                await asyncio.sleep(1)
            countGate4 = countGate4 + 1
            if countGate4>2:
                locker = 0
                countGate4 = 0
#             gate4Finished = 1
        #else:
        with lock:
            gate4Finished = 1
            #print(4)
        await asyncio.sleep(0.5)

async def gate5():
    door9 = 11
    door10 = 10
    toy.servo[door9].set_pulse_width_range(500,2800)
    toy.servo[door10].set_pulse_width_range(600,2900)
    countGate5 = 0
    i=4
    global gate5Finished
    global sub_count
    global startGates
    while True:

#         if countGate2 == 0:
#             i = 3
#         elif countGate2 > 0:
#             i = 4
        if gate5Finished==0 and mother_list[count-i]!=0 and startGates==1:
            print(sub_count[4]," gate5")
            print(count)
            print(mother_list[count-i][sub_count[4]-1])
            print(mother_list[count-i][sub_count[4]])
            if (mother_list[count-i][sub_count[4]-1]["content"]!="empty" and
                mother_list[count-i][sub_count[4]-1]["size"] == "Large" and
                mother_list[count-i][sub_count[4]-1]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[4]-1]["name"] == "Balut16"):
                print("Enters1")
                toy.servo[door10].angle = 0
                await asyncio.sleep(1)
                toy.servo[door10].angle = 90
                await asyncio.sleep(1)
            if (mother_list[count-i][sub_count[4]]["content"]!="empty" and
                mother_list[count-i][sub_count[4]]["size"] == "Large" and
                mother_list[count-i][sub_count[4]]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[4]]["name"] == "Balut16"):
                print("Enters2")
                toy.servo[door9].angle = 180
                await asyncio.sleep(1)
                toy.servo[door9].angle = 85
                await asyncio.sleep(1)
#             countGate2 = countGate2 + 1
#             if countGate2>3:
#                 countGate2 = 0
#             gate5Finished = 1
        #else:
        with lock:
            gate5Finished = 1
            #print(5)
        await asyncio.sleep(0.5)


async def gate6():
    door11 = 9
    door12 = 8
    toy.servo[door11].set_pulse_width_range(500,2500)
    toy.servo[door12].set_pulse_width_range(500,2800)
    countGate6 = 0
    locker = 0
    global gate6Finished
    global sub_count
    global startGates
    while True:
        if sub_count[5] == 2:
            locker = 1
        if countGate6 == 0:
            i = 4
        elif countGate6 > 1:
            i = 5
        if gate6Finished == 0 and mother_list[count-i]!=0 and startGates==1 and locker == 1:
            print(sub_count[5]," gate6")
            print(count)
            print(mother_list[count-i][sub_count[5]-1])
            print(mother_list[count-i][sub_count[5]])
            if (mother_list[count-i][sub_count[5]-1]["content"]!="empty" and
                mother_list[count-i][sub_count[5]-1]["size"] == "Large" and
                mother_list[count-i][sub_count[5]-1]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[5]-1]["name"] == "Penoy"):
                print("Enters1")
                toy.servo[door12].angle = 0
                await asyncio.sleep(1)
                toy.servo[door12].angle = 90
                await asyncio.sleep(1)
            if (mother_list[count-i][sub_count[5]]["content"]!="empty" and
                mother_list[count-i][sub_count[5]]["size"] == "Large" and
                mother_list[count-i][sub_count[5]]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[5]]["name"] == "Penoy"):
                print("Enters2")
                toy.servo[door11].angle = 180
                await asyncio.sleep(1)
                toy.servo[door11].angle = 90
                await asyncio.sleep(1)
            countGate6 = countGate6 + 1
            if countGate6>2:
                locker = 0
                countGate6 = 0
#             gate6Finished = 1
        #else:
        with lock:
            #print(6)
            gate6Finished = 1
        await asyncio.sleep(0.5)
        
async def gate7():
    door13 = 7
    door14 = 6
    toy.servo[door13].set_pulse_width_range(500,2500)
    toy.servo[door14].set_pulse_width_range(500,2800)
    countGate7 = 0
    locker = 0
    global gate7Finished
    global sub_count
    global startGates
    while True:
        if sub_count[6] == 2:
            locker = 1
        if countGate7 == 0:
            i = 4
        elif countGate7 > 0:
            i = 5
        if gate7Finished == 0 and mother_list[count-i]!=0 and startGates==1 and locker == 1:
            print(sub_count[6]," gate7")
            print(count)
            print(mother_list[count-i][sub_count[6]-1])
            print(mother_list[count-i][sub_count[6]])
            if (mother_list[count-i][sub_count[6]-1]["content"]!="empty" and
                mother_list[count-i][sub_count[6]-1]["size"] == "XLarge" and
                mother_list[count-i][sub_count[6]-1]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[6]-1]["name"] == "Balut18"):
                print("Enters1")
                toy.servo[door14].angle = 0
                await asyncio.sleep(1)
                toy.servo[door14].angle = 90
                await asyncio.sleep(1)
            if (mother_list[count-i][sub_count[6]]["content"]!="empty" and
                mother_list[count-i][sub_count[6]]["size"] == "XLarge" and
                mother_list[count-i][sub_count[6]]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[6]]["name"] == "Balut18"):
                print("Enters2")
                toy.servo[door13].angle = 180
                await asyncio.sleep(1)
                toy.servo[door13].angle = 74
                await asyncio.sleep(1)
            countGate7 = countGate7 + 1
            if countGate7>2:
                locker = 0
                countGate7 = 0
#             gate7Finished = 1
        #else:
        with lock:
            gate7Finished = 1
            #print(7)
        await asyncio.sleep(0.5)

async def gate8():
    door15 = 5
    door16 = 4
    toy.servo[door15].set_pulse_width_range(500,2800)
    toy.servo[door16].set_pulse_width_range(500,2700)
    countGate8 = 0
    i=5
    global gate8Finished
    global sub_count
    global startGates
    while True:

#         if countGate2 == 0:
#             i = 3
#         elif countGate2 > 0:
#             i = 4
        if gate8Finished == 0 and mother_list[count-i]!=0 and startGates==1:
            print(sub_count[7]," gate8")
            print(count)
            print(mother_list[count-i][sub_count[7]-1])
            print(mother_list[count-i][sub_count[7]])
            if (mother_list[count-i][sub_count[7]-1]["content"]!="empty" and
                mother_list[count-i][sub_count[7]-1]["size"] == "XLarge" and
                mother_list[count-i][sub_count[7]-1]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[7]-1]["name"] == "Balut16"):
                print("Enters1")
                toy.servo[door16].angle = 0
                await asyncio.sleep(1)
                toy.servo[door16].angle = 90
                await asyncio.sleep(1)
            if (mother_list[count-i][sub_count[7]]["content"]!="empty" and
                mother_list[count-i][sub_count[7]]["size"] == "XLarge" and
                mother_list[count-i][sub_count[7]-1]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[7]]["name"] == "Balut16"):
                print("Enters2")
                toy.servo[door15].angle = 180
                await asyncio.sleep(1)
                toy.servo[door15].angle = 90
                await asyncio.sleep(1)
#             countGate2 = countGate2 + 1
#             if countGate2>3:
#                 countGate2 = 0
#             gate8Finished = 1
        #else:
        with lock:
            gate8Finished = 1
            #print(8)
        await asyncio.sleep(0.5)


async def gate9():
    door17 = 3
    door18 = 2
    toy.servo[door17].set_pulse_width_range(500,2900)
    toy.servo[door18].set_pulse_width_range(500,2900)
    countGate9 = 0
    locker = 0
    global gate9Finished
    global sub_count
    global startGates
    while True:
        if sub_count[8] == 2:
            locker = 1
        if countGate9 == 0:
            i = 5
        elif countGate9 > 1:
            i = 6
        if gate9Finished == 0 and mother_list[count-i]!=0 and startGates==1 and locker == 1:
            print(sub_count[8]," gate9")
            print(count)
            print(mother_list[count-i][sub_count[8]-1])
            print(mother_list[count-i][sub_count[8]])
            if (mother_list[count-i][sub_count[8]-1]["content"]!="empty" and
                mother_list[count-i][sub_count[8]-1]["size"] == "XLarge" and
                mother_list[count-i][sub_count[8]-1]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[8]-1]["name"] == "Penoy"):
                print("Enters1")
                toy.servo[door18].angle = 100 
                await asyncio.sleep(1)
                toy.servo[door18].angle = 175
                await asyncio.sleep(1)
            if (mother_list[count-i][sub_count[8]]["content"]!="empty" and
                mother_list[count-i][sub_count[8]]["size"] == "XLarge" and
                mother_list[count-i][sub_count[8]]["integrity"] != "Crack" and
                mother_list[count-i][sub_count[8]]["name"] == "Penoy"):
                print("Enters2")
                toy.servo[door17].angle = 180
                await asyncio.sleep(1)
                toy.servo[door17].angle = 90
                await asyncio.sleep(1)
            countGate9 = countGate9 + 1
            if countGate9>2:
                locker = 0
                countGate9 = 0
#             gate9Finished = 1
        #else:
        with lock:
            gate9Finished = 1
            #print(9)
        await asyncio.sleep(0.5)
        
async def gate10():
    door19 = 1
    door20 = 0
    toy.servo[door19].set_pulse_width_range(500,2800)
    toy.servo[door20].set_pulse_width_range(500,2500)
    countGate10 = 0
    locker = 0
    global gate10Finished
    global sub_count
    global startGates
    while True:
        if sub_count[9] == 2:
            locker = 1
        if countGate10 == 0:
            i = 5
        elif countGate10 > 0:
            i = 6
        if gate10Finished == 0 and mother_list[count-i]!=0 and startGates==1 and locker == 1:
            print(sub_count[9]," gate10")
            print(count)
            print(mother_list[count-i][sub_count[9]-1])
            print(mother_list[count-i][sub_count[9]])
            if (mother_list[count-i][sub_count[9]-1]["content"]=="empty" or
                mother_list[count-i][sub_count[9]-1]["integrity"] == "Crack"):
                print("Enters1")
                toy.servo[door20].angle = 0
                await asyncio.sleep(1)
                toy.servo[door20].angle = 90
                await asyncio.sleep(1)
            if (mother_list[count-i][sub_count[9]]["content"]=="empty" or
                mother_list[count-i][sub_count[9]]["name"] == "Crack"):
                print("Enters2")
                toy.servo[door19].angle = 180
                await asyncio.sleep(1)
                toy.servo[door19].angle = 85
                await asyncio.sleep(1)
            countGate10 = countGate10 + 1
            if countGate10>2:
                locker = 0
                countGate10 = 0
#             gate10Finished = 1
        #else:
        with lock:
            gate10Finished = 1
            #print(10)
        await asyncio.sleep(0.5)
        
def turnVStop():
    bit1.off()
    bit2.off()
    
def turnV(incr):
    if incr == 1:
        bit1.off()
        bit2.on()
    if incr == 2:
        bit1.on()
        bit2.off()
    if incr == 3:
        bit1.on()
        bit2.on()
    while True:
        if not signalStop.is_pressed:
            turnVStop()
            break
# bitH = LED(23)
# signalStopH = Button(24)

def turnH():
    bitH.on()
    while True:
        if not signalStopH.is_pressed:
            bitH.off()
            break

def print_rcv():
    global checkIfLoopDone
    global key_pressed
    global hasReceived
    global hasReceived1
    global hasReceived2
    global gate1Finished
    global gate2Finished
    global gate3Finished
    global gate4Finished
    global gate5Finished
    global gate6Finished
    global gate7Finished 
    global gate8Finished 
    global gate9Finished
    global gate10Finished
    global sub_count
    global startGates
    global bit1
    global bit2
    global bitH

    for i in range(50):
        #---------
        '''
        key_pressed = input("VConveyor belt has completed one motion?: ")
        if key_pressed == "p":
            print(mother_list)
            print(count)
        #--------
        '''
        
        checkIfLoopDone = 0
        if i == 0:
            arduino.write("1".encode("utf-8"))
            arduino.flush()
            while True:
                if  arduino.in_waiting>0:
                    msg = arduino.readline().decode('utf-8')
                    print(msg)
                    break
            arduino.reset_input_buffer()
       
        print(count)
        if hasReceived == 0 and hasReceived1 == 0 and hasReceived2 == 0:
            while True:
#                 print(f"{hasReceived} {hasReceived1} {hasReceived2} {i}")
                if hasReceived > 5 and hasReceived1 > 5 and hasReceived2 > 5:       
                    break
        if mother_list[count]!=0 or mother_list[count-1]!=0:
            sleep(5)
            checkIfLoopDone=1
            print(mother_list)
            
        mother_counter()
        
        
        
        arduino.write("2".encode("utf-8"))
        arduino.flush()
        while True:
            if  arduino.in_waiting>0:
                msg = arduino.readline().decode('utf-8')
                print(msg)
                break
        arduino.reset_input_buffer()
        arduino.write("4".encode("utf-8"))
        arduino.flush()
        while True:
            if  arduino.in_waiting>0:
                msg = arduino.readline().decode('utf-8')
                print(msg)
                break
        arduino.reset_input_buffer()
        startGates = 1
        sub_count[0] = 4
        sub_count[1] = 2
        sub_count[2] = 6
        sub_count[3] = 4
        sub_count[4] = 2
        sub_count[5] = 6
        sub_count[6] = 4
        sub_count[7] = 2
        sub_count[8] = 6
        sub_count[9] = 4
        with lock:
            gate1Finished = 0
            gate2Finished = 0
            gate3Finished = 0
            gate4Finished = 0
            gate5Finished = 0
            gate6Finished = 0
            gate7Finished = 0
            gate8Finished = 0
            gate9Finished = 0
            gate10Finished = 0
        print(count," first")
        while True:
#             print("1",gate1Finished," ", gate2Finished ," ", gate3Finished ," ",
#                 gate4Finished ," ", gate5Finished ," ", gate6Finished ," ",
#                 gate7Finished ," ", gate8Finished ," ", gate9Finished ," ",
#                 gate10Finished)
            if (gate1Finished == 1 and gate2Finished == 1 and gate3Finished ==1 and
                gate4Finished == 1 and gate5Finished == 1 and gate6Finished ==1 and
                gate7Finished == 1 and gate8Finished == 1 and gate9Finished ==1 and
                gate10Finished==1):
                break
        startGates=0
        
        arduino.write("2".encode("utf-8"))
        arduino.flush()
        while True:
            if  arduino.in_waiting>0:
                msg = arduino.readline().decode('utf-8')
                print(msg)
                break
        arduino.reset_input_buffer()
        arduino.write("4".encode("utf-8"))
        arduino.flush()
        while True:
            if  arduino.in_waiting>0:
                msg = arduino.readline().decode('utf-8')
                print(msg)
                break
        arduino.reset_input_buffer()
        #print("finished2")
        startGates=1
        sub_count[0] = 6
        sub_count[1] = 4
        if i>1:
            sub_count[2] = 2
        sub_count[3] = 6
        sub_count[4] = 4
        if i>2:
            sub_count[5] = 2
        sub_count[6] = 6
        sub_count[7] = 4
        if i>3:
            sub_count[8] = 2
        sub_count[9] = 6
        with lock:
            gate1Finished = 0
            gate2Finished = 0
            gate3Finished = 0
            gate4Finished = 0
            gate5Finished = 0
            gate6Finished = 0
            gate7Finished = 0
            gate8Finished = 0
            gate9Finished = 0
            gate10Finished = 0
        while True:
#             print("2", gate1Finished," ", gate2Finished ," ", gate3Finished ," ",
#                 gate4Finished ," ", gate5Finished ," ", gate6Finished ," ",
#                 gate7Finished ," ", gate8Finished ," ", gate9Finished ," ",
#                 gate10Finished)
            if (gate1Finished == 1 and gate2Finished == 1 and gate3Finished ==1 and
                gate4Finished == 1 and gate5Finished == 1 and gate6Finished ==1 and
                gate7Finished == 1 and gate8Finished == 1 and gate9Finished ==1 and
                gate10Finished==1):
                break
        startGates=0
        
        bit1.on()
        bit2.on()
        
        arduino.write("3".encode("utf-8"))
        arduino.flush()
        while True:
            if  arduino.in_waiting>0:
                msg = arduino.readline().decode('utf-8')
                print(msg)
                break
        arduino.reset_input_buffer()
        arduino.write("4".encode("utf-8"))
        arduino.flush()
        while True:
            if  arduino.in_waiting>0:
                msg = arduino.readline().decode('utf-8')
                print(msg)
                break
        arduino.reset_input_buffer()
        startGates=1
        if i>0:
            sub_count[0] = 2
        sub_count[1] = 6
        sub_count[2] = 4
        if i>1:
            sub_count[3] = 2
        sub_count[4] = 6
        sub_count[5] = 4
        if i>2:
            sub_count[6] = 2
        sub_count[7] = 6
        sub_count[8] = 4
        if i>3:
            sub_count[9] = 2
        with lock:
            gate1Finished = 0
            gate2Finished = 0
            gate3Finished = 0
            gate4Finished = 0
            gate5Finished = 0
            gate6Finished = 0
            gate7Finished = 0
            gate8Finished = 0
            gate9Finished = 0
            gate10Finished = 0
        while True:
#             print("3", gate1Finished," ", gate2Finished ," ", gate3Finished ," ",
#                 gate4Finished ," ", gate5Finished ," ", gate6Finished ," ",
#                 gate7Finished ," ", gate8Finished ," ", gate9Finished ," ",
#                 gate10Finished)
            if (gate1Finished == 1 and gate2Finished == 1 and gate3Finished ==1 and 
                gate4Finished == 1 and gate5Finished == 1 and gate6Finished ==1 and
                gate7Finished == 1 and gate8Finished == 1 and gate9Finished ==1 and
                gate10Finished==1):
                break
                break
        startGates = 0
        hasReceived = 0
        hasReceived1 = 0
        hasReceived2 = 0

def mother_counter():
    global count
    count = count + 1
    if count == max_count:
        count = 0
        
async def send(websocket):
    global name
    global key_pressed
    #-------
    '''
    if key_pressed == "p":
        mother_counter()
        key_pressed = ""
    #-------
        '''
    await websocket.send([str(count),str(checkIfLoopDone)])
    
async def receive(websocket):
    global mother_list
    global count
    global greeting
    global hasReceived
    tmp = []
    greeting = await websocket.recv()
    #print("has received")
    if greeting == "[]":
        pass
    else:
        hasReceived = hasReceived + 1
        tmp.append(json.loads(greeting))
        if tmp[0][0] == 0:
            mother_list[count] = tmp[0]
        tmp.clear()

async def receive1(websocket):
    global mother_list
    global count
    global greeting1
    global hasReceived1
    tmp = []
    greeting1 = await websocket.recv()
    #print("has received")
    if greeting1 == "[]":
        pass
    else:
        hasReceived1 = hasReceived1 + 1
        tmp.append(json.loads(greeting1))
        if tmp[0][0] == 1 and mother_list[count-1]!=0:
            for i in range(1,7):
                if (len(mother_list[count-1])>i and i<len(tmp[0]) and
                    tmp[0][i]["content"]!="empty"):
#                     print(i," ", tmp[0][i]["name"])
                    mother_list[count-1][i]["size"] = tmp[0][i]["name"]
#                     print(i," ",mother_list[count-1][i]["size"])
        tmp.clear()
        
async def receive2(websocket):
    global mother_list
    global count
    global greeting2
    global hasReceived2
    tmp = []
    greeting2 = await websocket.recv()
    #print("has received")
    if greeting2 == "[]":
        pass
    else:
        hasReceived2 = hasReceived2 + 1
        tmp.append(json.loads(greeting2))
        if tmp[0][0] == 2 and mother_list[count-1]!=0:
            for i in range(1,7):
                if (len(mother_list[count-1])>i and i<len(tmp[0]) and
                    mother_list[count-1][i]["content"]!="empty" and
                    tmp[0][i]["content"]!="empty"):
#                     print(tmp[0][i]["integrity"])
                    mother_list[count-1][i]["integrity"] = tmp[0][i]["integrity"]
        tmp.clear()

async def detect():
    if (IO1.port[6] or IO1.port[8] or IO2.port[1] or IO2.port[2] or
        IO2.port[3] or IO2.port[8] or IO2.port[9] or IO2.port[10] or
        IO2.port[12] or IO2.port[13]):
        subprocess.run(["pkill", "-9", "python3"])
        pass
 
async def socket():
    uri = "ws://192.168.0.1:8000"
    while True:
         async with websockets.connect(uri) as websocket:
            await asyncio.gather(send(websocket),receive(websocket))
            
async def socket1():
    uri = "ws://192.168.0.1:8001"
    while True:
         async with websockets.connect(uri) as websocket:
            await receive1(websocket)
            
async def socket2():
    uri = "ws://192.168.0.1:8002"
    while True:
         async with websockets.connect(uri) as websocket:
            await receive2(websocket)

async def gatherAll():
    await asyncio.gather(socket(),socket1(),socket2())
#     await detect()
   
async def gatherGates():
    await asyncio.gather(gate1(),gate2(),gate3(),gate4(),gate5(),
                         gate6(),gate7(),gate8(),gate9(),gate10())
    

def asyncioThread1():
    asyncio.run(gatherAll())
    
def asyncioThread2():
    asyncio.run(gatherGates())

if __name__ == "__main__":
    t1 = threading.Thread(target=print_rcv,args=())
    t2 = threading.Thread(target=asyncioThread1)
    t3 = threading.Thread(target=asyncioThread2)
    
    t1.start()
    t2.start()
    t3.start()


 

   
    


