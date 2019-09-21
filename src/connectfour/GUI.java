package connectfour;

import javax.swing.*;
import java.awt.*;
import connectfour.Game;
import connectfour.Button;

class GUI extends JFrame {
	
	private JLabel message;
	private JLabel[][] spaces; 
		
	//GUI constructor
	//@param game - the connect four game the window will show
	public GUI(Game game) {
		//invoking JFrame constructor to specify window title
		super("Connect Four");
		
		spaces = new JLabel[Game.numRows][Game.numCols];
		
		//stops program when user exits frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//setting up the content pane.
        setComponents(this.getContentPane(), game);
		
		//sizing frame
		this.pack();
		
		//placing frame in center of screen
		this.setLocationRelativeTo(null);
	}
	
	//takes care of all components in the GUI
	//@param pane - the GUI's content pane
	//@param game - the connect four game the window will show
	private void setComponents(Container pane, Game game) {
		//creating section for status messages
		message = new JLabel("game started", SwingConstants.CENTER);
		int messageFontSize = 30;
		Font messageFont = new Font(message.getName(), Font.PLAIN, messageFontSize);
		message.setFont(messageFont);
		
		//creating grid where buttons and grid will fall
		JPanel gridPane = new JPanel();
		gridPane.setLayout(new GridLayout(7, 7));
		
		//adding buttons to grid
		for(int i=0; i<Game.numCols; i++) {
			Button btn = new Button(i, game);
			gridPane.add(btn);
		}
		
		
		//adding spaces to grid
		for(int i=0; i<Game.numRows; i++) {
			for(int j=0; j<Game.numCols; j++) {
				JLabel gridSpace = new JLabel();
				gridSpace.setBorder(BorderFactory.createLineBorder(Color.black));
				Dimension gridDim = new Dimension(75, 75);
				gridSpace.setPreferredSize(gridDim);
				gridPane.add(gridSpace);
				
				spaces[i][j] = gridSpace;
			}
		}
		
		//placing everything together
		pane.add(message, BorderLayout.PAGE_START);
		pane.add(gridPane, BorderLayout.CENTER);
	}
	
	
	//updates GUI to reflect game status
	//@param game - the connect four game the window will show
	void update(Game game) {
		String status = game.isRedTurn() ? "Red player's turn" : "Yellow player's turn";
		Color textColor = game.isRedTurn() ? Color.red : Color.yellow;
		message.setText(status);
		message.setForeground(textColor);
		
		//placing pieces in board
		for(int i=0; i<Game.numRows; i++) {
			for(int j=0; j<Game.numCols; j++) {
				char[][] board = game.getBoard();
				final char piece = board[i][j];
				
				JLabel space = spaces[i][j];
				space.setOpaque(true);
				
				//coloring cell
				switch(piece) {
				case ' ':
					space.setBackground(Color.blue);
					break;
				case 'x':
					space.setBackground(Color.red);
					break;
				case 'o':
					space.setBackground(Color.yellow);
					break;
				}		
			}
		}
	}
}
