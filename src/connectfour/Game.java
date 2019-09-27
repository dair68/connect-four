package connectfour;

import java.util.Arrays;
import java.util.Scanner;
import connectfour.AI;

public class Game {

	static final int numRows = 6;
	static final int numCols = 7;

	// ' ' = empty, 'x' = red piece, 'o' = yellow piece
	// 'X' = red connect 4 piece, 'O' = yellow connect 4 piece
	private final char[][] board;
	private boolean redTurn;
	private boolean over;
	private String winner;
	private final Scanner input;

	// initializes the game
	public Game() {
		board = new char[numRows][numCols];

		// initializing board. first index row, 2nd index col. [0][0] upperleft corner.
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				board[i][j] = ' ';
			}
		}

		redTurn = false;
		over = false;
		winner = "";
		input = new Scanner(System.in);
	}

	// plays a piece within a column, if possible
	// @param col - column to place piece. number from 0-6.
	void playPiece(final int col) {
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
	boolean isPlayable(final int col) {
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
	public boolean isDraw() {
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
	// returns "red", "yellow", '' (no winner yet), or "none" (draw)
	String getWinner() {
		return winner;
	}

	// checks for a winner
	private boolean hasWinner() {
		// checking for horizontal connect fours
		for (int i = 0; i < numRows; i++) {
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
		for (int i = 0; i <= 2; i++) {
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
		for (int i = 0; i <= 2; i++) {
			for (int j = 3; j <= 6; j++) {
				// found connect four
				if (diagonalConnectFour1(i, j)) {
					// highlighting connect four on board
					markDiagonalConnectFour1(i, j);
					return true;
				}
			}
		}

		// checking for diagonal connect fours of type \
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 3; j++) {
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
	private boolean horizontalConnectFour(final int row, final int col) {
		final char a = board[row][col];
		final char b = board[row][col + 1];
		final char c = board[row][col + 2];
		final char d = board[row][col + 3];

		return connectFour(a, b, c, d);
	}

	// converts all spots in a horizontal connect four into uppercase
	// @param row - row of leftmost space in connect four. 0-6.
	// @param col - col of leftmost space in connect four. 0-7.
	private void markHorizontalConnectFour(final int row, final int col) {
		// converting piece symbol to uppercase
		final char markedPiece = Character.toUpperCase(board[row][col]);

		board[row][col] = markedPiece;
		board[row][col + 1] = markedPiece;
		board[row][col + 2] = markedPiece;
		board[row][col + 3] = markedPiece;
	}

	// checks for vertical connect four
	// @param row - row of top-most space in col of four. 0-6.
	// @param col - col of top-most space in col of four. 0-7.
	private boolean verticalConnectFour(final int row, final int col) {
		final char a = board[row][col];
		final char b = board[row + 1][col];
		final char c = board[row + 2][col];
		final char d = board[row + 3][col];

		return connectFour(a, b, c, d);
	}

	// converts all spots in vertical connect four into uppercase
	// @param row - row of top-most space in col of four. 0-6
	// @param col - col of top-most space in col of four. 0-7
	private void markVerticalConnectFour(final int row, final int col) {
		final char markedPiece = Character.toUpperCase(board[row][col]);

		board[row][col] = markedPiece;
		board[row + 1][col] = markedPiece;
		board[row + 2][col] = markedPiece;
		board[row + 3][col] = markedPiece;
	}

	// checks for diagonal connect four of the type /
	// @param row - row of top-right space in diagonal of four
	// @param col - col of top-right space in diagonal of four
	private boolean diagonalConnectFour1(final int row, final int col) {
		final char a = board[row][col];
		final char b = board[row + 1][col - 1];
		final char c = board[row + 2][col - 2];
		final char d = board[row + 3][col - 3];

		return connectFour(a, b, c, d);
	}

	// converts all spots in a / diagonal into uppercase
	// @param row - row of bottom-left space in diagonal of four
	// @param col - col of bottom-left space in diagonal of four
	private void markDiagonalConnectFour1(final int row, final int col) {
		final char markedPiece = Character.toUpperCase(board[row][col]);

		board[row][col] = markedPiece;
		board[row + 1][col - 1] = markedPiece;
		board[row + 2][col - 2] = markedPiece;
		board[row + 3][col - 3] = markedPiece;
	}

	// checks for diagonal connect four of the type \
	// @param row - row of top-left space in diagonal of four
	// @param col - col of top-left space in diagonal of four
	private boolean diagonalConnectFour2(final int row, final int col) {
		final char a = board[row][col];
		final char b = board[row + 1][col + 1];
		final char c = board[row + 2][col + 2];
		final char d = board[row + 3][col + 3];

		return connectFour(a, b, c, d);
	}

	// converts all spaces in \ diagonal into uppercase
	// @param row - row of top-left space in connect four
	// @param col - col of top-left space in connect four
	private void markDiagonalConnectFour2(final int row, final int col) {
		final char markedPiece = Character.toUpperCase(board[row][col]);

		board[row][col] = markedPiece;
		board[row + 1][col + 1] = markedPiece;
		board[row + 2][col + 2] = markedPiece;
		board[row + 3][col + 3] = markedPiece;
	}

	// checks if 4 spaces have the same colored pieces
	// @param a, b, c, d - spaces in the board
	private boolean connectFour(final char a, final char b, final char c, final char d) {
		return (a == b && b == c & c == d && a != ' ');
	}

	// prints board to console
	private void printBoard() {
		// printing column numbers above board
		String columnNums = "";

		for (int k = 0; k < numCols; k++) {
			final String columnLabel = " " + k + " ";
			columnNums += columnLabel;
		}

		System.out.println(columnNums);

		// printing board starting with top row
		for (int i = 0; i < numRows; i++) {
			String line = "";

			// appending row data to line
			for (int j = 0; j < numCols; j++) {
				final String boardSpace = "[" + board[i][j] + "]";
				line += boardSpace;
			}

			// printing line to console
			System.out.println(line);
		}
	}

	// asks user for column input. function ends when valid input received
	// @param scanner - input stream
	private String getColumnInput() {
		// asking user to input a column
		System.out.println("Input column(0-6): ");
		final String[] columns = new String[] { "0", "1", "2", "3", "4", "5", "6" };
		String selectedCol = "";

		// obtaining valid column input
		while (!Arrays.asList(columns).contains(selectedCol)) {
			selectedCol = input.nextLine();
		}

		return selectedCol;
	}

	// asks user if they want to play again. function ends when valid input
	// received.
	private String getPlayAgainInput() {
		
		// asking if player wants to go again
		System.out.println("Play again? (y/n)");
		String response = "";

		// obtaining valid response
		while (!response.equals("y") && !response.equals("n")) {
			response = input.nextLine();
		}
		
		return response;
	}
	
	// prints the win screen to console
	private void printWinScreen() {
		// game over. displaying final messages
		final String player = redTurn ? "Red player" : "Yellow player";
		final String winMessage = "Congratulations! " + player + " is the winner!";
		final String tieMessage = "Game over. It's a tie!";

		final String message = isDraw() ? tieMessage : winMessage;
		System.out.println(message);
		printBoard();
	}

	// plays 2-player console version of game
	public void play() {
		final Scanner input = new Scanner(System.in);

		// main game event loop
		while (!over) {
			// printing game status
			final String player = redTurn ? "Red player" : "Yellow player";
			System.out.println(player + "'s Turn");
			printBoard();

			// playing column from user input
			String selectedCol = getColumnInput();
			playPiece(Integer.parseInt(selectedCol));
		}

		//game over. printing win screen.
		printWinScreen();
		
		//asking if player wants to play another game
		String response = getPlayAgainInput();

		// player wants to play another game
		if (response.equals("y")) {
			reset();
			play();
		}
		// player wants to quit
		else {
			System.out.println("Thanks for playing!");
			input.close();
			return;
		}
	}

	// plays console version of game vs AI
	public void play(AI ai) {
		final Scanner input = new Scanner(System.in);
		
		// main game event loop
		while (!over) {
			// printing game status
			final String player = redTurn ? "Red player" : "Yellow player";
			System.out.println(player + "'s Turn");
			printBoard();

			// red player's turn; AI makes a move
			if (redTurn) {
				final int selectedCol = ai.determineMove(this);
				System.out.println(selectedCol);
				playPiece(selectedCol);
			}
			// yellow player's turn; asking user to input a column
			else {
				String selectedCol = getColumnInput();
				playPiece(Integer.parseInt(selectedCol));
			}

		}

		// game over. displaying final messages
		printWinScreen();

		// asking if player wants to go again
		String response = getPlayAgainInput();

		// player wants to play another game
		if (response.equals("y")) {
			reset();
			play(ai);
		}
		// player wants to quit
		else {
			System.out.println("Thanks for playing!");
			input.close();
			return;
		}
	}

	// resets the game parameters
	public void reset() {
		// resetting board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				board[i][j] = ' ';
			}
		}

		redTurn = false;
		over = false;
		winner = "";
	}
}
