package connectfour;

import java.util.ArrayList;
import java.util.Random;

public class DumbAI extends AI {
	//randomly determines which column to play
	//returns column from 0-6.
	@Override
	int determineMove(Game game) {
		ArrayList<Integer> openColumns = new ArrayList<Integer>();
		
		//looping through board columns to find vacancies
		for(int k=0; k<Game.numCols; k++) {
			//column has a vacancy
			if(game.isPlayable(k)) {
				openColumns.add(k);
			}
		}
		
		//randomly selecting a playable column
		final Random rand = new Random();
		final int numOpenColumns = openColumns.size();
		final int randomIndex = rand.nextInt(numOpenColumns);
		
		return openColumns.get(randomIndex);
	}
}
