package connectfour;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class GUI extends JFrame {
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
		
		//adding layouts
		//Set up the content pane.
		
        addComponents(this.getContentPane());
		
		//sizing frame
		this.pack();
		
		//placing frame in center of screen
		this.setLocationRelativeTo(null);
	}
	
	//adds components to the GUI
	//@param pane - the GUI's content pane
	private void addComponents(Container pane) {
		Label testlabel = new Label("Game goes here");
		pane.add(testlabel, BorderLayout.CENTER);
	}
}
