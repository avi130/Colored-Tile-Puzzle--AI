

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class IDA {


	public tile_puzzle solveIDAstar(tile_puzzle start, tile_puzzle.DIRECTION[] strategy) {
		//Check if the input is the solution
		int[][] ans= start.GetCorrectPuzzle();
		if (start.isSolved()) {
			return start;
		}
		// check if is black numbers that not in the right place
		if( !start.PossibleToSolve()) { 
			return null;
		}
		int currentF;
		int neighbourF;
		tile_puzzle.num=1;
		Ex1.stepsCounterInNullCase=1;	
		HashMap<String, Boolean> MyHash = new HashMap<String, Boolean>();
		Stack<tile_puzzle> Mystack = new Stack<>();
		int t=huristic.getHuristicFuncPrice(start,ans);
		int MinF=Integer.MAX_VALUE;

		while(t!= Integer.MAX_VALUE ) {
			Mystack.add(start);
			MyHash.put(start.toString(), false);
			MinF=Integer.MAX_VALUE;

			while(!Mystack.isEmpty()) {
				if( Ex1.OPEN==true) {
					for (tile_puzzle temp : Mystack) {
						System.out.println(temp.toString());
					}

				}
				tile_puzzle current= Mystack.pop();
				//if current puzzle in MyHash and marked as true then delete
				if( MyHash.containsKey(current.toString()) && MyHash.get(current.toString())) {
					MyHash.remove(current.toString());			
				}
				else {
					MyHash.put(current.toString(), true);
					Mystack.add(current);
					for (int i = 0; i < strategy.length; i++) {

						if (current.canMove(strategy[i]) ) {				
							tile_puzzle neighbour = new tile_puzzle(current);	
							neighbour.move(strategy[i]);	
							tile_puzzle.num ++;
							Ex1.stepsCounterInNullCase++;

							if(huristic.getHuristicFuncPrice(neighbour,ans)+neighbour.getPrice() >t   ) {
								MinF=getMin(MinF, huristic.getHuristicFuncPrice(neighbour,ans) +neighbour.getPrice());
								continue;
							}
							//if neighbour in MyHash and marked as true
							if (MyHash.containsKey(neighbour.toString()) && MyHash.get(neighbour.toString())) {

								continue; 
							}
							//if neighbour in MyHash and didn't marked as true
							if (MyHash.containsKey(neighbour.toString()) && !MyHash.get(neighbour.toString()) ){

								currentF= huristic.getHuristicFuncPrice(current,ans)+current.getPrice();
								neighbourF=huristic.getHuristicFuncPrice(neighbour,ans)+neighbour.getPrice();
								if(neighbourF>currentF) {
									MyHash.remove(neighbour.toString());
								}
								else {
									continue;
								}
							}
							//check if neighbour is the answer befor inserting to MyHash and Mystack
							if (neighbour.isSolved()) {
								return neighbour;
							}

							MyHash.put(neighbour.toString(), false);
							Mystack.add(neighbour);

						}					
					}				
				}		
			}
			t=MinF;
		}
		return null;


	}

	public int getMin(int func, int t) {
		if(func<t)
			return func;
		else
			return t;

	}

}
