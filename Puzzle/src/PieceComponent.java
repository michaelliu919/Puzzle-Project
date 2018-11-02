import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * this class renders an image of the piece and it is connected to the nuts and bolts part of the project with methods
 * manipulating the behind the scenes mechanics, knows the location of the board and whether itself fits to a spot
 * @author michaelliu
 *
 */
public class PieceComponent extends JComponent {
	//data field
	private Piece piece;
	private BufferedImage myImage;
	private volatile int mouseX = 0;
	private volatile int mouseY = 0;
	private volatile int componentX = 0;
	private volatile int componentY = 0;
	private int originalX;
	private int originalY;
	boolean imageClicked = false;
	private BoardComponent board;
	// boolean whether piece is on board
	private boolean onBoard = false;
	private int row = -1, col = -1;

	/**
	 * returns whether the image is clicked on
	 * @return the imageClicked
	 */
	public boolean isImageClicked() {
		return imageClicked;
	}

	/**
	 * sets the boolean image clicked to whether image is being clicked given the coordinates
	 * @param mouseX
	 * @param mouseY
	 */
	public void imageClick(int mouseX, int mouseY) {
		if (mouseX >= getX()+24 && mouseX <= getX() + 94 && mouseY >= getY()+70 && mouseY <= getY() + 144) {
			imageClicked = true;
		} else {
			imageClicked = false;
		}
	}
	

	// returns the origin of the board
	public Point getOrigin() {
		return new Point(board.getX(), board.getY());
	}

	// returns boolean whether a coordinate is on board is on board
	public boolean onBoard(int mouseX, int mouseY) {
		int boardX = getOrigin().x, boardY = getOrigin().y;
		return mouseX >= boardX && mouseX <= boardX + board.boardSize && mouseY >= boardY
				&& mouseY <= boardY + board.boardSize+50;
	}

	//converts the matrix coordinates into x,y coordinates on the screen
	public Point matrixToCoordinates(int row, int col) {
		Point a;
		if (row < board.getRows() && col < board.getCols() && row >= 0 && col >= 0) {
			a = new Point(getOrigin().x + col * board.side - 24, getOrigin().y + row * board.side - 24);
		} else {
			a = new Point(getOrigin().x + 2 * board.side - 24, getOrigin().y + 2 * board.side - 24);
		}
		return a;
	}

//	 only works when the supplied coordinates are on board, returns a point in row, col fashion
//   converts the given coordinates into matrix
	public Point coordinateToMatrix(int mouseX, int mouseY) {
		return new Point((mouseY - getOrigin().y-50) / board.side, (mouseX - getOrigin().x) / board.side);
	}
	
//	places a piece on the board
	public void placePiece(int row, int col) {
		if (board.doesFit(getPiece(), row, col)) {
			setLocation(matrixToCoordinates(row, col));
			board.setPiece(getPiece(), row, col);
			onBoard = true;
			this.row = row;
			this.col = col;
			if(board.isSolved() == true){
				JFrame frame = new JFrame();
				frame.setLocation(300,300);
				frame.setResizable(false);
				frame.setSize(500,150);
				JLabel congrats = new JLabel("Congratulations!!");
				congrats.setFont(new Font("Impact", Font.BOLD, 50));
				JPanel panel = new JPanel();
				panel.add(congrats);
				frame.add(panel);
				frame.setVisible(true);
			}
		}
		else {
			this.reset();
		}
	}

	//renders the image into the given matrix location
	public void renderPiece(int row, int col) {
		setLocation(matrixToCoordinates(row, col));
		onBoard = true;
		this.row = row;
		this.col = col;
	}

	//initializes the original location of the piece
	public void init() {
		originalX = this.getX();
		originalY = this.getY();
	}

	/**
	 * @return the originalX
	 */
	public int getOriginalX() {
		return originalX;
	}

	/**
	 * @return the originalY
	 */
	public int getOriginalY() {
		return originalY;
	}
	
