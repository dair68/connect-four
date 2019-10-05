package connectfour;

import connectfour.Game;
import connectfour.GUI;
import connectfour.AI;
import connectfour.DumbAI;
import connectfour.SmartAI;

class Main {

	public static void main(String[] args) {
		// running GUI version of game
		Game game = new Game();
		GUI window = new GUI(game);
		window.start();

		// running console version of game
//		Game game = new Game();
//		game.play();

		// running console version of game vs dumbAI
//		Game game = new Game();
//		AI ai = new DumbAI();
//		game.play(ai);

		// running console version of game vs smartAI
//		Game game = new Game();
//		AI ai = new SmartAI();
//		game.play(ai);

		// running AI vs AI console version of game. P1 yellow, P2 red.
//		final AI player1 = new DumbAI(); 
//		final AI player2 = new SmartAI();
//		
//		//finding smartAI win rate
//		final int numGames = 1000;
//		int smartAIWins = 0;
//		
//		//finding smartAI win rate through monte carlo
//		for(int i=0; i<numGames; i++) {
//			final Game game = new Game();
//			final AI winner = game.runAITest(player1, player2);
//			
//			//checking if smartAI winner
//			if(winner == player2) {
//				smartAIWins++;
//			}
//		}
//		
//		final double winrate = (double) smartAIWins / numGames;
//		System.out.println(player2 + " winrate: " + winrate);
	}
}
