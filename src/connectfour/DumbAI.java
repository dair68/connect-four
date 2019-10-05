package connectfour;

import java.util.ArrayList;
import java.util.Random;

public class DumbAI extends AI {
	// randomly determines which column to play
	// @param game - instance of connectfour game
	// returns column from 0-6.
	@Override
	int determineMove(Game game) {
		final ArrayList<Integer> openColumns = new ArrayList<Integer>();

		// looping through board columns to find vacancies
		for (int k = 0; k < Game.numCols; k++) {
			// column has a vacancy
			if (game.isPlayable(k)) {
				openColumns.add(k);
			}
		}

		return randomElement(openColumns);
	}

	// selects a random element from an array
	// @param list - an arraylist
	// returns a random element. returns null if arraylist empty
	public static <T> T randomElement(ArrayList<T> list) {
		final Random rand = new Random();
		final int randomIndex = rand.nextInt(list.size());
		return list.get(randomIndex);
	}

	// returns "DumbAI" string
	@Override
	public String toString() {
		return "DumbAI";
	}
}
