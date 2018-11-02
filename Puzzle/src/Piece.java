public class Piece {

	private Side[] sides;
	private int orientation = 0;
	
	
	/*
	 * A Ctor that 
	 */
	Piece(Side top, Side right, Side bottom, Side left) {
		sides = new Side[4];
		sides[0] = top;
		sides[1] = right;
		sides[2] = bottom;
		sides[3] = left;
	}
	
	/*
	 * changes the orientation or the piece to rotate it clockwise
	 */
	public void rotateClockwise() {
		orientation = (orientation + 1) % 4;
	}
	
	/*
	 * changes the orientation or the piece to rotate it counter clockwise
	 */
	public void rotateCounterClockwise() {
		orientation = (orientation + 3) % 4; // plus 5 is like -1, but avoids problem with %
	}
	
	public String toString() {
		String str = "";
		int sideIndex = 0;
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if ((i*3+j)%2 == 0) {
				} else {
					int num = sides[(sideIndex+(4-orientation))%4].getValue();
					if (num > -1) {
						str += " " + num;
					} else {
						str += num;
					}
					sideIndex++;
				}
			}
		// 	str += "\n";
			
		}
		str += "|";
		return str;
	}
	
	
	public Side getSide(Direction direction) {
		int dir = direction.getValue();
		return sides[(dir + (4-orientation))%4];
	}
	
	public int getOrientation() {
		return orientation;
	}
	
}
