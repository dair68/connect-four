package connectfour;

import connectfour.GUI;

public class Game {
	//' ' = empty, 'x' = red piece, 'o' = yellow piece
	private char[][] board;
	static final int numRows = 6;
	static final int numCols = 7;
	
	private boolean redTurn = false;
	private GUI window; 

	//initializes the game
	public Game() {	
		board = new char[numRows][numCols];
		
		//initializing board
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numCols; j++) {
				board[i][j] = ' ';
			}
		}
		
		window = new GUI(this);
	}
	
	//starts the game
	public void start() {
		window.update(this);
		window.setVisible(true);
	}
	
	//plays a piece within a column, if possible
	//@param col - column to place piece. number from 0-6.
	void playPiece(int col) {
		//col is currently playable
		if(isPlayable(col)) {
			//finding vacancy
			for(int i=numRows-1; i>=0; i--) {
				//found the vacancy
				if(board[i][col] == ' ') {
					//determining color of piece based on player turn
					char piece = redTurn ? 'x' : 'o';
					
					//adding piece to board
					board[i][col] = piece;
					window.update(this);
					
					//changing turn
					redTurn = redTurn ? false : true;
					break;
				}
			}
		}
	}
	
	//checks to see if col is playable
	//@param col - chosen col
	//returns true if col is from 0-6 AND has a vacancy, false otherwise
	private boolean isPlayable(int col) {
		//checking if column is a valid number and has a vacancy
		return (0 <= col && col < numCols && board[0][col] == ' '); 
	}
	
	//checks if it's red turn
	//returns true if it's red turn, false if it's yellow
	boolean isRedTurn() {
		return redTurn;
	}

	//obtains the current game board
	char[][] getBoard() {
		return board;
	}
}
