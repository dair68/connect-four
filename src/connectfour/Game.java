package connectfour;

import java.util.Arrays;
import java.util.Scanner;
import connectfour.AI;

public class Game {

	static final int numRows = 6;
	static final int numCols = 7;

	// top row is row 0, left column is column 0
	private final char[][] board;
	private final Space[] connectFour;
	private Space lastChipLocation;
	private boolean redTurn;
	private boolean over;
	private String winner;

	// initializes the game
	public Game() {
		board = new char[numRows][numCols];

		// initializing board. first index row, 2nd index col. [0][0] upperleft corner.
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				board[i][j] = ' ';
			}
		}

		connectFour = new Space[4];
		lastChipLocation = new Space(-1, -1);

		redTurn = false;
		over = false;
		winner = "";
	}

	// copy constructor
	Game(Game game) {
		board = new char[numRows][numCols];

		// copying over board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				final char[][] gameBoard = game.getBoard();
				board[i][j] = gameBoard[i][j];
			}
		}

		connectFour = new Space[4];

		// copying over connect four
		for (int k = 0; k < 4; k++) {	
			final Space[] gameConnectFour = game.getConnectFour();
			connectFour[k] = gameConnectFour[k];
		}

		lastChipLocation = new Space(game.getLastChipLocation());
		redTurn = game.isRedTurn();
		over = game.isOver();
		winner = game.getWinner();
	}

	// obtains the current game board. ' ' = empty, '0' = red, 'O' = yellow
	char[][] getBoard() {
		return board;
	}

	// returns array containing game's connect four spaces, if connect four exists
	Space[] getConnectFour() {
		return connectFour;
	}

	// returns space containing most recently played chip
	Space getLastChipLocation() {
		return lastChipLocation;
	}

	// checks if it's red turn
	boolean isRedTurn() {
		return redTurn;
	}

	// checks to see if game over
	boolean isOver() {
		return over;
	}

	// obtains the game's winner
	// returns "red", "yellow", '' (no winner yet), or "none" (draw)
	String getWinner() {
		return winner;
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
					final char piece = redTurn ? '0' : 'O';
					board[i][col] = piece;
					lastChipLocation = new Space(i, col);

					finishTurn();
					break;
				}
			}
		}
	}

	// checks to see if col is playable
	// @param col - chosen col
	// returns true if col is from 0-6 AND has a vacancy, false otherwise
	boolean isPlayable(int col) {
		// checking if column is a valid number and has a vacancy
		return (0 <= col && col < numCols && board[0][col] == ' ');
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

	// checks for a winner
	private boolean hasWinner() {
		// checking for horizontal connect fours
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < 4; j++) {
				final Space[] spaces = this.getFourSpaces(i, j, '-');

				// found connect four
				if (isConnectFour(spaces)) {
					markConnectFour(spaces);
					return true;
				}
			}
		}

		// checking for vertical connect fours
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j < numCols; j++) {
				final Space[] spaces = getFourSpaces(i, j, '|');

				// found connect four
				if (isConnectFour(spaces)) {
					markConnectFour(spaces);
					return true;
				}
			}
		}

		// checking for diagonal connect fours of type /
		for (int i = 0; i <= 2; i++) {
			for (int j = 3; j <= 6; j++) {
				final Space[] spaces = getFourSpaces(i, j, '/');

				// found connect four
				if (isConnectFour(spaces)) {
					// highlighting connect four on board
					markConnectFour(spaces);
					return true;
				}
			}
		}

		// checking for diagonal connect fours of type \
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 3; j++) {
				final Space[] spaces = getFourSpaces(i, j, '\\');

				// found connect four
				if (isConnectFour(spaces)) {
					// highlighting connect four on board
					markConnectFour(spaces);
					return true;
				}
			}
		}

		return false;
	}

	// obtains the spaces for potential connect four
	// @param row - row of topmost space in connect four (leftmost space for
	// horizontal)
	// @param col - col of topmost space in connect four (left most space for
	// horizontal)
	// @param direction - character specifying type of connect four. '-', '|', '/',
	// or '\\'.
	// returns array of 4 Spaces, each Space being space in potential connect four
	private Space[] getFourSpaces(int row, int col, char direction) {
		final Space[] spaces = new Space[4];

		// generating space coordinates
		switch (direction) {
		case '-':
			spaces[0] = new Space(row, col);
			spaces[1] = new Space(row, col + 1);
			spaces[2] = new Space(row, col + 2);
			spaces[3] = new Space(row, col + 3);
			break;
		case '|':
			spaces[0] = new Space(row, col);
			spaces[1] = new Space(row + 1, col);
			spaces[2] = new Space(row + 2, col);
			spaces[3] = new Space(row + 3, col);
			break;
		case '/':
			spaces[0] = new Space(row, col);
			spaces[1] = new Space(row + 1, col - 1);
			spaces[2] = new Space(row + 2, col - 2);
			spaces[3] = new Space(row + 3, col - 3);
			break;
		case '\\':
			spaces[0] = new Space(row, col);
			spaces[1] = new Space(row + 1, col + 1);
			spaces[2] = new Space(row + 2, col + 2);
			spaces[3] = new Space(row + 3, col + 3);
			break;
		}

		return spaces;
	}

	// checks if four spaces form a connect four
	// @param spaces - an array of four Spaces of the form (row, col)
	// returns true if pieces in spaces form connect four, false otherwise
	private boolean isConnectFour(Space[] spaces) {
		final char[] pieces = new char[4];

		// obtaining pieces from space coordinates
		for (int i = 0; i < spaces.length; i++) {
			final Space space = spaces[i];
			final int row = space.row;
			final int col = space.col;

			final char piece = board[row][col];
			pieces[i] = piece;
		}

		final char a = pieces[0];
		final char b = pieces[1];
		final char c = pieces[2];
		final char d = pieces[3];

		return (a == b && b == c && c == d && a != ' ');
	}

	// records four spaces as a connect four
	// @param spaces - array of four spaces that form a connect four
	private void markConnectFour(Space[] spaces) {
		// storing spaces in Game object
		for (int i = 0; i < 4; i++) {
			connectFour[i] = spaces[i];
		}
	}

	// prints board to console
	void printBoard() {
		String columnNums = "";

		// printing column numbers above board
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
				Space space = new Space(i, j);

				// space part of a connect four
				if (Arrays.asList(connectFour).contains(space)) {
					final String boardSpace = "{" + board[i][j] + "}";
					line += boardSpace;
				}
				// space not part of a connect four
				else {
					final String boardSpace = "[" + board[i][j] + "]";
					line += boardSpace;
				}
			}

			// printing line to console
			System.out.println(line);
		}
	}

	// asks user for column input. function ends when valid input received
	// @param input - input stream
	private String getColumnInput(Scanner input) {
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
	// @param input - input stream
	private String getPlayAgainInput(Scanner input) {

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
			final String selectedCol = getColumnInput(input);
			playPiece(Integer.parseInt(selectedCol));
		}

		// game over. printing win screen.
		printWinScreen();

		// asking if player wants to play another game
		final String response = getPlayAgainInput(input);

		// player wants to play another game
		if (response.equals("y")) {
			reset();
			play();
		}
		// player wants to quit
		else {
			System.out.println("Thanks for playing!");
			input.close();
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
				final String selectedCol = getColumnInput(input);
				playPiece(Integer.parseInt(selectedCol));
			}
		}

		// game over. displaying final messages
		printWinScreen();

		// asking if player wants to go again
		final String response = getPlayAgainInput(input);

		// player wants to play another game
		if (response.equals("y")) {
			reset();
			play(ai);
		}
		// player wants to quit
		else {
			System.out.println("Thanks for playing!");
			input.close();
		}
	}

	// run console game of ai vs ai
	// @param cpu1 - AI for first player (yellow)
	// @param cpu2 - AI for second player (red)
	public void runAIGame(AI cpu1, AI cpu2) {
		// main game event loop
		while (!over) {
			// printing game status
			final AI activeAI = redTurn ? cpu2 : cpu1;
			final String player = activeAI.toString();
			System.out.println(player + "'s Turn");
			printBoard();

			// ai making move
			final int selectedCol = activeAI.determineMove(this);
			System.out.println(selectedCol);
			playPiece(selectedCol);
		}

		// game over. displaying final messages
		printWinScreen();
	}

	// runs a game (no console output) of ai vs ai to see which is more effective
	// @param cpu1 - AI for first player (yellow)
	// @param cpu2 - AI for second player (red)
	// returns the AI that wins
	public AI runAITest(AI cpu1, AI cpu2) {
		// main game event loop
		while (!over) {
			//player 1 red, player 2 yellow
			final AI activeAI = redTurn ? cpu2 : cpu1;

			// ai making move
			final int selectedCol = activeAI.determineMove(this);
			playPiece(selectedCol);
		}

		// returning winner
		AI winner = redTurn ? cpu2 : cpu1;
		return winner;
	}

	// resets the game parameters
	public void reset() {
		// resetting board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				board[i][j] = ' ';
			}
		}

		// resetting connect four
		for (int k = 0; k < 4; k++) {
			connectFour[k] = new Space(-1, -1);
		}

		lastChipLocation.row = -1;
		lastChipLocation.col = -1;

		redTurn = false;
		over = false;
		winner = "";
	}
}