	//constructor, constructs the piece given the following params
	public PieceComponent(BoardComponent board, Side top, Side right, Side bottom, Side left, String path) {
		this.board = board;
		piece = new Piece(top, right, bottom, left);
		try {
			myImage = ImageIO.read((new FileInputStream(path)));
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getXOnScreen();
				mouseY = e.getYOnScreen();
				componentX = getX();
				componentY = getY();
				imageClick(mouseX, mouseY);
				if (onBoard) {
					board.removePiece(row, col);
					onBoard = false;
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (imageClicked&&!onBoard) {
						piece.rotateClockwise();
						repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(imageClicked) {
				if (onBoard(e.getXOnScreen(), e.getYOnScreen())) {
					Point m = coordinateToMatrix(e.getXOnScreen(), e.getYOnScreen());
					placePiece(m.x, m.y);
				} else {
					goHome();
				}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (imageClicked) {
					int deltaX = e.getXOnScreen() - mouseX;
					int deltaY = e.getYOnScreen() - mouseY;
					setLocation(componentX + deltaX, componentY + deltaY);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}
		});
	}

	//resets the piece to original location
	public void reset() {
		if(onBoard) {
			board.removePiece(row, col);
		}
		goHome();
	}
	
	// returns the chosen side
	public Side getSide(Direction direction) {
		return piece.getSide(direction);
	}
	
	//returns the piece
	public Piece getPiece() {
		return piece;
	}

	//returns the image height
	public int getHeight() {
		return myImage.getHeight();
	}

	//returns the image width
	public int getWidth() {
		return myImage.getWidth();
	}
	
	//paints the image
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (myImage != null) {
			AffineTransform trans = new AffineTransform();
			trans.translate(getWidth() / 2, getHeight() / 2);
			trans.rotate(piece.getOrientation() * Math.PI / 2);
			trans.translate(-myImage.getWidth() / 2, -myImage.getHeight() / 2);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(myImage, trans, null);
		}
	}
	
	//returns the image to original location
	public void goHome() {
		setLocation(originalX, originalY);
	}
	
	//sets the orientation of the piece to original state
	public void setOrientation0() {
		int count = piece.getOrientation();
		while(count > 0) {
			piece.rotateCounterClockwise();
			count--;
		}
	}

	//rotates the piece clockwise
	public void rotateClockwise() {
		piece.rotateClockwise();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Frame");
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// JPanel puzzlePieces = new JPanel(new GridLayout(1, 9));
		// JPanel puzzle = new JPanel();
		PieceComponent piece1 = new PieceComponent(new BoardComponent(), Side.CLUB_IN, Side.CLUB_OUT, Side.HEART_OUT,
				Side.DIAMOND_IN, "res/piece_1.png");
		// PieceComponent piece2 = new PieceComponent(Side.SPADE_OUT,
		// Side.DIAMOND_OUT, Side.SPADE_IN, Side.HEART_IN, "res/piece_2.png");
		// PieceComponent piece3 = new PieceComponent(Side.HEART_OUT,
		// Side.SPADE_OUT, Side.SPADE_IN, Side.CLUB_IN, "res/piece_3.png");
		// PieceComponent piece4 = new PieceComponent(Side.HEART_OUT,
		// Side.DIAMOND_OUT, Side.CLUB_IN, Side.CLUB_IN, "res/piece_4.png");
		// PieceComponent piece5 = new PieceComponent(Side.SPADE_OUT,
		// Side.SPADE_OUT, Side.HEART_IN, Side.CLUB_IN, "res/piece_5.png");
		// PieceComponent piece6 = new PieceComponent(Side.HEART_OUT,
		// Side.DIAMOND_OUT, Side.DIAMOND_IN, Side.HEART_IN, "res/piece_6.png");
		// PieceComponent piece7 = new PieceComponent(Side.SPADE_OUT,
		// Side.DIAMOND_OUT, Side.HEART_IN, Side.DIAMOND_IN, "res/piece_7.png");
		// PieceComponent piece8 = new PieceComponent(Side.CLUB_OUT,
		// Side.HEART_OUT, Side.SPADE_IN, Side.HEART_IN, "res/piece_8.png");
		// PieceComponent piece9 = new PieceComponent(Side.DIAMOND_OUT,
		// Side.CLUB_OUT, Side.CLUB_IN, Side.DIAMOND_IN, "res/piece_9.png");
		frame.add(piece1);
		// puzzlePieces.add(piece2);
		// puzzlePieces.add(piece3);
		// puzzlePieces.add(piece4);
		// puzzlePieces.add(piece5);
		// puzzlePieces.add(piece6);
		// puzzlePieces.add(piece7);
		// puzzlePieces.add(piece8);
		// puzzlePieces.add(piece9);
		// frame.add(puzzlePieces);
		// frame.add(board);
		frame.setVisible(true);
	}

	/**
	 * @return the board
	 */
	public BoardComponent getBoard() {
		return board;
	}

}
