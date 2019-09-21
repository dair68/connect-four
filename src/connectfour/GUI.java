package connectfour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class GUI extends JFrame {
	JLabel message;
	
	//GUI constructor
	GUI() {
		//invoking JFrame constructor to specify window title
		super("Connect Four");
		
		//stops program when user exits frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//setting icon
		String imageFileName = "/red_chip.png";
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(imageFileName));   
		this.setIconImage(icon);
		
		//setting up the content pane.
        setComponents(this.getContentPane());
		
		//sizing frame
		this.pack();
		
		//placing frame in center of screen
		this.setLocationRelativeTo(null);
	}
	
	//takes care of all components in the GUI
	//@param pane - the GUI's content pane
	private void setComponents(Container pane) {
		//creating section for status messages
		message = new JLabel("Game started", SwingConstants.CENTER);
		int messageFontSize = 30;
		Font messageFont = new Font(message.getName(), Font.PLAIN, messageFontSize);
		message.setFont(messageFont);
		
		//creating grid where buttons and grid will fall
		JPanel gridPane = new JPanel();
		gridPane.setLayout(new GridLayout(7, 7));
		
		//adding buttons to grid
		for(int i=0; i<7; i++) {
			JButton button = new JButton("\u2193");
			int buttonFontSize = 25;
			Font buttonFont = new Font(message.getName(), Font.PLAIN, buttonFontSize);
			button.setFont(buttonFont);
			Dimension buttonDim = new Dimension(75, 20);
			button.setPreferredSize(buttonDim);
			gridPane.add(button);
		}
		
		//adding spaces to grid
		for(int i=0; i<42; i++) {
			JLabel gridSpace = new JLabel();
			gridSpace.setBorder(BorderFactory.createLineBorder(Color.black));
			Dimension gridDim = new Dimension(75, 75);
			gridSpace.setPreferredSize(gridDim);
			gridPane.add(gridSpace);
		}
		
		//placing everything together
		pane.add(message, BorderLayout.PAGE_START);
		pane.add(gridPane, BorderLayout.CENTER);
	}
}
