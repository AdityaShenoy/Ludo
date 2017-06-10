package inheritance;

import javax.annotation.Resources;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ludo_Board1 {

	private JFrame mainFrame;
	private JPanel panel1, panel2;
	private JButton dice;
	private JLabel red,blue,green,yellow;
	private JPanel[][] pathPanel;
	private Token red1,red2,red3,red4,green1,green2,green3,green4,blue1,blue2,blue3,blue4,yellow1,yellow2,yellow3,yellow4;
	private Token[] redGroup;
	private Token[] greenGroup;
	private Token[] yellowGroup;
	private Token[] blueGroup;
	private int diceNumber;
	private boolean isTurnPending;
	private Color turn;
	private Color[] playingColors;
	private int sixCount;

	public Ludo_Board1() {
		startGame();
		sixCount=0;
		isTurnPending = false;
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
		turn = playingColors[(int)Math.floor(playingColors.length*Math.random())];
		changeLabel();
		
		ImageIcon img = new ImageIcon("resources/star.png");
		pathPanel[6][2].add(new JLabel("",img,JLabel.CENTER),BorderLayout.CENTER);
		pathPanel[2][8].add(new JLabel("",img,JLabel.CENTER),BorderLayout.CENTER);
		pathPanel[8][12].add(new JLabel("",img,JLabel.CENTER),BorderLayout.CENTER);
		pathPanel[12][6].add(new JLabel("",img,JLabel.CENTER),BorderLayout.CENTER);
		
		mainFrame.add(panel1);
		mainFrame.setVisible(true);
		mainFrame.addComponentListener(new ComponentListener(){
			public void componentResized(ComponentEvent e){
/*				Dimension d = pathPanel[0][0].getSize();
				for(int i=0; i<4; i++){
					redGroup[i].b.setSize(d);
					redGroup[i].b.getParent().setSize(d);
					greenGroup[i].b.setSize(d);
					greenGroup[i].b.getParent().setSize(d);
					yellowGroup[i].b.setSize(d);
					yellowGroup[i].b.getParent().setSize(d);
					blueGroup[i].b.setSize(d);
					blueGroup[i].b.getParent().setSize(d);
				}
				panel1.repaint();
*/
			}
			
			public void componentMoved(ComponentEvent e) {	
			}
			public void componentShown(ComponentEvent e) {	
			}
			public void componentHidden(ComponentEvent e) {
			}
		});
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
				pathPanel[i][j] = new JPanel(new GridLayout(1,0));
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
				pathPanel[i][j] = new JPanel(new GridLayout(0,1));
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
				pathPanel[i][j] = new JPanel(new GridLayout(0,1));
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
				pathPanel[i][j] = new JPanel(new GridLayout(0,1));
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
		
		redGroup = new Token[]{red1,red2,red3,red4};
		greenGroup = new Token[]{green1,green2,green3,green4};
		yellowGroup = new Token[]{yellow1,yellow2,yellow3,yellow4};
		blueGroup = new Token[]{blue1,blue2,blue3,blue4};
		
/*		Dimension d = pathPanel[0][0].getSize();
		for(int i=0; i<4; i++){
			redGroup[i].b.getParent().setSize(d);
			redGroup[i].b.setSize(d);
			greenGroup[i].b.getParent().setSize(d);
			greenGroup[i].b.setSize(d);
			yellowGroup[i].b.getParent().setSize(d);
			yellowGroup[i].b.setSize(d);
			blueGroup[i].b.getParent().setSize(d);
			blueGroup[i].b.setSize(d);
		}
		mainFrame.repaint();
*/
	}

	public void move(Token t) {
		if(t.color == turn && isTurnPending){
			switch(diceNumber){
			case 1:
				if(t.pos!=0 && t.pos<56){
					t.pos++;
					changePos(t);
					comparePos(t);
				}
				else if(t.pos == 56){
					t.pos++;
					changePos(t);
					isTurnPending = false;
				}
				break;
				
			case 2:
				if(t.pos!=0 && t.pos<55){
					t.pos += 2;
					changePos(t);
					comparePos(t);
				}
				else if(t.pos == 55){
					t.pos += 2;
					changePos(t);
					isTurnPending = false;
				}
				break;
				
			case 3:
				if(t.pos!=0 && t.pos<54){
					t.pos += 3;
					changePos(t);
					comparePos(t);
				}
				else if(t.pos == 54){
					t.pos += 3;
					changePos(t);
					isTurnPending = false;
				}
				break;
				
			case 4:
				if(t.pos!=0 && t.pos<53){
					t.pos += 4;
					changePos(t);
					comparePos(t);
				}
				else if(t.pos == 53){
					t.pos += 4;
					changePos(t);
					isTurnPending = false;
				}
				break;
				
			case 5:
				if(t.pos!=0 && t.pos<52){
					t.pos += 5;
					changePos(t);
					comparePos(t);
				}
				else if(t.pos == 52){
					t.pos += 5;
					changePos(t);
					isTurnPending = false;
				}
				break;
				
			case 6:
				if(t.pos == 0){
					t.pos++;
					changePos(t);
					isTurnPending = false;
				}
				else if(t.pos<51){
					t.pos += 6;
					changePos(t);
					comparePos(t);
				}
				else if(t.pos == 51){
					t.pos = 57;
					changePos(t);
					isTurnPending = false;
				}
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
		changeLabel();
		isTurnPending = false;
		sixCount = 0;
	}

	public void changePos(Token t){
		pathPanel[t.path[t.pos][0]][t.path[t.pos][1]].add(t.b);
		t.isStarProtected = false;
		for(int i=0;i<8;i++){
			if(t.pos == t.starProtectedPos[i]){
				t.isStarProtected = true;
				break;
			}
		}
		panel1.repaint();
		mainFrame.repaint();
	}
	
	public void goHome(Token t){
		int x = t.HouseX;
		int y = t.HouseY;
		setPanelGrid(pathPanel[x][y],t.b,0,0,1,1);
		t.pos = 0;
		panel1.repaint();
		mainFrame.repaint();
	}
	
	public void setRightPane() {
		
		initializeLabelsAndDices();
		
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
		
		dice.setMnemonic(KeyEvent.VK_D);
		dice.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				if(!isTurnPending){
					diceNumber = (int)Math.ceil(6*Math.random());
					if(diceNumber == 6){
						sixCount++;
					}
					if(sixCount == 2){
						diceNumber = (int)Math.ceil(5*Math.random());
					}
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
				}
				isTurnPending = true;
				autoMove();
			}
		});
	}
	
	public void autoMove(){
		if(turn == Color.RED){
			autoMove(redGroup);
		}
		else if(turn == Color.GREEN){
			autoMove(greenGroup);
		}
		else if(turn == Color.YELLOW){
			autoMove(yellowGroup);
		}
		else{
			autoMove(blueGroup);
		}
	}
	
	public void autoMove(Token[] t){
		int inHouse = 0;
		int[] outPos = {0,0,0,0};
		Token inPos = null;
		int k = 0;
		for(int i=0; i<4; i++){
			if(t[i].pos == 0){
				inHouse++;
				inPos = t[i];
			}
			else{
				outPos[k++] = i;
			}
		}
		if(inHouse==4 && diceNumber!=6){
			nextTurn();
		}
		else if(inHouse==3 && diceNumber!=6){
			if(t[outPos[0]].pos < (57-diceNumber)){
				t[outPos[0]].pos += diceNumber;
				changePos(t[outPos[0]]);
				comparePos(t[outPos[0]]);
			}
			else if(t[outPos[0]].pos == (57-diceNumber)){
				t[outPos[0]].pos = 57;
				changePos(t[outPos[0]]);
				isTurnPending = false;
			}
			else{
				nextTurn();
			}
		}
		else if(inHouse==2 && diceNumber!=6){
			if(t[outPos[0]].pos == t[outPos[1]].pos){
				if(t[outPos[0]].pos < (57-diceNumber)){
					t[outPos[0]].pos += diceNumber;
					changePos(t[outPos[0]]);
					comparePos(t[outPos[0]]);
				}
				else if(t[outPos[0]].pos == (57-diceNumber)){
					t[outPos[0]].pos = 57;
					changePos(t[outPos[0]]);
					isTurnPending = false;
				}
				else{
					nextTurn();
				}
			}
			else{
				Token t1 = (t[outPos[0]].pos < t[outPos[1]].pos) ? t[outPos[0]]:t[outPos[1]];
				Token t2 = (t[outPos[0]].pos > t[outPos[1]].pos) ? t[outPos[0]]:t[outPos[1]];
				if(t2.pos > 57-diceNumber){
					if(t1.pos < (57-diceNumber)){
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
					else if(t1.pos == (57-diceNumber)){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					else{
						nextTurn();
					}
				}
			}
		}
		else if(inHouse==1 && diceNumber!=6){
			if(t[outPos[0]].pos==t[outPos[1]].pos && t[outPos[0]].pos==t[outPos[2]].pos){
				if(t[outPos[0]].pos < (57-diceNumber)){
					t[outPos[0]].pos += diceNumber;
					changePos(t[outPos[0]]);
					comparePos(t[outPos[0]]);
				}
				else if(t[outPos[0]].pos == (57-diceNumber)){
					t[outPos[0]].pos = 57;
					changePos(t[outPos[0]]);
					isTurnPending = false;
				}
				else{
					nextTurn();
				}
			}
			else if(t[outPos[0]].pos==t[outPos[1]].pos || t[outPos[0]].pos==t[outPos[2]].pos || t[outPos[2]].pos==t[outPos[1]].pos){
				Token s;
				Token p = (t[outPos[0]].pos==t[outPos[1]].pos || t[outPos[0]].pos==t[outPos[2]].pos) ? t[outPos[0]] : t[outPos[1]];
				if(t[outPos[0]].pos==t[outPos[1]].pos){
					s = t[outPos[2]];
				}
				else if(t[outPos[0]].pos==t[outPos[2]].pos){
					s = t[outPos[1]];
				}
				else{
					s = t[outPos[0]];
				}
				if(s.pos < p.pos){
					if(p.pos > 57-diceNumber){
						if(s.pos < (57-diceNumber)){
							s.pos += diceNumber;
							changePos(s);
							comparePos(s);
						}
						else if(s.pos == (57-diceNumber)){
							s.pos = 57;
							changePos(s);
							isTurnPending = false;
						}
						else{
							nextTurn();
						}
					}
				}
				else{
					if(s.pos > 57-diceNumber){
						if(p.pos < (57-diceNumber)){
							p.pos += diceNumber;
							changePos(p);
							comparePos(p);
						}
						else if(p.pos == (57-diceNumber)){
							p.pos = 57;
							changePos(p);
							isTurnPending = false;
						}
						else{
							nextTurn();
						}
					}
				}
			}
			else{
				Token t2 = null;
				Token t1 = (t[outPos[0]].pos<t[outPos[1]].pos && t[outPos[0]].pos<t[outPos[2]].pos) ? t[outPos[0]] : (t[outPos[1]].pos<t[outPos[2]].pos) ? t[outPos[1]] : t[outPos[2]];
				Token t3 = (t[outPos[0]].pos>t[outPos[1]].pos && t[outPos[0]].pos>t[outPos[2]].pos) ? t[outPos[0]] : (t[outPos[1]].pos>t[outPos[2]].pos) ? t[outPos[1]] : t[outPos[2]];
				for(int i=0; i<3; i++){
					if(t[outPos[i]].pos>t1.pos && t[outPos[i]].pos<t3.pos){
						t2 = t[outPos[i]];
						break;
					}
				}
				if(t2.pos>57-diceNumber && t3.pos>57-diceNumber){
					if(t1.pos < (57-diceNumber)){
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
					else if(t1.pos == (57-diceNumber)){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					else{
						nextTurn();
					}
				}
			}
		}
		else if(inHouse==0 && diceNumber!=6){
			for(int i=0; i<3; i++){
				for(int j=0; j<(2-i); j++){
					if(t[j].pos > t[j+1].pos){
						Token temp = t[j+1];
						t[j+1] = t[j];
						t[j] = temp;
					}
				}
			}
			Token t1 = t[0];
			Token t2 = t[1];
			Token t3 = t[2];
			Token t4 = t[3];
			
			if(t1.pos==t2.pos && t2.pos==t3.pos && t3.pos==t4.pos){
				if(t1.pos>57-diceNumber){
					nextTurn();
				}
				else if(t1.pos==57-diceNumber){
					t1.pos = 57;
					changePos(t1);
					isTurnPending = false;
				}
				else{
					t1.pos += diceNumber;
					changePos(t1);
					comparePos(t1);
				}
			}
			else if((t1.pos==t2.pos && t2.pos==t3.pos) || (t2.pos==t3.pos && t3.pos==t4.pos)){
				if(t4.pos>57-diceNumber){
					if(t1.pos>57-diceNumber){
						nextTurn();
					}
					else if(t1.pos == 57-diceNumber){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					else{
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
				}
			}
			else if(t1.pos==t2.pos && t3.pos==t4.pos){
				if(t3.pos>57-diceNumber){
					if(t1.pos>57-diceNumber){
						nextTurn();
					}
					else if(t1.pos == 57-diceNumber){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					else{
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
				}
			}
			else if((t1.pos == t2.pos) || (t2.pos == t3.pos)){
				if(t4.pos>57-diceNumber && t3.pos>57-diceNumber){
					if(t1.pos>57-diceNumber){
						nextTurn();
					}
					else if(t1.pos == 57-diceNumber){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					else{
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
				}
			}
			else if(t3.pos == t4.pos){
				if(t4.pos>57-diceNumber && t2.pos>57-diceNumber){
					if(t1.pos>57-diceNumber){
						nextTurn();
					}
					else if(t1.pos == 57-diceNumber){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					else{
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
				}
			}
			else{
				if(t4.pos>57-diceNumber && t3.pos>57-diceNumber && t2.pos>57-diceNumber){
					if(t1.pos>57-diceNumber){
						nextTurn();
					}
					else if(t1.pos == 57-diceNumber){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					else{
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
				}
			}
		}
		else if(inHouse==1 && diceNumber==6){
			if(t[outPos[0]].pos>57-diceNumber && t[outPos[1]].pos>57-diceNumber && t[outPos[2]].pos>57-diceNumber){
				inPos.pos++;
				changePos(inPos);
				isTurnPending = false;
			}
		}
	}
	
	public void comparePos(Token t){
		boolean captured = false;
		for(int i=0; i<4; i++){
			if(t.path[t.pos][0] == redGroup[i].path[redGroup[i].pos][0] && t.path[t.pos][1] == redGroup[i].path[redGroup[i].pos][1] && t.color!=Color.RED && !redGroup[i].isStarProtected){
				goHome(redGroup[i]);
				captured = true;
			}
			if(t.path[t.pos][0] == greenGroup[i].path[greenGroup[i].pos][0] && t.path[t.pos][1] == greenGroup[i].path[greenGroup[i].pos][1] && t.color!=Color.GREEN && !greenGroup[i].isStarProtected){
				goHome(greenGroup[i]);
				captured = true;
			}
			if(t.path[t.pos][0] == yellowGroup[i].path[yellowGroup[i].pos][0] && t.path[t.pos][1] == yellowGroup[i].path[yellowGroup[i].pos][1] && t.color!=Color.YELLOW && !yellowGroup[i].isStarProtected){
				goHome(yellowGroup[i]);
				captured = true;
			}
			if(t.path[t.pos][0] == blueGroup[i].path[blueGroup[i].pos][0] && t.path[t.pos][1] == blueGroup[i].path[blueGroup[i].pos][1] && t.color!=Color.BLUE && !blueGroup[i].isStarProtected){
				goHome(blueGroup[i]);
				captured = true;
			}
			
		}
		if(diceNumber!=6){
			if(captured){
				isTurnPending = true;
			}
			else{
				nextTurn();
			}
		}
		else{
			isTurnPending = false;
		}
	}
	
	public void changeLabel(){
		if(turn == Color.RED){
			red.setForeground(Color.RED);
			blue.setForeground(Color.BLACK);
			green.setForeground(Color.BLACK);
			yellow.setForeground(Color.BLACK);
		}
		else if(turn == Color.BLUE){
			blue.setForeground(Color.RED);
			red.setForeground(Color.BLACK);
			green.setForeground(Color.BLACK);
			yellow.setForeground(Color.BLACK);
		}
		else if(turn == Color.GREEN){
			green.setForeground(Color.RED);
			yellow.setForeground(Color.BLACK);
			blue.setForeground(Color.BLACK);
			red.setForeground(Color.BLACK);
		}
		else{
			yellow.setForeground(Color.RED);
			green.setForeground(Color.BLACK);
			blue.setForeground(Color.BLACK);
			red.setForeground(Color.BLACK);
		}
	}
}
