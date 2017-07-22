package inheritance;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class LudoBoard{
	//The main frame of Ludo
	private JFrame mainFrame;
	
	//The panel where all other components are added
	private JPanel mainPanel;
	//The panel grid for ludo's path
	private JPanel[][] pathPanel;
	
	//Screen height after subtracting the icons' space from bottom
	private int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 45;
	//Screen full width
	private int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	//sixCount keeps the count of six fall so that it doesn't exceed two times
	private int sixCount,
	//diceNumber is the number on the die
	diceNumber,
	//rankNumber is the counter for the rank of playing colors
	//0 is for 1st, 1 is for 2nd and 2 is for 3rd
	rankNumber = 0;
	
	//Color constants so that it can be easily used in program
	private Color red = Color.RED,
	green = Color.GREEN,
	blue = Color.BLUE,
	yellow = Color.YELLOW,
	white = Color.WHITE,
	black = Color.BLACK,
	//turn indicates which color's turn is going on now
	turn;
	//playingColors is the array of the colors which are playing in current game
	//rank array keeps the record for the rank of colors
	private Color[] playingColors, rank;
	
	//dice and newGame buttons are displayed when the game starts
	private JButton dice, newGame;
	
	//Color turn labels are the labels which tell users which color's turn is now 
	private JLabel redTurnLabel, blueTurnLabel, greenTurnLabel, yellowTurnLabel,
	//Color star and arrow labels are used to add star and arrow images on ludo board
	yellowStar, greenStar, blueStar, redStar,
	greenArrow, yellowArrow, redArrow, blueArrow,
	//Dice label shows the dotted image of dice according to the dice number
	//endPosLabel is the center image of ludo which is the last pos of tokens
	//Crown label is used to show the rank of a particular token
	endPosLabel,
	crown1 = new JLabel("", new ImageIcon("resources/crown_1.png"), JLabel.CENTER),
	crown2 = new JLabel("", new ImageIcon("resources/crown_2.png"), JLabel.CENTER),
	crown3 = new JLabel("", new ImageIcon("resources/crown_3.png"), JLabel.CENTER);
	
	//These tokens are coins that play ludo
	private Token red1, red2, red3, red4,
	green1, green2, green3, green4,
	blue1, blue2, blue3, blue4,
	yellow1, yellow2, yellow3, yellow4;
	//These arrays are collection of all tokens of a single color
	private Token[] redGroup, greenGroup, yellowGroup, blueGroup;
	
	//Flag indicating whether the user has played after the dice has rolled
	private boolean isTurnPending;
	
	private ImageIcon diceImg;
	
	public static void main(String[] args){
		//Constructor call
		new LudoBoard();
	}
	
	public LudoBoard(){
		//This function initiates the frame with its specs
		prepareFrame();
	}
	
	public void prepareFrame(){
		//Title of frame
		mainFrame = new JFrame("Ludo");
		//Disallows user to resize the frame
		mainFrame.setResizable(false);
		//Fits the frame to the user's screen
		mainFrame.setSize(width, height);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Setting layout to null to use absolute positioning
		mainFrame.setLayout(null);
		
		mainPanel = new JPanel();
		//This panel takes the full space of frame
		//and all the components are added to this panel
		mainPanel.setBounds(0, 0, width, height);
		//Setting background color
		mainPanel.setBackground(white);
		//Enabling absolute positioning
		mainPanel.setLayout(null);
		
		//Adding main panel to the main frame
		mainFrame.add(mainPanel);
		//Now show it to the user
		mainFrame.setVisible(true);
		
		//The code for menu which has options for number of players and colors
		showMenu();
	}
	
	public void showMenu(){
		//Makes panel empty
		mainPanel.removeAll();
		
		//Setting background color to peach color
		Color bgcolor = Color.decode("#F5DEB3");
		mainPanel.setBackground(bgcolor);
		
		//Initializing and setting the size of first radio option without label
		JRadioButton firstRadio = new JRadioButton("");
		firstRadio.setBounds(2*width/8, 7*height/13, width/8, height/13);
		firstRadio.setHorizontalAlignment(JLabel.RIGHT);
		firstRadio.setBackground(bgcolor);
		mainPanel.add(firstRadio);
		
		//Initializing and setting the size of second radio option without label
		JRadioButton secondRadio = new JRadioButton("");
		secondRadio.setBounds(2*width/8, 8*height/13, width/8, height/13);
		secondRadio.setHorizontalAlignment(JLabel.RIGHT);
		secondRadio.setBackground(bgcolor);
		mainPanel.add(secondRadio);
		
		//Initializing and setting the size of third radio option without label
		JRadioButton thirdRadio = new JRadioButton("");
		thirdRadio.setBounds(2*width/8, 9*height/13, width/8, height/13);
		thirdRadio.setHorizontalAlignment(JLabel.RIGHT);
		thirdRadio.setBackground(bgcolor);
		mainPanel.add(thirdRadio);
		
		//Initializing and setting the size of fourth radio option without label
		//without adding it to the panel during start
		JRadioButton fourthRadio = new JRadioButton("");
		fourthRadio.setBounds(2*width/8, 10*height/13, width/8, height/13);
		fourthRadio.setHorizontalAlignment(JLabel.RIGHT);
		fourthRadio.setBackground(bgcolor);
		
		//Grouping of radio buttons so that only a single option can be selected
		ButtonGroup group = new ButtonGroup();
		group.add(firstRadio);
		group.add(secondRadio);
		group.add(thirdRadio);

		//Title Label and its specifications for Ludo
		JLabel mainLabel = new JLabel("LUDO");
		mainLabel.setBounds(2*width/8, height/13, 4*width/8, 3*height/13);
		mainLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 3*height/13));
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		mainLabel.setAlignmentX(0.5f);
		mainLabel.setForeground(Color.decode("#FF0000"));
		mainLabel.setBackground(bgcolor);
		mainPanel.add(mainLabel);
		
		//Select Players Label and its specification
		JLabel SPlabel = new JLabel("Select the no. of players");
		SPlabel.setBounds(2*width/8, 5*height/13, 4*width/8, height/13);
		SPlabel.setFont(new Font(Font.SERIF, Font.PLAIN, height/15));
		SPlabel.setHorizontalAlignment(JLabel.CENTER);
		SPlabel.setForeground(Color.decode("#FF0000"));
		SPlabel.setBackground(bgcolor);
		mainPanel.add(SPlabel);
		
		//Label and its specs for indicating what does selecting first radio button does
		JLabel firstOption = new JLabel("2 players");
		firstOption.setBounds(3*width/8, 7*height/13, 2*width/8, height/13);
		firstOption.setFont(new Font(Font.SERIF, Font.PLAIN, height/20));
		firstOption.setHorizontalAlignment(JLabel.CENTER);
		firstOption.setForeground(Color.decode("#0000FF"));
		firstOption.setBackground(bgcolor);
		mainPanel.add(firstOption);
		
		//Label and its specs for indicating what does selecting second radio button does
		JLabel secondOption = new JLabel("3 players");
		secondOption.setBounds(3*width/8, 8*height/13, 2*width/8, height/13);
		secondOption.setFont(new Font(Font.SERIF, Font.PLAIN, height/20));
		secondOption.setHorizontalAlignment(JLabel.CENTER);
		secondOption.setForeground(Color.decode("#0000FF"));
		secondOption.setBackground(bgcolor);
		mainPanel.add(secondOption);
		
		//Label and its specs for indicating what does selecting third radio button does
		JLabel thirdOption = new JLabel("4 players");
		thirdOption.setBounds(3*width/8, 9*height/13, 2*width/8, height/13);
		thirdOption.setFont(new Font(Font.SERIF, Font.PLAIN, height/20));
		thirdOption.setHorizontalAlignment(JLabel.CENTER);
		thirdOption.setForeground(Color.decode("#0000FF"));
		thirdOption.setBackground(bgcolor);
		mainPanel.add(thirdOption);
		
		//Label and its specs for indicating what does selecting fourth option does
		//without adding it as fourth radio button is not added yet
		JLabel fourthOption = new JLabel("");
		fourthOption.setBounds(3*width/8, 10*height/13, 2*width/8, height/13);
		fourthOption.setFont(new Font(Font.SERIF, Font.PLAIN, height/20));
		fourthOption.setHorizontalAlignment(JLabel.CENTER);
		fourthOption.setForeground(Color.decode("#0000FF"));
		fourthOption.setBackground(bgcolor);
		
		//This button starts the game
		JButton start = new JButton("START");
		start.setBounds(3*width/8, 11*height/13, width/8, height/13);
		start.setHorizontalAlignment(JLabel.CENTER);
		
		//This button goes to previous menu
		JButton back = new JButton("BACK");
		back.setBounds(4*width/8, 11*height/13, width/8, height/13);
		back.setHorizontalAlignment(JLabel.CENTER);
		//Setting the action of this button so that it can show the menu again
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				showMenu();
			}
		});
		
		//This button goes to the next page of selection of players if applicable
		JButton next = new JButton("NEXT");
		next.setBounds(3*width/8, 11*height/13, 2*width/8, height/13);
		next.setHorizontalAlignment(JLabel.CENTER);
		//Setting the action of this button so that it can show
		//next menu for selecting colors if applicable
		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(firstRadio.isSelected()){
					//Changing the prompt message
					SPlabel.setText("Select colours");
					//Giving options of opposite colors on ludo board
					firstOption.setText("RED/YELLOW");
					secondOption.setText("GREEN/BLUE");
					
					//Adding required buttons
					mainPanel.add(start);
					mainPanel.add(back);
					
					//Remove the previously set action if any
					start.removeActionListener(start.getAction());
					//Initialize the game for the selected two colors
					start.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							if(firstRadio.isSelected()){
								playingColors = new Color[]{red,yellow};
								initGame();
							}
							else if(secondRadio.isSelected()){
								playingColors = new Color[]{green,blue};
								initGame();
							}
						}
						
					});
					
					//Remove unnecessary buttons from the menu
					mainPanel.remove(next);
					mainPanel.remove(thirdRadio);
					mainPanel.remove(thirdOption);
					//Refresh the changes made on the frame
					mainFrame.repaint();
				}
				else if(secondRadio.isSelected()){
					//Change the prompt message
					SPlabel.setText("Select colours");
					//Giving all possible combinations for three colors
					firstOption.setText("RED/GREEN/YELLOW");
					secondOption.setText("RED/GREEN/BLUE");
					thirdOption.setText("RED/YELLOW/BLUE");
					fourthOption.setText("GREEN/YELLOW/BLUE");
					
					//Enlarging the label as the text of the labels are bigger
					firstOption.setBounds(3*width/8, 7*height/13, 3*width/8, height/13);
					secondOption.setBounds(3*width/8, 8*height/13, 3*width/8, height/13);
					thirdOption.setBounds(3*width/8, 9*height/13, 3*width/8, height/13);
					fourthOption.setBounds(3*width/8, 10*height/13, 3*width/8, height/13);
					
					//Add required components
					mainPanel.add(fourthRadio);
					mainPanel.add(fourthOption);
					mainPanel.add(back);
					mainPanel.add(start);
					
					//Adding it to the button group so that only 1 out of 4 can be selected at once
					group.add(fourthRadio);
					
					//Removing the previously set action if any
					start.removeActionListener(start.getAction());
					//Initializing the game with the selected colors
					start.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							if(firstRadio.isSelected()){
								playingColors = new Color[]{red,green,yellow};
								initGame();
							}
							else if(secondRadio.isSelected()){
								playingColors = new Color[]{red,green,blue};
								initGame();
							}
							else if(thirdRadio.isSelected()){
								playingColors = new Color[]{red,yellow,blue};
								initGame();
							}
							else if(fourthRadio.isSelected()){
								playingColors = new Color[]{green,yellow,blue};
								initGame();
							}
						}
						
					});
					
					//Removing unnecessary components
					mainPanel.remove(next);
					//Refresh the changes made on the frame
					mainFrame.repaint();
				}
				else if(thirdRadio.isSelected()){
					//If four players are selected directly start the game
					//As there is no option to select colors
					playingColors = new Color[]{red,green,yellow,blue};
					initGame();
				}
			}
		});
		mainPanel.add(next);
		
		//Refresh the changes made in the frame
		mainFrame.repaint();
	}
		
	public void initGame(){
		//Initializing the counter for fallen six to 0
		sixCount = 0;
		//The turn is not pending so setting false for this flag
		isTurnPending = false;
		//Randomly selecting which color will start the game for fair game
		turn = playingColors[(int)Math.floor(playingColors.length*Math.random())];
		//Allotting memory for rank array which will be equal to
		//the number of players playing
		rank = new Color[playingColors.length];
		//Removing menu components
		mainPanel.removeAll();
		//Refresh the changes made on the main frame
		mainFrame.repaint();
		//This function has the code for initializing the ludo board
		setBoard();
	}
	
	public void setBoard(){
		//Grid for the path of tokens
		pathPanel = new JPanel[15][15];
		
		//Enabling absolute positioning for each panel of this grid
		for(int i=0; i<15; i++){
			for(int j=0; j<15; j++){
				pathPanel[i][j] = new JPanel(null);
			}
		}
		
		//This function sets the panels for the red house
		//where the first two parameters are the coordinates of
		//the top left corner of the house
		setHouse(0, 0, red);
		
		//Following loop adds color and border to the path between green and red houses
		for(int i=6; i<9; i++){
			for(int j=0; j<6; j++){
				if(j == 0){
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(2,1,1,1,black));
				}
				else{
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,black));
				}
				if((i==7 && j!=0) || (i==8 && j==1)){
					pathPanel[i][j].setBackground(green);
				}
				else{
					pathPanel[i][j].setBackground(white);
				}
				pathPanel[i][j].setBounds(i*3*height/32, j*height/16, 3*height/32, height/16);
				mainPanel.add(pathPanel[i][j]);
			}
		}
		
		//This function sets the panels for the green house
		//where the first two parameters are the coordinates of
		//the top left corner of the house
		setHouse(9, 0, green);
		
		//Following loop adds color and border to the path between blue and red houses
		for(int i=0; i<6; i++){
			for(int j=6; j<9; j++){
				if(i == 0){
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,2,1,1,black));
				}
				else{
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,black));
				}
				if((i!=0 && j==7) || (i==1 && j==6)){
					pathPanel[i][j].setBackground(red);
				}
				else{
					pathPanel[i][j].setBackground(white);
				}
				pathPanel[i][j].setBounds(i*3*height/32, j*height/16, 3*height/32, height/16);
				mainPanel.add(pathPanel[i][j]);
			}
		}
				
		//Following loop adds color and border to the path between green and yellow houses
		for(int i=9; i<15; i++){
			for(int j=6; j<9; j++){
				if(i == 15){
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,2,black));
				}
				else{
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,black));
				}
				if((i!=14 && j==7) || (i==13 && j==8)){
					pathPanel[i][j].setBackground(yellow);
				}
				else{
					pathPanel[i][j].setBackground(white);
				}
				pathPanel[i][j].setBounds(i*3*height/32, j*height/16, 3*height/32, height/16);
				mainPanel.add(pathPanel[i][j]);
			}
		}
		
		//This function sets the panels for the blue house
		//where the first two parameters are the coordinates of
		//the top left corner of the house
		setHouse(0, 9, blue);
		
		//Following loop adds color and border to the path between blue and yellow houses
		for(int i=6; i<9; i++){
			for(int j=9; j<15; j++){
				if(j == 15){
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,2,1,black));
				}
				else{
					pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,black));
				}
				if((i==7 && j!=14) || (i==6 && j==13)){
					pathPanel[i][j].setBackground(blue);
				}
				else{
					pathPanel[i][j].setBackground(white);
				}
				pathPanel[i][j].setBounds(i*3*height/32, j*height/16, 3*height/32, height/16);
				mainPanel.add(pathPanel[i][j]);
			}
		}
		
		//This function sets the panels for the green house
		//where the first two parameters are the coordinates of
		//the top left corner of the house
		setHouse(9, 9, yellow);
		
		//This function initializes the end position of the ludo
		initCentralTriangles();
		
		//This function adds stars and arrows on the board
		initStarAndArrow();
		
		//Refresh all the changes made on the main frame
		mainFrame.repaint();
		//This function initializes the tokens of the colors
		//which are selected in the menu
		initializeTokens();
		//This pane contains turn indicating labels,
		//New game button and dice for operating the game
		setRightPane();
	}
	
	public void setHouse(int i, int j, Color c){
		//This functions paints the house with panels of white and
		//the color mentioned in the parameter of this function
		//The panels are positioned with reference to the
		//coordinates of top left corner which are the first two parameters
		//and sets the border accordingly
		pathPanel[i][j].setBackground(c);
		pathPanel[i][j].setBorder(BorderFactory.createMatteBorder(2,2,0,0,black));
		pathPanel[i][j].setBounds(i*3*height/32,j*height/16,3*height/32,height/16);
		mainPanel.add(pathPanel[i][j]);
		
		pathPanel[i+1][j].setBackground(c);
		pathPanel[i+1][j].setBorder(BorderFactory.createMatteBorder(2,0,2,0,black));
		pathPanel[i+1][j].setBounds((i+1)*3*height/32,j*height/16,4*3*height/32,height/16);
		mainPanel.add(pathPanel[i+1][j]);
		
		pathPanel[i+5][j].setBackground(c);
		pathPanel[i+5][j].setBorder(BorderFactory.createMatteBorder(2,0,0,2,black));
		pathPanel[i+5][j].setBounds((i+5)*3*height/32,j*height/16,3*height/32,height/16);
		mainPanel.add(pathPanel[i+5][j]);
		
		pathPanel[i][j+1].setBackground(c);
		pathPanel[i][j+1].setBorder(BorderFactory.createMatteBorder(0,2,0,2,black));
		pathPanel[i][j+1].setBounds(i*3*height/32,(j+1)*height/16,3*height/32,4*height/16);
		mainPanel.add(pathPanel[i][j+1]);
		
		pathPanel[i+5][j+1].setBackground(c);
		pathPanel[i+5][j+1].setBorder(BorderFactory.createMatteBorder(0,2,0,2,black));
		pathPanel[i+5][j+1].setBounds((i+5)*3*height/32,(j+1)*height/16,3*height/32,4*height/16);
		mainPanel.add(pathPanel[i+5][j+1]);
		
		pathPanel[i][j+5].setBackground(c);
		pathPanel[i][j+5].setBorder(BorderFactory.createMatteBorder(0,2,2,0,black));
		pathPanel[i][j+5].setBounds(i*3*height/32,(j+5)*height/16,3*height/32,height/16);
		mainPanel.add(pathPanel[i][j+5]);
		
		pathPanel[i+1][j+5].setBackground(c);
		pathPanel[i+1][j+5].setBorder(BorderFactory.createMatteBorder(2,0,2,0,black));
		pathPanel[i+1][j+5].setBounds((i+1)*3*height/32,(j+5)*height/16,4*3*height/32,height/16);
		mainPanel.add(pathPanel[i+1][j+5]);
				
		pathPanel[i+5][j+5].setBackground(c);
		pathPanel[i+5][j+5].setBorder(BorderFactory.createMatteBorder(0,0,2,2,black));
		pathPanel[i+5][j+5].setBounds((i+5)*3*height/32,(j+5)*height/16,3*height/32,height/16);
		mainPanel.add(pathPanel[i+5][j+5]);
		
		pathPanel[i+1][j+1].setBackground(white);
		pathPanel[i+1][j+1].setBounds((i+1)*3*height/32,(j+1)*height/16,4*3*height/32,height/16);
		mainPanel.add(pathPanel[i+1][j+1]);
		
		pathPanel[i+1][j+2].setBackground(white);
		pathPanel[i+1][j+2].setBounds((i+1)*3*height/32,(j+2)*height/16,3*height/32,2*height/16);
		mainPanel.add(pathPanel[i+1][j+2]);
		
		pathPanel[i+4][j+2].setBackground(white);
		pathPanel[i+4][j+2].setBounds((i+4)*3*height/32,(j+2)*height/16,3*height/32,2*height/16);
		mainPanel.add(pathPanel[i+4][j+2]);
		
		pathPanel[i+1][j+4].setBackground(white);
		pathPanel[i+1][j+4].setBounds((i+1)*3*height/32,(j+4)*height/16,4*3*height/32,height/16);
		mainPanel.add(pathPanel[i+1][j+4]);
		
		pathPanel[i+2][j+2].setBackground(c);
		pathPanel[i+2][j+2].setBorder(BorderFactory.createMatteBorder(1,1,1,1,black));
		pathPanel[i+2][j+2].setBounds((i+2)*3*height/32,(j+2)*height/16,3*height/32,height/16);
		mainPanel.add(pathPanel[i+2][j+2]);
		
		pathPanel[i+2][j+3].setBackground(c);
		pathPanel[i+2][j+3].setBorder(BorderFactory.createMatteBorder(0,1,1,1,black));
		pathPanel[i+2][j+3].setBounds((i+2)*3*height/32,(j+3)*height/16,3*height/32,height/16);
		mainPanel.add(pathPanel[i+2][j+3]);
		
		pathPanel[i+3][j+2].setBackground(c);
		pathPanel[i+3][j+2].setBorder(BorderFactory.createMatteBorder(1,0,1,1,black));
		pathPanel[i+3][j+2].setBounds((i+3)*3*height/32,(j+2)*height/16,3*height/32,height/16);
		mainPanel.add(pathPanel[i+3][j+2]);
		
		pathPanel[i+3][j+3].setBackground(c);
		pathPanel[i+3][j+3].setBorder(BorderFactory.createMatteBorder(0,0,1,1,black));
		pathPanel[i+3][j+3].setBounds((i+3)*3*height/32,(j+3)*height/16,3*height/32,height/16);
		mainPanel.add(pathPanel[i+3][j+3]);
	}

	public void initializeTokens(){
		//Contains function checks whether the first parameter
		//which is an array contains the second parameter in it or not
		//and returns boolean value
		//We initialize token of a particular color
		//only if it is selected in the menu
		if(contains(playingColors, red)){
			//This constructor has four parameters
			//First is the color of the token
			//Second is the optional label we want to give the token
			//Next two are the coordinates of its starting position
			red1 = new Token(red,"",2,2);
			//If the token button is clicked then
			//move if it is not against the rules
			red1.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(red1);
				}
			});
			//Sending the token to its starting position
			goHome(red1);
			
			red2 = new Token(red,"",3,2);
			red2.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(red2);
				}
			});
			goHome(red2);
			
			red3 = new Token(red,"",2,3);
			red3.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(red3);
				}
			});
			goHome(red3);
			
			red4 = new Token(red,"",3,3);
			red4.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(red4);
				}
			});
			goHome(red4);
			
			//Creating the array of all red tokens
			redGroup = new Token[]{red1,red2,red3,red4};
		}
		
		if(contains(playingColors, green)){
			green1 = new Token(green,"",11,2);
			green1.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(green1);
				}
			});
			goHome(green1);
			
			green2 = new Token(green,"",12,2);
			green2.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(green2);
				}
			});
			goHome(green2);
			
			green3 = new Token(green,"",11,3);
			green3.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(green3);
				}
			});
			goHome(green3);
			
			green4 = new Token(green,"",12,3);
			green4.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(green4);
				}
			});
			goHome(green4);
			
			greenGroup = new Token[]{green1,green2,green3,green4};
		}
		
		if(contains(playingColors, blue)){
			blue1 = new Token(blue,"",2,11);
			blue1.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(blue1);
				}
			});
			goHome(blue1);
			
			blue2 = new Token(blue,"",3,11);
			blue2.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(blue2);
				}
			});
			goHome(blue2);
			
			blue3 = new Token(blue,"",2,12);
			blue3.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(blue3);
				}
			});
			goHome(blue3);
			
			blue4 = new Token(blue,"",3,12);
			blue4.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(blue4);
				}
			});
			goHome(blue4);
			
			blueGroup = new Token[]{blue1,blue2,blue3,blue4};
		}
		
		if(contains(playingColors, yellow)){
			yellow1 = new Token(yellow,"",11,11);
			yellow1.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(yellow1);
				}
			});
			goHome(yellow1);
			
			yellow2 = new Token(yellow,"",12,11);
			yellow2.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(yellow2);
				}
			});
			goHome(yellow2);
			
			yellow3 = new Token(yellow,"",11,12);
			yellow3.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(yellow3);
				}
			});
			goHome(yellow3);
			
			yellow4 = new Token(yellow,"",12,12);
			yellow4.b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					move(yellow4);
				}
			});
			goHome(yellow4);
			
			yellowGroup = new Token[]{yellow1,yellow2,yellow3,yellow4};
		}
	}
	
	public boolean contains(Color[] arr, Color c){
		//Checks if the first parameter which is an array
		//contains the second parameter or not
		//and returns the boolean value
		boolean result = false;
		for(Color x : arr){
			if(x == c){
				result = true;
				break;
			}
		}
		return result;
	}

	public void move(Token t) {
		//Move the clicked token only if its
		//the same color's turn
		//and the turn is pending to avoid multiple moves in a single turn
		if(t.color == turn && isTurnPending){
			//Conditions to implement
			//when a particular number is rolled on the dice
			//regarding the position of the token
			switch(diceNumber){
			case 1:
				//pos is the position tracker of the token
				//with 0 being the home or starting position
				//and 57 being the end or winning position
				//If the token is out of its house
				if(t.pos!=0 && t.pos<56){
					t.pos++;
					//Changes the position of the token
					//to the updated token pos
					changePos(t);
					//Check if the moving token
					//captures another color's token
					//if they both share the same pos
					//after updating the pos of token
					comparePos(t);
				}
				//If the token is just one step before its last position
				else if(t.pos == 56){
					t.pos++;
					changePos(t);
					//Player gets an extra chance
					//if one of its token ends its journey
					isTurnPending = false;
				}
				break;
				
			case 2:
				//If the token is out of its home
				//and not completed the game
				if(t.pos!=0 && t.pos<55){
					t.pos += 2;
					changePos(t);
					comparePos(t);
				}
				//If the token completes the game after this turn
				else if(t.pos == 55){
					t.pos += 2;
					changePos(t);
					isTurnPending = false;
				}
				break;
				
			case 3:
				//If the token is out of its home
				//and not completed the game
				if(t.pos!=0 && t.pos<54){
					t.pos += 3;
					changePos(t);
					comparePos(t);
				}
				//If the token completes the game after this turn
				else if(t.pos == 54){
					t.pos += 3;
					changePos(t);
					isTurnPending = false;
				}
				break;
				
			case 4:
				//If the token is out of its home
				//and not completed the game
				if(t.pos!=0 && t.pos<53){
					t.pos += 4;
					changePos(t);
					comparePos(t);
				}
				//If the token completes the game after this turn
				else if(t.pos == 53){
					t.pos += 4;
					changePos(t);
					isTurnPending = false;
				}
				break;
				
			case 5:
				//If the token is out of its home
				//and not completed the game
				if(t.pos!=0 && t.pos<52){
					t.pos += 5;
					changePos(t);
					comparePos(t);
				}
				//If the token completes the game after this turn
				else if(t.pos == 52){
					t.pos += 5;
					changePos(t);
					isTurnPending = false;
				}
				break;
				
			case 6:
				//Player will get an extra turn due to dice number 6
				
				//If the token is in the home
				if(t.pos == 0){
					t.pos++;
					changePos(t);
					isTurnPending = false;
				}
				//If the token is out of its home
				//and not completed the game
				else if(t.pos<51){
					t.pos += 6;
					changePos(t);
					comparePos(t);
				}
				//If the token completes the game after this turn
				else if(t.pos == 51){
					t.pos = 57;
					changePos(t);
					isTurnPending = false;
				}
			}	
		}
	}

	public void changePos(Token t){
		//This panel is the target panel where the token will land
		JPanel panel = pathPanel[t.path[t.pos][0]][t.path[t.pos][1]];
		panel.add(t.b);
		//This function adjusts the token symmetrically
		//depending on the count of tokens on that panel
		adjustPanel(panel);
		
		//If the previous position is not the starting position
		if(t.pos!=1){
			//This function will adjust the panel where it was previously
			adjustPanel(pathPanel[t.path[t.pos-diceNumber][0]][t.path[t.pos-diceNumber][1]]);
		}
		
		//The following set of code sets the star protected flag
		//as true if its new position is a star
		//Here two colors' tokens can sit together and no token is captured
		t.isStarProtected = false;
		for(int i=0;i<8;i++){
			if(t.pos == t.starProtectedPos[i]){
				t.isStarProtected = true;
				break;
			}
		}
		//Refresh the changes made on the main frame
		mainFrame.repaint();
	}

	public void adjustPanel(JPanel p){
		//Following four conditions check if the panel
		//to be adjusted contains image of star
		//if yes remove the star temporarily
		//and set its size equal to the panel
		if(p == pathPanel[6][2]){
			p.remove(yellowStar);
			yellowStar.setBounds(0, 0, 3*height/32, height/16);
		}
		else if(p == pathPanel[2][8]){
			p.remove(redStar);
			redStar.setBounds(0, 0, 3*height/32, height/16);
		}
		else if(p == pathPanel[12][6]){
			p.remove(greenStar);
			greenStar.setBounds(0, 0, 3*height/32, height/16);
		}
		else if(p == pathPanel[8][12]){
			p.remove(blueStar);
			blueStar.setBounds(0, 0, 3*height/32, height/16);
		}
		//Following four conditions check if the panel
		//to be adjusted contains image of arrow
		//if yes remove the arrow temporarily
		//and set its size equal to the panel
		else if(p == pathPanel[14][7]){
			p.remove(yellowArrow);
			yellowArrow.setBounds(0, 0, 3*height/32, height/16);
		}
		else if(p == pathPanel[0][7]){
			p.remove(redArrow);
			redArrow.setBounds(0, 0, 3*height/32, height/16);
		}
		else if(p == pathPanel[7][0]){
			p.remove(greenArrow);
			greenArrow.setBounds(0, 0, 3*height/32, height/16);
		}
		else if(p == pathPanel[7][14]){
			p.remove(blueArrow);
			blueArrow.setBounds(0, 0, 3*height/32, height/16);
		}
		//The count variable holds the number of tokens in the panel
		//Here the image of star and arrow is not counted
		//as they are already removed
		//in previous conditions
		int count = p.getComponentCount();
		//Store the components in a array of components
		Component[] c = p.getComponents();
		//panel height
		int h = height/16;
		//panel width
		int w = 3*height/32;
		//Remove all components to arrange it freshly
		p.removeAll();
		
		//Rectangular bound for components when
		//there is single element in the panel
		Rectangle r1 = new Rectangle(0, 0, w, h),
		
		//Rectangular bound for components when
		//there is two elements in the panel
		r21 = new Rectangle(0, h/4, w/2, h/2),
		r22 = new Rectangle(w/2, h/4, w/2, h/2),
		
		//Rectangular bound for components when
		//there is three elements in the panel
		r31 = new Rectangle(0, 0, w/2, h/2),
		r32 = new Rectangle(w/2, 0, w/2, h/2),
		r33 = new Rectangle(w/4, h/2, w/2, h/2),
		
		//Rectangular bound for components when
		//there is four elements in the panel
		r41 = r31,
		r42 = r32,
		r43 = new Rectangle(0, h/2, w/2, h/2),
		r44 = new Rectangle(w/2, h/2, w/2, h/2),
		
		//Rectangular bound for components when
		//there is five elements in the panel
		r51 = new Rectangle(0, 0, w/3, h/2),
		r52 = new Rectangle(w/3, 0, w/3, h/2),
		r53 = new Rectangle(2*w/3, 0, w/3, h/2),
		r54 = new Rectangle(w/6, h/2, w/3, h/2),
		r55 = new Rectangle(w/2, h/2, w/3, h/2),
		
		//Rectangular bound for components when
		//there is six elements in the panel
		r61 = r51,
		r62 = r52,
		r63 = r53,
		r64 = new Rectangle(0, h/2, w/3, h/2),
		r65 = new Rectangle(w/3, h/2, w/3, h/2),
		r66 = new Rectangle(2*w/3, h/2, w/3, h/2),
		
		//Rectangular bound for components when
		//there is seven elements in the panel
		r71 = new Rectangle(0, 0, w/3, h/3),
		r72 = new Rectangle(w/3, 0, w/3, h/3),
		r73 = new Rectangle(2*w/3, 0, w/3, h/3),
		r74 = new Rectangle(0, h/3, w/3, h/3),
		r75 = new Rectangle(w/3, h/3, w/3, h/3),
		r76 = new Rectangle(2*w/3, h/3, w/3, h/3),
		r77 = new Rectangle(w/3, 2*h/3, w/3, h/3),
		
		//Rectangular bound for components when
		//there is eight elements in the panel
		r81 = r71,
		r82 = r72,
		r83 = r73,
		r84 = r74,
		r85 = r75,
		r86 = r76,
		r87 = new Rectangle(w/6, 2*h/3, w/3, h/3),
		r88 = new Rectangle(w/2, 2*h/3, w/3, h/3),
		
		//Rectangular bound for components when
		//there is nine elements in the panel
		r91 = r81,
		r92 = r82,
		r93 = r83,
		r94 = r84,
		r95 = r85,
		r96 = r86,
		r97 = new Rectangle(0, 2*h/3, w/3, h/3),
		r98 = new Rectangle(w/3, 2*h/3, w/3, h/3),
		r99 = new Rectangle(2*w/3, 2*h/3, w/3, h/3),
		
		//Rectangular bound for components when
		//there is ten elements in the panel
		r101 = new Rectangle(0, 0, w/4, h/3),
		r102 = new Rectangle(w/4, 0, w/4, h/3),
		r103 = new Rectangle(w/2, 0, w/4, h/3),
		r104 = new Rectangle(3*w/4, 0, w/4, h/3),
		r105 = new Rectangle(0, h/3, w/4, h/3),
		r106 = new Rectangle(w/4, h/3, w/4, h/3),
		r107 = new Rectangle(w/2, h/3, w/4, h/3),
		r108 = new Rectangle(3*w/4, h/3, w/4, h/3),
		r109 = new Rectangle(w/4, 2*h/3, w/4, h/3),
		r1010 = new Rectangle(w/2, 2*h/3, w/4, h/3),
		
		//Rectangular bound for components when
		//there is eleven elements in the panel
		r111 = r101,
		r112 = r102,
		r113 = r103,
		r114 = r104,
		r115 = r105,
		r116 = r106,
		r117 = r107,
		r118 = r108,
		r119 = new Rectangle(w/8, 2*h/3, w/4, h/3),
		r1110 = new Rectangle(3*w/8, 2*h/3, w/4, h/3),
		r1111 = new Rectangle(5*w/8, 2*h/3, w/4, h/3),
		
		//Rectangular bound for components when
		//there is twelve elements in the panel
		r121 = r111,
		r122 = r112,
		r123 = r113,
		r124 = r114,
		r125 = r115,
		r126 = r116,
		r127 = r117,
		r128 = r118,
		r129 = new Rectangle(0, 2*h/3, w/4, h/3),
		r1210 = new Rectangle(w/4, 2*h/3, w/4, h/3),
		r1211 = new Rectangle(w/2, 2*h/3, w/4, h/3),
		r1212 = new Rectangle(3*w/4, 2*h/3, w/4, h/3),
		
		//Rectangular bound for components when
		//there is thirteen elements in the panel
		r131 = new Rectangle(0, 0, w/4, h/4),
		r132 = new Rectangle(w/4, 0, w/4, h/4),
		r133 = new Rectangle(w/2, 0, w/4, h/4),
		r134 = new Rectangle(3*w/4, 0, w/4, h/4),
		r135 = new Rectangle(0, h/4, w/4, h/4),
		r136 = new Rectangle(w/4, h/4, w/4, h/4),
		r137 = new Rectangle(w/2, h/4, w/4, h/4),
		r138 = new Rectangle(3*w/4, h/4, w/4, h/4),
		r139 = new Rectangle(0, h/2, w/4, h/4),
		r1310 = new Rectangle(w/4, h/2, w/4, h/4),
		r1311 = new Rectangle(w/2, h/2, w/4, h/4),
		r1312 = new Rectangle(3*w/4, h/2, w/4, h/4),
		r1313 = new Rectangle(3*w/8, 3*h/4, w/4, h/4),
		
		//Rectangular bound for components when
		//there is fourteen elements in the panel
		r141 = r131,
		r142 = r132,
		r143 = r133,
		r144 = r134,
		r145 = r135,
		r146 = r136,
		r147 = r137,
		r148 = r138,
		r149 = r139,
		r1410 = r1310,
		r1411 = r1311,
		r1412 = r1312,
		r1413 = new Rectangle(w/4, 3*h/4, w/4, h/4),
		r1414 = new Rectangle(w/2, 3*h/4, w/4, h/4),
		
		//Rectangular bound for components when
		//there is fifteen elements in the panel
		r151 = r141,
		r152 = r142,
		r153 = r143,
		r154 = r144,
		r155 = r145,
		r156 = r146,
		r157 = r147,
		r158 = r148,
		r159 = r149,
		r1510 = r1410,
		r1511 = r1411,
		r1512 = r1412,
		r1513 = new Rectangle(w/8, 3*h/4, w/4, h/4),
		r1514 = new Rectangle(3*w/8, 3*h/4, w/4, h/4),
		r1515 = new Rectangle(5*w/8, 3*h/4, w/4, h/4),
		
		//Rectangular bound for components when
		//there is sixteen elements in the panel
		r161 = r151,
		r162 = r152,
		r163 = r153,
		r164 = r154,
		r165 = r155,
		r166 = r156,
		r167 = r157,
		r168 = r158,
		r169 = r159,
		r1610 = r1510,
		r1611 = r1511,
		r1612 = r1512,
		r1613 = new Rectangle(0, 3*h/4, w/4, h/4),
		r1614 = new Rectangle(w/4, 3*h/4, w/4, h/4),
		r1615 = new Rectangle(w/2, 3*h/4, w/4, h/4),
		r1616 = new Rectangle(3*w/4, 3*h/4, w/4, h/4);
		
		//Conditions for the number of tokens in the given panel
		//Adjusts the tokens symmetrically according to the number
		switch(count){
		case 1:
			c[0].setBounds(r1);
			break;
			
		case 2:
			c[0].setBounds(r21);
			c[1].setBounds(r22);
			break;
			
		case 3:
			c[0].setBounds(r31);
			c[1].setBounds(r32);
			c[2].setBounds(r33);
			break;
			
		case 4:
			c[0].setBounds(r41);
			c[1].setBounds(r42);
			c[2].setBounds(r43);
			c[3].setBounds(r44);
			break;
			
		case 5:
			c[0].setBounds(r51);
			c[1].setBounds(r52);
			c[2].setBounds(r53);
			c[3].setBounds(r54);
			c[4].setBounds(r55);
			break;
			
		case 6:
			c[0].setBounds(r61);
			c[1].setBounds(r62);
			c[2].setBounds(r63);
			c[3].setBounds(r64);
			c[4].setBounds(r65);
			c[5].setBounds(r66);
			break;
			
		case 7:
			c[0].setBounds(r71);
			c[1].setBounds(r72);
			c[2].setBounds(r73);
			c[3].setBounds(r74);
			c[4].setBounds(r75);
			c[5].setBounds(r76);
			c[6].setBounds(r77);
			break;
			
		case 8:
			c[0].setBounds(r81);
			c[1].setBounds(r82);
			c[2].setBounds(r83);
			c[3].setBounds(r84);
			c[4].setBounds(r85);
			c[5].setBounds(r86);
			c[6].setBounds(r87);
			c[7].setBounds(r88);
			break;
			
		case 9:
			c[0].setBounds(r91);
			c[1].setBounds(r92);
			c[2].setBounds(r93);
			c[3].setBounds(r94);
			c[4].setBounds(r95);
			c[5].setBounds(r96);
			c[6].setBounds(r97);
			c[7].setBounds(r98);
			c[8].setBounds(r99);
			break;
			
		case 10:
			c[0].setBounds(r101);
			c[1].setBounds(r102);
			c[2].setBounds(r103);
			c[3].setBounds(r104);
			c[4].setBounds(r105);
			c[5].setBounds(r106);
			c[6].setBounds(r107);
			c[7].setBounds(r108);
			c[8].setBounds(r109);
			c[9].setBounds(r1010);
			break;
			
		case 11:
			c[0].setBounds(r111);
			c[1].setBounds(r112);
			c[2].setBounds(r113);
			c[3].setBounds(r114);
			c[4].setBounds(r115);
			c[5].setBounds(r116);
			c[6].setBounds(r117);
			c[7].setBounds(r118);
			c[8].setBounds(r119);
			c[9].setBounds(r1110);
			c[10].setBounds(r1111);
			break;
			
		case 12:
			c[0].setBounds(r121);
			c[1].setBounds(r122);
			c[2].setBounds(r123);
			c[3].setBounds(r124);
			c[4].setBounds(r125);
			c[5].setBounds(r126);
			c[6].setBounds(r127);
			c[7].setBounds(r128);
			c[8].setBounds(r129);
			c[9].setBounds(r1210);
			c[10].setBounds(r1211);
			c[11].setBounds(r1212);
			break;
			
		case 13:
			c[0].setBounds(r131);
			c[1].setBounds(r132);
			c[2].setBounds(r133);
			c[3].setBounds(r134);
			c[4].setBounds(r135);
			c[5].setBounds(r136);
			c[6].setBounds(r137);
			c[7].setBounds(r138);
			c[8].setBounds(r139);
			c[9].setBounds(r1310);
			c[10].setBounds(r1311);
			c[11].setBounds(r1312);
			c[12].setBounds(r1313);
			break;
			
		case 14:
			c[0].setBounds(r141);
			c[1].setBounds(r142);
			c[2].setBounds(r143);
			c[3].setBounds(r144);
			c[4].setBounds(r145);
			c[5].setBounds(r146);
			c[6].setBounds(r147);
			c[7].setBounds(r148);
			c[8].setBounds(r149);
			c[9].setBounds(r1410);
			c[10].setBounds(r1411);
			c[11].setBounds(r1412);
			c[12].setBounds(r1413);
			c[13].setBounds(r1414);
			break;
			
		case 15:
			c[0].setBounds(r151);
			c[1].setBounds(r152);
			c[2].setBounds(r153);
			c[3].setBounds(r154);
			c[4].setBounds(r155);
			c[5].setBounds(r156);
			c[6].setBounds(r157);
			c[7].setBounds(r158);
			c[8].setBounds(r159);
			c[9].setBounds(r1510);
			c[10].setBounds(r1511);
			c[11].setBounds(r1512);
			c[12].setBounds(r1513);
			c[13].setBounds(r1514);
			c[14].setBounds(r1515);
			break;
			
		case 16:
			c[0].setBounds(r161);
			c[1].setBounds(r162);
			c[2].setBounds(r163);
			c[3].setBounds(r164);
			c[4].setBounds(r165);
			c[5].setBounds(r166);
			c[6].setBounds(r167);
			c[7].setBounds(r168);
			c[8].setBounds(r169);
			c[9].setBounds(r1610);
			c[10].setBounds(r1611);
			c[11].setBounds(r1612);
			c[12].setBounds(r1613);
			c[13].setBounds(r1614);
			c[14].setBounds(r1615);
			c[15].setBounds(r1616);
			break;
		}
		
		//Add all components
		for(int i=0; i<count; i++){
			p.add(c[i]);
		}
		
		//Following code will add star or arrow to the bottom of the panel
		//if we had removed its star or arrow from the panel
		if(p == pathPanel[6][2]){
			p.add(yellowStar);
		}
		else if(p == pathPanel[2][8]){
			p.add(redStar);
		}
		else if(p == pathPanel[12][6]){
			p.add(greenStar);
		}
		else if(p == pathPanel[8][12]){
			p.add(blueStar);
		}
		else if(p == pathPanel[14][7]){
			p.add(yellowArrow);
		}
		else if(p == pathPanel[0][7]){
			p.add(redArrow);
		}
		else if(p == pathPanel[7][0]){
			p.add(greenArrow);
		}
		else if(p == pathPanel[7][14]){
			p.add(blueArrow);
		}
		
		//Refresh the changes made in the main frame
		mainFrame.repaint();
	}
	
	public void comparePos(Token t){
		//Assuming initially that no token is captured
		boolean captured = false;
		//This panel is the panel where we have to compare positions
		//of the different color tokens
		JPanel panel = pathPanel[t.path[t.pos][0]][t.path[t.pos][1]];
		
		//Following code checks if the coordinates of the token coming from back
		//matches the other colors' coordinates
		//also the token will not be captured if the token(s) is/are star protected
		//Same color tokens share position so will not be captured
		//If captured the captured token will be sent to its start
		for(int i=0; i<4; i++){
			if(contains(playingColors, red)){
				if(t.path[t.pos][0] == redGroup[i].path[redGroup[i].pos][0]
					&& t.path[t.pos][1] == redGroup[i].path[redGroup[i].pos][1]
					&& t.color!=red
					&& !redGroup[i].isStarProtected){
					goHome(redGroup[i]);
					captured = true;
				}
			}
			if(contains(playingColors, green)){
				if(t.path[t.pos][0] == greenGroup[i].path[greenGroup[i].pos][0] 
					&& t.path[t.pos][1] == greenGroup[i].path[greenGroup[i].pos][1] 
					&& t.color!=green 
					&& !greenGroup[i].isStarProtected){
					goHome(greenGroup[i]);
					captured = true;
				}
			}
			if(contains(playingColors, yellow)){
				if(t.path[t.pos][0] == yellowGroup[i].path[yellowGroup[i].pos][0] 
					&& t.path[t.pos][1] == yellowGroup[i].path[yellowGroup[i].pos][1] 
					&& t.color!=yellow 
					&& !yellowGroup[i].isStarProtected){
					goHome(yellowGroup[i]);
					captured = true;
				}
			}
			if(contains(playingColors, blue)){
				if(t.path[t.pos][0] == blueGroup[i].path[blueGroup[i].pos][0]
					&& t.path[t.pos][1] == blueGroup[i].path[blueGroup[i].pos][1] 
					&& t.color!=blue
					&& !blueGroup[i].isStarProtected){
					goHome(blueGroup[i]);
					captured = true;
				}
			}
		}
		
		//If the dice has not rolled 6
		if(diceNumber!=6){
			//If capturing is done player will get an extra chance
			//and the six count is set to zero again
			if(captured){
				isTurnPending = false;
				sixCount = 0;
			}
			//Else next turn
			else{
				nextTurn();
			}
		}
		//Due to fall of 6 turn will continue
		//regardless whether capturing is done or not
		else{
			isTurnPending = false;
		}
		//Due to change in the number of components in the target panel
		//it needs to be adjusted
		adjustPanel(panel);
	}
	
	public void nextTurn() {
		//If only one player is remaining in the game
		//and others have completed the game
		//disable the dice
		if(rankNumber == playingColors.length-1){
			dice.setEnabled(false);
		}
		//The next color in the playing colors array will get the turn
		for(int i=0; i<playingColors.length; i++){
			if(playingColors[i] == turn){
				turn = playingColors[(i+1)%playingColors.length];
				break;
			}
		}
		//If the rank array contains the newly allotted color
		//then it means that it has already completed the game
		//so the function will be called again
		//for changing the turn
		if(contains(rank, turn)){
			nextTurn();
		}
		//This function changes the label on the right pane
		//The label with red color has the turn
		changeLabel();
		//Enabling the rolling of dice
		//and indicating that the move is completed
		//so the next turn can be done
		isTurnPending = false;
		//As the turn is changing, six count will be 0 again
		sixCount = 0;
	}
	
	public void goHome(Token t){
		//x and y coordinates of the given token
		int x = t.HouseX;
		int y = t.HouseY;
		
		//Increasing the size of the token to full size
		t.b.setBounds(0, 0, 3*height/32, height/16);
		
		//Moving the token to its start position
		pathPanel[x][y].add(t.b);
		
		//Position of the token has become 0
		t.pos = 0;
		
		//Refresh the changes made on the main frame
		mainFrame.repaint();
	}

	public void initCentralTriangles(){
		//This image and panels are the central 4 triangle image and panels
		//which is the destination of the tokens
		pathPanel[7][6].setBounds(7*3*height/32, 6*height/16, 3*height/32, height/16);
		pathPanel[7][6].setOpaque(false);
		mainPanel.add(pathPanel[7][6]);
				
		pathPanel[6][7].setBounds(6*3*height/32, 7*height/16, 3*height/32, height/16);
		pathPanel[6][7].setOpaque(false);
		mainPanel.add(pathPanel[6][7]);
				
		pathPanel[8][7].setBounds(8*3*height/32, 7*height/16, 3*height/32, height/16);
		pathPanel[8][7].setOpaque(false);
		mainPanel.add(pathPanel[8][7]);
				
		pathPanel[7][8].setBounds(7*3*height/32, 8*height/16, 3*height/32, height/16);
		pathPanel[7][8].setOpaque(false);
		mainPanel.add(pathPanel[7][8]);
				
		ImageIcon img = new ImageIcon("resources/triangle.png");
		endPosLabel = new JLabel("", img, JLabel.CENTER);
		endPosLabel.setBounds(6*3*height/32, 6*height/16, 3*3*height/32, 3*height/16);
		mainPanel.add(endPosLabel);
	}
	
	public void initStarAndArrow(){
		//Loading the star image from the resources folder
		ImageIcon img = new ImageIcon("resources/star.png");
		
		//Initializing the star labels with the icon image
		yellowStar = new JLabel("",img,JLabel.CENTER);
		greenStar = new JLabel("",img,JLabel.CENTER);
		blueStar = new JLabel("",img,JLabel.CENTER);
		redStar = new JLabel("",img,JLabel.CENTER);
		
		//Adding the star imaged label to the panels
		pathPanel[6][2].add(yellowStar);
		pathPanel[2][8].add(redStar);
		pathPanel[8][12].add(blueStar);
		pathPanel[12][6].add(greenStar);
		
		//Adjust the panels where star is added
		adjustPanel(pathPanel[6][2]);
		adjustPanel(pathPanel[2][8]);
		adjustPanel(pathPanel[8][12]);
		adjustPanel(pathPanel[12][6]);
		
		//Loading the arrow images from the resources folder
		ImageIcon img1 = new ImageIcon("resources/greenArrow.png");
		ImageIcon img2 = new ImageIcon("resources/yellowArrow.png");
		ImageIcon img3 = new ImageIcon("resources/blueArrow.png");
		ImageIcon img4 = new ImageIcon("resources/redArrow.png");
		
		//Initializing the arrow labels with the icon image
		greenArrow = new JLabel("",img1,JLabel.CENTER);
		yellowArrow = new JLabel("",img2,JLabel.CENTER);
		blueArrow = new JLabel("",img3,JLabel.CENTER);
		redArrow = new JLabel("",img4,JLabel.CENTER);
		
		//Adding the arrow imaged label to the panels
		pathPanel[7][0].add(greenArrow);
		pathPanel[14][7].add(yellowArrow);
		pathPanel[7][14].add(blueArrow);
		pathPanel[0][7].add(redArrow);
		
		//Adjust the panels where arrow is added
		adjustPanel(pathPanel[7][0]);
		adjustPanel(pathPanel[14][7]);
		adjustPanel(pathPanel[7][14]);
		adjustPanel(pathPanel[0][7]);
	}
	
	public void setRightPane(){
		//Sets the components of right pane
		initializeLabelsAndDice();
		//Change the label colors
		changeLabel();
	}
	
	public void initializeLabelsAndDice() {
		//height and width of right pane
		int h = height;
		int w = (width-(3*height/2))/6;
		
		//This button shows the menu if we want to start a new game
		//Initializing the button and its specs
		newGame = new JButton("NEW");
		newGame.setBounds((3*h/2)+(2*w), h/13, 2*w, h/13);
		newGame.setMnemonic(KeyEvent.VK_N);
		mainPanel.add(newGame);
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				showMenu();
			}
			
		});
		
		
		//Labels for colors and its specs
		//which instructs the user which colors' turn is going on
		redTurnLabel = new JLabel("red",JLabel.CENTER);
		redTurnLabel.setBounds((3*h/2)+(2*w), 4*h/13, 2*w, h/13);
		mainPanel.add(redTurnLabel);
		
		greenTurnLabel = new JLabel("green",JLabel.CENTER);
		greenTurnLabel.setBounds((3*h/2)+(2*w), 5*h/13, 2*w, h/13);
		mainPanel.add(greenTurnLabel);
		
		yellowTurnLabel = new JLabel("yellow",JLabel.CENTER);
		yellowTurnLabel.setBounds((3*h/2)+(2*w), 6*h/13, 2*w, h/13);
		mainPanel.add(yellowTurnLabel);
		
		blueTurnLabel = new JLabel("blue",JLabel.CENTER);
		blueTurnLabel.setBounds((3*h/2)+(2*w), 7*h/13, 2*w, h/13);
		mainPanel.add(blueTurnLabel);
		
		//Dice button and its specs
		diceImg = new ImageIcon("resources/dice_6.png");
		dice = new JButton(diceImg);
		dice.setBounds((3*h/2)+(2*w), 9*h/13, 2*h/13, 2*h/13);
		dice.setMnemonic(KeyEvent.VK_D);
		mainPanel.add(dice);
		dice.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Dice will roll only if the turn is not pending
				//to avoid multiple moves in a single turn
				if(!isTurnPending){
					//Randomly generating a dice roll
					diceNumber = (int)Math.ceil(6*Math.random());
					//If dice roll is 6 increase the six count by 1
					if(diceNumber == 6){
						sixCount++;
					}
					//If two consecutive six falls
					//Generate a dice roll between 1 and 5
					//to avoid triple six
					if(sixCount == 2){
						diceNumber = (int)Math.ceil(5*Math.random());
					}
					
					diceImg = new ImageIcon("resources/dice_" + diceNumber + ".png");
					
					//Code to be changed
					switch(diceNumber){
					case 1:
						dice.setIcon(diceImg);
						break;
					case 2:
						dice.setIcon(diceImg);
						break;
					case 3:
						dice.setIcon(diceImg);
						break;
					case 4:
						dice.setIcon(diceImg);
						break;
					case 5:
						dice.setIcon(diceImg);
						break;
					case 6:
						dice.setIcon(diceImg);
					}
					//As the dice is rolled once
					//This flag will prevent more than once dice rolling
					//Dice cannot be rolled unless a move is played
					isTurnPending = true;
					//Checks whether a move is possible and does a move
					//which is the only possible move
					autoMove();
				}
			}
		});
	}

	public void autoMove(){
		//Passes the token group if the turn is of that color
		if(turn == red){
			autoMove(redGroup);
		}
		else if(turn == green){
			autoMove(greenGroup);
		}
		else if(turn == yellow){
			autoMove(yellowGroup);
		}
		else{
			autoMove(blueGroup);
		}
	}
	
	public void autoMove(Token[] t){
		//This function checks the position of all the four tokens
		//of a color whose turn is going on now
		//and if only one possibility is there to move
		//then that token will be moved automatically
		//else nothing will be done and program
		//will wait for user's decision
		
		//The counter of tokens which are in start position
		int inHouse = 0;
		//The array of positions of tokens
		int[] outPos = {0,0,0,0};
		//If a single token is in the House we can refer it by this inPos token
		Token inPos = null;
		//Counter for the index of the outPos array
		int k = 0;
		//Counting number of tokens inside
		//and storing the indices of the tokens which are outside their home
		for(int i=0; i<4; i++){
			if(t[i].pos == 0){
				inHouse++;
				inPos = t[i];
			}
			else{
				outPos[k++] = i;
			}
		}
		//If all 4 tokens are inside home, and dice has not rolled a 6
		//Turn will be skipped as you need a skip to take out token
		if(inHouse==4 && diceNumber!=6){
			nextTurn();
		}
		//If there are 3 tokens inside home, and dice has not rolled a 6
		//The only token that can move is the one that is outside
		else if(inHouse==3 && diceNumber!=6){
			//If the only token outside is movable but cannot complete the
			//game after the turn it is moved and positions of other color's
			//tokens are checked to check if any token is captured or not
			if(t[outPos[0]].pos < (57-diceNumber)){
				t[outPos[0]].pos += diceNumber;
				changePos(t[outPos[0]]);
				comparePos(t[outPos[0]]);
			}
			//If after moving the outside token the player completes it's journey
			//The color will be given an another turn
			else if(t[outPos[0]].pos == (57-diceNumber)){
				t[outPos[0]].pos = 57;
				changePos(t[outPos[0]]);
				isTurnPending = false;
			}
			//Else the token is not movable turn is skipped
			else{
				nextTurn();
			}
		}
		//If there are 2 tokens inside the house
		//and dice has not rolled a six
		//So the only two tokens that can move are the ones that are outside
		else if(inHouse==2 && diceNumber!=6){
			//If the outside tokens share same position
			//then any one token of the two may move
			if(t[outPos[0]].pos == t[outPos[1]].pos){
				//If it is movable but unable to complete the game
				//Condition for capturing is checked
				if(t[outPos[0]].pos < (57-diceNumber)){
					t[outPos[0]].pos += diceNumber;
					changePos(t[outPos[0]]);
					comparePos(t[outPos[0]]);
				}
				//If it just completes the game
				//The color is given an another turn
				else if(t[outPos[0]].pos == (57-diceNumber)){
					t[outPos[0]].pos = 57;
					changePos(t[outPos[0]]);
					isTurnPending = false;
				}
				//Else if it is not movable turn is skipped
				else{
					nextTurn();
				}
			}
			//Else if the outside two tokens has different positions
			else{
				//t1 is the token that is behind
				//and t2 is the token which is ahead
				Token t1 = (t[outPos[0]].pos < t[outPos[1]].pos) ? t[outPos[0]]:t[outPos[1]];
				Token t2 = (t[outPos[0]].pos > t[outPos[1]].pos) ? t[outPos[0]]:t[outPos[1]];
				//If t2 is not movable then
				//position of t1 is checked
				if(t2.pos > 57-diceNumber){
					//If t1 is movable but unable to complete the game
					//then the capturing condition is checked
					if(t1.pos < (57-diceNumber)){
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
					//If t1 completes its journey
					//another turn is given to that color
					else if(t1.pos == (57-diceNumber)){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					//If t1 is also not movable turn is skipped
					else{
						nextTurn();
					}
				}
			}
		}
		//If there is only one token inside house and dice has not rolled a six
		//then there are three movable pieces
		else if(inHouse==1 && diceNumber!=6){
			//If all the tokens share the same position
			//Any one token is moved if possible
			if(t[outPos[0]].pos==t[outPos[1]].pos && t[outPos[0]].pos==t[outPos[2]].pos){
				//If any token out of 3 is movable but not able to
				//complete the game then condition for capturing is checked
				if(t[outPos[0]].pos < (57-diceNumber)){
					t[outPos[0]].pos += diceNumber;
					changePos(t[outPos[0]]);
					comparePos(t[outPos[0]]);
				}
				//If any token out of 3 completes its journey then
				//the color is awarded an extra turn
				else if(t[outPos[0]].pos == (57-diceNumber)){
					t[outPos[0]].pos = 57;
					changePos(t[outPos[0]]);
					isTurnPending = false;
				}
				//IF all the 3 which are outside are not movable
				//turn is skipped as no token can move
				else{
					nextTurn();
				}
			}
			//If a pair of tokens out of 3 which are outside share same position
			else if(t[outPos[0]].pos==t[outPos[1]].pos || t[outPos[0]].pos==t[outPos[2]].pos || t[outPos[2]].pos==t[outPos[1]].pos){
				//s token represents the token which is single
				Token s;
				//p token represents any one of the token which is in pair
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
				//If the pair of tokens is ahead of that single token
				if(s.pos < p.pos){
					//If the pair is not movable
					//position of single token is checked
					if(p.pos > 57-diceNumber){
						//If single token is movable but not able to complete
						//the game then condition for capturing is checked
						if(s.pos < (57-diceNumber)){
							s.pos += diceNumber;
							changePos(s);
							comparePos(s);
						}
						//If the single token completes its journey
						//an extra turn is given to that color
						else if(s.pos == (57-diceNumber)){
							s.pos = 57;
							changePos(s);
							isTurnPending = false;
						}
						//If the single token is also not movable like the pair
						//turn of that color is skipped
						else{
							nextTurn();
						}
					}
				}
				//If the single token is ahead of that pair of tokens
				else{
					//If single token is not movable
					//the position of the pair is checked
					if(s.pos > 57-diceNumber){
						//If the pair of token is movable
						//but not able to complete the game
						//condition for capturing is checked
						if(p.pos < (57-diceNumber)){
							p.pos += diceNumber;
							changePos(p);
							comparePos(p);
						}
						//If the pair just completes its journey
						//extra turn is given to that color
						else if(p.pos == (57-diceNumber)){
							p.pos = 57;
							changePos(p);
							isTurnPending = false;
						}
						//Else if the pair is also not movable like the single one
						//turn of that color is skipped
						else{
							nextTurn();
						}
					}
				}
			}
			//If all the 3 tokens that are outside have different positions
			else{
				//t1 is the token which is most behind
				//t2 is the token in between
				//t3 is the token which is most ahead
				Token t2 = null;
				Token t1 = (t[outPos[0]].pos<t[outPos[1]].pos && t[outPos[0]].pos<t[outPos[2]].pos) ? t[outPos[0]] : (t[outPos[1]].pos<t[outPos[2]].pos) ? t[outPos[1]] : t[outPos[2]];
				Token t3 = (t[outPos[0]].pos>t[outPos[1]].pos && t[outPos[0]].pos>t[outPos[2]].pos) ? t[outPos[0]] : (t[outPos[1]].pos>t[outPos[2]].pos) ? t[outPos[1]] : t[outPos[2]];
				for(int i=0; i<3; i++){
					if(t[outPos[i]].pos>t1.pos && t[outPos[i]].pos<t3.pos){
						t2 = t[outPos[i]];
						break;
					}
				}
				//If t2 and t3 both are not movable then position of t1 is checked
				if(t2.pos>57-diceNumber && t3.pos>57-diceNumber){
					//if t1 is movable but not able to complete the game
					//condition for capturing is checked
					if(t1.pos < (57-diceNumber)){
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
					//If t1 completes its journey
					//extra turn is given to that color
					else if(t1.pos == (57-diceNumber)){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					//Else if all 3 tokens are not movable
					//turn of that color is skipped
					else{
						nextTurn();
					}
				}
			}
		}
		//If there is only one token inside home
		//and dice has rolled a six
		//And all three outside cannot move by 6
		//The inside token is automatically taken outside
		else if(inHouse==1 && diceNumber==6){
			if(t[outPos[0]].pos>51 && t[outPos[1]].pos>51 && t[outPos[2]].pos>51){
				inPos.pos++;
				changePos(inPos);
				isTurnPending = false;
			}
		}
		//If no token is inside the house
		//and dice has rolled any number
		else if(inHouse==0){
			//Sorting the token array in ascending order
			for(int i=0; i<3; i++){
				for(int j=0; j<(3-i); j++){
					if(t[j].pos > t[j+1].pos){
						Token temp = t[j+1];
						t[j+1] = t[j];
						t[j] = temp;
					}
				}
			}
			//t1 is the one which is most behind
			//t2 is next one
			//t3 is the second most ahead
			//t4 is the token which is most ahead of others
			Token t1 = t[0];
			Token t2 = t[1];
			Token t3 = t[2];
			Token t4 = t[3];
			
			//If all tokens are in the same position
			if(t1.pos==t2.pos && t2.pos==t3.pos && t3.pos==t4.pos){
				//If any token out of 4 is movable
				//but not able to complete the game
				//Condition for capturing is checked
				if(t1.pos < 57-diceNumber){
					t1.pos += diceNumber;
					changePos(t1);
					comparePos(t1);
				}
				//If any token out of 4 completes its journey
				//an extra turn is awarded to that color
				else if(t1.pos == 57-diceNumber){
					t1.pos = 57;
					changePos(t1);
					isTurnPending = false;
				}
				//If all tokens are not movable
				//turn is skipped
				else{					
					nextTurn();
				}
			}
			//If 3 out of four same positions
			//whatever be the position of trio and single
			//if t4 is not movable, and t1 is movable
			//then t1 is moved
			else if((t1.pos==t2.pos && t2.pos==t3.pos) || (t2.pos==t3.pos && t3.pos==t4.pos)){
				//If t4 is not movable
				if(t4.pos>57-diceNumber){
					//If t1 is movable but not able to complete the game
					//Condition for capturing is checked
					if(t1.pos < 57-diceNumber){
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
					//If t1 completes its journey
					//an extra turn is given to that color
					//only if there is at least one token remaining
					//which has not completed the game
					//or else next turn will be called
					else if(t1.pos == 57-diceNumber){
						t1.pos = 57;
						changePos(t1);
						if(t3.pos==57){
							rank[rankNumber++] = t1.color;
							allotRank(rankNumber, t1.color);
							nextTurn();
						}
						else{
							isTurnPending = false;
						}
					}
					//If t1 is not movable as the other 3
					//turn is skipped
					else{
						nextTurn();
					}
				}
			}
			//If there are 2 pairs which share the same position
			else if(t1.pos==t2.pos && t3.pos==t4.pos){
				//If t3 or t4 which has same position is not movable
				if(t3.pos > 57-diceNumber){
					//If t1 or t2 which has same positions is movable
					//but it is not able to complete its journey
					//condition for capturing is checked
					if(t1.pos < 57-diceNumber){
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
					//If t1 or t2 completes its journey
					//an extra turn is given to that color
					else if(t1.pos == 57-diceNumber){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					//If no token is able to move
					//turn is skipped
					else{
						nextTurn();
					}
				}
			}
			//If one pair and two single tokens are there
			//If the pair is behind or in between
			else if((t1.pos == t2.pos) || (t2.pos == t3.pos)){
				//If t4 and t3 are not movable
				//position of t1 and t2 are checked
				if(t4.pos>57-diceNumber && t3.pos>57-diceNumber){
					//If t1 is movable but not able to complete its journey
					//Condition for capturing is checked
					if(t1.pos < 57-diceNumber){
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
					//If t1 completes its journey
					//an extra turn is given to that color
					else if(t1.pos == 57-diceNumber){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					//If all tokens are not movable
					//turn is skipped
					else{
						nextTurn();
					}
				}
			}
			//If the only pair is ahead of two single tokens
			else if(t3.pos == t4.pos){
				//If t3, t4 which are in same position
				//and t2 are not movable
				//position of t1 is checked
				if(t4.pos>57-diceNumber && t2.pos>57-diceNumber){
					//If t1 is movable but not able to complete its journey
					//then condition for capturing is checked
					if(t1.pos < 57-diceNumber){
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
					//If t1 completes its journey
					//an extra turn is given to that color
					else if(t1.pos == 57-diceNumber){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					//If no token is movable turn is skipped
					else{
						nextTurn();
					}
				}
			}
			//If all tokens are at different position
			else{
				//If the 3 most ahead tokens are not movable
				if(t4.pos>57-diceNumber && t3.pos>57-diceNumber && t2.pos>57-diceNumber){
					//If t1 is movable but not able to complete its journey
					//then condition for capturing is checked
					if(t1.pos < 57-diceNumber){
						t1.pos += diceNumber;
						changePos(t1);
						comparePos(t1);
					}
					//If t1 completes its journey
					//an extra turn is given to that color
					else if(t1.pos == 57-diceNumber){
						t1.pos = 57;
						changePos(t1);
						isTurnPending = false;
					}
					//If no token is movable then turn is skipped
					else{
						nextTurn();
					}
				}
			}
		}
		//This code is for testing purpose
		//The dice will be clicked until there is at least 2 choices
		//for the user to click.
		dice.doClick();
	}
		
	public void allotRank(int r, Color c){
		//Temporary label
		JLabel crown;
		//Assign labels to temporary label with proper rank number by switch cases
		switch(r){
		case 1:
			crown = crown1;
			break;
			
		case 2:
			crown = crown2;
			break;
			
		case 3:
			crown = crown3;
			break;
			
		default:
			crown = null;
		}
		
		//Depending on the color which has gained the rank
		//Display the crown image containing the color's rank number
		if(c == red){
			crown.setBounds(0*3*height/32, 0*height/16, 6*3*height/32, 6*height/16);
			mainPanel.add(crown);
		}
		else if(c == green){
			crown.setBounds(9*3*height/32, 0*height/16, 6*3*height/32, 6*height/16);
			mainPanel.add(crown);
		}
		else if(c == blue){
			crown.setBounds(0*3*height/32, 9*height/16, 6*3*height/32, 6*height/16);
			mainPanel.add(crown);
		}
		else{
			crown.setBounds(9*3*height/32, 9*height/16, 6*3*height/32, 6*height/16);
			mainPanel.add(crown);
		}
		setHouse(0, 0, red);
		setHouse(9, 0, green);
		setHouse(0, 9, blue);
		setHouse(9, 9, yellow);
		
		//If there is only one color remaining in the game, then end the game
		if(r == playingColors.length-1){
			dice.setEnabled(false);
		}
	}
	
	public void changeLabel(){
		//Set all label colors to black
		redTurnLabel.setForeground(black);
		blueTurnLabel.setForeground(black);
		greenTurnLabel.setForeground(black);
		yellowTurnLabel.setForeground(black);
		
		//Change the color of the color label to red
		//if the turn is of that color
		if(turn == red){
			redTurnLabel.setForeground(red);
		}
		else if(turn == blue){
			blueTurnLabel.setForeground(red);
		}
		else if(turn == green){
			greenTurnLabel.setForeground(red);
		}
		else{
			yellowTurnLabel.setForeground(red);
		}
	}
}