import java.util.*;

////////////////////////////////////////////////////////////////////////////////
public class Driver
{
	static Scanner keyboard = new Scanner(System.in);
	static int[] puzzle = new int[9];
	static int steps = 0;
	static int debug = 10;
	static boolean h1 = false;
	static boolean h2 = false;
	static boolean h3 = true;
	// ------------------------------------------------------------------------------
	public static void main(String[] args)
	{
		double start = 0, end = 0;
		
		for (int i = 0; i < debug; i++)
		{
			do
			{
				generatePuzzle('a');
			} while (inverted());
			
			Node n = new Node(puzzle);
			start = System.nanoTime();
			aStar(n);
			end = (System.nanoTime() - start)/1000000000;
		}
		System.out.println("Time: " + end + " seconds");
		System.out.println("Steps: " + steps);
		System.out.println("Average Time: " + end/debug + " seconds");
		System.out.println("Average Steps: " + steps/debug);
	}
	// ------------------------------------------------------------------------------
	private static void generatePuzzle(char input)
	{
		Random r = new Random();
		ArrayList<Integer> array = new ArrayList<Integer>();
		switch (input)
		{
		case 'a':
		case '1':
			for (int i = 0; i < 9; i++)
			{
				int random = r.nextInt(9);
				while (array.contains(random))
				{
					random = r.nextInt(9);
				}
				array.add(random);
			}
			break;
		case 'b':
		case '2':
			for (int i = 0; i < 9; i++)
			{
				System.out.println("Enter element for " + i + "th index:");
				int next = keyboard.nextInt();

				while (next < 0 || next > 8)
				{
					System.out.println("Invalid Range. Please use 0 through 8.");
					System.out.println("Enter element for " + i + "th index:");
					next = keyboard.nextInt();
				}

				while (array.contains(next))
				{
					System.out.println("Duplicate.");
					System.out.println("Enter element for " + i + "th index:");
					next = keyboard.nextInt();
				}
				array.add(next);
			}
			break;
		}
		
		for (int i = 0; i < array.size(); i++)
		{
			puzzle[i] = array.get(i);
		}
	}
	// ------------------------------------------------------------------------------
	private static void aStar(Node start)
	{
		ArrayList<Node> closed = new ArrayList<Node>();
		ArrayList<Node> open = new ArrayList<Node>();
		ArrayList<Node> came_from = new ArrayList<Node>();
		PriorityQueue<Node> f = new PriorityQueue<Node>();
		
		start.g = 0;
		if (h1) { start.h = start.getH1(); }
		if (h2) { start.h = start.getH2(); }
		if (h3) { start.h = start.getH(); }
		start.f = start.h;
		
		open.add(start);
		f.add(start);
		
		while (!open.isEmpty())
		{
			Node x = f.poll();
			
			if (x.isGoal())
			{
				came_from.add(x);
				steps = came_from.size();
				//reconstruct_path(came_from);
				break;
			}

			open.remove(x);
			closed.add(x);
			ArrayList<Node> neighbors = x.getNeighbors();
			
			for (Node y : neighbors)
			{				
				if (closed.contains(y))	{ continue; }
				
				int tentative_g = 0;
				if (h1) { tentative_g = x.g + x.getH1(); }
				if (h2) { tentative_g = x.g + x.getH2(); }
				if (h3) { tentative_g = x.g + x.getH(); }
				boolean tentative_is_better = true;
				
				if (!open.contains(y))
				{
					open.add(y);
					tentative_is_better = true;
				}
				else if (tentative_g < y.g)	{ tentative_is_better = false; }
				else { tentative_is_better = true; }
				
				if (tentative_is_better)
				{
					came_from.add(x);
					y.g = tentative_g;
					y.getH();
					if (h1) { y.h = x.getH1(); }
					if (h2) { y.h = x.getH2(); }
					if (h3) { y.h = x.getH(); }
					f.add(y);
				}
			}
		}
	}
	// ------------------------------------------------------------------------------
	private static void reconstruct_path(ArrayList<Node> came_from)
	{
		for (int i = 0; i < came_from.size(); i++)
		{
			System.out.println(came_from.get(i));
		}
	}
	// ------------------------------------------------------------------------------
	private static boolean inverted()
	{
		int inversions = 0;
		for (int i = 0; i < puzzle.length; i++)
		{
			for (int j = 0; j < puzzle.length; j++)
			{
				if (puzzle[i] != 0 && puzzle[j] != 0)
				{
					if (puzzle[i] < puzzle[j])
					{
						inversions++;
					}
				}
			}
		}
		return inversions % 2 == 1;
	}
	// ------------------------------------------------------------------------------
}
// //////////////////////////////////////////////////////////////////////////////
