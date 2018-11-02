import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

//the graphics component of the puzzle, paints the board has method to manipulate the puzzle
public class BoardComponent extends JComponent{
	Puzzle board;
	/**
	 * @return the board
	 */
	public Puzzle getBoard() {
		return board;
	}
	
	private List<PieceComponent> pieceComponents = new ArrayList<PieceComponent>();
	/**
	 * @return the pieceComponents
	 */
	public List<PieceComponent> getPieceComponents() {
		return pieceComponents;
	}
	//data
	protected final int side = 70;
	protected int boardSize = 3*side;
	private List<Piece> pieces = new ArrayList<Piece>();

	//constructor initializes the piece components and sets up the board and puzzle
	public BoardComponent() {
		board = new Puzzle(3,3, pieces);
		pieceComponents.add(new PieceComponent(this, Side.CLUB_OUT, Side.HEART_OUT, Side.DIAMOND_IN, Side.CLUB_IN, "res/piece_1.png"));

		pieceComponents.add(new PieceComponent(this, Side.SPADE_OUT, Side.DIAMOND_OUT, Side.SPADE_IN, Side.HEART_IN, "res/piece_2.png"));

		pieceComponents.add(new PieceComponent(this,Side.HEART_OUT, Side.SPADE_OUT, Side.SPADE_IN, Side.CLUB_IN, "res/piece_3.png"));

		pieceComponents.add(new PieceComponent(this,Side.HEART_OUT, Side.DIAMOND_OUT, Side.CLUB_IN, Side.CLUB_IN, "res/piece_4.png"));

		pieceComponents.add(new PieceComponent(this,Side.SPADE_OUT, Side.SPADE_OUT, Side.HEART_IN, Side.CLUB_IN, "res/piece_5.png"));

		pieceComponents.add(new PieceComponent(this,Side.HEART_OUT, Side.DIAMOND_OUT, Side.DIAMOND_IN, Side.HEART_IN,"res/piece_6.png"));

		pieceComponents.add(new PieceComponent(this,Side.SPADE_OUT, Side.DIAMOND_OUT, Side.HEART_IN, Side.DIAMOND_IN, "res/piece_7.png"));

		pieceComponents.add(new PieceComponent(this,Side.CLUB_OUT, Side.HEART_OUT, Side.SPADE_IN, Side.HEART_IN, "res/piece_8.png"));

		pieceComponents.add(new PieceComponent(this, Side.DIAMOND_OUT, Side.CLUB_OUT, Side.CLUB_IN, Side.DIAMOND_IN,"res/piece_9.png"));
		
		for (int i = 0; i < pieceComponents.size(); i++) {
			pieces.add(pieceComponents.get(i).getPiece());
		}
	}
	
	
	//paints the board
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getCols(); j++) {
				g2.drawRect(j*side, i*side, side, side);
			}
		}
	}
	
	//solves the puzzle rendering all pieces into their locations on the board
	public void solve() {
		board.solve();
		for(int row =0; row< board.getRows(); row++) {
			for(int col = 0; col < board.getCols(); col++) {
				for(int i = 0; i < pieceComponents.size();) {
					PieceComponent piece = pieceComponents.get(i);
					if(board.getPiece(row, col) == piece.getPiece()) {
						piece.renderPiece(row,col);
					}
					i++;
				}
			}
		}
	}
	
	//returns col
	public int getCols() {
		return board.getCols();
	}
	
	//returns row
	public int getRows() {
		return board.getRows();
	}


	// Determines whether the puzzle has been completed
	public boolean isSolved() {
		return board.isSolved();
	}

	// Determines whether a piece will fit at the specified
	// location
	public boolean doesFit(Piece piece, int row, int col) {
		return board.doesFit(piece, row, col);
	}

	// returns the Piece at the specified location
	public Piece getPiece(int row, int col) {
		return board.getPiece(row, col);
	}

	//sets the piece at a specific location
	public Piece setPiece(Piece piece, int row, int col) {
		return board.setPiece(piece, row, col);
	}

	//remove a piece at a specific location
	public Piece removePiece(int row, int col) {
		return board.removePiece(row, col);
	}

	//resets the puzzle moving all pieces backing to original location
	public void reset() {
		board.reset();
		for (int row = 0; row < board.getRows(); row++) {
			for (int col = 0; col < board.getCols(); col++) {
				pieceComponents.get(row*3+col).goHome();
				pieceComponents.get(row*3+col).setOrientation0();
			}
		}
	}

	//returns the list of unused pieces
	public List<Piece> getUnused() {
		return pieces;
	}
	
	//prints out the board
	public String toString() {
		String str = "";
		str+= board.toString();
		for(int i = 0; i < 9; i++) {
			str += "\n" + pieceComponents.get(i).getOriginalX() + ", " + pieceComponents.get(i).getOriginalY();
		}
		return str;
	}
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame("Frame");
//		frame.setSize(400, 400);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		BoardComponent s = new BoardComponent();
//		frame.add(s);
//		frame.setVisible(true);
//	}

}