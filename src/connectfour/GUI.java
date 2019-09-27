package connectfour;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.*;
import javax.swing.UIManager;

public class GUI extends JFrame implements MouseListener, MouseMotionListener, WindowStateListener {

	private static final Color bgColor;
	private static final Dimension cellDim;
	private static final Dimension windowDim;
	private static final Rectangle boardRect;

	// initializing static variables
	static {
		// initializing background color
		bgColor = new Color(238, 238, 238);

		// initializing cell dimensions
		final int cellWidth = 50;
		final int cellHeight = cellWidth;
		cellDim = new Dimension(cellWidth, cellHeight);

		// initializing window dimensions
		final int scale = 175;
		final int width = 4 * scale;
		final int height = 3 * scale;
		windowDim = new Dimension(width, height);

		// initializing board dimensions
		final int boardWidth = cellDim.width * 7;
		final int boardHeight = cellDim.height * 6;
		final int boardX = (width - boardWidth) / 2;
		final int boardY = (height - boardHeight) * 3 / 4;
		boardRect = new Rectangle(boardX, boardY, boardWidth, boardHeight);
	}

	private Game game;
	private boolean drawBoard;
	private int selectedCol;

	// Constructor
	// @param game - some instance of connectfour.Game
	public GUI(Game game) {
		// setting up window components
		super("Connect Four");

		// stops program when user exits frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// sizing frame
		this.setPreferredSize(windowDim);
		this.setResizable(false);
		this.pack();

		// placing frame in center of screen
		this.setLocationRelativeTo(null);

		// initializing instance variables
		this.game = game;
		drawBoard = true;
		selectedCol = -1;

		// adding action listeners
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addWindowStateListener(this);
	}

	// starts the window connect four game
	public void start() {
		this.setVisible(true);
	}

	// paints window automatically when paint thread automatically scheduled
	// @param g - an instance of Graphics class
	@Override
	public void paint(Graphics g) {
		update(g);
	}

	// updates GUI graphics
	// @param g - an instance of Graphics class. call repaint() instead of update().
	@Override
	public void update(Graphics g) {

		// setting line width
		final Graphics2D g2 = (Graphics2D) g;
		final float lineWidth = 2;
		g2.setStroke(new BasicStroke(lineWidth));

		// painting board for first time
		if (drawBoard) {
			drawBackground(g2);
		}

		// drawing chips on board
		drawChips(g2);
		drawChipSilhouette(g2);

		// setting flavor text
		updateText(g);
	}

	// draws background of window and board
	// @param g2 - Graphics2D context. passed in from update()
	private void drawBackground(Graphics2D g2) {

		// coloring background
		g2.setColor(bgColor);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		// drawing board
		g2.setColor(Color.BLUE);
		g2.fillRect(boardRect.x, boardRect.y, boardRect.width, boardRect.height);

		// drawing board border
		g2.setColor(Color.BLACK);
		g2.drawRect(boardRect.x, boardRect.y, boardRect.width, boardRect.height);

		drawBoard = false;
	}

	// draws a chip in the window
	// @param g2 - graphics2D context. passed in from outside function
	// @param cellCoord - coordinates of upper left corner of bounding cell
	// @param chipColor - color of the chip
	// @param borderColor - color of chip border
	private void drawChip(Graphics2D g2, Point cellCoord, Color chipColor, Color borderColor) {

		// calculating circle parameters
		final int circleWidth = cellDim.width * 4 / 5;
		final int circleHeight = cellDim.height * 4 / 5;
		final int circleX = cellCoord.x + cellDim.width / 10;
		final int circleY = cellCoord.y + cellDim.height / 10;

		// drawing chip
		g2.setColor(chipColor);
		g2.fillOval(circleX, circleY, circleWidth, circleHeight);

		// drawing chip border
		g2.setColor(borderColor);
		g2.drawOval(circleX, circleY, circleWidth, circleHeight);
	}

