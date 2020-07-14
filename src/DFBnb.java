

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;



public class DFBnb {



	public tile_puzzle solveDFBnb(tile_puzzle start, tile_puzzle.DIRECTION[] strategy) {
		int[][] ans= start.GetCorrectPuzzle();
		// Check if is black numbers that not in the right place
		if( !start.PossibleToSolve()) {
			return null;
		}
		//Check if the input is the solution
		if (start.isSolved()) {
			return start;
		}

		ArrayList<tile_puzzle> neighbours=new ArrayList<tile_puzzle>() ; //The neighbors of current tile_puzzle will be in here
		Stack<tile_puzzle> Mystack = new Stack<>(); 
		HashMap<String, Boolean> MyHash = new HashMap<String, Boolean>();
		ArrayList<tile_puzzle> MyArray=new ArrayList<tile_puzzle>() ;	
		Mystack.add(start);
		
		MyHash.put(start.toString(), false);
	
		MyArray.add(start);

		boolean flag=false;
		int currentF = 0;
		int neighbourF;

		tile_puzzle current=null;
		tile_puzzle answer=null;
		int t=1;
		if (puzzleLoader.count<=12) {
			for (int i =1; i <=puzzleLoader.count; i++) {
				t=t*i;
			}
		}
		else {
			t=Integer.MAX_VALUE;
		}
		tile_puzzle.num=1;
		Ex1.stepsCounterInNullCase=1;

		Comparator<tile_puzzle> comparator = new Comparator<tile_puzzle>() { 
			@Override
			public int compare(tile_puzzle n1, tile_puzzle n2) {
				return (n1.getPrice() + huristic.getHuristicFuncPrice(n1,n1.GetCorrectPuzzle())) - (n2.getPrice() + huristic.getHuristicFuncPrice(n2,n2.GetCorrectPuzzle()));
			}
		};
		while( !Mystack.isEmpty() || Ex1.stepsCounterInNullCase>Integer.MAX_VALUE/10000000) {
			System.out.println("heyy");
			neighbours.clear();

			if( Ex1.OPEN==true) {
				for (tile_puzzle temp : Mystack) {
					System.out.println(temp.toString());
				}

			}
			current= Mystack.pop();
			if( MyHash.containsKey(current.toString()) && MyHash.get(current.toString())) {
				MyHash.remove(current.toString());		
				MyArray.remove(current);
			}
			else {

				MyHash.put(current.toString(), true);
				Mystack.add(current);
				for (int i = 0; i < strategy.length; i++) {

					if (current.canMove(strategy[i]) ) {				
						tile_puzzle neighbour = new tile_puzzle(current);	
						neighbour.move(strategy[i]);	
						neighbours.add(neighbour);
						tile_puzzle.num ++;
						Ex1.stepsCounterInNullCase++;
					}
				}
				neighbours.sort(comparator);
				flag=false;
				for (int i = 0; i < neighbours.size(); i++) {

					if(flag) {
						neighbours.remove(i);
						i--;
						continue;
					}

					if(neighbours.get(i).getPrice() + huristic.getHuristicFuncPrice(neighbours.get(i),ans) >=t) {
						flag=true;
						neighbours.remove(i);
						i--;
					}
					//if neighbour in MyHash and marked as true 
					else if (MyHash.containsKey(neighbours.get(i).toString()) && MyHash.get(neighbours.get(i).toString())) {
						neighbours.remove(i);
						i--;						
					}
					else if (MyHash.containsKey(neighbours.get(i).toString()) && !MyHash.get(neighbours.get(i).toString()) ){
						for (tile_puzzle temp: MyArray) {
							if(temp.toString()==neighbours.get(i).toString()) {
								currentF=huristic.getHuristicFuncPrice(temp,ans)+temp.getPrice();
								break;
							}
						}
						//currentF= current.getHuristicFuncPrice()+current.getPrice();
						neighbourF=huristic.getHuristicFuncPrice(neighbours.get(i),ans)+neighbours.get(i).getPrice();
						if(neighbourF>=currentF) {
							neighbours.remove(i);
							i--;

						}
						else {
							MyHash.remove(neighbours.get(i).toString());
							Mystack.remove(neighbours.get(i));
							MyArray.remove(neighbours.get(i));
						}
					}
					else if (neighbours.get(i).isSolved()) {
						t=huristic.getHuristicFuncPrice(neighbours.get(i),ans)+neighbours.get(i).getPrice();
						answer=neighbours.get(i);					
						flag=true;
						neighbours.remove(i);
						i--;

					}

				}
				//Insert the neighbours  in reverse order 
				for (int i =0; i<neighbours.size(); i++) {

					Mystack.add(neighbours.get(neighbours.size()-1-i));
					MyHash.put(neighbours.get(neighbours.size()-1-i).toString(), false);
					MyArray.add(neighbours.get(neighbours.size()-1-i));


				}

			}
		}
		return answer;
	}
}
