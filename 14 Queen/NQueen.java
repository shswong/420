import java.util.*;

public class NQueen
{
	private static Random r = new Random();
	private static int n = 14;
	private static int max_steps = 1000;
	private static int runs = 1000;
	private static int fails;
	private static int steps;
	private static long time;
	private static boolean test = false;
	
	public static void main(String[] args)
	{
		Queen[][] current = new Queen[n][n];
		ArrayList<Queen> queens = new ArrayList<Queen>();
		generateBoard(current, queens);
		Board b = new Board(current, queens);
		/*b.printBoard();
		System.out.println("");
		System.out.println("\nh: " + b.getH());*/
				
		init();
		for (int i = 0; i < runs; i++)
		{			
			generateBoard(current, queens);
			b = new Board(current, queens);
			
			if (test)
			{
				System.out.println("Initial:");
				b.printBoard();
				System.out.println("");
			}
			
			b = hillClimb(b);
			if (b == null) { fails++; }
			
			if (b != null && test)
			{
				System.out.println("Success Hill Climb:");
				b.printBoard();
				System.out.println("");
			}
		}
		printData();
		
		
		init();
		for (int i = 0; i < runs; i++)
		{
			generateBoard(current, queens);
			b = new Board(current, queens);
			
			if (test)
			{
				System.out.println("Initial:");
				b.printBoard();
				System.out.println("");
			}
						
			b = minConflicts(b);
			if (b == null) { fails++; }
			
			if (b != null && test)
			{
				System.out.println("Success Min Conflict:");
				b.printBoard();
				System.out.println("");
			}
		}
		printData();
	}
	
	private static Board hillClimb(Board board)
	{
		while (true)
		{
			Board neighbor = board.generateSuccessors(board);
			if (neighbor.getH() == board.getH()) { return null; }
			else if (neighbor.getH() == 0) return neighbor;
			board = neighbor;
			steps++;
		}
	}
	
	private static Board minConflicts(Board board)
	{
		Queen[][] current = board.getCurrent();
		ArrayList<Queen> queens = board.getQueens();
		
		for (int i = 0; i < max_steps; i++)
		{
			if (board.getH() == 0) { return board;	}
			steps++;
			
			for (int y = 0; y < n; y++)
			{
				Queen temp2 = queens.get(y);
				PriorityQueue<Queen> pq = new PriorityQueue<Queen>();
				
				for (int x = 0; x < n; x++)
				{
					Queen temp = current[x][y];
					board.getConflicts(current, temp);
					temp = current[x][y];
					pq.add(temp);
				}
				Queen temp = pq.poll();				
				board.swap(current, queens, temp2.getX(), temp2.getY(), temp.getX(), temp.getY());
			}
			board = new Board(current, queens);
		}
		return null;
	}
	
	private static void init()
	{
		fails = 0;
		steps = 0;
		time = System.nanoTime();
	}
	
	private static void printData()
	{
		System.out.println("Fails: "+fails+" of "+runs+" runs");
		System.out.println("Steps: "+steps);
		System.out.println("Time: "+(System.nanoTime() - time)+" nanoseconds");
		System.out.println("Average Time: "+((double) (time) / runs) + " nano seconds");
		System.out.println("");
	}

	private static void generateBoard(Queen[][] current, ArrayList<Queen> queens)
	{
		queens.clear();
		for (int i = 0; i < n; i++)
		{
			for (int k = 0; k < n; k++)
			{
				current[i][k] = null;
			}
		}
		
		for (int y = 0; y < current.length; y++)
		{
			int k = r.nextInt(n);
			Queen q = new Queen(k, y, true);
			current[k][y] = q;
			queens.add(q);
			for (int i = 0; i < current.length; i++)
			{
				if (i != k)
				{
					current[i][y] = new Queen(i, y);
				}
			}
		}
	}
}