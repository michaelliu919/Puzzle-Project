import java.util.List;
/*
 * 
 * 
 * The Puzzle class has been created so the user can define a Puzzle using
 * 	a number of rows, a number of cols, and a list of pieces
 * 	a size if it is a square Puzzle, and a list of Pieces
 * and allows a user to manipulate the Puzzle in different ways that a Puzzle 
 * should be able to be manipulated
 * 
 * For 5/7/16
 */
public class Puzzle {
	
	/*
	 * private data
	 */
	private Board board;
	private int rows;
	private int cols;
	private List<Piece> pieces;
	
	/*
	 * Constructs a Puzzle object using a given number of rows, col, and a List of Pieces
	 * 
	 * Parameters:
	 * 	int rows = the number of rows a Puzzle has
	 * 	int cols = the number of cols a Puzzle has
	 * 	List<Pieces> pieces = the pieces the Puzzle has
	 *
	 */
	public Puzzle(int rows, int cols, List<Piece> pieces) {
		this.pieces = pieces;
		board = new Board(rows,cols);
		this.rows = rows;
		this.cols = cols;
	}
	/*
	 * Constructs a Puzzle object using a given number size, and a List of Pieces
	 * 
	 * Parameters:
	 * 	int size = the number of rows/cols a Puzzle has
	 * 	List<Pieces> pieces = the pieces the Puzzle has
	 *
	 */
	
	public Puzzle(int size, List<Piece> pieces) {
		this(size, size, pieces);
	}
	
	/*
	 * checks if a Puzzle is solved
	 * 
	 * Parameters:
	 * 	None
	 * 
	 * Returns: 
	 * 	A boolean checking whether or not the puzzle is solved
	 */
	public boolean isSolved() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (!board.isOccupied(i, j)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
 	* Checks if a Piece does fit at a given location
 	* 
 	* Parameters:
 	* 	Piece piece = the piece you are checking about
 	* 	int row = the row where you want to check
 	* 	int col = the col where you want to check
 	* 
 	* Returns:
 	* 	a boolean telling whether or not the Piece fits
 	*/
	public boolean doesFit(Piece piece, int row, int col){
		if(board.isOccupied(row, col)){
			return false;
		}
		if(board.isValid(row, col - 1)){
			if((board.isOccupied(row, col - 1)) &&(piece.getSide(Direction.LEFT).getValue() + 
					board.getPiece(row, col - 1).getSide(Direction.RIGHT).getValue() != 0)) return false;
		}
		if(board.isValid(row, col + 1)){
			if((board.isOccupied(row, col + 1)) &&(piece.getSide(Direction.RIGHT).getValue() + 
				board.getPiece(row, col + 1).getSide(Direction.LEFT).getValue() != 0)) return false;
		}
		if(board.isValid(row - 1, col)){
			if((board.isOccupied(row - 1, col)) &&(piece.getSide(Direction.TOP).getValue() + 
				board.getPiece(row - 1, col).getSide(Direction.BOTTOM).getValue() != 0)) return false;
		}
		if(board.isValid(row + 1, col)){

			if((board.isOccupied(row + 1, col)) &&(piece.getSide(Direction.BOTTOM).getValue() + 
				board.getPiece(row + 1, col).getSide(Direction.TOP).getValue() != 0)) return false;
		}
		return true;  
	}
	
	/*
	 * solves the Puzzle using the Pieces in the pieces list
	 * 
	 * parameters:
	 * 	none
	 * 
	 * Returns:
	 * 	none
	 */
	public void solve() {
		reset();
		solve(0, 0);
	}
	
	/*
	 * helps solve() solve the puzzle
	 * 
	 * Parameters:
	 * 	int row = the row where the solver is working
	 * 	int col = the col where the solver is working
	 * 
	 * Returns:
	 * 	A boolean about whether or not to go on
	 */
	private boolean solve(int row, int col) {
		if (isSolved())
			return true;
		for (int i = 0; i < pieces.size(); i++) {
			for (int j = 0; j < 4; j++) {
				if (doesFit(pieces.get(i) ,row, col)) {
					setPiece(pieces.get(i), row, col);
					if (board.isValid(row, col + 1)) {
						if (!solve(row, col + 1)) {
							pieces.add(i, board.removePiece(row, col));
							pieces.get(i).rotateClockwise();
						} else
							return true;
					} 
					else {
						if (!solve(row + 1, 0)) {
							pieces.add(i, board.removePiece(row, col));
							pieces.get(i).rotateClockwise();
						} else
							return true;
					}
				} else
					pieces.get(i).rotateClockwise();
			}
		}
		return false;
	}
	
	/*
	 * Returns the piece at a given location
	 * 
	 * Parameters:
	 * 	int row = the row from where you want the piece
	 * 	int col = the col from where you want the piece
	 *
	 * Returns: 
	 * 	the Piece at that location
	 */
	public Piece getPiece(int row, int col) {
		return board.getPiece(row, col);
	}
	

	/*
	 * Sets a given piece at a given location
	 * 
	 * Parameters:
	 * 	Piece piece = the piece being set
	 * 	int row = the row where the piece may be set
	 * 	int col = the col where the piece may be set
	 * 
	 * Returns:
	 * 	The piece that used to be at that location
	 * 
	 */
	public Piece setPiece(Piece piece, int row, int col) {
		if (board.isOccupied(row, col)) {
			//System.out.println("piece not set");
			return null;
		}
		if(doesFit(piece, row, col)) {
		//	System.out.println("piece set");
			pieces.remove(piece);
			return board.setPiece(piece, row, col);
		} else {
		//	System.out.println("piece not set");
			return null;
		}
	}
		
	/*
	 * removes a Piece at a given location
	 * 
	 * Parameters:
	 * 	int row = the row where you want to remove the piece
	 * 	int col = the col where you want to remove the piece
	 *
	 * Returns:
	 * 	the Piece that was previously there
	 */
	public Piece removePiece(int row, int col) {
		Piece p = board.removePiece(row,col);
		if(p!= null) pieces.add(p);
		return p;
	}
	/*
	 * gets the number of rows a Board has
	 * 
	 * Parameters:	
	 * 	None
	 * 
	 * Returns:
	 * 	the number of rows as an int
	 */	
	public int getRows() {
		return rows;
	}

	/*
	 * gets the number of cols a Board has
	 * 
	 * Parameters:	
	 * 	None
	 * 
	 * Returns:
	 * 	the number of cols as an int
	 */
	public int getCols() {
		return cols;
	}

	/*
	 * Clears the board and puts all pieces back into the piece list
	 * Parameters: 
	 * 	None
	 * 
	 * Returns:
	 * 	Nothing 
	 */
	public void reset() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				removePiece(i,j);
			}
		}
	}
	
	/*
	 * Gets the piece that are not currently on the board
	 * 
	 * Parameters:
	 * 	None
	 * 
	 * Returns:
	 * 	A List of the Piece that are not on the Board
	 */
	public List<Piece> getUnused() {
		return pieces;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * toString for a Puzzle object
	 */
	public String toString() {
		String str = "";
		str += "Pieces\n";
		str += pieces;
		str += "\nBoard\n";
		str += board.toString();
		return str;
	}

}
