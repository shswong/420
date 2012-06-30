import java.util.*;
////////////////////////////////////////////////////////////////////////////////
public class Node implements Comparable<Node>
{
	private int[] current;
	private ArrayList<Square> mapping = new ArrayList<Square>();
	private int[] goal = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	public int g, h, f;
//------------------------------------------------------------------------------	
	Node() {}
//------------------------------------------------------------------------------	
	Node(int[] current)
	{
		this.current = current;
		
		for (int i = 0; i < 3; i++)
   	{
   		for (int j = 0; j < 3; j++)
   		{
   			mapping.add(new Square(i, j));
   		}
   	}
	}
//------------------------------------------------------------------------------
	public int compareTo (Node that) { return this.getH() - that.getH(); }
//------------------------------------------------------------------------------
	public boolean equals(Object obj)
	{
		for (int i = 0; i < current.length; i++)
		{
			if (current[i] != ((Node) obj).getCurrent()[i]) { return false; }
		}
		return true;
  }
//------------------------------------------------------------------------------
	public int[] getCurrent() { return current; }
//------------------------------------------------------------------------------
	public int getH1()
   {
   	int counter = 0;
   	for (int i = 0; i < current.length; i++)
   	{
   		if (goal[i] != current[i] && current[i] != 0)	{ counter++; }
   	}
   	return counter;
   }
//------------------------------------------------------------------------------
	public int getH2()
   {
   	int counter = 0;
   	for (int i = 0; i < current.length; i++)
   	{
   		if (goal[i] != current[i] && current[i] != 0)
   		{
   			int currentX = mapping.get(i).getX();
   			int currentY = mapping.get(i).getY();
   			int goalX = 0;
   			int goalY = 0;
   			
   			for (int j = 0; j < mapping.size(); j++)
   			{
   				if (current[i] == goal[j] && current[i] != 0)
   				{
   					goalX = mapping.get(j).getX();
   					goalY = mapping.get(j).getY();
   				}
   			}
   			
   			int totalX = Math.abs(goalX - currentX);
   			int totalY = Math.abs(goalY - currentY);
   			counter += totalX + totalY;
   		}
   	}
   	return counter;
   }
//------------------------------------------------------------------------------
	public int getH() { return getH1() + getH2(); }
//------------------------------------------------------------------------------
	public boolean isGoal()
	{
		for (int i = 0; i < current.length; i++)
		{
			if (goal[i] != current[i]) { return false; }
		}
		return true;
	}
//------------------------------------------------------------------------------
	public String toString()
	{
		System.out.println(current[0] + " " + current[1] + " " + current[2]);
   	System.out.println(current[3] + " " + current[4] + " " + current[5]);
   	System.out.println(current[6] + " " + current[7] + " " + current[8]);
   	return "\n";
	}
//------------------------------------------------------------------------------
	private int[] swap(int[] a1, int a, int b)
	{
		int[] a2 = new int[a1.length];
		System.arraycopy(a1, 0, a2, 0, a1.length);
		
		int temp = a2[a];
		a2[a] = a2[b];
		a2[b] = temp;
		
		return a2;
	}
//------------------------------------------------------------------------------
	public ArrayList<Node> getNeighbors()
	{
		int[] array1 = new int[9];
		int[] array2 = new int[9];
		int[] array3 = new int[9];
		int[] array4 = new int[9];
		ArrayList<Node> nodes = new ArrayList<Node>();
		int currentIndex = 0;
		//Find 0 index
		for (int i = 0; i < current.length; i++)
		{
			if (current[i] == 0) { currentIndex = i; }
		}
		
		switch (currentIndex)
		{
			case 0:
				System.arraycopy(current, 0, array1, 0, current.length);
				System.arraycopy(current, 0, array2, 0, current.length);
				array1 = swap(array1, 0, 1);
				array2 = swap(array2, 0, 3);
				nodes.add(new Node(array1));
				nodes.add(new Node(array2));
				break;
			case 2:
				System.arraycopy(current, 0, array1, 0, current.length);
				System.arraycopy(current, 0, array2, 0, current.length);
				array1 = swap(array1, 2, 1);
				array2 = swap(array2, 2, 5);
				nodes.add(new Node(array1));
				nodes.add(new Node(array2));
				break;
			case 6:
				System.arraycopy(current, 0, array1, 0, current.length);
				System.arraycopy(current, 0, array2, 0, current.length);
				array1 = swap(array1, 6, 3);
				array2 = swap(array2, 6, 7);
				nodes.add(new Node(array1));
				nodes.add(new Node(array2));
				break;
			case 8:
				System.arraycopy(current, 0, array1, 0, current.length);
				System.arraycopy(current, 0, array2, 0, current.length);
				array1 = swap(array1, 8, 5);
				array2 = swap(array2, 8, 7);
				nodes.add(new Node(array1));
				nodes.add(new Node(array2));
				break;
			case 1:
				System.arraycopy(current, 0, array1, 0, current.length);
				System.arraycopy(current, 0, array2, 0, current.length);
				System.arraycopy(current, 0, array3, 0, current.length);
				array1 = swap(array1, 1, 0);
				array2 = swap(array2, 1, 2);
				array3 = swap(array3, 1, 4);
				nodes.add(new Node(array1));
				nodes.add(new Node(array2));
				nodes.add(new Node(array3));
				break;
			case 3:
				System.arraycopy(current, 0, array1, 0, current.length);
				System.arraycopy(current, 0, array2, 0, current.length);
				System.arraycopy(current, 0, array3, 0, current.length);
				array1 = swap(array1, 3, 0);
				array2 = swap(array2, 3, 4);
				array3 = swap(array3, 3, 6);
				nodes.add(new Node(array1));
				nodes.add(new Node(array2));
				nodes.add(new Node(array3));
				break;
			case 5:
				System.arraycopy(current, 0, array1, 0, current.length);
				System.arraycopy(current, 0, array2, 0, current.length);
				System.arraycopy(current, 0, array3, 0, current.length);
				array1 = swap(array1, 5, 2);
				array2 = swap(array2, 5, 4);
				array3 = swap(array3, 5, 8);
				nodes.add(new Node(array1));
				nodes.add(new Node(array2));
				nodes.add(new Node(array3));
				break;
			case 7:
				System.arraycopy(current, 0, array1, 0, current.length);
				System.arraycopy(current, 0, array2, 0, current.length);
				System.arraycopy(current, 0, array3, 0, current.length);
				array1 = swap(array1, 7, 4);
				array2 = swap(array2, 7, 6);
				array3 = swap(array3, 7, 8);
				nodes.add(new Node(array1));
				nodes.add(new Node(array2));
				nodes.add(new Node(array3));
				break;
			case 4:
				System.arraycopy(current, 0, array1, 0, current.length);
				System.arraycopy(current, 0, array2, 0, current.length);
				System.arraycopy(current, 0, array3, 0, current.length);
				System.arraycopy(current, 0, array4, 0, current.length);
				array1 = swap(array1, 4, 1);
				array2 = swap(array2, 4, 3);
				array3 = swap(array3, 4, 5);
				array4 = swap(array4, 4, 7);
				nodes.add(new Node(array1));
				nodes.add(new Node(array2));
				nodes.add(new Node(array3));
				nodes.add(new Node(array4));
				break;
		}
		return nodes;
	}
//------------------------------------------------------------------------------
}
////////////////////////////////////////////////////////////////////////////////
