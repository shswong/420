import java.util.*;

public class Board
{
	// 1 or o is for user move
	// 2 or x is for comp move
	private int[][] current;
	private int n;

	public Board(int n)
	{
		this.n = n;
		generateBoard();
		printBoard();
	}

	public Board(int[][] current)
	{
		this.current = current;
		this.n = current.length;
	}
	
	public Board(Board that, int n)
	{
		for (int x = 0; x < n; x++)
		{
			for (int y = 0; y < n; y++)
			{
				this.current[x][y] = that.current[x][y];
			}
		}
		this.n = n;
	}

	public void add(String s, int i)
	{
		Character a = s.charAt(0);
		Character b = s.charAt(1);
		int row = Character.getNumericValue(a) - 10;
		int column = Character.getNumericValue(b) - 1;

		current[row][column] = i;
	}

	/*
	 * 0 = empty
	 * 1 = user
	 * 2 = computer
	 */
	private void generateBoard()
	{
		current = new int[n][n];
		for (int x = 0; x < n; x++)
		{
			for (int y = 0; y < n; y++)
			{
				current[x][y] = 0;
			}
		}
	}

	public Board copy()
	{
		int[][] newCurrent = new int[n][n];
		for (int x = 0; x < n; x++)
		{
			for (int y = 0; y < n; y++)
			{
				newCurrent[x][y] = this.current[x][y];
			}
		}
		return new Board(newCurrent);
	}
	
	public boolean terminal_test(int depth, Board b)
	{
		if (depth >= 5) { return true; }
		else if (check1() || check2()) { return true; }
		return false;
	}

	/*
	 * runs through row by row and column by column
	 * to check for 4-in-a-lines
	 */
	public boolean check1()
	{
		int countX = 0, countO = 0;
		boolean win = false;
		for (int x = 0; x < n; x++)
		{
			for (int y = 0; y < n; y++)
			{
				if (current[x][y] == 1)
				{
					countX++;
					countO = 0;
				}
				else if (current[x][y] == 2)
				{
					countO++;
					countX = 0;
				}
				else
				{
					countX = 0;
					countO = 0;
				}

				if (countX == 4)
				{
					System.out.println("X wins");
					return true;
				}
				else if (countO == 4)
				{
					System.out.println("O wins");
					return true;
				}
			}
		}
		return win;
	}
	
	public boolean check2()
	{
		int countX = 0, countO = 0;
		boolean win = false;
		for (int y = 0; y < n; y++)
		{
			for (int x = 0; x < n; x++)
			{
				if (current[x][y] == 1)
				{
					countX++;
					countO = 0;
				}
				else if (current[x][y] == 2)
				{
					countO++;
					countX = 0;
				}
				else
				{
					countX = 0;
					countO = 0;
				}

				if (countX == 4)
				{
					System.out.println("X wins");
					return true;
				}
				else if (countO == 4)
				{
					System.out.println("O wins");
					return true;
				}
			}
		}
		return win;
	}
	
	/*
	 * runs through row by row and column by column
	 * to determine the utility of the current state
	 */
	public int getUtility()
	{
		int utility = 0;
		int consecutive_x = 0;
		int consecutive_o = 0;
		
		for (int y = 0; y < n; y++)
		{
			for (int x = 0; x < n; x++)
			{
				if (current[x][y] == 2)
				{
					consecutive_x++;
					consecutive_o = 0;
				}
				else if (current[x][y] == 1)
				{
					consecutive_o++;
					consecutive_x = 0;
				}
				else
				{
					consecutive_x = 0;
					consecutive_o = 0;
				}

				if (consecutive_x == 2)	{ utility -= 5000; }
				else if (consecutive_x == 3) { utility -= 10000; }
				
				if (consecutive_o == 3) { utility += 10000; }
				else if (consecutive_o == 2) { utility += 5000; }
			}
		}
		
		for (int x = 0; x < n; x++)
		{
			for (int y = 0; y < n; y++)
			{
				if (current[x][y] == 2)
				{
					consecutive_x++;
					consecutive_o = 0;
				}
				else if (current[x][y] == 1)
				{
					consecutive_o++;
					consecutive_x = 0;
				}
				else
				{
					consecutive_x = 0;
					consecutive_o = 0;
				}

				if (consecutive_x == 2)	{ utility -= 5000; }
				else if (consecutive_x == 3) { utility -= 10000; }
				
				if (consecutive_o == 3) { utility += 10000; }
				else if (consecutive_o == 2) { utility += 5000; }
			}
		}
		
		return utility;
	}

	public ArrayList<Board> generateSuccessors(Board board, int i)
	{
		ArrayList<Board> successors = new ArrayList<Board>();

		for (int x = 0; x < n; x++)
		{
			for (int y = 0; y < n; y++)
			{
				if (this.current[x][y] == 0)
				{
					Board secondary = board.copy();
					secondary.current[x][y] = i;
					successors.add(secondary);
				}
			}
		}
		return successors;
	}

	public boolean valid(String s)
	{
		Character a = s.charAt(0);
		Character b = s.charAt(1);
		int row = Character.getNumericValue(a) - 10;
		int column = Character.getNumericValue(b) - 1;

		if (row < 0 || row > 7 || column < 0 || column > 7)
		{
			System.out.println("Out of bounds");
			return false;
		}
		else if (current[row][column] != 0)
		{
			System.out.println("Placement exists");
			return false;
		}
		return true;
	}

	public void printBoard()
	{
		System.out.println("  1 2 3 4 5 6 7 8");
		for (int x = 0; x < n; x++)
		{
			System.out.print((char) (x + 65));
			for (int y = 0; y < n; y++)
			{
				if (current[x][y] == 2) { System.out.print(" x"); }
				else if (current[x][y] == 1) { System.out.print(" o"); }
				else { System.out.print(" -"); }
			}
			System.out.println();
		}
	}
}