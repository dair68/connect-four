package connectfour;

public class Game {
	
	private GUI gameWindow;

	//initializes the game
	public Game() {
		gameWindow = new GUI();
	}
	
	//starts the game
	public void start() {
		System.out.println("starting game");
		gameWindow.setVisible(true);
	}
}
