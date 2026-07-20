package CollectionOfProjects.checkers.finalproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DamaPanel extends JPanel implements MouseListener{
	

	public String MoveTurn;
	
	//--------------------------------
	//fields that will be used later to improve game,
	//to allow players to change first move from white
	//to brown. (If time allows it).
	//--------------------------------
	//--------------------------------
	// private String NewMoveTurn1;
	// private String NewMoveTurn2;
	//--------------------------------
	//--------------------------------
	
	private int flag=0;
	private int flag2=0;
	private int flag3=0;
	
	private int flag4=0;//white
	private int flag5=0;//brown
	private int flag6=0;//white
	private int flag7=0;//brown
	
	private int flagKingWhite=0;
	private int flagKingBrown=0;
	public int size;
	public int countWhite;
	public int countBrown;
	public int count=0;
	
	//this variable is used to count the number of capture of one piece
	//which will be used later to obligate a piece to capture the pieces on the side with
	//the greatest number of capture.
	
	private Integer[] countJumpWhite;
	private Integer[] countJumpBrown;
	private Integer[] countJump_Index1;//handles white number of jumps (index in CountJumpWhite array)
	private Integer[] countJump_Index2;//handles brown number of jumps (index in CountJumpBrown array)
	
	private int highestNumberJumpsIndex1;//handles the highest white number of jumps (index in CountJumpWhite array)
	private int highestNumberJumpsIndex2;//handles the highest brown number of jumps (index in CountJumpBrown array)
	
	public double location_y;
	public double location_x;
	
	public DamaCell[] dama;
	public DamaCell lastClicked;
	public DamaCell lastPositionOrigin;
	
	
	public DamaPanel() {
	 this(8,"brown");
	}
public DamaPanel(int size,String secondMoveTurn) {
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(800,800));
		setLayout(new GridLayout(size,size));
		
		setSecondMoveTurn(secondMoveTurn);
		
		
		dama=new DamaCell[size*size];
		
		int i=0;
		
		
		
		for(int row=0;row<size;row++) {
			
			for(int col=0;col<size;col++) {
				
				dama[i]=new DamaCell(new Point(col,row));
				
				if(row%2==0&&col%2!=0)
					dama[i].PieceName="null";
				else if(row%2!=0&&col%2==0)
					dama[i].PieceName="null";
				
				if(row%2==0&&col%2==0) {
					if(MoveTurn.equals("white")) {
						dama[i].setNamePiece("brown", "white");
					}
					dama[i].setContent(dama[i].location.getY());
					System.out.print(dama[i].location.getY());
				}
				
				
				else if(row%2!=0&&col%2!=0) {
					if(MoveTurn.equals("white")) {
						dama[i].setNamePiece("brown", "white");
					}
					dama[i].setContent(dama[i].location.getY());
				}
		
				
				
				dama[i].addMouseListener(this);
				
				
				add(dama[i]);
				i++;
			
				this.size=size;
			
			}
		}
		
		
		
	}
	
	
	private void setSecondMoveTurn(String set) {
		MoveTurn=set;
	}
	
	
	public void mouseClicked(MouseEvent event)  {}

