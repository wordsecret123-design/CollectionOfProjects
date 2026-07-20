module GrayCodesmodule();
parameter [44:0] Max = 32; //change this  
reg [0:4] grayCode; //change this
reg [0:4] catch [0:31]; //change this
reg [0:44] i,j;   
reg [0:44] selectOneBit[0:31]; //change this
reg flag = 0;
reg flag1[0:31];//change this
reg [44:0] allSelectOneBitIs4 = 0;
reg [44:0] selectOneBitMax = 5; //change this
reg [44:0] ceiling = 31; //change this
reg [44:0] test = 0;
reg [44:0] sum = 0;
integer fileno;



initial begin
fileno=$fopen("grayCode.txt");
	

	catch[0]=4'b0001; // change this
 
	for(i=0;Max>i;i=i+1)
	begin

		selectOneBit[i] = 0;
		flag1[i] = 0;
	
	end


	for(i=0;Max>i;i=i+1)
	begin
		if(i!=0) 
		begin
			grayCode =catch[i-1]; 
			grayCode[selectOneBit[i]]=~grayCode[selectOneBit[i]];
			catch[i] = grayCode;
			
	
		
		for(j=0;i>j;j=j+1)
		begin
			if(grayCode==catch[j]) 
			begin
				selectOneBit[i] = selectOneBit[i] + 1;
				i = i - 1;
				j = i;
				flag=1;
				
			end
			else
			begin
				flag=0;
			end
		
		
		end
		
		end
		//endfirstIf
		else
		begin
			grayCode = catch[i];
		end
	

		if(flag==0)
		begin
			#1 $display("%d %b %d",i+1,grayCode,grayCode);
			#1 $fdisplay(fileno,"%d %b %d",i+1,grayCode,grayCode);
		end

	


	end //first gray Code

	sum=sum+1; 

	for(i=ceiling;Max>i;i=i+1)
	begin
		//test=test+1;
		//152 913
		//#1 $display("Enters here %d %d",i,selectOneBit[i]);
		
		 	
		if(selectOneBit[i]==selectOneBitMax)
		begin
			//#1 $display("1Enters here %d %d",i,selectOneBit[i]);
			selectOneBit[i] = 0;
			flag1[i] = 1;
			i=i-1; 
			//ceiling=ceiling - 1;
			flag1[i] = 0;
		end
		//#1 $display("Enters here %d %d %d",i,selectOneBit[i]);
		
		if (flag1[i]==0)
			selectOneBit[i] = selectOneBit[i] + 1; 
				
		if(i!=0&&selectOneBit[i]!=selectOneBitMax)
		begin
			grayCode = catch[i-1]; 
			
			grayCode[selectOneBit[i]] = ~grayCode[selectOneBit[i]];
			
			catch[i] = grayCode; 
			
		//#1 $display("Enters here %d %d %b",i,selectOneBit[i],grayCode);
		end
		
		
		//#1 $display("Enters here %d %d %b %d",i,selectOneBit[i],grayCode, flag1[i]);
	
		
		for(j=0;i>j;j=j+1)
		begin
			
			if(selectOneBit[i]==selectOneBitMax)
			begin
				i=i-1; 
				j=i;
				//#1 $display("2Enters here %d %d %d %b",i,selectOneBit[i],j, grayCode); 
			end
			//#1 $display("Enters here %d %d %d %b",i,selectOneBit[i],j, grayCode);
			else if(i!=0&&grayCode==catch[j]&&selectOneBit[i]!=selectOneBitMax)
			begin		
				flag1[i]=1;
				//#1 $display("3Enters here %d %d %d %b %b",i,selectOneBit[i],j, grayCode,catch[j]);
				selectOneBit[i] = selectOneBit[i] + 1; 
				i = i - 1;
				j = i;
				
				
			end
			
		
		end
		
		//#1 $display("Enters here %d %d %d %b",test,i,selectOneBit[i], grayCode);


		//if(test==6000)
			//i=Max;

		if(i==0)
			i=Max; 

		if(i==(Max-1))
		begin
			$display("######################");
			$fdisplay(fileno,"######################");
			for(j=0;Max>j;j=j+1)
			begin
				#1 $display("%d %b %d",j+1,catch[j],catch[j]);//,catch[j]);//,selectOneBit[j],flag1[j]);
				#1 $fdisplay(fileno,"%d %b %d",j+1,catch[j],catch[j]);
		
			end
		
			for(j=0;Max>j;j=j+1)
			begin
				flag1[j] = 0;
		
			end
		
			i=i-1;
			sum=sum+1;
		end

	end // end sa for loop


#1 $display("All Possible GrayCodes = %d", sum);
#1 $fdisplay(fileno,"All Possible GrayCodes = %d", sum);
#1 $fclose(fileno);
end //initial end



endmodule
