package connectfour;

public class Space {
	public int row;
	public int col;

	// constructor
	Space() {
		row = 0;
		col = 0;
	}

	// constructor with parameters
	Space(int row, int col) {
		this.row = row;
		this.col = col;
	}

	// returns string of the form (row, col)
	@Override
	public String toString() {
		return "(" + row + ", " + col + ")";
	}

	// checks if two spaces are the same
	@Override
	public boolean equals(Object object) {
		// checking if other is a reference to a Space
		if (object instanceof Space) {
			Space space = (Space) object;
			return (row == space.row && col == space.col);
		}

		return false;
	}
}
