package connectfour;

import connectfour.AI;
import connectfour.Game;
import java.util.ArrayList;
import java.util.Random;

public class SmartAI extends AI {
	//determines best move for the game
	//@param game - instance of connectfour game
	int determineMove(Game game) {
		
		//searching for a winning move
		for(int k=0; k<Game.numCols; k++) {
			//playing move in a game copy
			final Game gameCopy = new Game(game);
			gameCopy.playPiece(k);
			
			//checks if this completes a connect four or creates draw
			if(gameCopy.isOver()) {
				return k;
			}
		}
		
		//searching for moves that block opponent's connect four
		for(int k=0; k<Game.numCols; k++) {
			//playing move in column other than k of game copy
			final Game gameCopy = new Game(game);
			int alternateCol = (k + 1)% Game.numCols;
			
			//finding a different playable column
			while(alternateCol != k) {
				//checking if column playable
				if(gameCopy.isPlayable(alternateCol)) {
					break;
				}
				
				alternateCol = (alternateCol + 1) % Game.numCols;
			}
			
			//no other playable columns
			if(alternateCol == k) {
				return k;
			}
			//alternate playable column found
			else {
				gameCopy.playPiece(alternateCol);
				
				//opponent plays column k
				gameCopy.playPiece(k);
				
				//checking if opponent has a win
				if(gameCopy.isOver()) {
					return k;
				}	
			}
		}
		
		//selecting a random column, preferably one that does not aid opponent
		ArrayList<Integer> viableColumns = new ArrayList<Integer>(); 
		ArrayList<Integer> playableColumns = new ArrayList<Integer>();
		
		//searching for viable columns
		for(int k=0; k<Game.numCols; k++) {
			Game gameCopy = new Game(game);
			
			//checking if column playable in game copy
			if(gameCopy.isPlayable(k)) {
				playableColumns.add(k);
				
				//playing piece in column
				gameCopy.playPiece(k);
				
				//opponent plays piece in same column
				gameCopy.playPiece(k);
				
				//checking if playing column allowed opponent a connect four
				if(!gameCopy.isOver()) {
					//opponent does not have connect four; viable column
					viableColumns.add(k);
				}
			}
		}
		
		//viable columns found
		if(!viableColumns.isEmpty()) {
			final Random rand = new Random();
			final int randomIndex = rand.nextInt(viableColumns.size());
			return viableColumns.get(randomIndex);
		}
		//no viable columns; selecting random playable column
		else if(!playableColumns.isEmpty()) {
			final Random rand = new Random();
			final int randomIndex = rand.nextInt(playableColumns.size());
			return playableColumns.get(randomIndex);
		}
		
		return -1;
	}
}
