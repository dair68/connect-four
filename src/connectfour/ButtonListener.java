package connectfour;

import java.awt.event.*;
import connectfour.Game;

public class ButtonListener implements ActionListener {
	int buttonNum;
	Game game;
	
	ButtonListener(int buttonNum, Game game) {
		this.buttonNum = buttonNum;
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("button " + buttonNum + " clicked");
		game.playPiece(buttonNum);
	}
}
