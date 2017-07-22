package inheritance;

import java.awt.Color;
import javax.swing.JButton;

public class Token {

	//Flag whether the token object is sitting on a star or not
	public boolean isStarProtected;
	//All the star protected positions on the board
	public int[] starProtectedPos;
	//Color of the token
	public Color color;
	//Path coordinates of the token object with respect to its color
	public int[][] path;
	//The token's button which is used by the user to move it
	public JButton b;
	//Coordinates of the starting position of the token object
	public int HouseX;
	public int HouseY;
	//The number of panels moved by the token in its path
	//where 0 is the home position and 57 being the end position
	public int pos;
	
	//Constructor
	public Token(Color c,String label,int x,int y){
		HouseX = x;
		HouseY = y;
		color = c;
		b = new RoundButton(label);
		b.setBackground(c);
		if(color == Color.RED){
			//Path of the red in coordinates
			path = new int[][]{{HouseX,HouseY},{1,6},{2,6},{3,6},{4,6},{5,6},{6,5},{6,4},{6,3},{6,2},{6,1},{6,0},{7,0},{8,0},{8,1},{8,2},{8,3},{8,4},{8,5},{9,6},{10,6},{11,6},{12,6},{13,6},{14,6},{14,7},{14,8},{13,8},{12,8},{11,8},{10,8},{9,8},{8,9},{8,10},{8,11},{8,12},{8,13},{8,14},{7,14},{6,14},{6,13},{6,12},{6,11},{6,10},{6,9},{5,8},{4,8},{3,8},{2,8},{1,8},{0,8},{0,7},{1,7},{2,7},{3,7},{4,7},{5,7},{6,7}};
		}
		if(color == Color.GREEN){
			//Path of the green in coordinates
			path = new int[][]{{HouseX,HouseY},{8,1},{8,2},{8,3},{8,4},{8,5},{9,6},{10,6},{11,6},{12,6},{13,6},{14,6},{14,7},{14,8},{13,8},{12,8},{11,8},{10,8},{9,8},{8,9},{8,10},{8,11},{8,12},{8,13},{8,14},{7,14},{6,14},{6,13},{6,12},{6,11},{6,10},{6,9},{5,8},{4,8},{3,8},{2,8},{1,8},{0,8},{0,7},{0,6},{1,6},{2,6},{3,6},{4,6},{5,6},{6,5},{6,4},{6,3},{6,2},{6,1},{6,0},{7,0},{7,1},{7,2},{7,3},{7,4},{7,5},{7,6}};
		}
		if(color == Color.YELLOW){
			//Path of the yellow in coordinates
			path = new int[][]{{HouseX,HouseY},{13,8},{12,8},{11,8},{10,8},{9,8},{8,9},{8,10},{8,11},{8,12},{8,13},{8,14},{7,14},{6,14},{6,13},{6,12},{6,11},{6,10},{6,9},{5,8},{4,8},{3,8},{2,8},{1,8},{0,8},{0,7},{0,6},{1,6},{2,6},{3,6},{4,6},{5,6},{6,5},{6,4},{6,3},{6,2},{6,1},{6,0},{7,0},{8,0},{8,1},{8,2},{8,3},{8,4},{8,5},{9,6},{10,6},{11,6},{12,6},{13,6},{14,6},{14,7},{13,7},{12,7},{11,7},{10,7},{9,7},{8,7}};
		}
		if(color == Color.BLUE){
			//Path of the blue in coordinates
			path = new int[][]{{HouseX,HouseY},{6,13},{6,12},{6,11},{6,10},{6,9},{5,8},{4,8},{3,8},{2,8},{1,8},{0,8},{0,7},{0,6},{1,6},{2,6},{3,6},{4,6},{5,6},{6,5},{6,4},{6,3},{6,2},{6,1},{6,0},{7,0},{8,0},{8,1},{8,2},{8,3},{8,4},{8,5},{9,6},{10,6},{11,6},{12,6},{13,6},{14,6},{14,7},{14,8},{13,8},{12,8},{11,8},{10,8},{9,8},{8,9},{8,10},{8,11},{8,12},{8,13},{8,14},{7,14},{7,13},{7,12},{7,11},{7,10},{7,9},{7,8}};
		}
		//During initializing the token will be at home so its position will be 0
		pos = 0;
		//Star protected of all tokens will be the same
		//irrespective of the color due to symmetry of ludo board
		starProtectedPos = new int[]{1,9,14,22,27,35,40,48};
	}
}