	// draws connect four chips
	// @param g2 - the graphics context. passed in from update()
	private void drawChips(Graphics2D g2) {

		// drawing chips
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				// circles take up middle 80 percent of each square cell
				final int cellX = boardRect.x + i * cellDim.width;
				final int cellY = boardRect.y + j * cellDim.height;

				// setting up circle colors
				final char board[][] = game.getBoard();
				final char piece = board[j][i];

				final Color chipColor;
				final Color borderColor;
				final float borderWidth;
				final float thinBorderWidth = 2;
				final float thickBorderWidth = 3;

				// determining colors based on player turn and wins
				switch (piece) {
				case 'x':
					chipColor = Color.red;
					borderColor = Color.black;
					borderWidth = thinBorderWidth;
					break;
				case 'X':
					chipColor = Color.red;
					borderColor = Color.white;
					borderWidth = thickBorderWidth;
					break;
				case 'o':
					chipColor = Color.yellow;
					borderColor = Color.black;
					borderWidth = thinBorderWidth;
					break;
				case 'O':
					chipColor = Color.yellow;
					borderColor = Color.orange;
					borderWidth = thickBorderWidth;
					break;
				default:
					chipColor = bgColor;
					borderColor = Color.black;
					borderWidth = thinBorderWidth;
					break;
				}

				// drawing chip
				g2.setStroke(new BasicStroke(borderWidth));
				drawChip(g2, new Point(cellX, cellY), chipColor, borderColor);
			}

		}
	}

	// draws floating chip silhouette
	// @param g2 - Graphics2d context. passed in from update().
	private void drawChipSilhouette(Graphics2D g2) {
		//setting dimensions of silhouette area
		final int hoverRectX = boardRect.x;
		final int hoverRectY = boardRect.y - cellDim.height;
		final int hoverRectWidth = boardRect.width;
		final int hoverRectHeight = cellDim.height;

		// erasing tops of columns
		g2.setColor(bgColor);
		g2.fillRect(hoverRectX, hoverRectY, hoverRectWidth, hoverRectHeight);

		// drawing chip silhouette
		if (!game.isOver()) {
			// searching for selected column
			for (int i = 0; i < 7; i++) {
				// found the selected column
				if (i == selectedCol) {
					// finding chip location
					final int cellX = hoverRectX + i * cellDim.width;
					final int cellY = hoverRectY;

					// determining chip color
					Color chipColor = game.isRedTurn() ? Color.red : Color.yellow;
					
					// drawing chip
					drawChip(g2, new Point(cellX, cellY), chipColor, Color.black);
					break;
				}
			}
		}
	}

	// updates status text in window
	// g - the graphics context. passed in from update().
	private void updateText(Graphics g) {

		// finding dimensions of text area
		final int wideRectWidth = this.getWidth() * 3 / 4;
		final int smallRectWidth = this.getWidth() * 3 / 5;

		final int textRectWidth = game.isOver() ? wideRectWidth : smallRectWidth;
		final int textRectHeight = cellDim.height;
		final int textRectX = (this.getWidth() - textRectWidth) / 2;
		final int textRectY = boardRect.y - cellDim.height * 2;

		// coloring text area
		final Color messageBG; 
		final Color tieBGColor = Color.DARK_GRAY;
		final Color turnBGColor = game.isRedTurn() ? Color.red : Color.yellow;
		messageBG = game.isDraw() ? tieBGColor : turnBGColor;
		
		g.setColor(messageBG);
		g.fillRect(textRectX, textRectY, textRectWidth, textRectHeight);

		// outlining border
		g.setColor(Color.black);
		g.drawRect(textRectX, textRectY, textRectWidth, textRectHeight);

		// selecting font
		final int fontSize = 25;
		final Font font = new Font("Comic Sans MS", Font.PLAIN, fontSize);
		g.setFont(font);

		// determining message to display
		final Color fontColor;
		final String message;

		// game ended in a tie
		if (game.isDraw()) {
			fontColor = Color.white;
			message = "Game over. It's a tie!";
		}
		// game not in tie state
		else {
			fontColor = game.isRedTurn() ? Color.white : Color.black;

			final String player = game.isRedTurn() ? "Red player" : "Yellow player";
			final String turnMessage = player + "'s turn";
			final String winMessage = "Congratulations! " + player + " is the winner!";

			message = game.isOver() ? winMessage : turnMessage;
		}

		// writing message
		g.setColor(fontColor);

		final int textX = textRectX + 5;
		final int textY = textRectY + textRectHeight * 3 / 4;
		g.drawString(message, textX, textY);
	}

	// prints message to console if mouse key pressed down
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed");
	}

	// prints message to console if mouse key lifted up
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("Mouse released");
	}

	// listens for mouse clicks. plays piece if needed.
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse clicked (" + e.getX() + ", " + e.getY() + ")");

		// user clicked on a column
		if (selectedCol != -1) {
			game.playPiece(selectedCol);
			this.repaint();
		}
	}

	// prints message to console when mouse enters window
	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("Mouse entered");
	}

	// prints message to console when mouse leaves window
	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Mouse exited");
	}

	// listens for mouse movement. draws chip silhouettes if mouse in certain regions.
	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("Mouse moved to (" + e.getX() + ", " + e.getY() + ")");

		final int x = e.getX();

		final int leftEdge = boardRect.x;
		final int rightEdge = leftEdge + boardRect.width;

		final int prevSelectedCol = selectedCol;

		// checking if mouse in a column
		if (leftEdge < x && x < rightEdge) {
			selectedCol = (x - leftEdge) / cellDim.width;
		}
		// mouse not in a column
		else {
			selectedCol = -1;
		}

		// repainting if selected column has changed
		if (prevSelectedCol != selectedCol) {
			this.repaint();
		}
	}

	// outputs message to console everytime mouse is dragged
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("Mouse dragged");
	}

	//checks if window state changed and repaints if needed
	@Override
	public void windowStateChanged(WindowEvent e) {
		drawBoard = true;
		this.repaint();
	}
}
