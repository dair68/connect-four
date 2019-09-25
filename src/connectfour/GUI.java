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
	
	static {
		//initializing background color
		bgColor = new Color(238, 238, 238);
		
		//initializing cell dimensions
		int cellWidth = 50;
		int cellHeight = cellWidth;
		cellDim = new Dimension(cellWidth, cellHeight);
		
		//initializing window dimensions
		int scale = 175;
		int width = 4 * scale;
		int height = 3 * scale;
		windowDim = new Dimension(width, height);

		//initializing board dimensions
		int rectWidth = cellDim.width * 7;
		int rectHeight = cellDim.height * 6;
		int rectX = (width - rectWidth) / 2;
		int rectY = (height - rectHeight) * 3 / 4;
		boardRect = new Rectangle(rectX, rectY, rectWidth, rectHeight);
	}
	
	private Game game;
	private boolean drawBoard;
	private int selectedCol;

	GUI(Game game) {
		//setting up window components
		super("Connect Four");
		
		//adding action listeners
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addWindowStateListener(this);
		
		// stops program when user exits frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sizing frame
		this.setPreferredSize(windowDim);
		this.setResizable(false);
		this.pack();
		
		// placing frame in center of screen
		this.setLocationRelativeTo(null);

		//initializing instance variables
		this.game = game;
		drawBoard = true;
		selectedCol = -1;
	}

	@Override
	public void paint(Graphics g) {
		update(g);
	}

	// updates GUI based on game progress
	@Override
	public void update(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		float strokeWidth = (float) 2;
		g2.setStroke(new BasicStroke(strokeWidth));
		
		int rectX = (int) boardRect.getX();
		int rectY = (int) boardRect.getY();
		int rectWidth = (int) boardRect.getWidth();
		int rectHeight = (int) boardRect.getHeight();

		int cellWidth = (int) cellDim.getWidth();
		int cellHeight = (int) cellDim.getHeight();

		// painting board for first time
		if (drawBoard) {
			//coloring background
			g.setColor(bgColor);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());

			g.setColor(Color.BLUE);
			g.fillRect(rectX, rectY, rectWidth, rectHeight);
			g.setColor(Color.BLACK);
			g.drawRect(rectX, rectY, rectWidth, rectHeight);

		

			// where chips will fall
//			g.setColor(Color.black);
//			g.drawRect(rectX, rectY - cellHeight, rectWidth, cellHeight);

			drawBoard = false;
		}
		
		// drawing circles
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {

				int x = rectX + cellWidth / 10 + i * cellWidth;
				int y = rectY + cellHeight / 10 + j * cellHeight;
				int circleWidth = cellWidth * 4 / 5;
				int circleHeight = cellHeight * 4 / 5;
//							g.setColor(Color.white);
//							g.fillOval(x, y, circleWidth, circleHeight);

				// painting circle appropriate color
				char board[][] = game.getBoard();
				char piece = board[j][i];
				Color chipColor = Color.black;
				Color borderColor = Color.black;
				float borderWidth = strokeWidth;
				float thickBorderWidth = 3;

				switch (piece) {
				case 'x':
					chipColor = Color.red;
					break;
				case 'X':
					chipColor = Color.red;
					borderColor = Color.white;
					borderWidth = thickBorderWidth;
					break;
				case 'o':
					chipColor = Color.yellow;
					break;
				case 'O':
					chipColor = Color.yellow;
					borderColor = Color.orange;
					borderWidth = thickBorderWidth;
					break;
				case ' ':
					chipColor = bgColor;
					break;
				}
				
				g.setColor(chipColor);
				g.fillOval(x, y, circleWidth, circleHeight);
				
				g2.setStroke(new BasicStroke(borderWidth));
				g.setColor(borderColor);
				g.drawOval(x, y, circleWidth, circleHeight);
			}

		}

		// drawing chip shilloute over selected column
		for (int i = 0; i < 7; i++) {
			//drawing shilloute over selected column
			if(selectedCol == i) {
				int circleX = rectX + cellWidth / 10 + i * cellWidth;
				int circleY = rectY + cellHeight / 10 - cellHeight;
				int circleWidth = cellWidth * 4 / 5;
				int circleHeight = cellHeight * 4 / 5;
				
				Color chipColor = game.isRedTurn() ? Color.red : Color.yellow;

				g.setColor(chipColor);
				g.fillOval(circleX, circleY, circleWidth, circleHeight);

				g.setColor(Color.black);
				g.drawOval(circleX, circleY, circleWidth, circleHeight);
			}
			//erasing top of column
			else {
				//erasing tops of all columns
				int x = rectX + i * cellWidth;
				int y = rectY - cellHeight;
				
				g.setColor(bgColor);
				g.fillRect(x, y, cellWidth, cellHeight);
			}
			
		}
		
		updateText(g);
	}

	//updates status text in window
	//g - the graphics context
	private void updateText(Graphics g) {
		g.setColor(Color.black);
		
		int textX = (int) (this.getWidth()*.2);
		int textY = boardRect.y - cellDim.height * 2;
		int textWidth = (int) (this.getWidth()*.6);
		int textHeight =  (int) (cellDim.height);
		
		
		//clearing text area
		//g.setColor(bgColor);
		//g.setColor(Color.LIGHT_GRAY);
		Color messageBG = game.isRedTurn() ? Color.red : Color.yellow;
		g.setColor(messageBG);
		g.fillRect(textX, textY, textWidth, textHeight);
		
		g.setColor(Color.black);
		g.drawRect(textX, textY, textWidth, textHeight);
		
		Font font = new Font("Comic Sans MS", Font.PLAIN, 20);
		g.setFont(font);
		
		//Color fontColor = game.isRedTurn() ? Color.red : Color.yellow;
		Color fontColor = Color.black;
		g.setColor(fontColor);
		
		//determining message to display
		String player = game.isRedTurn() ? "Red player" : "Yellow player";
		String winningText = "Congratulations! " + player + " is the winner!";
		String turnText = player + "'s turn";
		String message = game.isOver() ? winningText : turnText;
		
		
		g.drawString(message, textX, (int) (textY+textHeight*.8));
	}
	
	// mouse key pressed down
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed");
	}

	// mouse key released
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("Mouse released");
	}

	// mouse clicked
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse clicked (" + e.getX() + ", " + e.getY() + ")");
		
		//user clicked on a column
		if(selectedCol != -1) {
			game.playPiece(selectedCol);
			this.repaint();
		}
	}

	// mouse entered
	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("Mouse entered");
	}

	// mouse exited
	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Mouse exited");
	}

	// overriding MouseMotionListener methods

	// everytime mouse moves
	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("Mouse moved to (" + e.getX() + ", " + e.getY() + ")");

		int x = e.getX();

		int leftEdge = (int) boardRect.getX();
		int rightEdge = leftEdge + (int) boardRect.getWidth();
		
		int prevSelectedCol = selectedCol;

		// checking if mouse in a column
		if (leftEdge < x && x < rightEdge) {
			selectedCol = (x - leftEdge) / (int) cellDim.getWidth();
		}
		// mouse not in a column
		else {
			selectedCol = -1;
		}
		
		//repainting if selected column has changed
		if(prevSelectedCol != selectedCol) {
			this.repaint();
		}
	}

	// everytime mouse dragged
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("Mouse dragged");
	}
	
	@Override
	public void windowStateChanged(WindowEvent e) {
		drawBoard = true;
		this.repaint();
	}
}
