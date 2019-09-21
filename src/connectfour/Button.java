package connectfour;

import java.awt.*;
import connectfour.ButtonListener;
import connectfour.Game;
import javax.swing.JButton;

class Button extends JButton {
	private int buttonNum;
	
	//creates a new GUI button
	//@param buttonNum - column the button corresponds to, from 0-6
	Button(int buttonNum, Game game) {
		//gives button down arrow symbol
		super("\u2193");
		this.buttonNum = buttonNum;
		this.style();
		this.addActionListener(new ButtonListener(buttonNum, game));
	}
	
	//sets style attributes of button
	private void style() {
		//styling font
		int buttonFontSize = 25;
		Font buttonFont = new Font("Consalas", Font.PLAIN, buttonFontSize);
		this.setFont(buttonFont);
		
		//setting up size
		Dimension buttonDim = new Dimension(75, 20);
		this.setPreferredSize(buttonDim);
	}
}
