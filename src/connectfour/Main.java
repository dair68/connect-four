package connectfour;

import connectfour.Game;
import connectfour.GUI;

class Main {
	
	public static void main(String[] args) {
		Game game = new Game();
		GUI window = new GUI(game);
		window.start();
	}
}
