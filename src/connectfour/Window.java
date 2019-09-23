package connectfour;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import connectfour.ConnectFourMouseListener;

public class Window extends JFrame {
	int scale;
	int width;
	int height;
	
	int cellWidth;
	int cellHeight;
	
	int rectX;
	int rectY;
	int rectWidth;
	int rectHeight;
	
	Game game;

	boolean boardDrawn = false;

	Window(Game game) {
		super("Connect Four");
		
		this.game = game;
		
		ConnectFourMouseListener eventListener = new ConnectFourMouseListener();
		this.addMouseListener(eventListener);
		this.addMouseMotionListener(eventListener);
		
		// stops program when user exits frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		scale = 175;
		width = 4 * scale;
		height = 3 * scale;
		
		cellWidth = 50;
		cellHeight = cellWidth;
		
		rectWidth = cellWidth * 7;
		rectHeight = cellHeight * 6;
		rectX = (width - rectWidth) / 2;
		rectY = (height - rectHeight) * 3 / 4;
		
		//In the main method:
		Insets insets = getInsets();
		this.setSize(width, height);
		
		System.out.println(rectX);

		Toolkit.getDefaultToolkit().setDynamicLayout(false);

		this.setPreferredSize(new Dimension(width, height));
		this.setResizable(false);
		this.setLayout(null);
		
		//adding buttons to container
		Container pane = this.getContentPane();

		// sizing frame
		this.pack();

		// placing frame in center of screen
		this.setLocationRelativeTo(null);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		//g.setClip(rectX, rectY, rectWidth, rectHeight);

		g.setColor(Color.BLUE);
		g.fillRect(rectX, rectY, rectWidth, rectHeight);
		g.setColor(Color.BLACK);
		g.drawRect(rectX, rectY, rectWidth, rectHeight);

		// horizontal grid lines
//			for(int i=0; i<7; i++) {
//				int x = rectX;
//				int y = rectY + i * cellHeight;
//				g.drawLine(x, y, x + rectWidth, y);
//			}
//			
//			//vertical grid lines
//			for(int j=0; j<8; j++) {
//				int x = rectX + j*cellWidth;
//				int y = rectY;
//				g.drawLine(x, y, x, y+rectHeight);
//			}

		// drawing circles
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				int x = rectX + cellWidth / 10 + i * cellWidth;
				int y = rectY + cellHeight / 10 + j * cellHeight;
				int circleWidth = cellWidth * 4 / 5;
				int circleHeight = cellHeight * 4 / 5;
//					g.setColor(Color.white);
//					g.fillOval(x, y, circleWidth, circleHeight);
				
				//painting circle appropriate color
				char board[][] = game.getBoard();
				char piece = board[j][i];
				
				switch(piece) {
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
		
		//where chips will fall
		g.setColor(Color.black);
		g.drawRect(rectX, rectY - cellHeight, rectWidth, cellHeight);
		
		//chip shillouettes
		for(int i=0; i<7; i++) {
			int x = rectX + cellWidth / 10 + i * cellWidth;
			int y = rectY + cellHeight / 10 - cellHeight;
			int circleWidth = cellWidth * 4 / 5;
			int circleHeight = cellHeight * 4 / 5;
			
			g.drawOval(x, y, circleWidth, circleHeight);
		}
	}
	

}
