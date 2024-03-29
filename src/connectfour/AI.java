package connectfour;

import connectfour.Game;

public abstract class AI {
	// determines which column to play based on game status
	// @param game - connect four game in question
	// returns column from 0-6.
	abstract int determineMove(Game game);
}
