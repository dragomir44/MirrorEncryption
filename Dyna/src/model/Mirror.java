package model;

public class Mirror {
	public final int row;
	public final int col;
	public final String direction;

	public Mirror(int r, int c, String direction) {
		this.row = r;
		this.col = c;
		this.direction = direction;
	}

	public String toString() {
		String result = direction.toString();
		return result;
	}
	
}
