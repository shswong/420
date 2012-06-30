public class Queen implements Comparable<Queen>
{
	private int x, y, h;
	private boolean isQueen;
	private char c;

	public Queen(int x, int y)
	{
		this.x = x;
		this.y = y;
		isQueen = false;
		c = '-';
		h = 0;
	}
	
	public Queen(int x, int y, boolean isQueen)
	{
		this.x = x;
		this.y = y;
		this.isQueen = isQueen;
		c = 'Q';
		h = 0;
	}

	public int getX() { return x; }

	public int getY() { return y; }
	
	public int getH() { return h; }
	
	public void setH(int h) { this.h = h; }

	public boolean isQueen() { return isQueen; }

	public void setQueen(boolean isQueen)
	{
		if (isQueen==true)
		{
			this.isQueen = true;
			c = 'Q';
		}
		else
		{
			this.isQueen = false;
			c = '-';
		}
	}
	
	public int compareTo (Queen that) { return this.getH() - that.getH(); }
	
	public String toString() { return Character.toString(c); }
}