package CollectionOfProjects.checkers.finalproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DamaCell extends JPanel{ 
	
	public Point location;
	public JLabel content;
	public JPanel dama_piece;
	public String PieceName="empty"; 
	public String TrailPieceName="empty";//
	public String NamePiece1="white";
	public String NamePiece2="brown";
	public String CrownName="none";
	public double x_position;
	public double y_position;
	public int Index;
	public int Index2;
	
	
	public DamaCell() {
		this(null);
	}
	

	
	public DamaCell(Point pt) {
		
		setLayout(new BorderLayout());
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		dama_piece = new JPanel();
		dama_piece.setLayout(new FlowLayout());
		dama_piece.setBackground(Color.white);
		
		content = new JLabel();
		
		//setContent(pt.getY()); //for testing only
		
		dama_piece.add(content);
		add(dama_piece,"Center");
		
		//addMouseListener(this); for testing only
		location = pt;
		x_position = location.getX();
		y_position = location.getY();
	}
	
	public Point getPosition() {
		return location;
	}
	
	public void setNamePiece(String namePiece1, String namePiece2) {
		NamePiece1=namePiece1;//white
		NamePiece2=namePiece2;//brown
	}

	
	public void setContent(double y) {
		//white
		if (y>4) { 
			content.setIcon(new ImageIcon("CollectionOfProjects/checkers/finalproject/"+NamePiece1+" "+"piece.png"));
			dama_piece.setBackground(Color.darkGray);
			PieceName=NamePiece1;
			}
		//brown
		else if(y<3) {
			content.setIcon(new ImageIcon("CollectionOfProjects/checkers/finalproject/"+NamePiece2+" "+"piece.png"));
			dama_piece.setBackground(Color.DARK_GRAY);
			System.out.print("Yup");
			PieceName=NamePiece2;
			}
		//blank
		else if(y==4||y==3) {
			dama_piece.setBackground(Color.DARK_GRAY);
			PieceName="empty";
			}		
		
	}

	
	
	public void setContent(int x,String name) {
		
		if (x==1&&name.equals("white")) { 
			content.setIcon(new ImageIcon("CollectionOfProjects/checkers/finalproject/white piece.png"));
			PieceName="white";
			}
		
		else if(x==2&&name.equals("brown")) {
			content.setIcon(new ImageIcon("CollectionOfProjects/checkers/finalproject/brown piece.png"));
			PieceName="brown";
		}
		else if(x==3&&name.equals("King White")) {
			content.setIcon(new ImageIcon("CollectionOfProjects/checkers/finalproject/King White.png"));
			CrownName="King White";
			PieceName="white";
		}
		else if(x==4&&name.equals("King Brown")) {
			content.setIcon(new ImageIcon("CollectionOfProjects/checkers/finalproject/King Brown.png"));
			CrownName="King Brown";
			PieceName="brown";
		}
	


		
			
		
	}


	


}
