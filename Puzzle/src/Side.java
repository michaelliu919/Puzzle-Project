public enum Side {
	CLUB_IN(1), CLUB_OUT(-1), SPADE_IN(2), SPADE_OUT(-2), HEART_IN(3), HEART_OUT(-3), DIAMOND_IN(4), DIAMOND_OUT(-4);
	private final int side;

	Side(int side) {
		this.side = side;
	}

	public int getValue() {
		return side;
	}
}