public void mousePressed(MouseEvent event)  {
		
	
	
	
	try {
		
		
		
		DamaCell cell = (DamaCell) event.getSource();
		
		
		
		countJumpWhite=new Integer[size*size];
		countJumpBrown=new Integer[size*size];
		countJump_Index1=new Integer[3];
		countJump_Index2=new Integer[3];
		
		//initializes countJump_Index1 and countJump_Index2
		for(int i=0;i<3;i++) {
			countJump_Index1[i]=0;
			
			countJump_Index2[i]=63;
		}
		
		
		
		if(flag==0) {
		location_x=cell.x_position;
		location_y=cell.y_position;
		}
		
		if(location_x==cell.x_position&&location_y==cell.y_position) {
			
			if((cell.PieceName.equals("white")||cell.PieceName.equals("brown"))&&flag==1) {
				cell.dama_piece.setBackground(Color.darkGray);	
				flag=0;
				location_x=cell.x_position;
				location_y=cell.y_position;
				
			}
			else if((cell.PieceName.equals("white")||cell.PieceName.equals("brown"))&&flag==0) {
				cell.dama_piece.setBackground(Color.yellow);	
				flag=1;
				location_x=cell.x_position;
				location_y=cell.y_position;
				lastClicked=cell;
				lastPositionOrigin=lastClicked;
			}		
		}
		
		
		
		//This piece of code checks to see if 
		//A WhiteKing piece has an available jump capture
		//if true, it flags a value of 1, else it flags 0.
				
		for(int i=0;i<size*size;i++) {
			if(
					dama[i].CrownName.equals("King White")&&MoveTurn.equals("brown")
					&&(
					//upper right corner
					(((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("brown")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
						
					||((int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))>=0&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("empty")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("brown")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))].PieceName.equals("empty")))
						
					||
						
						//upper left corner
					(
					((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("brown")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
								
					||((int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))>=0&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("empty")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("brown")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))].PieceName.equals("empty"))
						
					)
						
					||
						
					//lower right corner
					(((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("brown")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
										
					||((int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))<=63&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("empty")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("brown")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))].PieceName.equals("empty")))
						
					||
						//lower left corner
					((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63&&
					(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("brown")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
						
					//lower left corner
					||((int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))<=63&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("empty")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("brown")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))].PieceName.equals("empty"))) )
						
						
					) {
					
				flagKingWhite=1;
				break;
			}
			else {
				flagKingWhite=0;
			}
		}
				
				//This piece of code checks to see if 
				//A Brown King piece has an available jump capture
				//if true, it flags a value of 1, else it flags 0.
		for(int i=0;i<size*size;i++) {
			if(
					dama[i].CrownName.equals("King Brown")&&MoveTurn.equals("white")
					&&(
					//upper right corner
					(((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("white")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
						
					||((int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))>=0&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("empty")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("white")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))].PieceName.equals("empty")))
						
					||
					
					//upper left corner
					(((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("white")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("empty"))
								
					||((int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))>=0&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("empty")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("white")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))].PieceName.equals("empty")))
						
					||
						
					//lower right corner
					(((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("white")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
										
					||((int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))<=63&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("empty")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("white")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))].PieceName.equals("empty")))
						
					||
						
					((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63&&
					(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("white")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
						
						//lower left corner
					||((int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))<=63&&
					dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("empty")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("white")
					&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))].PieceName.equals("empty"))) )
						
						
					) {
					
				flagKingBrown=1;
				break;
			}
			else {
				flagKingBrown=0;
			}
		}

				
			
				
		if(flag2==0) {
			for(int i=0;i<size*size;i++) {
				
				
		
				
				
				
				if(		
						
						//When white is to capture
						(dama[i].PieceName.equals("white")&&MoveTurn.equals("brown")	
						//upper left corner
						&&(((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)))>=0
						&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1 ))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)))].PieceName.equals("empty")))
						
						//lower left corner
						||(flagKingWhite==1
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)))<=63
						&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)))].PieceName.equals("empty"))))
						
						//upper right corner
						||((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)))>=0
						&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)))].PieceName.equals("empty")))
						
						//lower right corner
						||(flagKingWhite==1
						&&(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)))<=63
						&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size+1)))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)))].PieceName.equals("empty"))))
						 
						
						//When brown is to capture
						||dama[i].PieceName.equals("brown")&&MoveTurn.equals("white")			
						//upper left corner
						&&(flagKingBrown==1
						&&(((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)))>=0
						&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1 ))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)))].PieceName.equals("empty"))))
								
						//lower left corner
						||((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)))<=63
						&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)))].PieceName.equals("empty")))
									
						//upper right corner
						||(flagKingBrown==1
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)))>=0
						&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)))].PieceName.equals("empty"))))
								
						//lower right corner
						||((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)))<=63
						&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size+1)))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)))].PieceName.equals("empty")))))
						) {
					flag2=3;
					
					break;
					
				}
				
			
			
			
			}	
			
			
		}
	
		
		//white index
		
		if(		
				//upper left corner
				(((int)((cell.y_position*size)+cell.x_position-2*(size+1))>=0&&
				dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("brown")
				&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size+1))].PieceName.equals("empty"))
				
				||
				
				//upper right corner
				((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
				dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("brown")
				&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].PieceName.equals("empty")))
				
				&&
				
				((highestNumberJumpsIndex1>=0&&
				lastClicked.equals(dama[highestNumberJumpsIndex1]))
				
				||(highestNumberJumpsIndex1-2*(size-1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-2*(size-1)]))
				
				||(highestNumberJumpsIndex1-4*(size-1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-4*(size-1)]))
				
				||(highestNumberJumpsIndex1-6*(size-1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-6*(size-1)]))
				
				||(	highestNumberJumpsIndex1-2*(size+1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-2*(size+1)]))
				
				||(	highestNumberJumpsIndex1-4*(size+1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-4*(size+1)]))
				
				||(	highestNumberJumpsIndex1-6*(size+1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-6*(size+1)]))
				
				||(highestNumberJumpsIndex1-(2*(size+1)+2*(size-1))>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-(2*(size+1)+2*(size-1))]))
				
				||(highestNumberJumpsIndex1-(4*(size+1)+2*(size-1))>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-(4*(size+1)+2*(size-1))]))
				
				||(highestNumberJumpsIndex1-(2*(size-1)+2*(size+1))>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-(2*(size-1)+2*(size+1))]))
				
				||(highestNumberJumpsIndex1-(4*(size-1)+2*(size+1))>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-(4*(size-1)+2*(size+1))])))
				
				){
	
				flag6=0;
					
		}
					 
					
					//brown index
			if(		
					//lower right corner
					(((int)((cell.y_position*size)+cell.x_position+2*(size+1))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+(size+1))].PieceName.equals("white")
					&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size+1))].PieceName.equals("empty"))
					
					||
					
					//lower left corner
					((int)((cell.y_position*size)+cell.x_position+2*(size-1))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+(size-1))].PieceName.equals("white")
					&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].PieceName.equals("empty")))
					
					
					&&
					
					((highestNumberJumpsIndex2<=63&&
					lastClicked.equals(dama[highestNumberJumpsIndex2]))
							
					||(highestNumberJumpsIndex2+2*(size-1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+2*(size-1)]))
						
					||(highestNumberJumpsIndex2+4*(size-1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+4*(size-1)]))
							
					||(highestNumberJumpsIndex2+6*(size-1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+6*(size-1)]))
							
					||(	highestNumberJumpsIndex2+2*(size+1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+2*(size+1)]))
							
					||(	highestNumberJumpsIndex2+4*(size+1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+4*(size+1)]))
							
					||(	highestNumberJumpsIndex2+6*(size+1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+6*(size+1)]))
							
					||(highestNumberJumpsIndex2+(2*(size+1)+2*(size-1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(2*(size+1)+2*(size-1))]))
							
					||(highestNumberJumpsIndex2+(4*(size+1)+2*(size-1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(4*(size+1)+2*(size-1))]))
							
					||(highestNumberJumpsIndex2+(2*(size-1)+2*(size+1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(2*(size-1)+2*(size+1))]))
							
					||(highestNumberJumpsIndex2+(4*(size-1)+2*(size+1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(4*(size-1)+2*(size+1))])))
					
					
					) {
					
				flag7=0;
				
				}
				
		
			
			
		
			
		
		//BELOW IS THE GROUP OF CODE THAT HANDLES THE COUNTING OF AVAILABLE NUMBER OF JUMPS OF A PIECE	
		
		for(int i=0;i<size*size;i++) {
			
				countJumpWhite[i]=0;
				countJumpBrown[i]=0;

		//--------------------------------------			
		// counting available jumps for white
		//--------------------------------------				
						
					
					//RIGHT
						
						
					if(	//upper right corner straight up count +1
						lastClicked.PieceName.equals("white")
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))){
						
						countJumpWhite[i]++;
					
					}
						
					
						
					if(
							//upper right corner straight up count(+1) +1
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position-4*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-4*(size-1))].PieceName.equals("empty")))
						
						||
						
						//upper right first then alternate from left to right count(+1)+1
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
									
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+2*(size+1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+(size+1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+2*(size+1)))].PieceName.equals("empty")))
						
							) {
						
					
						
						countJumpWhite[i]++;
		
					}
								
						
						
						
					
						
					if(
							//upper right corner straight up count(+2)+1	
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
								
						&&((int)((dama[i].y_position*size)+dama[i].x_position-4*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-4*(size-1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position-6*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-5*(size-1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-6*(size-1))].PieceName.equals("empty")))
						
						||
						
						//upper right first then alternate from left to right count(+2)+1
						
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+2*(size+1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+(size+1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+2*(size+1)))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(4*(size-1)+2*(size+1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(3*(size-1)+2*(size+1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(4*(size-1)+2*(size+1)))].PieceName.equals("empty")))
						
						||
						
						//upper right first then proceed to two left count(+2)+1
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
									
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+2*(size+1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+(size+1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+2*(size+1)))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+4*(size+1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+3*(size+1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)+4*(size+1)))].PieceName.equals("empty")))
						
						||
						
						//upper right corner straight two counts then alternate to left count(+2) +1
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position-4*(size-1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-4*(size-1))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(4*(size-1)+2*(size+1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(4*(size-1)+(size+1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(4*(size-1)+2*(size+1)))].PieceName.equals("empty")))
						
						) {
						
						countJumpWhite[i]++;

			
					}
							
						
						
						
						
					
					//UPPER LEFT
					
					if( //upper left corner straight up count +1
						(lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("empty"))
						
							){
						
						countJumpWhite[i]++;
						

		
					}
						
					
						
					if(
							//upper left corner straight up count(+1)+1
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position-4*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-4*(size+1))].PieceName.equals("empty"))
						
						||
						
						//upper left first then alternate from right to left count(+1)+1
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+2*(size-1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+(size-1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+2*(size-1)))].PieceName.equals("empty")))
						
								
								)
						
							) {
						
	
						
						countJumpWhite[i]++;
						

					}
								
						
						
						
					
						
					if(
							//upper left corner straight up count(+2)+1	
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("empty"))
								
						&&((int)((dama[i].y_position*size)+dama[i].x_position-4*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-4*(size+1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position-6*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-5*(size+1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-6*(size+1))].PieceName.equals("empty")))
						
						||
						
						//upper left first then alternate from right to left count(+2)+1		
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+2*(size-1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+(size-1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+2*(size-1)))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(4*(size+1)+2*(size-1))) >=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(3*(size+1)+2*(size-1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(4*(size+1)+2*(size-1)))].PieceName.equals("empty")))
						
							
						||
						
						//upper left first then proceed to two right count(+2)+1
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("empty"))
									
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+2*(size-1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+(size-1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+2*(size-1)))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+4*(size-1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+3*(size-1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)+4*(size-1)))].PieceName.equals("empty")))
						
						||
						
						//upper left corner straight two counts then alternate to right count(+2) +1
						((lastClicked.PieceName.equals("white")||lastClicked.PieceName.equals("King White"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position-4*(size+1))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-4*(size+1))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position-(4*(size+1)+2*(size-1)))>=0
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(4*(size+1)+(size-1)))].PieceName.equals("brown")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(4*(size+1)+2*(size-1)))].PieceName.equals("empty")))	
							
							) {
						
						countJumpWhite[i]++;
						
						
			
					}
					
					//END OF COUNTING AVAILABLE JUMPS FOR WHITE	
					
					
				
					
				//----------------------------------
				//COUNTING AVAILABLE JUMPS FOR BROWN
				//----------------------------------
					
					//LOWER LEFT
					
					if(	//lower left corner straight down count +1
						(lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
						
							){
						
						countJumpBrown[i]++;

						
	
					}
						
					
						
					if(
							//lower left corner straight down count(+1) +1
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position+4*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+4*(size-1))].PieceName.equals("empty")))
						
						||
						
						//lower left first then alternate from right to left count(+1)+1
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
									
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+2*(size+1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+(size+1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+2*(size+1)))].PieceName.equals("empty")))
						
							) {
						
	
						
						countJumpBrown[i]++;
		
					}
								
						
						
						
					
						
					if(
							//lower left corner straight down count(+2)+1	
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
								
						&&((int)((dama[i].y_position*size)+dama[i].x_position+4*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+4*(size-1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position+6*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+5*(size-1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+6*(size-1))].PieceName.equals("empty")))
						
						||
						
						//lower left first then alternate from right to left count(+2)+1
						
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+2*(size+1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+(size+1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+2*(size+1)))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(4*(size-1)+2*(size+1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(3*(size-1)+2*(size+1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(4*(size-1)+2*(size+1)))].PieceName.equals("empty")))
						
						||
						
						//lower left first then proceed to two right count(+2)+1
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
									
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+2*(size+1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+(size+1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+2*(size+1)))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+4*(size+1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+3*(size+1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)+4*(size+1)))].PieceName.equals("empty")))
						
						||
						
						//lower left corner straight two counts then alternate to right count(+2) +1
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position+4*(size-1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+4*(size-1))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(4*(size-1)+2*(size+1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(4*(size-1)+(size+1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(4*(size-1)+2*(size+1)))].PieceName.equals("empty")))
						
						) {
						
						countJumpBrown[i]++;

						
			
					}
							
						
						
						
						
					
					//LOWER RIGHT 
					
					if( //lower right corner straight down count +1
						(lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
						
							){
						
						countJumpBrown[i]++;
						

			
					}
						
					
						
					if(
							//lower right corner straight down count(+1)+1
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position+4*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+4*(size+1))].PieceName.equals("empty"))
						
						||
						
						//lower right first then alternate from left to right count(+1)+1
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+2*(size-1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+(size-1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+2*(size-1)))].PieceName.equals("empty")))
						
								
								)
						
							) {

						
						countJumpBrown[i]++;
						

					}
								
						
						
						
					
						
					if(
							//lower right corner straight down count(+2)+1	
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
								
						&&((int)((dama[i].y_position*size)+dama[i].x_position+4*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+4*(size+1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position+6*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+5*(size+1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+6*(size+1))].PieceName.equals("empty")))
						
						||
						
						//lower right first then alternate from left to right count(+2)+1		
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+2*(size-1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+(size-1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+2*(size-1)))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(4*(size+1)+2*(size-1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(3*(size+1)+2*(size-1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(4*(size+1)+2*(size-1)))].PieceName.equals("empty")))
						
							
						||
						
						//lower right first then proceed to two left count(+2)+1
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
									
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+2*(size-1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+(size-1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+2*(size-1)))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+4*(size-1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+3*(size-1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)+4*(size-1)))].PieceName.equals("empty")))
						
						||
						
						//lower right corner straight two counts then alternate to left count(+2) +1
						((lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))
						&&((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position)].PieceName.equals("brown")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("white")
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
							
						&&((int)((dama[i].y_position*size)+dama[i].x_position+4*(size+1))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+4*(size+1))].PieceName.equals("empty"))
						
						&&((int)((dama[i].y_position*size)+dama[i].x_position+(4*(size+1)+2*(size-1)))<=63
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(4*(size+1)+(size-1)))].PieceName.equals("white")								
						&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(4*(size+1)+2*(size-1)))].PieceName.equals("empty")))	
							
							) {
						
						countJumpBrown[i]++;
						

					}

					
					
					
		
		}//END OF GROUP OF CODE THAT HANDLES THE COUNTING OF AVAILABLE JUMPS FOR PIECES
		
		
		
		


		
{//THIS GROUP OF CODE OBLIGATES A WHITE PIECE WITH THE HIGHEST NUMBER OF AVAILABLE JUMPS TO DO THE CAPTURING
	
		//This sets the index where the pieces that have available jumps are located
	
	
			for(int i=0;i<size*size;i++) {
				
				if(countJumpWhite[i]!=0) {

					for(int j=0;j<3;j++) {	
						
						if(countJump_Index1[j]==0) { 
								countJump_Index1[j]=i;	
								break;
						}	
					}	
				}
			}
		
			
			
		// This sets the index in the array of cells that contain the white piece
		//with the highest available number of jumps

			if(countJumpWhite[countJump_Index1[0]]>countJumpWhite[countJump_Index1[1]]
				&&countJumpWhite[countJump_Index1[0]]>countJumpWhite[countJump_Index1[2]]) {
						
				highestNumberJumpsIndex1=countJump_Index1[0];
				
			}
		
			else if(countJumpWhite[countJump_Index1[1]]>countJumpWhite[countJump_Index1[0]]
					&&countJumpWhite[countJump_Index1[1]]>countJumpWhite[countJump_Index1[2]]) {
						
				highestNumberJumpsIndex1=countJump_Index1[1];
		
			}
		
			else if(countJumpWhite[countJump_Index1[2]]>countJumpWhite[countJump_Index1[0]]
					&&countJumpWhite[countJump_Index1[2]]>countJumpWhite[countJump_Index1[1]]) {
						
				highestNumberJumpsIndex1=countJump_Index1[2];
			}
			
			
		
		//flag4 flags value that will allow player to choose any pieces to jump
		//when all pieces with available jumps has an equal number of available jumps
		
		if(flag4==0&&highestNumberJumpsIndex1!=-1
			&&
			(
			(countJumpWhite[countJump_Index1[0]]==countJumpWhite[countJump_Index1[1]]
			&&countJumpWhite[countJump_Index1[0]]==countJumpWhite[countJump_Index1[2]]
			&&countJumpWhite[countJump_Index1[1]]==countJumpWhite[countJump_Index1[2]])
	
			||(countJumpWhite[countJump_Index1[0]]==countJumpWhite[countJump_Index1[1]]&&
				
			(countJumpWhite[countJump_Index1[2]]>=0&&countJumpWhite[countJump_Index1[2]]<countJumpWhite[countJump_Index1[0]]))
			
			||(countJumpWhite[countJump_Index1[0]]==countJumpWhite[countJump_Index1[2]]&&
			
			(countJumpWhite[countJump_Index1[1]]>=0&&countJumpWhite[countJump_Index1[1]]<countJumpWhite[countJump_Index1[0]]))
			
			||(countJumpWhite[countJump_Index1[1]]==countJumpWhite[countJump_Index1[2]]&&
			
			countJumpWhite[countJump_Index1[0]]>=0&&countJumpWhite[countJump_Index1[0]]<countJumpWhite[countJump_Index1[1]])
			
			||(countJumpWhite[countJump_Index1[0]]!=0&&
			countJumpWhite[countJump_Index1[1]]==0&&
			countJumpWhite[countJump_Index1[2]]==0)
			
			||(countJumpWhite[countJump_Index1[1]]!=0&&
			countJumpWhite[countJump_Index1[0]]==0&&
			countJumpWhite[countJump_Index1[2]]==0)
			
			||(countJumpWhite[countJump_Index1[2]]!=0&&
			countJumpWhite[countJump_Index1[1]]==0&&
			countJumpWhite[countJump_Index1[0]]==0)
					) ) {
			
			flag4=1;	
			flag6=1;
			
		
		} 
		
		
		
		if(cell.PieceName.equals("white")) {
			
			if(cell.Index==0&&
				((countJumpWhite[countJump_Index1[0]]==countJumpWhite[countJump_Index1[1]]&&
				
				(countJumpWhite[countJump_Index1[2]]>=0&&countJumpWhite[countJump_Index1[2]]<countJumpWhite[countJump_Index1[0]]))
				
				||(countJumpWhite[countJump_Index1[0]]==countJumpWhite[countJump_Index1[2]]&&
				
				(countJumpWhite[countJump_Index1[1]]>=0&&countJumpWhite[countJump_Index1[1]]<countJumpWhite[countJump_Index1[0]]))
				
				||(countJumpWhite[countJump_Index1[1]]==countJumpWhite[countJump_Index1[2]]&&
				
				countJumpWhite[countJump_Index1[0]]>=0&&countJumpWhite[countJump_Index1[0]]<countJumpWhite[countJump_Index1[1]]))) {
				
				//if two pieces have equal number of greatest number of jumps, this code prevents the piece with the 
				//smallest amount of available jump from jumping
				
				if(	cell.Index==0&&
						(countJumpWhite[(int)((cell.y_position*size)+cell.x_position)]<=countJumpWhite[countJump_Index1[0]]&&
						countJumpWhite[(int)((cell.y_position*size)+cell.x_position)]<=countJumpWhite[countJump_Index1[1]]&&
						countJumpWhite[(int)((cell.y_position*size)+cell.x_position)]<=countJumpWhite[countJump_Index1[2]])) {
					
						flag4=0;
						highestNumberJumpsIndex1=-1;
					
				}
				
				//if two pieces have equal number of greatest number of jumps, sets highest index to index of lastClicked
				if(	countJumpWhite[(int)((cell.y_position*size)+cell.x_position)]>=countJumpWhite[countJump_Index1[0]]&&
						countJumpWhite[(int)((cell.y_position*size)+cell.x_position)]>=countJumpWhite[countJump_Index1[1]]&&
						countJumpWhite[(int)((cell.y_position*size)+cell.x_position)]>=countJumpWhite[countJump_Index1[2]]) {
				
					highestNumberJumpsIndex1=(int)((cell.y_position*size)+cell.x_position);
					flag4=1;
			
		
				}
				
						
						
				
			
			}
		}
		

		
}//END OF THE GROUP OF CODE THAT HANDLES OBLIGATING WHITE PIECE.



