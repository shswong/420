import java.util.*;

public class FourLine
{
	private static final int size = 8;
	private static char first;
	private static int move_time;
	private static String move;
	private static boolean done = false;
	private static int depth = 0;
	private static final boolean debug = false;
	private static final boolean debug2 = false;
	private static Board action = null;

	public static void main(String[] args)
	{
		Scanner k = new Scanner(System.in);
		/*
		System.out.print("Would you like to go first? (y/n): ");
		first = k.nextLine().toLowerCase().charAt(0);
		System.out.print("\nHow long should the computer be able to think for its moves (in seconds): ");
		move_time = k.nextInt();
		 */
		Board b = new Board(size);
		//ArrayList<Board> successors = b.generateSuccessors(b);
		if (first == 'n') { b.add("e4", 2); }

		while (!done)
		{
			do
			{
				System.out.print("\nChoose your move: ");
				move = k.nextLine();
			}  while (move.length() != 2);

			if (b.valid(move))
			{
				b.add(move, 1);
				System.out.println();
				b.printBoard();
				System.out.println("You chose: " + move);
				alphaBeta(b);
				b = new Board(action, size);
				b.printBoard();
			}
		}
	}
	
	private static void alphaBeta(Board b)
	{
		int v = max(b, -5000000, 5000000);
	}
	
	private static int max(Board b, int alpha, int beta)
	{
		depth++;
		if (b.terminal_test(depth, b))
		{
			if (debug) System.out.println("min: "+depth);
			if (debug2) b.printBoard();
			done = true;
			return b.getUtility();
		}
		int v = alpha;
		ArrayList<Board> successors = b.generateSuccessors(b, 1);
		for (Board successor : successors)
		{
			v = Math.max(v, min(successor, alpha, beta));
			if (v >= beta)
			{
				action = new Board(successor, size);
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
	}
	
	private static int min(Board b, int alpha, int beta)
	{
		depth++;
		if (b.terminal_test(depth, b))
		{
			if (debug) System.out.println("min: "+depth);
			if (debug2) b.printBoard();
			done = true;
			return b.getUtility();
		}
		int v = beta;
		ArrayList<Board> successors = b.generateSuccessors(b, 2);
		for (Board successor : successors)
		{
			v = Math.min(v, max(successor, alpha, beta));
			if (v <= alpha)
			{
				action = new Board(successor, size);
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}
}