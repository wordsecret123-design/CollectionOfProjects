//vertical
#define dirV 2
#define pulV 3
#define triggerV 4
#define stepsVIncrement 1350
#define stepsVFinalIncr 3000
#define stepsV 5700
//Horizontal
#define dirH 5
#define pulH 8
#define triggerH 7
#define stepsH 3200
#define bit1 6
#define bit2 9
#define signalStopV 10
#define bitH 11
#define signalStopH 12

void runV(int stepsVV){
  int countV = 0;
  for(int i=0;i<=stepsVV;i++){

    digitalWrite(pulV,HIGH);
    delayMicroseconds(500);
    digitalWrite(pulV,LOW);
    delayMicroseconds(500);
    countV++;
    if (digitalRead(triggerV)==LOW && countV>1000){
      countV = 0;
      break;  
    }
  }
  
}

void runH(int stepsHH){
  int countH = 0;
  int detect = 0;
  for(int i=0;i<=stepsHH;i++){

    digitalWrite(pulH,HIGH);
    delayMicroseconds(500);
    digitalWrite(pulH,LOW);
    delayMicroseconds(500);
    countH++;
    if (digitalRead(triggerH)==LOW&&countH>400){
      countH=0;
      detect = 1;
      break;  
    }
  }
  
  
  
}


void setup() {
  Serial.begin(9600);
  Serial.setTimeout(1);
  pinMode(bit1, INPUT);
  pinMode(bit2, INPUT);
  pinMode(signalStopV, OUTPUT);
  pinMode(bitH, INPUT);
  pinMode(signalStopH, OUTPUT);
  pinMode(dirV,OUTPUT);
  pinMode(pulV,OUTPUT);
  pinMode(triggerV, INPUT);
  digitalWrite(dirV,HIGH);
  pinMode(dirH,OUTPUT);
  pinMode(pulH,OUTPUT);
  pinMode(triggerH, INPUT);
  digitalWrite(dirH,LOW);
 
  while (Serial.available()>0){
    Serial.read();
  }
  
  
}

void loop() {
   while(Serial.available()==0){}
   String message = Serial.readString();
  if (message=="1"){
    runV(stepsV);
    Serial.print("1");
  }else if (message=="2"){
    runV(stepsVIncrement);
    Serial.print("1");
  }else if (message=="3"){
    runV(stepsVFinalIncr);
    Serial.print("1");
  }
  if(message=="4"){
    runH(stepsH);
    Serial.print("1");
  }



  


    

    

}