{//THIS GROUP OF CODE OBLIGATES A BROWN PIECE WITH THE HIGHEST NUMBER OF AVAILABLE JUMPS TO DO THE CAPTURING
	

	//This sets the index where the pieces that have available jumps are located

	
		for(int i=0;i<size*size;i++) {
			
			if(countJumpBrown[i]!=0) {

				for(int j=0;j<3;j++) {	
					
					if(countJump_Index2[j]==63) { 
							countJump_Index2[j]=i;	
							break;
					}	
				}	
			}
		}
	
		
		// This sets the index in the array of cells that contain the brown piece
		//with the highest available number of jumps

					if(countJumpBrown[countJump_Index2[0]]>countJumpBrown[countJump_Index2[1]]
						&&countJumpBrown[countJump_Index2[0]]>countJumpBrown[countJump_Index2[2]]) {
								
						highestNumberJumpsIndex2=countJump_Index2[0];
						
						
				
					}
				
					else if(countJumpBrown[countJump_Index2[1]]>countJumpBrown[countJump_Index2[0]]
							&&countJumpBrown[countJump_Index2[1]]>countJumpBrown[countJump_Index2[2]]) {
								
						highestNumberJumpsIndex2=countJump_Index2[1];
						
						
				
					}
				
					else if(countJumpBrown[countJump_Index2[2]]>countJumpBrown[countJump_Index2[0]]
							&&countJumpBrown[countJump_Index2[2]]>countJumpBrown[countJump_Index2[1]]) {
								
						highestNumberJumpsIndex2=countJump_Index2[2];
						
						
					
					}
					
					
				
				//flag5 flags value that will allow player to choose any pieces to jump
				//when all pieces with available jumps has an equal number of available jumps
				
				if(flag5==0&&highestNumberJumpsIndex2!=64
					&&
					(
					countJumpBrown[countJump_Index2[0]]==countJumpBrown[countJump_Index2[1]]
					&&countJumpBrown[countJump_Index2[0]]==countJumpBrown[countJump_Index2[2]]
					&&countJumpBrown[countJump_Index2[1]]==countJumpBrown[countJump_Index2[2]]
			
					||(countJumpBrown[countJump_Index2[0]]==countJumpBrown[countJump_Index2[1]]&&
						
					(countJumpBrown[countJump_Index2[2]]>=0&&countJumpBrown[countJump_Index2[2]]<countJumpBrown[countJump_Index2[0]]))
					
					||(countJumpBrown[countJump_Index2[0]]==countJumpBrown[countJump_Index2[2]]&&
					
					(countJumpBrown[countJump_Index2[1]]>=0&&countJumpBrown[countJump_Index2[1]]<countJumpBrown[countJump_Index2[0]]))
					
					||(countJumpBrown[countJump_Index2[1]]==countJumpBrown[countJump_Index2[2]]&&
					
					countJumpBrown[countJump_Index2[0]]>=0&&countJumpBrown[countJump_Index2[0]]<countJumpBrown[countJump_Index2[1]])
					
					||(countJumpBrown[countJump_Index2[0]]!=0&&
					countJumpBrown[countJump_Index2[1]]==0&&
					countJumpBrown[countJump_Index2[2]]==0)
					
					||(countJumpBrown[countJump_Index2[1]]!=0&&
					countJumpBrown[countJump_Index2[0]]==0&&
					countJumpBrown[countJump_Index2[2]]==0)
					
					||(countJumpBrown[countJump_Index2[2]]!=0&&
					countJumpBrown[countJump_Index2[1]]==0&&
					countJumpBrown[countJump_Index2[0]]==0)
							)
						) {
					
					flag5=1;	
					flag7=1;
					
					
				}
				
				
				
				if(cell.PieceName.equals("brown")) {
					if(cell.Index2==63&&
						((countJumpBrown[countJump_Index2[0]]==countJumpBrown[countJump_Index2[1]]&&
						
						(countJumpBrown[countJump_Index2[2]]>=0&&countJumpBrown[countJump_Index2[2]]<countJumpBrown[countJump_Index2[0]]))
						
						||(countJumpBrown[countJump_Index2[0]]==countJumpBrown[countJump_Index2[2]]&&
						
						(countJumpBrown[countJump_Index2[1]]>=0&&countJumpBrown[countJump_Index2[1]]<countJumpBrown[countJump_Index2[0]]))
						
						||(countJumpBrown[countJump_Index2[1]]==countJumpBrown[countJump_Index2[2]]&&
						
						countJumpBrown[countJump_Index2[0]]>=0&&countJumpBrown[countJump_Index2[0]]<countJumpBrown[countJump_Index2[1]]))) {
						
						//if two pieces have equal number of greatest number of jumps, this code prevents the piece with the 
						//smallest amount of available jump from jumping
						if(	cell.Index2==63&&
								(countJumpBrown[(int)((cell.y_position*size)+cell.x_position)]<=countJumpBrown[countJump_Index2[0]]&&
								countJumpBrown[(int)((cell.y_position*size)+cell.x_position)]<=countJumpBrown[countJump_Index2[1]]&&
								countJumpBrown[(int)((cell.y_position*size)+cell.x_position)]<=countJumpBrown[countJump_Index2[2]])) {
							
								flag5=0;
								highestNumberJumpsIndex2=64;
								
								
							
						}
						
						//if two pieces have equal number of greatest number of jumps, sets highest index to index of lastClicked
						if(countJumpBrown[(int)((cell.y_position*size)+cell.x_position)]>=countJumpBrown[countJump_Index2[0]]&&
								countJumpBrown[(int)((cell.y_position*size)+cell.x_position)]>=countJumpBrown[countJump_Index2[1]]&&
								countJumpBrown[(int)((cell.y_position*size)+cell.x_position)]>=countJumpBrown[countJump_Index2[2]]) {
						
							highestNumberJumpsIndex2=(int)((cell.y_position*size)+cell.x_position);
							flag5=1;
					
					
						}
						
								
								
						
					
					}
				}
		

	
}//END OF THE GROUP OF CODE THAT HANDLES OBLIGATING BROWN PIECE.

		
		
		
		//-----------------
		//WHITEMOVE
		//----------------

		
		if(	!cell.PieceName.equals("white")&&!cell.PieceName.equals("brown")&&!cell.PieceName.equals("null")
			&&lastClicked.PieceName.equals("white")&&MoveTurn.equals("brown")
			&&((
			(((lastClicked.CrownName.equals("King White"))?		
					(flag2==0&&(cell.y_position<lastClicked.y_position||cell.y_position>lastClicked.y_position)
						
					&&
					((cell.x_position==(lastClicked.x_position+1)||cell.x_position==(lastClicked.x_position-1))
					&&(cell.y_position==(lastClicked.y_position+1)||cell.y_position==(lastClicked.y_position-1))
					
					||(cell.x_position==(lastClicked.x_position+2)||cell.x_position==(lastClicked.x_position-2))
					&&(cell.y_position==(lastClicked.y_position+2)||cell.y_position==(lastClicked.y_position-2))
					
					||(cell.x_position==(lastClicked.x_position+3)||cell.x_position==(lastClicked.x_position-3))
					&&(cell.y_position==(lastClicked.y_position+3)||cell.y_position==(lastClicked.y_position-3))))
					
					:((flag2==0&&cell.y_position<lastClicked.y_position)
					&&(cell.x_position==(lastClicked.x_position+1)||cell.x_position==(lastClicked.x_position-1))
					&&(cell.y_position==(lastClicked.y_position+1)||cell.y_position==(lastClicked.y_position-1)))))
			 )
			
			||(flag2==1
			
			//-------------------------------------------------------------------------------------------------------------------------------------
			//Conditions to obligate the piece with the highest available jump to jump and if piece is King, it gets 
			//the obligation.
			//-------------------------------------------------------------------------------------------------------------------------------------
			&&( (flagKingWhite==1)?
				
				(lastClicked.CrownName.equals("King White")&&
						
				(cell.y_position<lastClicked.y_position||cell.y_position>lastClicked.y_position)
					
				&&
				((cell.x_position==(lastClicked.x_position+1)||cell.x_position==(lastClicked.x_position-1))
				&&(cell.y_position==(lastClicked.y_position+1)||cell.y_position==(lastClicked.y_position-1))
				
				
				||(cell.x_position==(lastClicked.x_position+2)||cell.x_position==(lastClicked.x_position-2))
				&&(cell.y_position==(lastClicked.y_position+2)||cell.y_position==(lastClicked.y_position-2))
				
				||(cell.x_position==(lastClicked.x_position+3)||cell.x_position==(lastClicked.x_position-3))
				&&(cell.y_position==(lastClicked.y_position+3)||cell.y_position==(lastClicked.y_position-3))))
					
				:((flag4==1&&cell.Index==0&&flag6==1)
				
				||((highestNumberJumpsIndex1>=0&&
				lastClicked.equals(dama[highestNumberJumpsIndex1]))
						
				||(highestNumberJumpsIndex1-2*(size-1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-2*(size-1)]))
					
				||(highestNumberJumpsIndex1-4*(size-1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-4*(size-1)]))
						
				||(highestNumberJumpsIndex1-6*(size-1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-6*(size-1)]))
						
				||(	highestNumberJumpsIndex1-2*(size+1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-2*(size+1)]))
						
				||(	highestNumberJumpsIndex1-4*(size+1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-4*(size+1)]))
						
				||(	highestNumberJumpsIndex1-6*(size+1)>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-6*(size+1)]))
						
				||(highestNumberJumpsIndex1-(2*(size+1)+2*(size-1))>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-(2*(size+1)+2*(size-1))]))
						
				||(highestNumberJumpsIndex1-(4*(size+1)+2*(size-1))>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-(4*(size+1)+2*(size-1))]))
						
				||(highestNumberJumpsIndex1-(2*(size-1)+2*(size+1))>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-(2*(size-1)+2*(size+1))]))
						
				||(highestNumberJumpsIndex1-(4*(size-1)+2*(size+1))>=0&&
				cell.equals(dama[highestNumberJumpsIndex1-(4*(size-1)+2*(size+1))]))))
				)
				
			//------------------------------------------------------------------
					
			&&(!lastClicked.CrownName.equals("King White")?
				
				((cell.y_position<lastClicked.y_position)
				
				&&((cell.x_position==(lastClicked.x_position-2)
					//upper left corner
					&&((int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1))>=0
					&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1))].PieceName.equals("brown")))	
				||
				
				(cell.x_position==(lastClicked.x_position+2)
					//upper right corner
					&&((int)((lastClicked.y_position*size)+lastClicked.x_position-(size-1))>=0
					&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size-1))].PieceName.equals("brown")))))
				
				:
					
				((cell.y_position<lastClicked.y_position||cell.y_position>lastClicked.y_position)
				
				&&(
				//Forward movement of capture
				((cell.y_position==(lastClicked.y_position-2)||cell.y_position==(lastClicked.y_position-3))
				
				&&(
				//upper left corner
				(((int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1))>=0
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1))].PieceName.equals("brown")
				&&(cell.x_position==(lastClicked.x_position-2)||cell.x_position==(lastClicked.x_position-3)))
				||
				((int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1))>=0
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1))].PieceName.equals("empty")
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-2*(size+1))].PieceName.equals("brown")
				&&(cell.x_position==(lastClicked.x_position-3))))
				
				||
				//upper right corner		
				(((int)((lastClicked.y_position*size)+lastClicked.x_position-(size-1))>=0
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size-1))].PieceName.equals("brown")
				&&(cell.x_position==(lastClicked.x_position+2)||cell.x_position==(lastClicked.x_position+3)))
				||
				((int)((lastClicked.y_position*size)+lastClicked.x_position-(size-1))>=0
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size-1))].PieceName.equals("empty")
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-2*(size-1))].PieceName.equals("brown")
				&&(cell.x_position==(lastClicked.x_position+3))))))
				
				||
				
				//Backward movement of capture
				((cell.y_position==(lastClicked.y_position+2)||cell.y_position==(lastClicked.y_position+3))
						
				&&(
				//lower right corner
				(((int)((lastClicked.y_position*size)+lastClicked.x_position+(size+1))<=63
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size+1))].PieceName.equals("brown")
				&&(cell.x_position==(lastClicked.x_position+2)||cell.x_position==(lastClicked.x_position+3)))
				||
				((int)((lastClicked.y_position*size)+lastClicked.x_position+(size+1))<=63
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size+1))].PieceName.equals("empty")
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+2*(size+1))].PieceName.equals("brown")
				&&(cell.x_position==(lastClicked.x_position+3))))
						
				||
				//lower left corner		
				(((int)((lastClicked.y_position*size)+lastClicked.x_position+(size-1))<=63
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size-1))].PieceName.equals("brown")
				&&(cell.x_position==(lastClicked.x_position-2)||cell.x_position==(lastClicked.x_position-3)))
				||
				((int)((lastClicked.y_position*size)+lastClicked.x_position+(size-1))<=63
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size-1))].PieceName.equals("empty")
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+2*(size-1))].PieceName.equals("brown")
				&&(cell.x_position==(lastClicked.x_position-3))))))))) 
			
					) ) ){
			
				//------------------------
				//------------------------
			
				cell.setContent(1,"white");
				cell.dama_piece.setBackground(Color.yellow);
				
				if(cell.y_position==0||lastClicked.CrownName.equals("King White"))
					cell.setContent(3,"King White");
				
				
				lastClicked.content.setIcon(null);
				lastClicked.TrailPieceName=lastClicked.PieceName;
			
				if(cell.y_position==0||lastClicked.CrownName.equals("King White"))
					lastClicked.TrailPieceName=lastClicked.CrownName;					
				
				lastClicked.PieceName="empty";
				lastClicked.CrownName="none";
				lastClicked.dama_piece.setBackground(Color.darkGray);
				MoveTurn="white";
				flag=0;
				flag2=0;
				
				
				if(		
						//upper left corner
						(((int)((cell.y_position*size)+cell.x_position-2*(size+1))>=0&&
						dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("brown")
						&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size+1))].PieceName.equals("empty"))
						
						||
						
						//upper right corner
						((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
						dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("brown")
						&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].PieceName.equals("empty")))
					
						&&
						
						((highestNumberJumpsIndex1>=0&&
						lastClicked.equals(dama[highestNumberJumpsIndex1]))
						
						||(highestNumberJumpsIndex1-2*(size-1)>=0&&
						cell.equals(dama[highestNumberJumpsIndex1-2*(size-1)]))
						
						||(highestNumberJumpsIndex1-4*(size-1)>=0&&
						cell.equals(dama[highestNumberJumpsIndex1-4*(size-1)]))
						
						||(highestNumberJumpsIndex1-6*(size-1)>=0&&
						cell.equals(dama[highestNumberJumpsIndex1-6*(size-1)]))
						
						||(	highestNumberJumpsIndex1-2*(size+1)>=0&&
						cell.equals(dama[highestNumberJumpsIndex1-2*(size+1)]))
						
						||(	highestNumberJumpsIndex1-4*(size+1)>=0&&
						cell.equals(dama[highestNumberJumpsIndex1-4*(size+1)]))
						
						||(	highestNumberJumpsIndex1-6*(size+1)>=0&&
						cell.equals(dama[highestNumberJumpsIndex1-6*(size+1)]))
						
						||(highestNumberJumpsIndex1-(2*(size+1)+2*(size-1))>=0&&
						cell.equals(dama[highestNumberJumpsIndex1-(2*(size+1)+2*(size-1))]))
						
						||(highestNumberJumpsIndex1-(4*(size+1)+2*(size-1))>=0&&
						cell.equals(dama[highestNumberJumpsIndex1-(4*(size+1)+2*(size-1))]))
						
						||(highestNumberJumpsIndex1-(2*(size-1)+2*(size+1))>=0&&
						cell.equals(dama[highestNumberJumpsIndex1-(2*(size-1)+2*(size+1))]))
						
						||(highestNumberJumpsIndex1-(4*(size-1)+2*(size+1))>=0&&
						cell.equals(dama[highestNumberJumpsIndex1-(4*(size-1)+2*(size+1))])))
						
						
						) {
					
					for(int i=0;i<size*size;i++) {
						dama[i].Index=highestNumberJumpsIndex1;
					}

				}
				
				else {
					for(int i=0;i<size*size;i++) {
						if(dama[i].Index!=0)
							dama[i].Index=0;
					}
				}
				

	
	
		}
		
		//------------
		//BROWNMOVE
		//------------
		if(	!cell.PieceName.equals("brown")&&!cell.PieceName.equals("white")&&!cell.PieceName.equals("null")
			&&(lastClicked.PieceName.equals("brown")||lastClicked.PieceName.equals("King Brown"))&&MoveTurn.equals("white")
			
			&&((
			((lastClicked.CrownName.equals("King Brown"))?		
			(flag2==0&&(cell.y_position<lastClicked.y_position||cell.y_position>lastClicked.y_position)
								
			&&
			((cell.x_position==(lastClicked.x_position+1)||cell.x_position==(lastClicked.x_position-1))
			&&(cell.y_position==(lastClicked.y_position+1)||cell.y_position==(lastClicked.y_position-1))
							
			||(cell.x_position==(lastClicked.x_position+2)||cell.x_position==(lastClicked.x_position-2))
			&&(cell.y_position==(lastClicked.y_position+2)||cell.y_position==(lastClicked.y_position-2))
							
			||(cell.x_position==(lastClicked.x_position+3)||cell.x_position==(lastClicked.x_position-3))
			&&(cell.y_position==(lastClicked.y_position+3)||cell.y_position==(lastClicked.y_position-3))))
							
			:((flag2==0&&cell.y_position>lastClicked.y_position)
			&&(cell.x_position==(lastClicked.x_position+1)||cell.x_position==(lastClicked.x_position-1))
			&&(cell.y_position==(lastClicked.y_position+1)||cell.y_position==(lastClicked.y_position-1)))))
			
			||(flag2==2
			
			//-------------------------------------------------------------------------------------------------------------------------------------
			//Conditions to obligate the piece with the highest available jump to jump
			//-------------------------------------------------------------------------------------------------------------------------------------
			&&(
					
					(flagKingBrown==1)?
							
					(lastClicked.CrownName.equals("King Brown")&&
									
					(cell.y_position<lastClicked.y_position||cell.y_position>lastClicked.y_position)
								
					&&
					((cell.x_position==(lastClicked.x_position+1)||cell.x_position==(lastClicked.x_position-1))
					&&(cell.y_position==(lastClicked.y_position+1)||cell.y_position==(lastClicked.y_position-1))
							
					||(cell.x_position==(lastClicked.x_position+2)||cell.x_position==(lastClicked.x_position-2))
					&&(cell.y_position==(lastClicked.y_position+2)||cell.y_position==(lastClicked.y_position-2))
							
					||(cell.x_position==(lastClicked.x_position+3)||cell.x_position==(lastClicked.x_position-3))
					&&(cell.y_position==(lastClicked.y_position+3)||cell.y_position==(lastClicked.y_position-3))))
								
					:((flag5==1&&cell.Index2==63&&flag7==1)
					
					||((highestNumberJumpsIndex2<=63&&
					lastClicked.equals(dama[highestNumberJumpsIndex2]))
							
					||(highestNumberJumpsIndex2+2*(size-1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+2*(size-1)]))
						
					||(highestNumberJumpsIndex2+4*(size-1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+4*(size-1)]))
							
					||(highestNumberJumpsIndex2+6*(size-1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+6*(size-1)]))
							
					||(	highestNumberJumpsIndex2+2*(size+1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+2*(size+1)]))
							
					||(	highestNumberJumpsIndex2+4*(size+1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+4*(size+1)]))
							
					||(	highestNumberJumpsIndex2+6*(size+1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+6*(size+1)]))
							
					||(highestNumberJumpsIndex2+(2*(size+1)+2*(size-1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(2*(size+1)+2*(size-1))]))
							
					||(highestNumberJumpsIndex2+(4*(size+1)+2*(size-1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(4*(size+1)+2*(size-1))]))
							
					||(highestNumberJumpsIndex2+(2*(size-1)+2*(size+1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(2*(size-1)+2*(size+1))]))
							
					||(highestNumberJumpsIndex2+(4*(size-1)+2*(size+1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(4*(size-1)+2*(size+1))]))))
			)
						
			//------------------------------------------------------------------
			
			&&(!lastClicked.CrownName.equals("King Brown")?
				(cell.y_position>lastClicked.y_position
				
				&&(	(cell.x_position==(lastClicked.x_position+2)
				//lower right corner
				&&((int)((lastClicked.y_position*size)+lastClicked.x_position+(size+1))<=63	
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size+1))].PieceName.equals("white")))	
				||
				(cell.x_position==(lastClicked.x_position-2)
				//lower left corner
				&&((int)((lastClicked.y_position*size)+cell.x_position+(size-1))<=63
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size-1))].PieceName.equals("white")))))
				
				:
					
				((cell.y_position<lastClicked.y_position||cell.y_position>lastClicked.y_position)
							
				&&(
				//Backward movement of capture
				((cell.y_position==(lastClicked.y_position-2)||cell.y_position==(lastClicked.y_position-3))
							
				&&(
				//upper left corner
				(((int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1))>=0
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1))].PieceName.equals("white")
				&&(cell.x_position==(lastClicked.x_position-2)||cell.x_position==(lastClicked.x_position-3)))
				||
				((int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1))>=0
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1))].PieceName.equals("empty")
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-2*(size+1))].PieceName.equals("white")
				&&(cell.x_position==(lastClicked.x_position-3))))
							
				||
				//upper right corner		
				(((int)((lastClicked.y_position*size)+lastClicked.x_position-(size-1))>=0
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size-1))].PieceName.equals("white")
				&&(cell.x_position==(lastClicked.x_position+2)||cell.x_position==(lastClicked.x_position+3)))
				||
				((int)((lastClicked.y_position*size)+lastClicked.x_position-(size-1))>=0
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size-1))].PieceName.equals("empty")
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-2*(size-1))].PieceName.equals("white")
				&&(cell.x_position==(lastClicked.x_position+3))))))
							
				||
							
				//Forward movement of capture
				((cell.y_position==(lastClicked.y_position+2)||cell.y_position==(lastClicked.y_position+3))
									
				&&(
				//lower right corner
				(((int)((lastClicked.y_position*size)+lastClicked.x_position+(size+1))<=63
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size+1))].PieceName.equals("white")
				&&(cell.x_position==(lastClicked.x_position+2)||cell.x_position==(lastClicked.x_position+3)))
				||
				((int)((lastClicked.y_position*size)+lastClicked.x_position+(size+1))<=63
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size+1))].PieceName.equals("empty")
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+2*(size+1))].PieceName.equals("white")
				&&(cell.x_position==(lastClicked.x_position+3))))
									
				||
				//lower left corner		
				(((int)((lastClicked.y_position*size)+lastClicked.x_position+(size-1))<=63
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size-1))].PieceName.equals("white")
				&&(cell.x_position==(lastClicked.x_position-2)||cell.x_position==(lastClicked.x_position-3)))
				||
				((int)((lastClicked.y_position*size)+lastClicked.x_position+(size-1))<=63
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size-1))].PieceName.equals("white")
				&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+2*(size-1))].PieceName.equals("brown")
				&&(cell.x_position==(lastClicked.x_position-3)))))))))
					) )	) {
			
			//-------------------------------------------------------
			//-------------------------------------------------------
			cell.setContent(2,"brown");
			cell.dama_piece.setBackground(Color.yellow);
			
			if(cell.y_position==7||lastClicked.CrownName.equals("King Brown"))
				cell.setContent(4,"King Brown");
			
			lastClicked.content.setIcon(null);
			lastClicked.TrailPieceName=lastClicked.PieceName;

			if(cell.y_position==7||lastClicked.CrownName.equals("King Brown"))
				lastClicked.TrailPieceName =lastClicked.CrownName;
			
			lastClicked.PieceName="empty";
			lastClicked.CrownName="none";
			lastClicked.dama_piece.setBackground(Color.darkGray);
			MoveTurn="brown";
			flag=0;
			flag2=0;
			
			if(		
					//lower right corner
					(((int)((cell.y_position*size)+cell.x_position+2*(size+1))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+(size+1))].PieceName.equals("white")
					&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size+1))].PieceName.equals("empty"))
					
					||
					
					//lower left corner
					((int)((cell.y_position*size)+cell.x_position+2*(size-1))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+(size-1))].PieceName.equals("white")
					&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].PieceName.equals("empty")))
					
					
					&&
					((highestNumberJumpsIndex2<=63&&
					lastClicked.equals(dama[highestNumberJumpsIndex2]))
							
					||(highestNumberJumpsIndex2+2*(size-1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+2*(size-1)]))
						
					||(highestNumberJumpsIndex2+4*(size-1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+4*(size-1)]))
							
					||(highestNumberJumpsIndex2+6*(size-1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+6*(size-1)]))
							
					||(	highestNumberJumpsIndex2+2*(size+1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+2*(size+1)]))
							
					||(	highestNumberJumpsIndex2+4*(size+1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+4*(size+1)]))
							
					||(	highestNumberJumpsIndex2+6*(size+1)<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+6*(size+1)]))
							
					||(highestNumberJumpsIndex2+(2*(size+1)+2*(size-1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(2*(size+1)+2*(size-1))]))
							
					||(highestNumberJumpsIndex2+(4*(size+1)+2*(size-1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(4*(size+1)+2*(size-1))]))
							
					||(highestNumberJumpsIndex2+(2*(size-1)+2*(size+1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(2*(size-1)+2*(size+1))]))
							
					||(highestNumberJumpsIndex2+(4*(size-1)+2*(size+1))<=63&&
					cell.equals(dama[highestNumberJumpsIndex2+(4*(size-1)+2*(size+1))]))) 
					
					
					
					
					
					) {
				
				for(int i=0;i<size*size;i++) {
					dama[i].Index2=highestNumberJumpsIndex2;
				}

			}
			
			else {
				for(int i=0;i<size*size;i++) {
					if(dama[i].Index2!=63)
						dama[i].Index2=63;
				}
			}
			
			
		
			
		}	
		
		//resets flag3 to 2 if King White captures from y=7;
		
		

				
	
		
		
		
		//flags value to enter WhiteMove so that white piece jumps over brown piece when capturing

		if(	
			(lastClicked.PieceName.equals("white")&&flag3==1
			
			
			&&(
					
			flagKingWhite!=1?
			
					//upper left corner
			(((int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))>=0
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1 ))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))].PieceName.equals("empty")))
			
			
		
			
			//upper right corner
			||((int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size-1)))>=0
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-((size-1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size-1)))].PieceName.equals("empty"))))
			
			
			
			:(
					
			//lower right corner x1
			((int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size+1)))<=63
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+((size+1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size+1)))].PieceName.equals("empty")))
			
			||(//lower left corner x1
			(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size-1)))<=63
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+((size-1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size-1)))].PieceName.equals("empty")))
			
			//upper right corner x1
			||((int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size-1)))>=0
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-((size-1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size-1)))].PieceName.equals("empty")))
			
			||(//upper left corner x1
			(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))>=0
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-((size+1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))].PieceName.equals("empty")))
			
			||(//lower right corner x2
			(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size+1)))<=63
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+((size+1)))].PieceName.equals("empty")
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size+1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(3*(size+1)))].PieceName.equals("empty"))))
			
			||(//lower left corner x2
			(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size-1)))<=63
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+((size-1)))].PieceName.equals("empty")
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size-1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(3*(size-1)))].PieceName.equals("empty"))))
			
			||(//upper left corner x2
			(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))>=0
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-((size+1)))].PieceName.equals("empty")
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(3*(size+1)))].PieceName.equals("empty"))))
			
			||(//upper right corner x2
			(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))>=0
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-((size+1)))].PieceName.equals("empty")
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(3*(size+1)))].PieceName.equals("empty"))))
			
			)
			
			))) {
					
					
	
			flag2=1;
			MoveTurn="brown";
		
			
	
										
		}//end of if statement for capturing brown with white
		

		//flags value to enter BrownMove so that brown piece jumps over white piece when capturing
		 if(
				 
				 
			lastClicked.PieceName.equals("brown")&&flag3==2
			
			
				 
				 
				 
			&&(
			
			flagKingBrown!=1?
			(//lower left corner
			((int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size-1)))<=63
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+((size-1)))].PieceName.equals("white")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size-1)))].PieceName.equals("empty")))
				
			
			
			//lower right corner
			||((int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size+1)))<=63
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+((size+1)))].PieceName.equals("white")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size+1)))].PieceName.equals("empty"))))
			
			
			:(		
			//upper right corner x1
			((int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size-1)))>=0
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-((size-1)))].PieceName.equals("white")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size-1)))].PieceName.equals("empty")))		
					
					
			||
			//upper left corner x1
			((int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))>=0
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(size+1 ))].PieceName.equals("white")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))].PieceName.equals("empty")))
			
			
			//lower right corner x1
			||((int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size+1)))<=63
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+((size+1)))].PieceName.equals("white")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size+1)))].PieceName.equals("empty")))		
					
					
			||
			//lower left corner x1
			((int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size-1)))<=63
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(size-1))].PieceName.equals("white")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size-1)))].PieceName.equals("empty")))
			
			
					
			||(//upper right corner
			(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size-1)))>=0
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-((size-1)))].PieceName.equals("empty")
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size-1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(3*(size-1)))].PieceName.equals("empty"))))
					
			||(//upper left corner
			(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))>=0
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-((size+1)))].PieceName.equals("empty")
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(2*(size+1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position-(3*(size+1)))].PieceName.equals("empty"))))		
					
			||(//lower right corner
			(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size+1)))<=63
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+((size+1)))].PieceName.equals("empty")
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size+1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(3*(size+1)))].PieceName.equals("empty"))))
							
			||(//lower left corner
			(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size-1)))<=63
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+((size-1)))].PieceName.equals("empty")
			&&(dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(2*(size-1)))].PieceName.equals("brown")
			&&dama[(int)((lastClicked.y_position*size)+lastClicked.x_position+(3*(size-1)))].PieceName.equals("empty")))) ) )
				 ) {
					
		
			
			flag2=2;
			MoveTurn="white";		
	
	
		}//end of if statement for capturing white with brown
		 
		
		 //reset countJump_Index to zero
		 
		 for(int i=0;i<3;i++) {
			 countJump_Index1[i]=0;
			 countJump_Index2[i]=63;
		
		 }
		 
		 
		
		 

		

	}catch(Exception e) {
		
	}
	
		
		
		
	}

	public void mouseReleased(MouseEvent event) {
		
		DamaCell cell = (DamaCell)event.getSource();
		
		
	try {
		if(	flag==0&&lastClicked.PieceName.equals("empty")&&!cell.PieceName.equals("null")) {
			cell.dama_piece.setBackground(Color.DARK_GRAY);
			lastClicked=cell;
					
			
			for(int i=0;i<size*size;i++) {
				//Capturing brown piece with white piece
				if(	lastPositionOrigin.PieceName.equals("empty")&&lastClicked.PieceName.equals("white")
					&&dama[i].PieceName.equals("brown")
					&&(dama[i].x_position==(lastPositionOrigin.x_position+1)||dama[i].x_position==(lastPositionOrigin.x_position-1))
					&&(dama[i].y_position==(lastPositionOrigin.y_position+1)||dama[i].y_position==(lastPositionOrigin.y_position-1))
					&&(dama[i].x_position==(lastClicked.x_position+1)||dama[i].x_position==(lastClicked.x_position-1))
					&&(dama[i].y_position==(lastClicked.y_position+1)||dama[i].y_position==(lastClicked.y_position-1))
					
					||(lastClicked.CrownName.equals("King White")
					&&dama[i].PieceName.equals("brown")
					&&(dama[i].x_position==(lastPositionOrigin.x_position+2)||dama[i].x_position==(lastPositionOrigin.x_position-2))
					&&(dama[i].y_position==(lastPositionOrigin.y_position+2)||dama[i].y_position==(lastPositionOrigin.y_position-2))
					&&(dama[i].x_position==(lastClicked.x_position+1)||dama[i].x_position==(lastClicked.x_position-1))
					&&(dama[i].y_position==(lastClicked.y_position+1)||dama[i].y_position==(lastClicked.y_position-1)))
					
					||(lastClicked.CrownName.equals("King White")
					&&dama[i].PieceName.equals("brown")
					&&(dama[i].x_position==(lastPositionOrigin.x_position+1)||dama[i].x_position==(lastPositionOrigin.x_position-1))
					&&(dama[i].y_position==(lastPositionOrigin.y_position+1)||dama[i].y_position==(lastPositionOrigin.y_position-1))
					&&(dama[i].x_position==(lastClicked.x_position+2)||dama[i].x_position==(lastClicked.x_position-2))
					&&(dama[i].y_position==(lastClicked.y_position+2)||dama[i].y_position==(lastClicked.y_position-2)))
						
						){
				
					dama[i].content.setIcon(null);
					dama[i].PieceName="empty";

					countBrown++;
				}
				//Capturing white piece with brown piece
				if(	lastPositionOrigin.PieceName.equals("empty")&&lastClicked.PieceName.equals("brown")
					&&dama[i].PieceName.equals("white")
					&&(dama[i].x_position==(lastPositionOrigin.x_position+1)||dama[i].x_position==(lastPositionOrigin.x_position-1))
					&&(dama[i].y_position==(lastPositionOrigin.y_position+1)||dama[i].y_position==(lastPositionOrigin.y_position-1))
					&&(dama[i].x_position==(lastClicked.x_position+1)||dama[i].x_position==(lastClicked.x_position-1))
					&&(dama[i].y_position==(lastClicked.y_position+1)||dama[i].y_position==(lastClicked.y_position-1))
					
					||(lastClicked.CrownName.equals("King Brown")
					&&dama[i].PieceName.equals("white")
					&&(dama[i].x_position==(lastPositionOrigin.x_position+2)||dama[i].x_position==(lastPositionOrigin.x_position-2))
					&&(dama[i].y_position==(lastPositionOrigin.y_position+2)||dama[i].y_position==(lastPositionOrigin.y_position-2))
					&&(dama[i].x_position==(lastClicked.x_position+1)||dama[i].x_position==(lastClicked.x_position-1))
					&&(dama[i].y_position==(lastClicked.y_position+1)||dama[i].y_position==(lastClicked.y_position-1)))
							
					||(lastClicked.CrownName.equals("King Brown")
					&&dama[i].PieceName.equals("white")
					&&(dama[i].x_position==(lastPositionOrigin.x_position+1)||dama[i].x_position==(lastPositionOrigin.x_position-1))
					&&(dama[i].y_position==(lastPositionOrigin.y_position+1)||dama[i].y_position==(lastPositionOrigin.y_position-1))
					&&(dama[i].x_position==(lastClicked.x_position+2)||dama[i].x_position==(lastClicked.x_position-2))
					&&(dama[i].y_position==(lastClicked.y_position+2)||dama[i].y_position==(lastClicked.y_position-2)))
							
									){
					
					dama[i].content.setIcon(null);
					dama[i].PieceName="empty";	

					countWhite++;
					}		
				
			}
			
			//This piece of code obligates white piece to capture first and prevent brown piece from capturing white out of turn
		
			if(	(
					(flagKingWhite!=1?
					//upper right corner
					((((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
					dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("brown")		
					&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].PieceName.equals("empty"))
					
					//upper left corner
					||(
						(int)((cell.y_position*size)+cell.x_position-(2*(size+1)))>=0&&
						dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("brown")
						&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size+1))].PieceName.equals("empty")))
					
					&&(
						((int)((cell.y_position*size)+cell.x_position+2*(size-1))<=63&&
						dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].TrailPieceName.equals("white"))
						||((int)((cell.y_position*size)+cell.x_position+(2*(size+1)))<=63&&
						dama[(int)((cell.y_position*size)+cell.x_position+(2*(size+1)))].TrailPieceName.equals("white"))))
							
					:(
					(//upper right corner		
					((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
					(dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("brown")
					&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].PieceName.equals("brown")))
											
					//upper left corner
					||((int)((cell.y_position*size)+cell.x_position-(2*(size+1)))>=0&&
					dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("brown")
					&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size+1))].PieceName.equals("empty"))
									
					//lower right corner		
					||((int)((cell.y_position*size)+cell.x_position+2*(size+1))<=63&&
					(dama[(int)((cell.y_position*size)+cell.x_position+(size+1))].PieceName.equals("brown")
					&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size+1))].PieceName.equals("empty")))
											
					//lower left corner
					||((int)((cell.y_position*size)+cell.x_position+(2*(size-1)))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+(size-1))].PieceName.equals("brown")
					&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].PieceName.equals("empty"))
									
					||//upper right corner+1		
					((int)((cell.y_position*size)+cell.x_position-3*(size-1))>=0&&
					(dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("empty")
					&&(dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].PieceName.equals("brown")
					&&dama[(int)((cell.y_position*size)+cell.x_position-3*(size-1))].PieceName.equals("empty"))))
									
					//upper left corner+1
					||((int)((cell.y_position*size)+cell.x_position-(3*(size+1)))>=0&&
					dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("empty")
					&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size+1))].PieceName.equals("brown")
					&&dama[(int)((cell.y_position*size)+cell.x_position-3*(size+1))].PieceName.equals("empty"))
									
									//lower right corner+1		
					||((int)((cell.y_position*size)+cell.x_position+3*(size+1))<=63&&
					(dama[(int)((cell.y_position*size)+cell.x_position+(size+1))].PieceName.equals("empty")
					&&(dama[(int)((cell.y_position*size)+cell.x_position+2*(size+1))].PieceName.equals("brown")
					&&dama[(int)((cell.y_position*size)+cell.x_position+3*(size+1))].PieceName.equals("empty"))))
											
					//lower left corner+1
					||((int)((cell.y_position*size)+cell.x_position+(3*(size-1)))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+(size-1))].PieceName.equals("empty")
					&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].PieceName.equals("brown")
					&&dama[(int)((cell.y_position*size)+cell.x_position+3*(size-1))].PieceName.equals("empty")))		
											
					&&(
					//trail piece name lower left corner	
					((int)((cell.y_position*size)+cell.x_position+2*(size-1))<=63&&
						dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].TrailPieceName.equals("King White"))
									
					//trail piece name lower right corner
					||((int)((cell.y_position*size)+cell.x_position+(2*(size+1)))<=63&&
						dama[(int)((cell.y_position*size)+cell.x_position+(2*(size+1)))].TrailPieceName.equals("King White"))
									
					//trail piece name upper right corner
					||((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
						dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].TrailPieceName.equals("King White"))
									
					//trail piece name upper left corner
					||((int)((cell.y_position*size)+cell.x_position-(2*(size+1)))>=0&&
					dama[(int)((cell.y_position*size)+cell.x_position-(2*(size+1)))].TrailPieceName.equals("King White"))
										
					//trail piece name lower left corner+1
					||((int)((cell.y_position*size)+cell.x_position+3*(size-1))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+3*(size-1))].TrailPieceName.equals("King White"))
								
					//trail piece name lower right corner+1
					||((int)((cell.y_position*size)+cell.x_position+(3*(size+1)))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+(3*(size+1)))].TrailPieceName.equals("King White"))
								
					//trail piece name upper right corner+1
					||((int)((cell.y_position*size)+cell.x_position-3*(size-1))>=0&&
					dama[(int)((cell.y_position*size)+cell.x_position-3*(size-1))].TrailPieceName.equals("King White"))
								
					//trail piece name upper left corner+1
					||((int)((cell.y_position*size)+cell.x_position-(3*(size+1)))>=0&&
					dama[(int)((cell.y_position*size)+cell.x_position-(3*(size+1)))].TrailPieceName.equals("King White"))))
					)
					
					
					
					
					||((flagKingBrown==1? false: (lastPositionOrigin.TrailPieceName.equals("brown")))	
					||lastPositionOrigin.TrailPieceName.equals("King Brown")	
					
					||(((int)((cell.y_position*size)+cell.x_position+(size-1))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position)].CrownName.equals("King Brown")
					&&dama[(int)((cell.y_position*size)+cell.x_position+(size-1))].PieceName.equals("white"))
					||((int)((cell.y_position*size)+cell.x_position+(size+1))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position)].CrownName.equals("King Brown")
					&&dama[(int)((cell.y_position*size)+cell.x_position+(size+1))].PieceName.equals("white")))))
					) {
				
				
					flag3=1;
				
				
					if((flagKingWhite!=1?
							//upper right corner
							((((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
							dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("brown")		
							&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].PieceName.equals("empty"))
							
							//upper left corner
							||(
								(int)((cell.y_position*size)+cell.x_position-(2*(size+1)))>=0&&
								dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("brown")
								&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size+1))].PieceName.equals("empty")))
							
							&&(
								((int)((cell.y_position*size)+cell.x_position+2*(size-1))<=63&&
								dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].TrailPieceName.equals("white"))
								||((int)((cell.y_position*size)+cell.x_position+(2*(size+1)))<=63&&
								dama[(int)((cell.y_position*size)+cell.x_position+(2*(size+1)))].TrailPieceName.equals("white"))))
									
							:(
							(//upper right corner		
							((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
							(dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("brown")
							&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].PieceName.equals("brown")))
													
							//upper left corner
							||((int)((cell.y_position*size)+cell.x_position-(2*(size+1)))>=0&&
							dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("brown")
							&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size+1))].PieceName.equals("empty"))
											
							//lower right corner		
							||((int)((cell.y_position*size)+cell.x_position+2*(size+1))<=63&&
							(dama[(int)((cell.y_position*size)+cell.x_position+(size+1))].PieceName.equals("brown")
							&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size+1))].PieceName.equals("empty")))
													
							//lower left corner
							||((int)((cell.y_position*size)+cell.x_position+(2*(size-1)))<=63&&
							dama[(int)((cell.y_position*size)+cell.x_position+(size-1))].PieceName.equals("brown")
							&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].PieceName.equals("empty"))
											
							||//upper right corner+1		
							((int)((cell.y_position*size)+cell.x_position-3*(size-1))>=0&&
							(dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("empty")
							&&(dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].PieceName.equals("brown")
							&&dama[(int)((cell.y_position*size)+cell.x_position-3*(size-1))].PieceName.equals("empty"))))
											
							//upper left corner+1
							||((int)((cell.y_position*size)+cell.x_position-(3*(size+1)))>=0&&
							dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("empty")
							&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size+1))].PieceName.equals("brown")
							&&dama[(int)((cell.y_position*size)+cell.x_position-3*(size+1))].PieceName.equals("empty"))
											
											//lower right corner+1		
							||((int)((cell.y_position*size)+cell.x_position+3*(size+1))<=63&&
							(dama[(int)((cell.y_position*size)+cell.x_position+(size+1))].PieceName.equals("empty")
							&&(dama[(int)((cell.y_position*size)+cell.x_position+2*(size+1))].PieceName.equals("brown")
							&&dama[(int)((cell.y_position*size)+cell.x_position+3*(size+1))].PieceName.equals("empty"))))
													
							//lower left corner+1
							||((int)((cell.y_position*size)+cell.x_position+(3*(size-1)))<=63&&
							dama[(int)((cell.y_position*size)+cell.x_position+(size-1))].PieceName.equals("empty")
							&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].PieceName.equals("brown")
							&&dama[(int)((cell.y_position*size)+cell.x_position+3*(size-1))].PieceName.equals("empty")))		
													
							&&(
							//trail piece name lower left corner	
							((int)((cell.y_position*size)+cell.x_position+2*(size-1))<=63&&
								dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].TrailPieceName.equals("King White"))
											
							//trail piece name lower right corner
							||((int)((cell.y_position*size)+cell.x_position+(2*(size+1)))<=63&&
								dama[(int)((cell.y_position*size)+cell.x_position+(2*(size+1)))].TrailPieceName.equals("King White"))
											
							//trail piece name upper right corner
							||((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
								dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].TrailPieceName.equals("King White"))
											
							//trail piece name upper left corner
							||((int)((cell.y_position*size)+cell.x_position-(2*(size+1)))>=0&&
							dama[(int)((cell.y_position*size)+cell.x_position-(2*(size+1)))].TrailPieceName.equals("King White"))
												
							//trail piece name lower left corner+1
							||((int)((cell.y_position*size)+cell.x_position+3*(size-1))<=63&&
							dama[(int)((cell.y_position*size)+cell.x_position+3*(size-1))].TrailPieceName.equals("King White"))
										
							//trail piece name lower right corner+1
							||((int)((cell.y_position*size)+cell.x_position+(3*(size+1)))<=63&&
							dama[(int)((cell.y_position*size)+cell.x_position+(3*(size+1)))].TrailPieceName.equals("King White"))
										
							//trail piece name upper right corner+1
							||((int)((cell.y_position*size)+cell.x_position-3*(size-1))>=0&&
							dama[(int)((cell.y_position*size)+cell.x_position-3*(size-1))].TrailPieceName.equals("King White"))
										
							//trail piece name upper left corner+1
							||((int)((cell.y_position*size)+cell.x_position-(3*(size+1)))>=0&&
							dama[(int)((cell.y_position*size)+cell.x_position-(3*(size+1)))].TrailPieceName.equals("King White"))))
							)) {
					
							lastPositionOrigin.TrailPieceName="empty";
							
							
					}
					
					
				
			}
		
			//This piece of code obligates brown piece to capture first and prevent white piece from capturing white out of turn
			
			if(
				(flagKingBrown!=1?
						
				((	(int)((cell.y_position*size)+cell.x_position+2*(size-1))<=63&&
					(dama[(int)((cell.y_position*size)+cell.x_position+(size-1))].PieceName.equals("white")
					&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].PieceName.equals("empty"))
						
				||((int)((cell.y_position*size)+cell.x_position+(2*(size+1)))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+(size+1))].PieceName.equals("white")
					&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size+1))].PieceName.equals("empty")))		
						
				&&(
				(((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
					dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].TrailPieceName.equals("brown"))
				||((int)((cell.y_position*size)+cell.x_position-(2*(size+1)))>=0&&
					dama[(int)((cell.y_position*size)+cell.x_position-(2*(size+1)))].TrailPieceName.equals("brown")))))
				
				:(
				(//upper right corner		
				((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
				(dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("white")
				&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].PieceName.equals("empty")))
						
				//upper left corner
				||((int)((cell.y_position*size)+cell.x_position-(2*(size+1)))>=0&&
				dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("white")
				&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size+1))].PieceName.equals("empty"))
				
				//lower right corner		
				||((int)((cell.y_position*size)+cell.x_position+2*(size+1))<=63&&
				(dama[(int)((cell.y_position*size)+cell.x_position+(size+1))].PieceName.equals("white")
				&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size+1))].PieceName.equals("empty")))
						
				//lower left corner
				||((int)((cell.y_position*size)+cell.x_position+(2*(size-1)))<=63&&
				dama[(int)((cell.y_position*size)+cell.x_position+(size-1))].PieceName.equals("white")
				&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].PieceName.equals("empty"))
				
				||//upper right corner+1		
				((int)((cell.y_position*size)+cell.x_position-3*(size-1))>=0&&
				(dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("empty")
				&&(dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].PieceName.equals("white")
				&&dama[(int)((cell.y_position*size)+cell.x_position-3*(size-1))].PieceName.equals("empty"))))
				
				//upper left corner+1
				||((int)((cell.y_position*size)+cell.x_position-(3*(size+1)))>=0&&
				dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("empty")
				&&dama[(int)((cell.y_position*size)+cell.x_position-2*(size+1))].PieceName.equals("white")
				&&dama[(int)((cell.y_position*size)+cell.x_position-3*(size+1))].PieceName.equals("empty"))
				
				//lower right corner+1		
				||((int)((cell.y_position*size)+cell.x_position+3*(size+1))<=63&&
				(dama[(int)((cell.y_position*size)+cell.x_position+(size+1))].PieceName.equals("empty")
				&&(dama[(int)((cell.y_position*size)+cell.x_position+2*(size+1))].PieceName.equals("white")
				&&dama[(int)((cell.y_position*size)+cell.x_position+3*(size+1))].PieceName.equals("empty"))))
						
				//lower left corner+1
				||((int)((cell.y_position*size)+cell.x_position+(3*(size-1)))<=63&&
				dama[(int)((cell.y_position*size)+cell.x_position+(size-1))].PieceName.equals("empty")
				&&dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].PieceName.equals("white")
				&&dama[(int)((cell.y_position*size)+cell.x_position+3*(size-1))].PieceName.equals("empty")))		
						
				&&(
				//trail piece name lower left corner	
				((int)((cell.y_position*size)+cell.x_position+2*(size-1))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+2*(size-1))].TrailPieceName.equals("King Brown"))
				
				//trail piece name lower right corner
				||((int)((cell.y_position*size)+cell.x_position+(2*(size+1)))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+(2*(size+1)))].TrailPieceName.equals("King Brown"))
				
				//trail piece name upper right corner
				||((int)((cell.y_position*size)+cell.x_position-2*(size-1))>=0&&
					dama[(int)((cell.y_position*size)+cell.x_position-2*(size-1))].TrailPieceName.equals("King Brown"))
				
				//trail piece name upper left corner
				||((int)((cell.y_position*size)+cell.x_position-(2*(size+1)))>=0&&
					dama[(int)((cell.y_position*size)+cell.x_position-(2*(size+1)))].TrailPieceName.equals("King Brown"))
					
				//trail piece name lower left corner+1
				||((int)((cell.y_position*size)+cell.x_position+3*(size-1))<=63&&
				dama[(int)((cell.y_position*size)+cell.x_position+3*(size-1))].TrailPieceName.equals("King Brown"))
			
				//trail piece name lower right corner+1
				||((int)((cell.y_position*size)+cell.x_position+(3*(size+1)))<=63&&
					dama[(int)((cell.y_position*size)+cell.x_position+(3*(size+1)))].TrailPieceName.equals("King Brown"))
			
				//trail piece name upper right corner+1
				||((int)((cell.y_position*size)+cell.x_position-3*(size-1))>=0&&
				dama[(int)((cell.y_position*size)+cell.x_position-3*(size-1))].TrailPieceName.equals("King Brown"))
			
				//trail piece name upper left corner+1
				||((int)((cell.y_position*size)+cell.x_position-(3*(size+1)))>=0&&
				dama[(int)((cell.y_position*size)+cell.x_position-(3*(size+1)))].TrailPieceName.equals("King Brown"))))
				
				)
								
				||((flagKingWhite==1? false: (lastPositionOrigin.TrailPieceName.equals("white")))	
				
				||lastPositionOrigin.TrailPieceName.equals("King White")
				
				||(((int)((cell.y_position*size)+cell.x_position-(size-1))>=0&&
				dama[(int)((cell.y_position*size)+cell.x_position)].CrownName.equals("King White")
				&&dama[(int)((cell.y_position*size)+cell.x_position-(size-1))].PieceName.equals("brown"))
				||((int)((cell.y_position*size)+cell.x_position-(size+1))>=0&&
				dama[(int)((cell.y_position*size)+cell.x_position)].CrownName.equals("King White")
				&&dama[(int)((cell.y_position*size)+cell.x_position-(size+1))].PieceName.equals("brown"))))
				
					) {
				
				flag3=2;
				
			

			}
			
			
	
					
			
			
			for(int i=0;i<size*size;i++) {
				
				if(dama[i].PieceName.equals("empty")) {
					dama[i].TrailPieceName=dama[i].PieceName;
				}
			}
		
	
		
		}
		


	if(countBrown==12) {
		JOptionPane.showMessageDialog(this,"White wins!");
	}
	
	if(countWhite==12) {
		JOptionPane.showMessageDialog(this,"Brown wins!");
	}
	
	
	
	int WinWhite=0;
	int WinBrown=0;
	
	
	
		for (int i=0;i<size*size;i++) {
		
			//This code checks if there are no more available jumps for white and hence brown wins.
			if(
					
			(dama[i].PieceName.equals("white")
			//upper left corner
			&&((((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1 ))].PieceName.equals("brown")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)))].PieceName.equals("empty"))))
			//upper right corner
			||((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))].PieceName.equals("brown")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)))].PieceName.equals("empty")))
			
			//upper left corner-1
			||(((int)((dama[i].y_position*size)+dama[i].x_position-((size+1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1 ))].PieceName.equals("empty"))))
			
			//upper right corner-1
			||((int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))].PieceName.equals("empty")))))
			
			||(
					
					
					
			dama[i].CrownName.equals("KingWhite")
			&&((
			//upper right corner
			(((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("brown")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
								
			||((int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))>=0&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("empty")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("brown")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))].PieceName.equals("empty")))
								
			||
								
								//upper left corner
			(((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("brown")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
										
			||((int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))>=0&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("empty")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("brown")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))].PieceName.equals("empty")))
								
			||
								
			//lower right corner
			(((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("brown")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
												
			||((int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))<=63&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("empty")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("brown")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))].PieceName.equals("empty")))
								
			||
			//lower left corner
			((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63&&
			(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("brown")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
								
			//lower left corner
			||((int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))<=63&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("empty")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("brown")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))].PieceName.equals("empty"))) )
			
			//upper left corner -1
			||(((int)((dama[i].y_position*size)+dama[i].x_position-((size+1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1 ))].PieceName.equals("empty"))))
					
			//upper right corner-1
			||((int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))].PieceName.equals("empty")))					
			
			//lower right corner-1
			||(((int)((dama[i].y_position*size)+dama[i].x_position+((size+1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1 ))].PieceName.equals("empty"))))
					
			//lower left corner-1
			||((int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))].PieceName.equals("empty")))	
			
			
			//upper left corner -2
			||((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1 ))].PieceName.equals("empty"))
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1 ))].PieceName.equals("empty"))
				)
					
			//upper right corner-2
			||((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))].PieceName.equals("empty"))
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)))].PieceName.equals("empty")))					
			
			
			//lower right corner-2
			||((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1 ))].PieceName.equals("empty"))
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty")))
					
					
					
			//lower left corner-2
			||((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))].PieceName.equals("empty"))
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)))].PieceName.equals("empty"))
			)))
			){
				
				WinBrown=0;
				break;
				
			}
			else {
				
				WinBrown=1;
	
			}
		} 
			//This code checks if there are no more available jumps for brown and hence white wins
		
	for(int i=0;i<size*size;i++) {
			
		if(	
			(dama[i].PieceName.equals("brown")
			//lower left corner
			&&(((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))].PieceName.equals("white")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)))].PieceName.equals("empty")))
					
			//lower right corner
			||((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size+1)))].PieceName.equals("white")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)))].PieceName.equals("empty")))
			
			//lower right corner-1
			||(((int)((dama[i].y_position*size)+dama[i].x_position+((size+1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1 ))].PieceName.equals("empty"))))
			
			//lower left corner-1
			||((int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))].PieceName.equals("empty")))))
			
					
			||(
			
			dama[i].CrownName.equals("King Brown")
			&&(
			//upper right corner
			(((int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))>=0&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("white")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("empty"))
						
			||((int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))>=0&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size-1))].PieceName.equals("empty")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size-1))].PieceName.equals("white")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size-1))].PieceName.equals("empty")))
						
			||
					
			//upper left corner
			(((int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))>=0&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("white")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("empty"))
								
			||((int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))>=0&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1))].PieceName.equals("empty")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1))].PieceName.equals("white")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position-3*(size+1))].PieceName.equals("empty")))
						
			||
						
			//lower right corner
			(((int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))<=63&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("white")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty"))
										
			||((int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))<=63&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1))].PieceName.equals("empty")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("white")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size+1))].PieceName.equals("empty")))
						
			||
						
			((int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))<=63&&
			(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("white")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("empty"))
						
			//lower left corner
			||((int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))<=63&&
			dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size-1))].PieceName.equals("empty")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size-1))].PieceName.equals("white")
			&&dama[(int)((dama[i].y_position*size)+dama[i].x_position+3*(size-1))].PieceName.equals("empty"))) 
			
			//upper left corner -1
			||(((int)((dama[i].y_position*size)+dama[i].x_position-((size+1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1 ))].PieceName.equals("empty"))))
					
			//upper right corner-1
			||((int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))].PieceName.equals("empty")))					
			
			//lower right corner-1
			||(((int)((dama[i].y_position*size)+dama[i].x_position+((size+1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1 ))].PieceName.equals("empty"))))
					
			//lower left corner-1
			||((int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))].PieceName.equals("empty")))	
			
			
			//upper left corner -2
			||((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size+1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-(size+1 ))].PieceName.equals("empty"))
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-2*(size+1 ))].PieceName.equals("empty"))
				)
					
			//upper right corner-2
			||((int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)))>=0
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-((size-1)))].PieceName.equals("empty"))
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position-(2*(size-1)))].PieceName.equals("empty")))					
			
			
			//lower right corner-2
			||((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size+1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(size+1 ))].PieceName.equals("empty"))
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+2*(size+1))].PieceName.equals("empty")))
			
			//lower left corner-2
			||((int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)))<=63
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+((size-1)))].PieceName.equals("empty"))
			&&(dama[(int)((dama[i].y_position*size)+dama[i].x_position+(2*(size-1)))].PieceName.equals("empty"))
			)))	
					
					){
				
				WinWhite=0;
				break;
			}
			else {
				WinWhite=1;
			}
		}
		
		if(WinWhite==1) {
			JOptionPane.showMessageDialog(this,"White wins!");
		}
		
		if(WinBrown==1) {
			JOptionPane.showMessageDialog(this,"Brown wins!");
		}
		
		
	
		
	}catch(Exception e) {}


	}

	public void mouseEntered(MouseEvent event)  {}

	public void mouseExited(MouseEvent event)   {}

}


