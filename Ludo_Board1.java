package inheritance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ludo_Board1 {

	private JFrame mainFrame;
	private JPanel panel1, panel2;
	private JButton dice;
	private JLabel red,blue,green,yellow;
	private static JPanel[][] pathPanel;
	private Token red1,red2,red3,red4,green1,green2,green3,green4,blue1,blue2,blue3,blue4,yellow1,yellow2,yellow3,yellow4;
	private int diceNumber;
	private boolean isTurnPending;
	private Color turn;
	private Color[] playingColors;
	
	public Ludo_Board1() {
		startGame();
	}

	public static void main(String[] args) {
		new Ludo_Board1();
	}

	public void startGame(){
		mainFrame = new JFrame("LUDO");
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setBoard();
				
		initializeTokens();
		
		setRightPane();
		
		playingColors = new Color[]{Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE};
		turn = playingColors[0];
		
		mainFrame.add(panel1);
		mainFrame.setVisible(true);
	}

	public void setRightPane() {
		
		initializeLabelsAndDices();
		
		dice.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				diceNumber = (int)Math.ceil(6*Math.random());
				switch(diceNumber){
				case 1:
					dice.setText("1");
					break;
				case 2:
					dice.setText("2");
					break;
				case 3:
					dice.setText("3");
					break;
				case 4:
					dice.setText("4");
					break;
				case 5:
					dice.setText("5");
					break;
				case 6:
					dice.setText("6");
				}
				isTurnPending = true;
			}
		});
		
		panel2 = new JPanel(new GridBagLayout());
		setPanelGrid(panel1,panel2,15,0,4,15);
		
		for(int i=0; i<15; i++){
				setPanelGrid(panel2,new JLabel(""),0,i,2,1);
		}
		
		for(int i=0; i<4; i++){
			setPanelGrid(panel2,new JLabel(""),2,i,2,1);
		}
		
		setPanelGrid(panel2,red,2,4,2,1);
		
		setPanelGrid(panel2,green,2,5,2,1);
		
		setPanelGrid(panel2,yellow,2,6,2,1);
		
		setPanelGrid(panel2,blue,2,7,2,1);
		
		setPanelGrid(panel2,new JLabel(""),2,8,2,1);
		
		setPanelGrid(panel2,dice,2,9,2,2);
		
		for(int i=10; i<15; i++){
			setPanelGrid(panel2,new JLabel(""),2,i,2,1);
		}
		
		for(int i=0; i<15; i++){
				setPanelGrid(panel2,new JLabel(""),4,i,2,1);
		}
	}

	public void initializeLabelsAndDices() {
		red = new JLabel("red",JLabel.CENTER);
		green = new JLabel("green",JLabel.CENTER);
		yellow = new JLabel("yellow",JLabel.CENTER);
		blue = new JLabel("blue",JLabel.CENTER);
		dice = new JButton("6");
	}

	public void move(Token t) {
		switch(diceNumber){
		case 1:
			if(!t.isInHouse && !t.hasCompleted){
				changePos(t,1);
			}
			break;
			
		case 2:
			if(!t.isInHouse && !t.hasCompleted){
				changePos(t,2);
			}
			break;
			
		case 3:
			if(!t.isInHouse && !t.hasCompleted){
				changePos(t,3);
			}
			break;
			
		case 4:
			if(!t.isInHouse && !t.hasCompleted){
				changePos(t,4);
			}
			break;
			
		case 5:
			if(!t.isInHouse && !t.hasCompleted){
				changePos(t,5);
			}
			break;
			
		case 6:
			if(t.isInHouse){
				changePos(t,1);
			}
			else if(!t.hasCompleted){
				changePos(t,6);
			}
		}
	}

	public void nextTurn() {
		for(int i=0; i<playingColors.length; i++){
			if(playingColors[i] == turn){
				turn = playingColors[(i+1)%playingColors.length];
				break;
			}
		}
	}

	public void changePos(Token t, int p) {
		if((t.pos + p) < 58){
			int x = t.path[t.pos + p][0];
			int y = t.path[t.pos + p][1];
			setPanelGrid(pathPanel[x][y],t.b,x,y,1,1);
			if(p!=6){
				nextTurn();
			}
		}
		else if((t.pos + p) == 57){
			int x = t.path[57][0];
			int y = t.path[57][0];
			setPanelGrid(pathPanel[x][y],t.b,x,y,1,1);
			t.hasCompleted = true;
		}
	}

	private void comparePos(Token t) {
		// TODO Auto-generated method stub
		
	}

	public void initializeTokens() {
		red1 = new Token(Color.RED,"R1",2,2);
		red1.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(red1);
			}
		});
		goHome(red1);
		red2 = new Token(Color.RED,"R2",3,2);
		red2.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(red2);
			}
		});
		goHome(red2);
		red3 = new Token(Color.RED,"R3",2,3);
		red3.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(red3);
			}
		});
		goHome(red3);
		red4 = new Token(Color.RED,"R4",3,3);
		red4.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(red4);
			}
		});
		goHome(red4);
		
		green1 = new Token(Color.GREEN,"G1",11,2);
		green1.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(green1);
			}
		});
		goHome(green1);
		green2 = new Token(Color.GREEN,"G2",12,2);
		green2.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(green2);
			}
		});
		goHome(green2);
		green3 = new Token(Color.GREEN,"G3",11,3);
		green3.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(green3);
			}
		});
		goHome(green3);
		green4 = new Token(Color.GREEN,"G4",12,3);
		green4.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(green4);
			}
		});
		goHome(green4);
		
		blue1 = new Token(Color.BLUE,"B1",2,11);
		blue1.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(blue1);
			}
		});
		goHome(blue1);
		blue2 = new Token(Color.BLUE,"B2",3,11);
		blue2.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(blue2);
			}
		});
		goHome(blue2);
		blue3 = new Token(Color.BLUE,"B3",2,12);
		blue3.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(blue3);
			}
		});
		goHome(blue3);
		blue4 = new Token(Color.BLUE,"B4",3,12);
		blue4.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(blue4);
			}
		});
		goHome(blue4);
		
		yellow1 = new Token(Color.YELLOW,"Y1",11,11);
		yellow1.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(yellow1);
			}
		});
		goHome(yellow1);
		yellow2 = new Token(Color.YELLOW,"Y2",12,11);
		yellow2.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(yellow2);
			}
		});
		goHome(yellow2);
		yellow3 = new Token(Color.YELLOW,"Y3",11,12);
		yellow3.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(yellow3);
			}
		});
		goHome(yellow3);
		yellow4 = new Token(Color.YELLOW,"Y4",12,12);
		yellow4.b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				move(yellow4);
			}
		});
		goHome(yellow4);
	}

	public void setBoard() {
		pathPanel = new JPanel[15][15];
		
		for(int i=0; i<15; i++){
			for(int j=0; j<15; j++){
				pathPanel[i][j] = new JPanel(new GridBagLayout());
			}
		}
		
		panel1 = new JPanel(new GridBagLayout());
		
		setHouse(0,0,Color.RED);
		
		for(int i=6; i<9; i++){
			for(int j=0; j<6; j++){
				if(j == 0){
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(2,1,1,1,Color.BLACK));
				}
				else{
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
				}
				if((i==7 && j!=0) || (i==8 && j==1)){
					pathPanel[i][j].setBackground(Color.GREEN);
				}
				else{
					pathPanel[i][j].setBackground(Color.WHITE);
				}
				setPanelGrid(panel1,pathPanel[i][j],i,j,1,1);
			}
		}
		
		setHouse(9,0,Color.GREEN);
		
		for(int i=0; i<6; i++){
			for(int j=6; j<9; j++){
				if(i == 0){
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,2,1,1,Color.BLACK));
				}
				else{
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
				}
				if((i!=0 && j==7) || (i==1 && j==6)){
					pathPanel[i][j].setBackground(Color.RED);
				}
				else{
					pathPanel[i][j].setBackground(Color.WHITE);
				}
				setPanelGrid(panel1,pathPanel[i][j],i,j,1,1);
			}
		}
		
		for(int i=9; i<15; i++){
			for(int j=6; j<9; j++){
				if(i == 15){
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,2,Color.BLACK));
				}
				else{
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
				}
				if((i!=14 && j==7) || (i==13 && j==8)){
					pathPanel[i][j].setBackground(Color.YELLOW);
				}
				else{
					pathPanel[i][j].setBackground(Color.WHITE);
				}
				setPanelGrid(panel1,pathPanel[i][j],i,j,1,1);
			}
		}
		
		setHouse(0,9,Color.BLUE);
		
		for(int i=6; i<9; i++){
			for(int j=9; j<15; j++){
				if(j == 15){
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,2,1,Color.BLACK));
				}
				else{
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
				}
				if((i==7 && j!=14) || (i==6 && j==13)){
					pathPanel[i][j].setBackground(Color.BLUE);
				}
				else{
					pathPanel[i][j].setBackground(Color.WHITE);
				}
				setPanelGrid(panel1,pathPanel[i][j],i,j,1,1);
			}
		}
		
		setHouse(9,9,Color.YELLOW);
		
	}

	public void setPanelGrid(JPanel p,Component comp,int x,int y,int width,int height){
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(0,0,0,0);
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		p.add(comp,c);
	}
	
	public void setHouse(int i, int j, Color c){
		pathPanel[i][j].setBackground(c);
		pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(2,2,0,0,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i][j],i,j,1,1);
		
		pathPanel[i+1][j].setBackground(c);
		pathPanel[i+1][j].setBorder(BorderFactory.createMatteBorder(2,0,2,0,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i+1][j],i+1,j,4,1);
		
		pathPanel[i+5][j].setBackground(c);
		pathPanel[i+5][j].setBorder(BorderFactory.createMatteBorder(2,0,0,2,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i+5][j],i+5,j,1,1);
		
		pathPanel[i][j+1].setBackground(c);
		pathPanel[i][j+1].setBorder(BorderFactory.createMatteBorder(0,2,0,2,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i][j+1],i,j+1,1,4);
		
		pathPanel[i+5][j+1].setBackground(c);
		pathPanel[i+5][j+1].setBorder(BorderFactory.createMatteBorder(0,2,0,2,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i+5][j+1],i+5,j+1,1,4);
		
		pathPanel[i][j+5].setBackground(c);
		pathPanel[i][j+5].setBorder(BorderFactory.createMatteBorder(0,2,2,0,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i][j+5],i,j+5,1,1);
		
		pathPanel[i+1][j+5].setBackground(c);
		pathPanel[i+1][j+5].setBorder(BorderFactory.createMatteBorder(2,0,2,0,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i+1][j+5],i+1,j+5,4,1);
		
		pathPanel[i+5][j+5].setBackground(c);
		pathPanel[i+5][j+5].setBorder(BorderFactory.createMatteBorder(0,0,2,2,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i+5][j+5],i+5,j+5,1,1);
		
		pathPanel[i+1][j+1].setBackground(Color.WHITE);
		setPanelGrid(panel1,pathPanel[i+1][j+1],i+1,j+1,4,1);
		
		pathPanel[i+1][j+2].setBackground(Color.WHITE);
		setPanelGrid(panel1,pathPanel[i+1][j+2],i+1,j+2,1,2);
		
		pathPanel[i+4][j+2].setBackground(Color.WHITE);
		setPanelGrid(panel1,pathPanel[i+4][j+2],i+4,j+2,1,2);
		
		pathPanel[i+1][j+4].setBackground(Color.WHITE);
		setPanelGrid(panel1,pathPanel[i+1][j+4],i+1,j+4,4,1);
		
		pathPanel[i+2][j+2].setBackground(c);
		pathPanel[i+2][j+2].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i+2][j+2],i+2,j+2,1,1);
		
		pathPanel[i+2][j+3].setBackground(c);
		pathPanel[i+2][j+3].setBorder(BorderFactory.createMatteBorder(0,1,1,1,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i+2][j+3],i+2,j+3,1,1);
		
		pathPanel[i+3][j+2].setBackground(c);
		pathPanel[i+3][j+2].setBorder(BorderFactory.createMatteBorder(1,0,1,1,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i+3][j+2],i+3,j+2,1,1);
		
		pathPanel[i+3][j+3].setBackground(c);
		pathPanel[i+3][j+3].setBorder(BorderFactory.createMatteBorder(0,0,1,1,Color.BLACK));
		setPanelGrid(panel1,pathPanel[i+3][j+3],i+3,j+3,1,1);
	}
	
	public void goHome(Token t){
		setPanelGrid(pathPanel[t.HouseX][t.HouseY],t.b,t.HouseX,t.HouseY,1,1);
	}
}