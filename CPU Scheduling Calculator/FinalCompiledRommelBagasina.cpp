#include <stdio.h>
#include <iostream>
#include <stdlib.h>

struct group{
        int processNumber;
        int arrivalTime;
        int burstTime;
        int turnAroundTime;
        int waitingTime;
        int completionTime;
        
    };
    
struct group tempFCFS;

typedef struct node{
	int processNumber;
	int arrivalTime;
	int burstTime;
	int completionTime;
	int turnaroundTime;
	int waitingTime;
	int hasArrived;
	int last;
	int isPreempTed;
	int isDuplicateArrivalTime;
	int isNotDuplicateArrivalTime;
	int isDuplicate;
	int isDuplicate2;
	int finalBurstTime;
	int priority;
	
	struct node *next;
	struct node *prev;
}Node;
	
Node *first = NULL, *temp = NULL, *last = NULL;
Node *first1=NULL, *temp1=NULL, *last1 = NULL;

int sizeGlobal=0;

void insertArrivalTime(int x){

	if(first==NULL){
		
		first= (Node*)malloc(sizeof(Node));
		first->arrivalTime=x;
		first->hasArrived=0;
		first->last=0;
		first->isDuplicateArrivalTime=0;
		first->isPreempTed=0;
		first->isNotDuplicateArrivalTime=1;
		first->isDuplicate = 0;
		first->isDuplicate2=0;
		first->processNumber=sizeGlobal+1;
		first->next = NULL;
		first->prev = NULL;
		temp=first;
	}
	else{
		last = (Node*)malloc(sizeof(Node));
		last->arrivalTime= x;
		last->hasArrived=0;
		last->last = 0;
		last->isDuplicateArrivalTime=0;
		last->isPreempTed=0;
		last->isNotDuplicateArrivalTime=1;
		last->isDuplicate = 0;
		last->isDuplicate2=0;
		last->processNumber=sizeGlobal+1;
		last->next = NULL;
		temp->next =last;
		last->prev = temp;
		temp=last;
	}
	
	sizeGlobal++;	
}

	
void insertBurstTime(int x, int size){
	Node *hold = first;
	
	
	for(int i=0;i<size;i++){
		
		if(i==size-1){
			hold->burstTime=x;
		}
		hold=hold->next;
	}
	
	
}

void insertPriority(int x, int size){
	Node *hold = first;
	
	
	for(int i=0;i<size;i++){
		
		if(i==size-1){
			hold->priority=x;
		}
		hold=hold->next;
	}
	
	
}

void printList(Node *ptr){
	Node *hold = ptr;
	
	while(hold!=NULL){
		
		
		printf("\nPN:AT:BT:CT:TAT:WT:PT %d:%d:%d:%d:%d:%d:%d\n",hold->processNumber,hold->arrivalTime,hold->burstTime,hold->completionTime,hold->turnaroundTime,hold->waitingTime,hold->priority);
		hold = hold->next;
		
	}
}



void sortList(Node *ptr){
	Node *hold = ptr;
	int tempArrivalTime;
	int tempBurstTime;
	int tempProcessNumber;
	int tempPriority;
	
	while(ptr){
		
	
		hold = ptr->next;
		while(hold){
			if(ptr->arrivalTime>hold->arrivalTime){
				tempArrivalTime = ptr->arrivalTime;
				tempBurstTime = ptr->burstTime;
				tempProcessNumber = ptr->processNumber;
				tempPriority = ptr->priority;
				
				ptr->burstTime = hold->burstTime;
				ptr->arrivalTime = hold->arrivalTime;
				ptr->processNumber=hold->processNumber;
				ptr->priority = hold->priority;
				
				hold->burstTime = tempBurstTime; 
				hold->arrivalTime = tempArrivalTime;
				hold->processNumber=tempProcessNumber;
				hold->priority = tempPriority;
			}
			
			hold = hold->next;
		}
		
		ptr=ptr->next;
		
	}
}

void sortListBasedOnBT(Node *ptr){
	Node *hold = ptr;
	int tempArrivalTime;
	int tempBurstTime;
	int tempProcessNumber;
	int tempCompletionTime;
	int tempTurnaroundTime;
	int tempWaitingTime;
	
	while(ptr){
		
	
		hold = ptr->next;
		while(hold){
			if(ptr->burstTime>hold->burstTime){
				tempArrivalTime = ptr->arrivalTime;
				tempBurstTime = ptr->burstTime;
				tempProcessNumber = ptr->processNumber;
				tempCompletionTime = ptr->completionTime;
				tempTurnaroundTime = ptr->turnaroundTime;
				tempWaitingTime = ptr->waitingTime;
				
				ptr->burstTime = hold->burstTime;
				ptr->arrivalTime = hold->arrivalTime;
				ptr->processNumber=hold->processNumber;
				ptr->completionTime=hold->completionTime;
				ptr->turnaroundTime=hold->turnaroundTime;
				ptr->waitingTime = hold->waitingTime;
				
				
				hold->burstTime = tempBurstTime; 
				hold->arrivalTime = tempArrivalTime;
				hold->processNumber = tempProcessNumber;
				hold->completionTime = tempCompletionTime;
				hold->turnaroundTime = tempTurnaroundTime;
				hold->waitingTime = tempWaitingTime;
			}
			
			hold = hold->next;
		}
		
		ptr=ptr->next;
		
	}
}

void sortListBasedOnBT2(Node *ptr, int completionTime){
	Node *hold = ptr;
	int tempArrivalTime;
	int tempBurstTime;
	int tempProcessNumber;
	int tempCompletionTime;
	int tempTurnaroundTime;
	int tempWaitingTime;
	
	while(ptr){
		
	
		hold = ptr->next;
		while(hold){
			
			
			if(completionTime>=hold->arrivalTime&&completionTime>=ptr->arrivalTime&&(ptr->burstTime>hold->burstTime||(ptr->burstTime==hold->burstTime&&ptr->arrivalTime>hold->arrivalTime))){
						
				tempArrivalTime = ptr->arrivalTime;
				tempBurstTime = ptr->burstTime;
				tempProcessNumber = ptr->processNumber;
				tempCompletionTime = ptr->completionTime;
				tempTurnaroundTime = ptr->turnaroundTime;
				tempWaitingTime = ptr->waitingTime;
				
				ptr->burstTime = hold->burstTime;
				ptr->arrivalTime = hold->arrivalTime;
				ptr->processNumber=hold->processNumber;
				ptr->completionTime=hold->completionTime;
				ptr->turnaroundTime=hold->turnaroundTime;
				ptr->waitingTime = hold->waitingTime;
			//	ptr->hasArrived=1;
				
				hold->burstTime = tempBurstTime; 
				hold->arrivalTime = tempArrivalTime;
				hold->processNumber = tempProcessNumber;
				hold->completionTime = tempCompletionTime;
				hold->turnaroundTime = tempTurnaroundTime;
				hold->waitingTime = tempWaitingTime;
			//	hold->hasArrived=1;
			}
			
			
			hold = hold->next;
		}
		
		ptr=ptr->next;
		
	}
}


