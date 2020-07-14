

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class A {


	public tile_puzzle solveAstar(tile_puzzle start, tile_puzzle.DIRECTION[] strategy) {
		int[][] ans= start.GetCorrectPuzzle();
		//Check if the input is the solution
		if (start.isSolved()) {
			return start;
		}
		// check if is black numbers that not in the right place
		if( !start.PossibleToSolve()) {
			return null;
		}
		PriorityQueue<tile_puzzle> pQueue ;
		//create Prioritie Queue
		Comparator<tile_puzzle> comparator = new Comparator<tile_puzzle>() { 
			@Override
			public int compare(tile_puzzle n1, tile_puzzle n2) {
				return (n1.getPrice() + huristic.getHuristicFuncPrice(n1,ans)) - (n2.getPrice() + huristic.getHuristicFuncPrice(n2,ans));
			}
		};
		pQueue = new PriorityQueue<tile_puzzle>(comparator);
		Set<tile_puzzle> openList = new HashSet<>();
		Set<tile_puzzle> closedList = new HashSet<>();

		pQueue.add(start);
		openList.add(start);
		tile_puzzle.num=1;
		Ex1.stepsCounterInNullCase=1;

		while (!pQueue.isEmpty()) 
		{
			if( Ex1.OPEN==true) 
			{
				for (tile_puzzle temp : pQueue) 
				{
					System.out.println(temp.toString());
				}

			}
			tile_puzzle current = pQueue.poll();

			openList.remove(current);

			if (current.isSolved()) 
			{
				return current;
			}
			closedList.add(current);
			//Creates any good son. 
			for (int i = 0; i < strategy.length; i++)
			{

				if (current.canMove(strategy[i]) )
				{				
					tile_puzzle neighbour = new tile_puzzle(current);	
					neighbour.move(strategy[i]);	
					tile_puzzle.num ++;
					Ex1.stepsCounterInNullCase++;

					//If not in openList and closedList then put in pQueue and openList
					if (!tile_puzzle.checkIfNodeExistsInList(neighbour, openList) && !tile_puzzle.checkIfNodeExistsInList(neighbour, closedList))
					{
						pQueue.add(neighbour);
						openList.add(neighbour);
						//if in openList then checks which one is cheaper.Throws away the precious and leaves the cheaper
					}
					else if (tile_puzzle.checkIfNodeExistsInList(neighbour, openList))
					{

						pQueue = huristic.changeByValueInQueue(pQueue, openList, neighbour);
					}
				}
			}
		}
		return null;
	}

}