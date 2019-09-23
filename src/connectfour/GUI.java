package connectfour;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.*;

public class GUI extends JFrame implements MouseListener, MouseMotionListener, WindowStateListener {
	int scale;
	private Dimension windowDim;
	private Dimension cellDim;

	private Rectangle board;
	private int selectedCol;

	private Game game;

	
	boolean drawBoard;
	Color backgroundColor;

	GUI(Game game) {
		super("Connect Four");

		this.game = game;
		drawBoard = true;

		backgroundColor = this.getBackground();
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addWindowStateListener(this);

		// stops program when user exits frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		scale = 175;
		int width = 4 * scale;
		int height = 3 * scale;

		windowDim = new Dimension(width, height);

		int cellWidth = 50;
		int cellHeight = cellWidth;
		cellDim = new Dimension(cellWidth, cellHeight);

		int rectWidth = cellWidth * 7;
		int rectHeight = cellHeight * 6;
		int rectX = (width - rectWidth) / 2;
		int rectY = (height - rectHeight) * 3 / 4;

		board = new Rectangle(rectX, rectY, rectWidth, rectHeight);
		selectedCol = -1;
	
		// In the main method:
		Insets insets = getInsets();
		this.setSize(width, height);

		System.out.println(rectX);

		// Toolkit.getDefaultToolkit().setDynamicLayout(false);
		this.setBackground(this.getBackground());

		this.setPreferredSize(new Dimension(width, height));
		this.setResizable(false);
		this.setLayout(null);

		// adding buttons to container
		Container pane = this.getContentPane();

		// sizing frame
		this.pack();

		// placing frame in center of screen
		this.setLocationRelativeTo(null);
	}

	@Override
	public void paint(Graphics g) {
		update(g);
	}

	// updates GUI based on game progress
	@Override
	public void update(Graphics g) {
		// g.setClip(rectX, rectY, rectWidth, rectHeight);
		
		Graphics2D g2 = (Graphics2D) g;
		float strokeWidth = (float) 1.5;
		g2.setStroke(new BasicStroke(strokeWidth));
		
		int rectX = (int) board.getX();
		int rectY = (int) board.getY();
		int rectWidth = (int) board.getWidth();
		int rectHeight = (int) board.getHeight();

		int cellWidth = (int) cellDim.getWidth();
		int cellHeight = (int) cellDim.getHeight();

		// painting board for first time
		if (drawBoard) {
			//coloring background
			g.setColor(backgroundColor);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());

			g.setColor(Color.BLUE);
			g.fillRect(rectX, rectY, rectWidth, rectHeight);
			g.setColor(Color.BLACK);
			g.drawRect(rectX, rectY, rectWidth, rectHeight);

			// drawing circles
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {

					int x = rectX + cellWidth / 10 + i * cellWidth;
					int y = rectY + cellHeight / 10 + j * cellHeight;
					int circleWidth = cellWidth * 4 / 5;
					int circleHeight = cellHeight * 4 / 5;
//								g.setColor(Color.white);
//								g.fillOval(x, y, circleWidth, circleHeight);

					// painting circle appropriate color
					char board[][] = game.getBoard();
					char piece = board[j][i];

					switch (piece) {
					case 'x':
						g.setColor(Color.red);
						break;
					case 'o':
						g.setColor(Color.yellow);
						break;
					case ' ':
						g.setColor(Color.white);
						break;
					}
					g.fillOval(x, y, circleWidth, circleHeight);

					g.setColor(Color.black);
					g.drawOval(x, y, circleWidth, circleHeight);

				}

			}

			// where chips will fall
//			g.setColor(Color.black);
//			g.drawRect(rectX, rectY - cellHeight, rectWidth, cellHeight);

			drawBoard = false;
		}

		// drawing chip shilloute over selected column
		for (int i = 0; i < 7; i++) {
			//drawing shilloute over selected column
			if(selectedCol == i) {
				int circleX = rectX + cellWidth / 10 + i * cellWidth;
				int circleY = rectY + cellHeight / 10 - cellHeight;
				int circleWidth = cellWidth * 4 / 5;
				int circleHeight = cellHeight * 4 / 5;

				g.setColor(Color.yellow);
				g.fillOval(circleX, circleY, circleWidth, circleHeight);

				g.setColor(Color.black);
				g.drawOval(circleX, circleY, circleWidth, circleHeight);
			}
			//erasing top of column
			else {
				//erasing tops of all columns
				int x = rectX + i * cellWidth;
				int y = rectY - cellHeight;
				
				g.setColor(backgroundColor);
				g.fillRect(x, y, cellWidth, cellHeight);
			}
			
		}
		
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

		int leftEdge = (int) board.getX();
		int rightEdge = leftEdge + (int) board.getWidth();
		
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