void sortListBasedOnPriority(Node *ptr){
	Node *hold = ptr;
	int tempArrivalTime;
	int tempBurstTime;
	int tempProcessNumber;
	int tempCompletionTime;
	int tempTurnaroundTime;
	int tempWaitingTime;
	int tempPriority;
	
	while(ptr){
		
	
		hold = ptr->next;
		while(hold){
			if(ptr->priority>hold->priority){
				tempArrivalTime = ptr->arrivalTime;
				tempBurstTime = ptr->burstTime;
				tempProcessNumber = ptr->processNumber;
				tempCompletionTime = ptr->completionTime;
				tempTurnaroundTime = ptr->turnaroundTime;
				tempWaitingTime = ptr->waitingTime;
				tempPriority = ptr->priority;
				
				ptr->burstTime = hold->burstTime;
				ptr->arrivalTime = hold->arrivalTime;
				ptr->processNumber=hold->processNumber;
				ptr->completionTime=hold->completionTime;
				ptr->turnaroundTime=hold->turnaroundTime;
				ptr->waitingTime = hold->waitingTime;
				ptr->priority = hold->priority;
				
				
				hold->burstTime = tempBurstTime; 
				hold->arrivalTime = tempArrivalTime;
				hold->processNumber = tempProcessNumber;
				hold->completionTime = tempCompletionTime;
				hold->turnaroundTime = tempTurnaroundTime;
				hold->waitingTime = tempWaitingTime;
				hold->priority = tempPriority;
			}
			
			hold = hold->next;
		}
		
		ptr=ptr->next;
		
	}
}


void sortListBasedOnPriority2(Node *ptr, int completionTime){
	Node *hold = ptr;
	int tempArrivalTime;
	int tempBurstTime;
	int tempProcessNumber;
	int tempCompletionTime;
	int tempTurnaroundTime;
	int tempWaitingTime;
	int tempPriority;
	
	while(ptr){
		
	
		hold = ptr->next;
		while(hold){
			
			
			if(completionTime>=hold->arrivalTime&&completionTime>=ptr->arrivalTime&&(ptr->priority>hold->priority||(ptr->priority==hold->priority&&ptr->arrivalTime>hold->arrivalTime))){
						
				tempArrivalTime = ptr->arrivalTime;
				tempBurstTime = ptr->burstTime;
				tempProcessNumber = ptr->processNumber;
				tempCompletionTime = ptr->completionTime;
				tempTurnaroundTime = ptr->turnaroundTime;
				tempWaitingTime = ptr->waitingTime;
				tempPriority = ptr->priority;
				
				ptr->burstTime = hold->burstTime;
				ptr->arrivalTime = hold->arrivalTime;
				ptr->processNumber=hold->processNumber;
				ptr->completionTime=hold->completionTime;
				ptr->turnaroundTime=hold->turnaroundTime;
				ptr->waitingTime = hold->waitingTime;
				ptr->priority = hold->priority;
			//	ptr->hasArrived=1;
				
				hold->burstTime = tempBurstTime; 
				hold->arrivalTime = tempArrivalTime;
				hold->processNumber = tempProcessNumber;
				hold->completionTime = tempCompletionTime;
				hold->turnaroundTime = tempTurnaroundTime;
				hold->waitingTime = tempWaitingTime;
				hold->priority = tempPriority;
			//	hold->hasArrived=1;
			}
			
			
			hold = hold->next;
		}
		
		ptr=ptr->next;
		
	}
}

