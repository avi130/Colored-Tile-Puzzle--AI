

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class huristic {

	Comparator<tile_puzzle> comparator = new Comparator<tile_puzzle>() { 
		 @Override
	 public int compare(tile_puzzle n1, tile_puzzle n2) {
			 
        return (n1.getPrice() + getHuristicFuncPrice(n1, n1.GetCorrectPuzzle())) - (n2.getPrice() + getHuristicFuncPrice(n2,n2.GetCorrectPuzzle()));
    }
	 };

	 
	 
	public static PriorityQueue<tile_puzzle> changeByValueInQueue(PriorityQueue<tile_puzzle> pQueue,Set<tile_puzzle> openList, tile_puzzle neighbour){
		PriorityQueue<tile_puzzle> tempQueue = new PriorityQueue<tile_puzzle>(); 
		while(pQueue.isEmpty()) {
			tile_puzzle tempPuzzle= pQueue.poll();
			if(tempPuzzle.toString()==neighbour.toString() && tempPuzzle.getPrice() >neighbour.getPrice() ) {
				tempQueue.add(neighbour);
				openList.remove(tempPuzzle);
				openList.add(neighbour);
							
			}
			else {
				tempQueue.add(tempPuzzle);
			}						
		}	
		return tempQueue;
	}
	
	
	
	public static int getHuristicFuncPrice(tile_puzzle temp ,int [][] correctPuzzle) { 
		int count=0;
		int row;
		int column;
		int needToMove;
		int [][] tempPuzzle= temp.getPuzzle();
		for (int i = 0; i < correctPuzzle.length; i++) {
			for (int j = 0; j < correctPuzzle[0].length; j++) {			
				if((correctPuzzle[i][j]!= tempPuzzle[i][j]) && tempPuzzle[i][j]!=0) {
					for (int k = 0; k < correctPuzzle.length; k++) {
						for (int l = 0; l < correctPuzzle[0].length; l++) {
							
							if(tempPuzzle[i][j]==correctPuzzle[k][l]) {
								row=Math.abs(i-k);
								column=Math.abs(j-l);
								needToMove= row+column;
								
								if (temp.red != null && temp.red.contains(""+tempPuzzle[i][j])) {
									count+=30*needToMove;
									break;
								}
								else
									count+=1*needToMove;
								break;

							}
						}
					}
				}
			}
		}

		return count;
	}

	
	
	
	 
	 
	 
	 
	 
	 
}


