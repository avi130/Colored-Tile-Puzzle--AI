
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFS {

	private final Queue<tile_puzzle> frontiers = new LinkedList<>();
	Set<String> frontiersStrings = new HashSet<>();   //To check if "tile_puzzle" is already exists I compared their print.(act like frontiers)
	Set<String> closedList = new HashSet<>();         //To check if "tile_puzzle" is already exists I compared their print.


	public tile_puzzle solve (tile_puzzle puzzleToSolve, tile_puzzle.DIRECTION[] strategy) {
		frontiers.add(puzzleToSolve);
		frontiersStrings.add(puzzleToSolve.toString());
		tile_puzzle.num ++;
		Ex1.stepsCounterInNullCase=1;
		 // check if is black numbers that not in the right place
		if( !puzzleToSolve.PossibleToSolve()) {
				return null;
			}
		while (!frontiers.isEmpty()) {
			if( Ex1.OPEN==true) {
				for (tile_puzzle temp : frontiers) {
					System.out.println(temp.toString());
									}
				
			}
			tile_puzzle puzzle = frontiers.poll();
			//	alredyMake.add(puzzle.toString());
			frontiersStrings.remove(puzzleToSolve.toString());
			//tile_puzzle.num++;
			if (puzzle.isSolved()) {
				return puzzle;
			}
			//Creates any good son. If this is the solution then returns. If not, and not in closedList and frontiersStrings then I put in frontiersStrings
			for (int i = 0; i < strategy.length; i++) {

				if (puzzle.canMove(strategy[i]) ) {				
					tile_puzzle neighbour = new tile_puzzle(puzzle);	
					neighbour.move(strategy[i]);	
					tile_puzzle.num ++;
					
					System.out.println(tile_puzzle.num);
					Ex1.stepsCounterInNullCase++;
					if (!closedList.contains(neighbour.toString()) && !frontiersStrings.contains(neighbour.toString()) ) {
						if (neighbour.isSolved()) {
							return neighbour;
						}

						frontiers.add(neighbour);	
						frontiersStrings.add(neighbour.toString());
					}
				}
			}
			closedList.add(puzzle.toString());			
			frontiersStrings.remove(puzzle.toString());
			

		}
		return null;
	}
}