int main(){
	
int choice;

printf("Choose s scheduling algorithm: \n");
printf("1) FCFS\n");
printf("2) SJF non-preemptive\n");
printf("3) SJF preemptive\n");
printf("4) Priority non-preemptive\n");
printf("5) Priority preemptive\n");
printf("6) Round Robin\n");
	
scanf("%d", &choice);

system("cls");
	
if(choice == 1){
	printf("FCFS\n");
	int size,i,j,tempCT, sum1=0,sum2=0;
    float ave1,ave2;
        
    printf("Enter number of procecesses: ");
    scanf("%d",&size);
    
    struct group process[size];
     
    printf("Enter Arrival Time of each process:\n");
    
    for(i=0;i<size;i++){
        scanf("%d", &process[i].arrivalTime);
        process[i].processNumber = i+1;
       
    }
	
	printf("Enter Burst Time for each process:\n");
	
    for(i=0;i<size;i++){
        scanf("%d",&process[i].burstTime);
    }
    
   
    for(i=0;i<size;i++){
    	int j = i+1;
    	for(int j=i+1;j<size;j++){
			if(process[i].arrivalTime>process[j].arrivalTime){
				tempFCFS = process[i];
				process[i] = process[j];
				process[j]=tempFCFS;
			}
		}	
		
	}
	

	for(i=0;i<size;i++){
		if(i==0){
			process[i].completionTime = process[i].arrivalTime+process[i].burstTime;
			process[i].turnAroundTime = process[i].completionTime - process[i].arrivalTime;
			process[i].waitingTime = process[i].turnAroundTime - process[i].burstTime;
		}else{
			
			if(process[i].arrivalTime<tempCT){
				process[i].completionTime = tempCT+process[i].burstTime;
			}else{
				process[i].completionTime = process[i].arrivalTime+process[i].burstTime;
			}
			process[i].turnAroundTime = process[i].completionTime - process[i].arrivalTime;
			process[i].waitingTime = process[i].turnAroundTime - process[i].burstTime;
		}
		tempCT = process[i].completionTime;
		
	}
	
	printf("\n");
	
	int completionTime=0, p,decrement,max;
	int size2=size;
	
	for(i=0;i<size2;i++){
		
		
		if(process[i-decrement].arrivalTime>completionTime){
			size2++;
			max = process[i-decrement].arrivalTime - completionTime;
			
			for(p=0;p<max+1;p++){
				
				if(i==0&&p==0){
					printf("|%d|",completionTime);
				}
				else if(p==max){
					printf("|%d|",completionTime);
				}
				else if(p==max/2){
					printf("-e-");
				}
				else{
					printf("-");
				}
			
				if(p!=max){
					completionTime++;
				}
			}
			decrement++;
			
		}
		else{
			for(j=0;j<process[i-decrement].burstTime+1;j++){
			
				if(i==0&&j==0){
					printf("|%d|",completionTime);
				}
				else if(process[i-decrement].burstTime==1&&j==process[i-decrement].burstTime&&i==0){
					printf("-P%d-|%d|",process[i-decrement].processNumber,completionTime);
				}
				else if(j==process[i-decrement].burstTime){
					printf("|%d|",completionTime);
				
				}
				else if(j==process[i-decrement].burstTime/2){
					printf("-P%d-",process[i-decrement].processNumber);
				}
				else{
					printf("-");
				}
			
				if(j!=process[i-decrement].burstTime){
					completionTime++;
				}
			
			}
			
		}
		
		
	}
	
	
	printf("\n\n");
	printf("No. Of Process |Arrival Time|Burst Time|Turnaround Time|Waiting Time|Completion Time|\n");

	int testing;
	
	for (i = 0; i < size; i++) {
		printf("%14d |%12d|%10d|%15d|%12d|%15d|\n", process[i].processNumber, process[i].arrivalTime, process[i].burstTime, process[i].turnAroundTime, process[i].waitingTime,process[i].completionTime);
	
		sum1 += process[i].turnAroundTime;
		sum2 += process[i].waitingTime;
	}
	
	ave1 = (float)sum1/(float)size;
	ave2 = (float)sum2/(float)size;
	
	printf("\n Average Turnaround Time: %.3f." , ave1);
	printf("\n Average Waiting Time: %.3f.\n" , ave2);
	
	system("pause");
}
	
if(choice == 2){
	
	printf("SJF non-preemptive\n");		
	int size,i,sizeBurstTime=0;
	printf("Enter size: ");
	scanf("%d",&size);
	
	printf("Enter Arrival Time of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
		insertArrivalTime(x);
	}
	
	printf("Enter Burst Time of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
	 	sizeBurstTime++;
		insertBurstTime(x,sizeBurstTime);
	}
	
	
	sortList(first);
	
	Node *ptr = first, *hold1,*hold2;
	int completionTime=1;
	int flag=0;
	ptr->last=1;
	
	while(ptr){
		
		for(i=0;i<ptr->burstTime;i++){
			Node *hold = ptr->next;
			//printf("enters here %d:%d:%d\n", completionTime,ptr->arrivalTime,ptr->burstTime);
			
		
			while(hold){
				
			//	printf("enters here %d:%d:%d\n", completionTime,hold->arrivalTime,ptr->arrivalTime);
				
				if(completionTime>=hold->arrivalTime&&hold->hasArrived==0){
					
					if(first1==NULL){
					//	printf("enters here %d\n",hold->arrivalTime);
						first1=hold;
						last1=hold;
						hold->hasArrived=1;
						hold1=hold->prev;
						hold->prev->next=hold->next;
						hold->next->prev=hold->prev;
						last1->next=NULL;
						first1->prev=NULL;
						hold=hold1;
						flag=1;
						
					}
					else if(hold->next==NULL){
					//	printf("enters here\n");
						hold->hasArrived=1;
						hold->prev->next==NULL;
						last1->next = hold;
						hold->prev=last1; 
						hold->next=NULL;
					}
					else{
					//	printf("enters here %d  ;  %d\n", hold->burstTime,hold->arrivalTime);
						hold1=hold->prev;
						hold->hasArrived=1;
						hold->prev->next=hold->next;
						hold->next->prev=hold->prev;
						last1->next=hold;
						last1->next->prev=last1;
						last1=hold;
						last1->next=NULL;
						hold = hold1;
						flag =1;
					}
					
				}
				
				hold = hold->next;
				
			}
			
			hold2 = first1;
			
			
			
			
			ptr->completionTime=completionTime;
			ptr->turnaroundTime=completionTime-ptr->arrivalTime;
			ptr->waitingTime=ptr->turnaroundTime-ptr->burstTime;
			
			if(ptr->arrivalTime>completionTime-1){
				completionTime = ptr->arrivalTime+ptr->burstTime;
				ptr->completionTime=completionTime;
				ptr->turnaroundTime=completionTime-ptr->arrivalTime;
				ptr->waitingTime=ptr->turnaroundTime-ptr->burstTime;
				ptr->last=1;
				i=ptr->burstTime;
			}
			
			
			
				completionTime++;
			
			
			
		}
		
		
		
		
		if(flag==1&&ptr->last==1){
			sortListBasedOnBT(first1);
	
			last1->next = ptr->next;
			ptr->next->prev=last1;
			ptr->next=first1;
			first1->prev=ptr;
			flag=0; 
			last1->last=1;
			first1=NULL;	
		}
		
		
		ptr = ptr->next;
	}
	
	//printList(first);
	
	
	ptr = first;
	int sum1=0, sum2=0;
	float ave1, ave2;
	
	printf("\n\n");
	printf("No. Of Process |Arrival Time|Burst Time|Turnaround Time|Waiting Time|Completion Time|\n");
	
	while (ptr) {
		printf("%14d |%12d|%10d|%15d|%12d|%15d|\n", ptr->processNumber, ptr->arrivalTime, ptr->burstTime, ptr->turnaroundTime, ptr->waitingTime,ptr->completionTime);
	
		sum1 += ptr->turnaroundTime;
		sum2 += ptr->waitingTime;
		
		
		
		ptr = ptr->next;
	}
	
	
	ave1 = (float)sum1/(float)size;
	ave2 = (float)sum2/(float)size;

	printf("\n Average Turnaround Time: %.3f." , ave1);
	printf("\n Average Waiting Time: %.3f.\n" , ave2);
	
	
	size=0;
	ptr=first;
	
	while(ptr){
		size++;
		ptr= ptr->next;
	}
	
	Node process[size];
	
	ptr=first; 
	i=0;
	
	while(ptr){
		
		process[i].burstTime = ptr->burstTime;
		process[i].arrivalTime = ptr->arrivalTime;
		process[i].processNumber = ptr->processNumber;
		ptr=ptr->next;
		i++;
	}
	
	printf("\n");
	
	
	completionTime=0;
	int p,decrement=0,max,j;
	int size2=size;
	
	
	for(i=0;i<size2;i++){
		
		
		if(process[i-decrement].arrivalTime>completionTime){
			size2++;
			max = process[i-decrement].arrivalTime - completionTime;
			
			for(p=0;p<max+1;p++){
				
				if(i==0&&p==0){
					printf("|%d|",completionTime);
				}
				else if(p==max){
					printf("|%d|",completionTime);
				}
				else if(p==max/2){
					printf("-e-");
				}
				else{
					printf("-");
				}
			
				if(p!=max){
					completionTime++;
				}
			}
			decrement++;
			
		}
		else{
			for(j=0;j<process[i-decrement].burstTime+1;j++){
			
				if(i==0&&j==0){
					printf("|%d|",completionTime);
				}
				else if(process[i-decrement].burstTime==1&&j==process[i-decrement].burstTime&&i==0){
					printf("-P%d-|%d|",process[i-decrement].processNumber,completionTime);
				}
				else if(j==process[i-decrement].burstTime){
					printf("|%d|",completionTime);
				
				}
				else if(j==process[i-decrement].burstTime/2){
					printf("-P%d-",process[i-decrement].processNumber);
				}
				else{
					printf("-");
				}
			
				if(j!=process[i-decrement].burstTime){
					completionTime++;
				}
			
			}
			
		}
		
		
	}
}

system("pause");

if(choice == 3){
	
	printf("SJF preemptive\n");
	int size,i,sizeBurstTime=0;
	printf("Enter size: ");
	scanf("%d",&size);
	
	printf("Enter Arrival Time of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
		insertArrivalTime(x);
	}
	
	printf("Enter Burst Time of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
	 	sizeBurstTime++;
		insertBurstTime(x,sizeBurstTime);
	}
	
	
	sortList(first);
	
	printf("\n");
	
//	printList(first);
	
	printf("\n");
	
	Node *ptr = first, *hold,*hold1,*hold2,*hold3;
	int completionTime=0, duplicateEnters;
	int tempCompletionTime;
	int flag=0;
	ptr->last=1;
	
	
		
	
	while(ptr){
		hold = ptr->next;
		
		while(hold){
			
			if(ptr->arrivalTime==hold->arrivalTime){
				ptr->isDuplicateArrivalTime=1;
				ptr->isNotDuplicateArrivalTime=0;
				hold->isNotDuplicateArrivalTime=0;
			}
			
			hold=hold->next;
		}
		
	//	printf("%d:%d:%d:%d\n",ptr->processNumber,ptr->arrivalTime,ptr->burstTime,ptr->isDuplicateArrivalTime);
		
		
		ptr=ptr->next;
	}
	
	
	
	ptr=first;
	
	while(ptr){
			
		tempCompletionTime=completionTime;
		
		if(ptr->prev==NULL&&ptr->arrivalTime>0){
			tempCompletionTime = ptr->arrivalTime;
			
		}
	
	
		if(completionTime<ptr->arrivalTime){
			completionTime=ptr->arrivalTime+ptr->burstTime;
		//	printf("enters here 2\n");
		}
		else if(completionTime>=ptr->arrivalTime){
		//	printf("enters here 3 %d\n", completionTime+ptr->burstTime);
			completionTime = completionTime + ptr->burstTime ;
			
		}
		
		ptr->completionTime=completionTime;
		ptr->turnaroundTime=completionTime-ptr->arrivalTime;
	
		
		hold = ptr->next;	
		
		
		while(hold){
			
			
			if(ptr->next==hold){
				
					
			
				
				
				
				duplicateEnters=1;
				hold1 = hold;
				while(hold1){
					
				//	printf("%d:%d:%d:%d\n",hold1->processNumber,hold1->arrivalTime,hold1->burstTime,ptr->processNumber);
					
					if((tempCompletionTime+ptr->burstTime)-hold1->arrivalTime>hold1->burstTime&&hold1->arrivalTime<tempCompletionTime+ptr->burstTime&&
						hold1->arrivalTime>tempCompletionTime){
				
					//	printf("%d:%d:%d:%d\n",hold1->processNumber,hold1->arrivalTime,hold1->burstTime,hold1->isDuplicateArrivalTime);
		
						if(ptr->arrivalTime!=hold1->arrivalTime){
				
							completionTime = hold1->arrivalTime;
							ptr->completionTime=completionTime;
							ptr->turnaroundTime=completionTime-ptr->arrivalTime;
							
							hold2 = hold1->prev;
							
							ptr->next->hasArrived=0;
							ptr->next->last = 0;
							
							
							if(hold1!=hold&&hold1->burstTime<ptr->next->burstTime){
							
								hold2 = hold1->prev;
								
								if(hold1->next!=NULL){
						 			hold1->next->prev = hold1->prev;
								}
						
								hold1->prev->next = hold1->next;
							
							
								hold1->next = ptr->next;
								ptr->next->prev=hold1;
								ptr->next=hold1;
								hold1->prev=ptr;	
								
							}
							 
						
							
							if(duplicateEnters==1){
							
							
								if(first1==NULL){
									first1 = (Node *) malloc(sizeof(Node));
									first1->processNumber = ptr->processNumber;
									first1->arrivalTime = ptr->arrivalTime;
									first1->burstTime = (tempCompletionTime+ptr->burstTime)-hold1->arrivalTime;
									first1->hasArrived=0;
									first1->last=0;
									first1->isNotDuplicateArrivalTime=1;
									first->isDuplicateArrivalTime=0;
									first1->isPreempTed=0;
									first1->isDuplicate = 0;
									first1->isDuplicate2 =0;
									last1 = first1;
									last1->next=NULL;
								}else{
						
									temp1 = (Node *) malloc(sizeof(Node));
									temp1->processNumber = ptr->processNumber;
									temp1->arrivalTime = ptr->arrivalTime;
									temp1->burstTime=(tempCompletionTime+ptr->burstTime)-hold1->arrivalTime;
									temp1->hasArrived=0;
									temp1->last = 0;
									temp1->isNotDuplicateArrivalTime=1;
									temp1->isDuplicateArrivalTime=0;
									temp->isPreempTed=0;
									temp->isDuplicate = 0;
									temp->isDuplicate2 =0;
									temp1->prev=last1;
									last1->next = temp1;
									last1=temp1; 
									last1->next=NULL;
						
								}
								
								
								
								
								
								
							}
					
							hold1->hasArrived=1;
							hold1->last = 1;
							ptr->last=0;
							ptr->isPreempTed=1;
				
					
							
					
							if(hold1->isDuplicateArrivalTime!=1){
								break;
							}
								
							duplicateEnters++;
							
							
							if(hold1!=hold){
								hold1=hold2;
							}
						
					
						}
				
					}
					
					if((hold1->prev->isDuplicateArrivalTime==1&&hold1->isDuplicateArrivalTime==0&&hold1->isNotDuplicateArrivalTime==0)||
						(hold1->prev->isNotDuplicateArrivalTime==1&&hold1->isNotDuplicateArrivalTime==0&&hold1->prev!=ptr)){
					
						break;
					}
					
					
					
					hold1 = hold1->next;
				}
				
				
			
		
				

				
				hold1=ptr->next;
				
				while(hold1){
					
				//	printf("%d:%d:%d:%d\n",hold1->processNumber,hold1->hasArrived,hold1->burstTime,hold1->isPreempTed);
				
					
					if(hold1->hasArrived==0&&hold1->isPreempTed==0){
						
				//		printf("enters here %d:%d:%d\n",hold1->processNumber,hold1->burstTime,ptr->processNumber);
					
						sortListBasedOnBT2(hold1,completionTime);
					
					}  		
			
				hold1 = hold1->next;
					
				}
				
				
				
			}
			
			
	
			hold = hold->next;
		}
		
		
		
		hold = ptr->next;
		
		while(hold){
			
					
			
			
			if(ptr->next==hold&&hold->last==1){
			
				if(hold->next!=NULL){
					hold->next->prev = last1;
				}
			
				last1->next=hold->next;
				first1->prev = hold;
				hold->next=first1;
			
		
				first1=NULL;
				
				
			}
			
			
			hold=hold->next;
		}
		
		

		
		 
		ptr=ptr->next;
	}
	
	ptr=first;
	
	while(ptr){
		hold = ptr->next;
		
		while(hold){
			
			if(ptr->prev==NULL){
				ptr->finalBurstTime=ptr->burstTime;
			}
			
			
			if(ptr->processNumber==hold->processNumber){
				ptr->isDuplicate2 = 1;
			}
			
			
			if(ptr->isDuplicate==0&&ptr->processNumber==hold->processNumber){
				hold->finalBurstTime=ptr->burstTime;
				hold->isDuplicate=1;
			}
			else if(hold->isDuplicate==0){
				
				hold->finalBurstTime = hold->burstTime;
			}
			
			
			
			
			hold=hold->next;
		}
		
		
		ptr->waitingTime=ptr->turnaroundTime-ptr->finalBurstTime;
		
		
		ptr=ptr->next;	
		
	}
	
	
	//printList(first);
	printf("%\n");
	
	
	ptr = first;
	int sum1=0, sum2=0;
	float ave1, ave2;
	
	printf("\n\n");
	printf("No. Of Process |Arrival Time|Burst Time|Turnaround Time|Waiting Time|Completion Time|\n");
	
	while (ptr) {
		
		
		if(ptr->isDuplicate2==0){
			
			printf("%14d |%12d|%10d|%15d|%12d|%15d|\n", ptr->processNumber, ptr->arrivalTime, ptr->finalBurstTime, ptr->turnaroundTime, ptr->waitingTime,ptr->completionTime);
	
			sum1 += ptr->turnaroundTime;
			sum2 += ptr->waitingTime;
			
		}
			
			
			
		
		
		
		
		
		
		ptr = ptr->next;
	}
	
	
	ave1 = (float)sum1/(float)size;
	ave2 = (float)sum2/(float)size;

	printf("\n Average Turnaround Time: %.3f." , ave1);
	printf("\n Average Waiting Time: %.3f.\n" , ave2);
	
	
	size=0;
	ptr=first;
	
	while(ptr){
		size++;
		ptr= ptr->next;
	}
	
	Node process[size];
	
	ptr=first; 
	i=0;
	
	while(ptr){
		
		process[i].burstTime = ptr->burstTime;
		process[i].arrivalTime = ptr->arrivalTime;
		process[i].processNumber = ptr->processNumber;
		process[i].isPreempTed = ptr->isPreempTed;
		ptr=ptr->next;
		i++;
	}
	
	printf("\n");
	
	
	completionTime=0;
	int p,decrement=0,max,j;
	int size2=size;
	
	for(i=0;i<size2;i++){
		
		if(process[i-decrement].isPreempTed==1){
			process[i-decrement].burstTime=process[i-decrement+1].arrivalTime - completionTime;
		}
		
		if(process[i-decrement].arrivalTime>completionTime){
			size2++;
			max = process[i-decrement].arrivalTime - completionTime;
			
			for(p=0;p<max+1;p++){
				
				if(i==0&&p==0){
					printf("|%d|",completionTime);
				}
				else if(p==max){
					printf("|%d|",completionTime);
				}
				else if(p==max/2){
					printf("-e-");
				}
				else{
					printf("-");
				}
			
				if(p!=max){
					completionTime++;
				}
			}
			decrement++;
			
		}
		else{
			for(j=0;j<process[i-decrement].burstTime+1;j++){
			
				if(i==0&&j==0){
					printf("|%d|",completionTime);
				}
				else if(process[i-decrement].burstTime==1&&j==process[i-decrement].burstTime&&i==0){
					printf("-P%d-|%d|",process[i-decrement].processNumber,completionTime);
				}
				else if(j==process[i-decrement].burstTime){
					printf("|%d|",completionTime);
				
				}
				else if(j==process[i-decrement].burstTime/2){
					printf("-P%d-",process[i-decrement].processNumber);
				}
				else{
					printf("-");
				}
			
				if(j!=process[i-decrement].burstTime){
					completionTime++;
				}
			
			}
			
		}
		
		
	}
	
}

system("pause");

if(choice == 4){
	
	printf("Priority non-preemptive\n");
	
	int size,i,sizeBurstTime=0,sizePriority=0;
	printf("Enter size: ");
	scanf("%d",&size);
	
	printf("Enter Arrival Time of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
		insertArrivalTime(x);
	}
	
	printf("Enter Burst Time of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
	 	sizeBurstTime++;
		insertBurstTime(x,sizeBurstTime);
	}
	
	printf("Enter Priority of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
	 	sizePriority++;
		insertPriority(x,sizePriority);
	}
	
	
	sortList(first);
	
	Node *ptr = first, *hold1;
	int completionTime=1;
	int flag=0;
	
	ptr->last=1;
	
	while(ptr){
		
		for(i=0;i<ptr->burstTime;i++){
			Node *hold = ptr->next;
			//printf("enters here %d:%d:%d\n", completionTime,ptr->arrivalTime,ptr->burstTime);
			
		
			while(hold){
				
			//	printf("enters here %d:%d:%d\n", completionTime,hold->arrivalTime,ptr->arrivalTime);
				
				if(completionTime>hold->arrivalTime&&hold->hasArrived==0){
					
					if(first1==NULL){
					//	printf("enters here %d\n",hold->arrivalTime);
						first1=hold;
						last1=hold;
						hold->hasArrived=1;
						hold1=hold->prev;
						hold->prev->next=hold->next;
						hold->next->prev=hold->prev;
						last1->next=NULL;
						first1->prev=NULL;
						hold=hold1;
						flag=1;
						
					}
					else if(hold->next==NULL){
					//	printf("enters here\n");
						hold->hasArrived=1;
						hold->prev->next==NULL;
						last1->next = hold;
						hold->prev=last1; 
						hold->next=NULL;
					}
					else{
					//	printf("enters here %d;%d\n", hold->burstTime,hold->arrivalTime);
						hold1=hold->prev;
						hold->hasArrived=1;
						hold->prev->next=hold->next;
						hold->next->prev=hold->prev;
						last1->next=hold;
						last1->next->prev=last1;
						last1=hold;
						last1->next=NULL;
						hold = hold1;
						flag =1;
					}
					
				}
				
				hold = hold->next;
				
			}
			ptr->completionTime=completionTime;
			ptr->turnaroundTime=completionTime-ptr->arrivalTime;
			ptr->waitingTime=ptr->turnaroundTime-ptr->burstTime;
			
			if(ptr->arrivalTime>completionTime-1){
				completionTime = ptr->arrivalTime+ptr->burstTime;
				ptr->completionTime=completionTime;
				ptr->turnaroundTime=completionTime-ptr->arrivalTime;
				ptr->waitingTime=ptr->turnaroundTime-ptr->burstTime;
				ptr->last = 1;
				i=ptr->burstTime;
			}
			
			
			
				completionTime++;
			
			
			
		}
		
		
		
		
		if(flag==1&&ptr->last==1){
			sortListBasedOnPriority(first1);
	
			last1->next = ptr->next;
			ptr->next->prev=last1;
			ptr->next=first1;
			first1->prev=ptr;
			flag=0; 
			first1=NULL;	
		}
		
		
		ptr = ptr->next;
	}
	 
	ptr = first;
	int sum1=0, sum2=0;
	float ave1, ave2;
	
	printf("\n\n");
	printf("No. Of Process |Arrival Time|Burst Time|Turnaround Time|Waiting Time|Completion Time|Priority|\n");
	
	while (ptr) {
		printf("%14d |%12d|%10d|%15d|%12d|%15d|%8d|\n", ptr->processNumber, ptr->arrivalTime, ptr->burstTime, ptr->turnaroundTime, ptr->waitingTime,ptr->completionTime,ptr->priority);
	
		sum1 += ptr->turnaroundTime;
		sum2 += ptr->waitingTime;
		
		
		
		ptr = ptr->next;
	}
	
	
	ave1 = (float)sum1/(float)size;
	ave2 = (float)sum2/(float)size;

	printf("\n Average Turnaround Time: %.3f." , ave1);
	printf("\n Average Waiting Time: %.3f.\n" , ave2);

	
	size=0;
	ptr=first;
	
	while(ptr){
		size++;
		ptr= ptr->next;
	}
	
	Node process[size];
	
	ptr=first; 
	i=0;
	
	while(ptr){
		
		process[i].burstTime = ptr->burstTime;
		process[i].arrivalTime = ptr->arrivalTime;
		process[i].processNumber = ptr->processNumber;
		ptr=ptr->next;
		i++;
	}
	
	printf("\n");
	
	
	completionTime=0;
	int p,decrement=0,max,j;
	int size2=size;
	
	
	for(i=0;i<size2;i++){
		
		
		if(process[i-decrement].arrivalTime>completionTime){
			size2++;
			max = process[i-decrement].arrivalTime - completionTime;
			
			for(p=0;p<max+1;p++){
				
				if(i==0&&p==0){
					printf("|%d|",completionTime);
				}
				else if(p==max){
					printf("|%d|",completionTime);
				}
				else if(p==max/2){
					printf("-e-");
				}
				else{
					printf("-");
				}
			
				if(p!=max){
					completionTime++;
				}
			}
			decrement++;
			
		}
		else{
			for(j=0;j<process[i-decrement].burstTime+1;j++){
			
				if(i==0&&j==0){
					printf("|%d|",completionTime);
				}
				else if(process[i-decrement].burstTime==1&&j==process[i-decrement].burstTime&&i==0){
					printf("-P%d-|%d|",process[i-decrement].processNumber,completionTime);
				}
				else if(j==process[i-decrement].burstTime){
					printf("|%d|",completionTime);
				
				}
				else if(j==process[i-decrement].burstTime/2){
					printf("-P%d-",process[i-decrement].processNumber);
				}
				else{
					printf("-");
				}
			
				if(j!=process[i-decrement].burstTime){
					completionTime++;
				}
			
			}
			
		}
		
		
	}	
}

system("pause");

if(choice == 5){
		
	printf("Priority preemptive\n");
	int size,i,sizeBurstTime=0,sizePriority ;
	printf("Enter size: ");
	scanf("%d",&size);
	
	printf("Enter Arrival Time of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
		insertArrivalTime(x);
	}
	
	printf("Enter Burst Time of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
	 	sizeBurstTime++;
		insertBurstTime(x,sizeBurstTime);
	}
	
		
	printf("Enter Priority of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
	 	sizePriority++;
		insertPriority(x,sizePriority);
	}
	
	sortList(first);
	
	printf("\n");
	
	//printList(first);
	
	printf("\n");
	
	Node *ptr = first, *hold,*hold1,*hold2,*hold3;
	int completionTime=0, duplicateEnters;
	int tempCompletionTime;
	int flag=0;
	ptr->last=1;
	
	
		
	
	while(ptr){
		hold = ptr->next;
		
		while(hold){
			
			if(ptr->arrivalTime==hold->arrivalTime){
				ptr->isDuplicateArrivalTime=1;
				ptr->isNotDuplicateArrivalTime=0;
				hold->isNotDuplicateArrivalTime=0;
			}
			
			hold=hold->next;
		}
		
	//	printf("%d:%d:%d:%d\n",ptr->processNumber,ptr->arrivalTime,ptr->burstTime,ptr->isDuplicateArrivalTime);
		
		
		ptr=ptr->next;
	}
	
	
	
	ptr=first;
	
	while(ptr){
			
		tempCompletionTime=completionTime;
		
		if(ptr->prev==NULL&&ptr->arrivalTime>0){
			tempCompletionTime = ptr->arrivalTime;
			
		}
	
	
		if(completionTime<ptr->arrivalTime){
			completionTime=ptr->arrivalTime+ptr->burstTime;
		//	printf("enters here 2\n");
		}
		else if(completionTime>=ptr->arrivalTime){
		//	printf("enters here 3 %d\n", completionTime+ptr->burstTime);
			completionTime = completionTime + ptr->burstTime ;
			
		}
		
		ptr->completionTime=completionTime;
		ptr->turnaroundTime=completionTime-ptr->arrivalTime;
	
		
		hold = ptr->next;	
		
		
		while(hold){
			
			
			if(ptr->next==hold){
				
					
			
				
				
				
				duplicateEnters=1;
				hold1 = hold;
				while(hold1){
					
				//	printf("%d:%d:%d:%d\n",hold1->processNumber,hold1->arrivalTime,hold1->burstTime,ptr->processNumber);
					
					if(ptr->priority>hold1->priority &&hold1->arrivalTime<tempCompletionTime+ptr->burstTime&&
						hold1->arrivalTime>tempCompletionTime){
				
					//	printf("%d:%d:%d:%d\n",hold1->processNumber,hold1->arrivalTime,hold1->burstTime,hold1->priority);
		
						if(ptr->arrivalTime!=hold1->arrivalTime){
				
							completionTime = hold1->arrivalTime;
							ptr->completionTime=completionTime;
							ptr->turnaroundTime=completionTime-ptr->arrivalTime;
							
							hold2 = hold1->prev;
							
							ptr->next->hasArrived=0;
							ptr->next->last = 0;
							
							
							if(hold1!=hold&&hold1->priority<ptr->next->priority){
							
								hold2 = hold1->prev;
								
								if(hold1->next!=NULL){
						 			hold1->next->prev = hold1->prev;
								}
						
								hold1->prev->next = hold1->next;
							
							
								hold1->next = ptr->next;
								ptr->next->prev=hold1;
								ptr->next=hold1;
								hold1->prev=ptr;	
								
							}
							 
						
							
							if(duplicateEnters==1){
							
							
								if(first1==NULL){
									first1 = (Node *) malloc(sizeof(Node));
									first1->processNumber = ptr->processNumber;
									first1->arrivalTime = ptr->arrivalTime;
									first1->priority = ptr->priority;
									first1->burstTime = (tempCompletionTime+ptr->burstTime)-hold1->arrivalTime;
									first1->hasArrived=0;
									first1->last=0;
									first1->isNotDuplicateArrivalTime=1;
									first->isDuplicateArrivalTime=0;
									first1->isPreempTed=0;
									first1->isDuplicate = 0;
									first1->isDuplicate2 =0;
									last1 = first1;
									last1->next=NULL;
								}else{
						
									temp1 = (Node *) malloc(sizeof(Node));
									temp1->processNumber = ptr->processNumber;
									temp1->arrivalTime = ptr->arrivalTime;
									temp1->priority = ptr->priority;
									temp1->burstTime=(tempCompletionTime+ptr->burstTime)-hold1->arrivalTime;
									temp1->hasArrived=0;
									temp1->last = 0;
									temp1->isNotDuplicateArrivalTime=1;
									temp1->isDuplicateArrivalTime=0;
									temp->isPreempTed=0;
									temp->isDuplicate = 0;
									temp->isDuplicate2 =0;
									temp1->prev=last1;
									last1->next = temp1;
									last1=temp1; 
									last1->next=NULL;
						
								}
								
								
								
								
								
								
							}
					
							hold1->hasArrived=1;
							hold1->last = 1;
							ptr->last=0;
							ptr->isPreempTed=1;
				
					
							
					
							if(hold1->isDuplicateArrivalTime!=1){
								break;
							}
								
							duplicateEnters++;
							
							
							if(hold1!=hold){
								hold1=hold2;
							}
						
					
						}
				
					}
					
					if((hold1->prev->isDuplicateArrivalTime==1&&hold1->isDuplicateArrivalTime==0&&hold1->isNotDuplicateArrivalTime==0)||
						(hold1->prev->isNotDuplicateArrivalTime==1&&hold1->isNotDuplicateArrivalTime==0&&hold1->prev!=ptr)){
					
						break;
					}
					
					
					
					hold1 = hold1->next;
				}
				
				
			
		
				

				
				hold1=ptr->next;
				
				while(hold1){
					
				//	printf("%d:%d:%d:%d\n",hold1->processNumber,hold1->hasArrived,hold1->burstTime,hold1->isPreempTed);
				
					
					if(hold1->hasArrived==0&&hold1->isPreempTed==0){
						
					//	printf("enters here %d:%d:%d:%d\n",hold1->processNumber,hold1->arrivalTime,hold1->burstTime, hold1->priority);
					
						sortListBasedOnPriority2(hold1,completionTime);
					
					}  		
			
				hold1 = hold1->next;
					
				}
				
				
				
			}
			
			
	
			hold = hold->next;
		}
		
		
		
		hold = ptr->next;
		
		while(hold){
			
					
			
			
			if(ptr->next==hold&&hold->last==1){
			
				if(hold->next!=NULL){
					hold->next->prev = last1;
				}
			
				last1->next=hold->next;
				first1->prev = hold;
				hold->next=first1;
			
		
				first1=NULL;
				
				
			}
			
			
			hold=hold->next;
		}
		
		

		
		ptr=ptr->next;
	}
	
	ptr=first;
	
	while(ptr){
		hold = ptr->next;
		
		while(hold){
			
			
			if(ptr->prev==NULL){
				ptr->finalBurstTime=ptr->burstTime;
			}
			
			if(ptr->processNumber==hold->processNumber){
				ptr->isDuplicate2 = 1;
			}
			
			
			if(ptr->isDuplicate==0&&ptr->processNumber==hold->processNumber){
				hold->finalBurstTime=ptr->burstTime;
				hold->isDuplicate=1;
			}
			else if(hold->isDuplicate==0){
				
				hold->finalBurstTime = hold->burstTime;
			}
			
			
			
			
			hold=hold->next;
		}
		
		
		ptr->waitingTime=ptr->turnaroundTime-ptr->finalBurstTime;
		
		
		ptr=ptr->next;	
		
	}
	
	
//	printList(first);
	printf("%\n");
	
	
	
	ptr = first;
	int sum1=0, sum2=0;
	float ave1, ave2;
	
	printf("\n\n");
	printf("No. Of Process |Arrival Time|Burst Time|Turnaround Time|Waiting Time|Completion Time|Priority|\n");
	
	while (ptr) {
		
		
		if(ptr->isDuplicate2==0){
			
			printf("%14d |%12d|%10d|%15d|%12d|%15d|%8d|\n", ptr->processNumber, ptr->arrivalTime, ptr->finalBurstTime, ptr->turnaroundTime, ptr->waitingTime,ptr->completionTime,ptr->priority);
	
			sum1 += ptr->turnaroundTime;
			sum2 += ptr->waitingTime;
			
		}
		ptr = ptr->next;
	}
	
	
	ave1 = (float)sum1/(float)size;
	ave2 = (float)sum2/(float)size;

	printf("\n Average Turnaround Time: %.3f." , ave1);
	printf("\n Average Waiting Time: %.3f.\n" , ave2);
	
//	printList(first);
	
	printf("\n");
	ptr=first;
	
	while(ptr){
		
		if(ptr->prev!=NULL&&ptr->arrivalTime>ptr->prev->completionTime){
			printf("--e--|%d|",ptr->arrivalTime);
		}
		
		if(ptr->prev==NULL&&ptr->arrivalTime>0){
			printf("|%d|",ptr->arrivalTime);
		}
		else if(ptr->prev==NULL&&ptr->arrivalTime==0){
			printf("|0|");
		}
		
		printf("--P%d--|%d|",ptr->processNumber,ptr->completionTime);
		
		ptr=ptr->next;
	}
}

system("pause");

if(choice == 6){
	
	printf("Round Robin\n");	
	int size,i,sizeBurstTime=0,timeQuantum;
	printf("Enter size: ");
	scanf("%d",&size);
	printf("Enter Time Quantum: ");
	scanf("%d",&timeQuantum);
	
	printf("Enter Arrival Time of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
		insertArrivalTime(x);
	}
	
	printf("Enter Burst Time of each process:\n");
	
	for(i=0;i<size;i++){
		int x;
		scanf("%d",&x);
	 	sizeBurstTime++;
		insertBurstTime(x,sizeBurstTime);
	}
	
	
	sortList(first);
	
	Node *ptr = first, *hold,*hold1;
	int completionTime=0;
	int flag=0;
	
	while(ptr){
		
		

		
		if(ptr->prev==NULL&&ptr->burstTime>timeQuantum){
			completionTime = ptr->arrivalTime+timeQuantum;
		//	printf("enter her 1\n");
		}
		else if(ptr->prev==NULL&&ptr->burstTime<=timeQuantum){
			completionTime = ptr->arrivalTime+ptr->burstTime;
		//	printf("enter here 2\n");
		}
		else if(ptr->arrivalTime>completionTime&&ptr->burstTime>timeQuantum){
			completionTime = ptr->arrivalTime +timeQuantum;
		//	printf("enter here 3\n");
		}
		else if(ptr->arrivalTime>completionTime&&ptr->burstTime<=timeQuantum){
			completionTime = ptr->arrivalTime +ptr->burstTime;
		//	printf("enter here 4\n");
		}
		else if(ptr->burstTime>timeQuantum){
			completionTime = completionTime+timeQuantum;
		//	printf("enter here 5\n");
		}
		else if(ptr->burstTime<=timeQuantum){
			completionTime = completionTime+ptr->burstTime;
		//	printf("enter here 6\n");
		}
		
		ptr->completionTime=completionTime;
		ptr->turnaroundTime = completionTime - ptr->arrivalTime;
		
		
		hold=ptr;
		
		while(hold){
			
			//printf("%d:%d:%d\n",completionTime,hold->arrivalTime,hold->burstTime);
			
			if(hold->arrivalTime<=completionTime){
				last1 = hold;
			}
			
		//	printf("enters %d\n",hold->arrivalTime);
			hold=hold->next;
		}
		
		
		if(ptr->burstTime>timeQuantum){
			temp1 = (Node *) malloc(sizeof(Node));
			temp1->processNumber = ptr->processNumber;
			temp1->arrivalTime = ptr->arrivalTime;
			temp1->burstTime = ptr->burstTime-timeQuantum;
			
			
			
			//printf("diri na pod %d:%d\n", last1->arrivalTime,last1->burstTime);
			
			temp1->next = last1->next;
			last1->next=temp1;
			temp1->prev=last1;
			
			ptr->isPreempTed=1;
			
		}
		
		
		
		ptr = ptr->next;
		
	}
	
	ptr = first;
	
	while(ptr){
		hold = ptr->next;
		
		while(hold){
			
			
			if(ptr->isDuplicate==0&&ptr->processNumber==hold->processNumber){
				hold->finalBurstTime=ptr->burstTime;
				hold->isDuplicate=1;
			}
			else if(hold->isDuplicate==0){
				
				hold->finalBurstTime = hold->burstTime;
			}
			
			
			
			
			hold=hold->next;
		}
		
		
		ptr->waitingTime=ptr->turnaroundTime-ptr->finalBurstTime;
		
		
		ptr=ptr->next;
		
		
		
	}
	

	

	 

	ptr = first;
	int sum1=0, sum2=0;
	float ave1, ave2;
	
	printf("\n\n");
	printf("No. Of Process |Arrival Time|Burst Time|Turnaround Time|Waiting Time|Completion Time|\n");
	
	while (ptr) {
		
		
		if(ptr->burstTime<=timeQuantum){
			
			printf("%14d |%12d|%10d|%15d|%12d|%15d|\n", ptr->processNumber, ptr->arrivalTime, ptr->finalBurstTime, ptr->turnaroundTime, ptr->waitingTime,ptr->completionTime);
	
			sum1 += ptr->turnaroundTime;
			sum2 += ptr->waitingTime;
			
		}
			
			
			
		
		
		
		
		
		
		ptr = ptr->next;
	}
	
	
	ave1 = (float)sum1/(float)size;
	ave2 = (float)sum2/(float)size;

	printf("\n Average Turnaround Time: %.3f." , ave1);
	printf("\n Average Waiting Time: %.3f.\n" , ave2);
	
	
	size=0;
	ptr=first;
	
	while(ptr){
		size++;
		ptr= ptr->next;
	}
	
	Node process[size];
	
	ptr=first; 
	i=0;
	
	while(ptr){
		
		process[i].burstTime = ptr->burstTime;
		process[i].arrivalTime = ptr->arrivalTime;
		process[i].processNumber = ptr->processNumber;
		process[i].isPreempTed = ptr->isPreempTed;
		ptr=ptr->next;
		i++;
	}
	
	printf("\n");
	
	
	completionTime=0;
	int p,decrement=0,max,j;
	int size2=size;
	
	for(i=0;i<size2;i++){
		
		if(process[i-decrement].isPreempTed==1){
			process[i-decrement].burstTime= timeQuantum;
		}
		
		if(process[i-decrement].arrivalTime>completionTime){
			size2++;
			max = process[i-decrement].arrivalTime - completionTime;
			
			for(p=0;p<max+1;p++){
				
				if(i==0&&p==0){
					printf("|%d|",completionTime);
				}
				else if(p==max){
					printf("|%d|",completionTime);
				}
				else if(p==max/2){
					printf("-e-");
				}
				else{
					printf("-");
				}
			
				if(p!=max){
					completionTime++;
				}
			}
			decrement++;
			
		}
		else{
			for(j=0;j<process[i-decrement].burstTime+1;j++){
			
				if(i==0&&j==0){
					printf("|%d|",completionTime);
				}
				else if(process[i-decrement].burstTime==1&&j==process[i-decrement].burstTime&&i==0){
					printf("-P%d-|%d|",process[i-decrement].processNumber,completionTime);
				}
				else if(j==process[i-decrement].burstTime){
					printf("|%d|",completionTime);
				
				}
				else if(j==process[i-decrement].burstTime/2){
					printf("-P%d-",process[i-decrement].processNumber);
				}
				else{
					printf("-");
				}
			
				if(j!=process[i-decrement].burstTime){
					completionTime++;
				}
			
			}
			
		}
	}
}
	
system("pause");	
	
	return 0;
}

