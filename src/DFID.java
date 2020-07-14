


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.print.DocFlavor.STRING;




public class DFID {
	tile_puzzle newPuzzle;
	private String path;
	private int price;
	public tile_puzzle solveDFID (tile_puzzle puzzleToSolve, tile_puzzle.DIRECTION[] strategy) {
		tile_puzzle.num=1;
		Ex1.stepsCounterInNullCase=1;
		// check if is black numbers that not in the right place
		if( !puzzleToSolve.PossibleToSolve()) {  
			return null;
		}
		//Check if the input is the solution
		if (puzzleToSolve.isSolved()) {
			return puzzleToSolve;
		}
		
		//Sends to recursive function and each time goes deeper in one
		for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
			Hashtable<tile_puzzle,Boolean> loop_avoidance_list = new Hashtable<>();
			
			tile_puzzle temp = Limited_DFS(strategy,puzzleToSolve, depth, loop_avoidance_list);
			if (temp != null) {
				path = temp.getPath();
				price = temp.getPrice();
				return temp;
			}
		}
		return null;
	}

	private tile_puzzle Limited_DFS(tile_puzzle.DIRECTION[] strategy,tile_puzzle current, int limit, Hashtable<tile_puzzle,Boolean> loop_avoidance_list) {
		//stop condition
		if (limit == 0) {
			return null;
		} 

		else if (current.isSolved()) {
			return current;
		}
		else {

			loop_avoidance_list.put(current, false);

			//Creates any good son. If this is the solution then returns, if not then Sends to recursive function and each time goes deeper in one
			for (int i = 0; i < strategy.length; i++) {

				if (current.canMove(strategy[i]) ) {				
					tile_puzzle neighbour = new tile_puzzle(current);	
					neighbour.move(strategy[i]);	
					tile_puzzle.num ++;
					Ex1.stepsCounterInNullCase++;
				
					if (neighbour.isSolved()) {
						return neighbour;
					}
					if (!tile_puzzle.checkIfNodeExistsInList(neighbour, loop_avoidance_list)) {
						tile_puzzle temp = Limited_DFS(strategy,neighbour, limit - 1, loop_avoidance_list);
						if (temp != null) {
							return temp;
						}
					}
				}
			}
			loop_avoidance_list.remove(current);
			
			
		}

		return null;


	}
}