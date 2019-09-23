package connectfour;

public class Game {
	// ' ' = empty, 'x' = red piece, 'o' = yellow piece
	// 'X' = red connect 4 piece, 'O' = yellow connect 4 piece
	private char[][] board;
	static final int numRows = 6;
	static final int numCols = 7;

	private boolean redTurn;
	private boolean over;
	private String winner;
	private GUI window;
	
	// initializes the game
	public Game() {
		board = new char[numRows][numCols];

		// initializing board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				board[i][j] = ' ';
			}
		}

		redTurn = false;
		over = false;
		winner = "";
		window = new GUI(this);
	}

	// starts the game
	public void start() {
		//window.update(this);
		//window.setVisible(true);
		window.setVisible(true);
	}

	// plays a piece within a column, if possible
	// @param col - column to place piece. number from 0-6.
	void playPiece(int col) {
		// col is currently playable
		if (isPlayable(col) && !over) {
			// finding vacancy
			for (int i = numRows - 1; i >= 0; i--) {
				// found the vacancy
				if (board[i][col] == ' ') {
					// determining color of piece based on player turn
					char piece = redTurn ? 'x' : 'o';

					// adding piece to board
					board[i][col] = piece;

					finishTurn();
					break;
				}
			}
		}
	}

	// checks to see if col is playable
	// @param col - chosen col
	// returns true if col is from 0-6 AND has a vacancy, false otherwise
	private boolean isPlayable(int col) {
		// checking if column is a valid number and has a vacancy
		return (0 <= col && col < numCols && board[0][col] == ' ');
	}

	// checks if it's red turn
	// returns true if it's red turn, false if it's yellow
	boolean isRedTurn() {
		return redTurn;
	}

	// obtains the current game board
	char[][] getBoard() {
		return board;
	}

	// checks to see if game over
	boolean isOver() {
		// checking for draw
		return over;
	}

	// checks to see if game is a draw
	private boolean isDraw() {
		// checking if whole board is filled
		for (int i = 0; i < numCols; i++) {
			// found empty space on top row
			if (board[0][i] == ' ') {
				return false;
			}
		}

		return true;
	}

	// checks if the game has a winner, and handles things accordingly
	private void finishTurn() {
		// game is draw
		if (isDraw()) {
			over = true;
			winner = "none";
		}
		// game has winner
		else if (hasWinner()) {
			over = true;
			winner = redTurn ? "red" : "yellow";
		}
		// no winner yet
		else {
			redTurn = redTurn ? false : true;
		}
	}

	// obtains the game's winner
	// returns "red", "yellow", or "none"
	String getWinner() {
		return winner;
	}

	// checks for a winner
	private boolean hasWinner() {
		// checking for horizontal connect fours
		for (int i = numRows - 1; i >= 0; i--) {
			for (int j = 0; j < 4; j++) {
				// found connect four
				if (horizontalConnectFour(i, j)) {
					// highlighting connect four on board
					markHorizontalConnectFour(i, j);
					return true;
				}
			}
		}

		// checking for vertical connect fours
		for (int i = numRows - 1; i >= 3; i--) {
			for (int j = 0; j < numCols; j++) {
				// found connect four
				if (verticalConnectFour(i, j)) {
					// highlighting connect four on board
					markVerticalConnectFour(i, j);
					return true;
				}
			}
		}

		// checking for diagonal connect fours of type /
		for (int i = numRows - 1; i >= 3; i--) {
			for (int j = 0; j < 4; j++) {
				// found connect four
				if (diagonalConnectFour1(i, j)) {
					// highlighting connect four on board
					markDiagonalConnectFour1(i, j);
					return true;
				}
			}
		}

		// checking for diagonal connect fours of type \
		for (int i = numRows - 1; i >= 3; i--) {
			for (int j = numCols - 1; j >= 3; j--) {
				// found connect four
				if (diagonalConnectFour2(i, j)) {
					// highlighting connect four on board
					markDiagonalConnectFour2(i, j);
					return true;
				}
			}
		}

		return false;
	}

	// checks for horizontal connect four
	// @param row - row of leftmost space in the row of four. 0-6.
	// @param col - col of leftmost space in the row of four. 0-7.
	private boolean horizontalConnectFour(int row, int col) {
		char a = board[row][col];
		char b = board[row][col + 1];
		char c = board[row][col + 2];
		char d = board[row][col + 3];

		return connectFour(a, b, c, d);
	}

	// converts all spots in a horizontal connect four into uppercase
	// @param row - row of leftmost space in connect four. 0-6.
	// @param col - col of leftmost space in connect four. 0-7.
	private void markHorizontalConnectFour(int row, int col) {
		// converting piece symbol to uppercase
		char markedPiece = Character.toUpperCase(board[row][col]);

		board[row][col] = markedPiece;
		board[row][col + 1] = markedPiece;
		board[row][col + 2] = markedPiece;
		board[row][col + 3] = markedPiece;
	}

	// checks for vertical connect four
	// @param row - row of bottom-most space in col of four. 0-6.
	// @param col - col of bottom-most space in col of four. 0-7.
	private boolean verticalConnectFour(int row, int col) {
		char a = board[row][col];
		char b = board[row - 1][col];
		char c = board[row - 2][col];
		char d = board[row - 3][col];

		return connectFour(a, b, c, d);
	}

	// converts all spots in vertical connect four into uppercase
	// @param row - row of bottom-most space in col of four. 0-6
	// @param col - col of bottom-most space in col of four. 0-7
	private void markVerticalConnectFour(int row, int col) {
		char markedPiece = Character.toUpperCase(board[row][col]);

		board[row][col] = markedPiece;
		board[row - 1][col] = markedPiece;
		board[row - 2][col] = markedPiece;
		board[row - 3][col] = markedPiece;
	}

	// checks for diagonal connect four of the type /
	// @param row - row of bottom-left space in diagonal of four
	// @param col - col of bottom-left space in diagonal of four
	private boolean diagonalConnectFour1(int row, int col) {
		char a = board[row][col];
		char b = board[row - 1][col + 1];
		char c = board[row - 2][col + 2];
		char d = board[row - 3][col + 3];

		return connectFour(a, b, c, d);
	}

	// converts all spots in a / diagonal into uppercase
	// @param row - row of bottom-left space in diagonal of four
	// @param col - col of bottom-left space in diagonal of four
	private void markDiagonalConnectFour1(int row, int col) {
		char markedPiece = Character.toUpperCase(board[row][col]);

		board[row][col] = markedPiece;
		board[row - 1][col + 1] = markedPiece;
		board[row - 2][col + 2] = markedPiece;
		board[row - 3][col + 3] = markedPiece;
	}

	// checks for diagonal connect four of the type \
	// @param row - row of bottom-right space in diagonal of four
	// @param col - col of bottom-right space in diagonal of four
	private boolean diagonalConnectFour2(int row, int col) {
		char a = board[row][col];
		char b = board[row - 1][col - 1];
		char c = board[row - 2][col - 2];
		char d = board[row - 3][col - 3];

		return connectFour(a, b, c, d);
	}

	// converts all spaces in \ diagonal into uppercase
	// @param row - row of bottom-right space in connect four
	// @param col - col of bottom-right space in connect four
	private void markDiagonalConnectFour2(int row, int col) {
		char markedPiece = Character.toUpperCase(board[row][col]);

		board[row][col] = markedPiece;
		board[row - 1][col - 1] = markedPiece;
		board[row - 2][col - 2] = markedPiece;
		board[row - 3][col - 3] = markedPiece;
	}

	// checks if 4 spaces have the same colored pieces
	// @param a, b, c, d - spaces in the board
	private boolean connectFour(char a, char b, char c, char d) {
		return (a == b && b == c & c == d && a != ' ');
	}
}
