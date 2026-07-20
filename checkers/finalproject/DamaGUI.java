package CollectionOfProjects.checkers.finalproject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;




public class DamaGUI extends JFrame implements ActionListener{
    //Main Frameholder    
      
    public JPanel panel = new JPanel();
    String countWhiteText;
    String countBrownText;
    JLabel countWhite,countBrown; 
    JButton newGame;
	DamaPanel damaPanel;
	
    public static void main(String[]args) {
            //Main class to run the Dama Game
     	DamaGUI game = new DamaGUI();
        game.mainGUI();
        
     		
    }
    
    public void mainGUI() {
      //Frame details and customization of the frame including size, title
      //location of the Dama Game Frame
      JPanel sidePanel = new JPanel();
      newGame = new JButton("New Game");
      
                add(panel);
                setSize(990,800);
               	setTitle("Dama Game");
                setLayout(new BorderLayout());
                setResizable(false);
                panel.setLayout(new BorderLayout());
                damaPanel= new DamaPanel(8,"brown");
                sidePanel.setLayout(new FlowLayout(0,40,700));
        newGame.addActionListener(this);
        sidePanel.add(newGame,"Center");
        sidePanel.setPreferredSize(new Dimension(1,1));
       
        
		panel.add(damaPanel,"West");

		
		
		add(panel,"West");
		add(sidePanel,"Center");
	
		setVisible(true);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

	
	public void actionPerformed(ActionEvent e) {
	
	
		panel.remove(damaPanel);
		damaPanel=new DamaPanel(8,"brown");
		panel.add(damaPanel,"West");
		SwingUtilities.updateComponentTreeUI(panel);
	}


    
}

