
public enum Direction {
	TOP(0),
	RIGHT(1),
	BOTTOM(2),
	LEFT(3);
	
	private final int direction;
	
	Direction(int direction) {
		this.direction = direction;
	}
	
	public int getValue() {
		return direction;
	}

}
