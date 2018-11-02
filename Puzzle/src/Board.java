/*
 * 
 * 
 * The Board class has been created so the user can define a puzzle board using
 * 	a number of rows and a number of cols
 * 	a size if it is a square Board
 * and allows a user to manipulate the Board in different ways
 * 
 * For 5/7/16
 */
public class Board {
	
	/*
	 * private data
	 */
	private Piece[][] pieces;
	private int rows;
	private int cols;
	
	/*
	 * Creates a board with a given number of rows and columns
	 * 
	 * Parameters:
	 * 	int numRows = the number of rows
	 * 	int numCols = the number of columns
	 */
	public Board(int numRows, int numCols){
		rows = numRows;
		cols = numCols;
		pieces = new Piece[rows][cols];
	}
	
	/*
	 * Creates a board with a given number of size
	 * 
	 * Parameters:
	 * 	int size = the number of rows/columns the board has
	 */
	public Board(int size){
		new Board(size, size);
	}
	
	
	
	/*
	 * returns the piece at a specified location
	 * 
	 * Parameters:
	 * 	int row = the row number of the piece you want
	 * 	int col = the col number of the piece you want
	 * 
	 * Returns:
	 * 	the Piece at the specified location
	 */
	public Piece getPiece(int row, int col){
		if(isValid(row, col))
			return pieces[row][col];
		return null;
			
	}
	
	/*
	 * A method to check whether or not there is a piece at a given location
	 * 
	 *  Parameters:
	 * 	int row = the row number you are checking
	 * 	int col = the col number you are checking
	 * 
	 * Returns:
	 * 	a boolean telling whether or not there is a Piece at the given location
	 */
	public boolean isOccupied(int row, int col){
		if(isValid(row, col)){
			if(pieces[row][col] == null) {
				return false;
			} else {
				return true;
			}	
		}
		else{
			return false;
		}
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
	public int getRows(){
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
	public int getCols(){
		return cols;
	}
	
	/*
	 * clears the board
	 * 
	 * Parameters:
	 * 	none
	 * 
	 * Returns:
	 * 	Nothing
	 */
	public void clear(){
		pieces = new Piece[pieces.length][pieces[0].length];
	}
	
	/*
	 * sets a piece at a given location, and returns the piece that used to be there
	 * 
	 * parameters:
	 * 	Piece piece = the Piece you want to set at the location
	 * 	int row = the row where you want to set the piece
	 * 	int col = the col where you want to set the piece
	 * 
	 * Returns:
	 * 	the Piece that used to be at that location
	 */
	public Piece setPiece(Piece piece, int row, int col){
		if(isValid(row, col)){
			Piece old = pieces[row][col];
			pieces[row][col] = piece;
			return old;
		}
		else{
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
	public Piece removePiece(int row, int col){
		if(isValid(row, col)){
			Piece a = pieces[row][col];
			setPiece(null, row, col);
			return a;
		}
		
		return null;
	}
	
	
	/*
	 * Checks if a location on the board is valid
	 *  
	 * Parameters:
	 * 	int row = the row where you want to check
	 * 	int col = the col where you want to check
	 * 
	 * Returns:
	 * 	a boolean of whether or not the location is valid
	 * 
	 */
	public boolean isValid(int row, int col){
		if((row < rows && row > -1) && (col < cols && col > -1)){
			return true;
		}else{
			return false;
				
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * toString for a board
	 */
	public String toString(){
		String str = "";
		for(int row = 0; row < pieces.length; row++){
			str += "|";
			for(int cols = 0; cols < pieces[row].length; cols++){
				if(pieces[row][cols] == null){
					str+= "  null  |";
				}
				else{
					str += (pieces[row][cols]);
				}
			}
			str+= "\n";
		}
		return str;
	}
	
	

}
