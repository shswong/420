import java.util.*;

public class Board implements Comparable<Board>
{
	private Queen[][] current;
	private int n, h;
	private ArrayList<Queen> queens;

	public Board(Queen[][] current, ArrayList<Queen> queens)
	{
		this.current = current;
		this.queens = queens;
		n = current.length;
		getHeuristic();
	}
	
	private void getHeuristic()
	{
		for (Queen q : queens)
		{
			int row = q.getX();
			int column = q.getY();
			
			// check right rows
			for (int y = column; y < n; y++)
			{
				Queen temp = current[row][y];
				if (y != column && temp.isQueen()) { h++; }
			}
			
			// check diagonals down right of queen
			for (int x = row, y = column; x < n && y < n; x++, y++)
			{
				Queen temp = current[x][y];
				if (x != row && y != column && temp.isQueen()) { h++; }
			}
			
			// check diagonals up right of queen
			for (int x = row, y = column; x < n && y > -1; x++, y--)
			{
				Queen temp = current[x][y];
				if (x != row && y != column && temp.isQueen()) { h++; }
			}
		}
	}
	
	public int getH() { return h; }
	
	public Queen[][] getCurrent() { return current; }
	
	public ArrayList<Queen> getQueens() { return queens; }
	
	public Board generateSuccessors(Board board)
	{		
		Queen[][] currentCopy = board.getCurrent();
		ArrayList<Queen> queensCopy = board.getQueens();
		
		for (int y = 0; y < n; y++)
		{
			PriorityQueue<Queen> queens = new PriorityQueue<Queen>();
			Queen q = queensCopy.get(y);
			
			int originalX = q.getX();
			int originalY = q.getY();
			
			for (int x = 0; x < n; x++)
			{
				if (x != originalX)
				{
					queens.add(getConflicts(currentCopy, currentCopy[x][y]));
				}
			}
			
			Queen q1 = queens.poll();
			swap(currentCopy, queensCopy, originalX, originalY, q1.getX(), q1.getY());
		}
		return new Board(currentCopy, queensCopy);
	}
	
	public Queen getConflicts(Queen[][] current, Queen queen)
	{
		int row = queen.getX();
		int column = queen.getY();
		int conflicts = 0;
		
		// check right rows
		for (int y = column; y < n; y++)
		{
			Queen temp = current[row][y];
			if (y != column && temp.isQueen()) { conflicts++; }
		}
		
		// check left rows
		for (int y = column; y > -1; y--)
		{
			Queen temp = current[row][y];
			if (y != column && temp.isQueen()) { conflicts++; }
		}
		
		// check diagonals down right of queen
		for (int x = row, y = column; x < n && y < n; x++, y++)
		{
			Queen temp = current[x][y];
			if (x != row && y != column && temp.isQueen()) { conflicts++; }
		}
		
		// check diagonals up right of queen
		for (int x = row, y = column; x < n && y > -1; x++, y--)
		{
			Queen temp = current[x][y];
			if (x != row && y != column && temp.isQueen()) { conflicts++; }
		}
		
		// check diagonals up left of queen
		for (int x = row, y = column; x > -1 && y > -1; x--, y--)
		{
			Queen temp = current[x][y];
			if (x != row && y != column && temp.isQueen()) { conflicts++; }
		}
		
		//check diagonals down left of queen
		for (int x = row, y = column; x > -1 && y < n; x--, y++)
		{
			Queen temp = current[x][y];
			if (x != row && y != column && temp.isQueen()) { conflicts++; }
		}
		
		queen.setH(conflicts);
		return queen;
	}
	
	public void swap(Queen[][] q, ArrayList<Queen> qc, int x1, int y1, int x2, int y2)
	{
		// x1,y1 real queen; x2,y2 fake queen before
		// set x2,y2 to real queen, and x1,y1 to fake queen
		q[x1][y1].setQueen(false);
		q[x2][y2].setQueen(true);
		qc.remove(y1);
		qc.add(y1, q[x2][y2]);
	}
	
	public int compareTo (Board that) { return this.getH() - that.getH(); }
	
	public void printBoard()
	{
		System.out.println("\t0  1  2  3  4  5  6  7" + 
								 "  8  9  10 11 12 13");
		for (int x = 0; x < n; x++)
		{
			System.out.print(x + "\t");
			for (int y = 0; y < n; y++)
			{
				System.out.print(current[x][y] + "  ");
			}
			System.out.println("");
		}
	}
}