package connectfour;

public class Space {
	public int row;
	public int col;

	public Space() {
		row = 0;
		col = 0;
	}

	// constructor with parameters
	//@param row - row where this space occurs
	//@param col - col where this space occurs
	public Space(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Space(Space space) {
		this.row = space.row;
		this.col = space.col;
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
