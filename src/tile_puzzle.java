

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;



public class tile_puzzle {
	public enum DIRECTION {LEFT,UP,RIGHT , DOWN}
	private int[][] puzzle;
	private int[][] correctPuzzle;
	private String path = "";

	private ArrayList<String> Pricepath=new ArrayList<String>();
	private int zeroX, zeroY;

	Set<String> red = new HashSet<>();
	Set<String> black = new HashSet<>();
	Set<String> green = new HashSet<>();

	private int price;
	static public int num=0;
	public String previousStep;
	Set<String> list = new HashSet<>();
	int HuristicFuncPrice;
	boolean wasOut=false;


	////////////////////Constructors/////////////////////////

	public tile_puzzle(int[][] puzzle, int[][] correctPuzzle) {
		this.puzzle = puzzle;
		this.correctPuzzle = correctPuzzle;
		findEmptyTile();

	}

	public tile_puzzle(tile_puzzle newPuzzle) {
		puzzle = new int[newPuzzle.puzzle.length][newPuzzle.puzzle[0].length];
		for (int i = 0; i < puzzle.length; i++) {
			puzzle[i] = Arrays.copyOf(newPuzzle.puzzle[i], puzzle[i].length);
		}

		correctPuzzle = newPuzzle.correctPuzzle;
		zeroX = newPuzzle.zeroX;
		zeroY = newPuzzle.zeroY;
		path = newPuzzle.path;
		red= newPuzzle.red;
		black =newPuzzle.black;
		green =newPuzzle.green;
		Pricepath=newPuzzle.Pricepath;
		previousStep=newPuzzle.previousStep;
		price=newPuzzle.price;
		wasOut=newPuzzle.wasOut;



	}

	//Check if we reached to the desired solution

	public boolean isSolved() {
		for (int y = 0; y < puzzle.length; ++y) {
			for (int x = 0; x < puzzle[y].length; ++x) {
				if (puzzle[y][x] != correctPuzzle[y][x]) {
					return false;
				}
			}
		}
		return true;
	}


	//Circuit prevention function
	public static boolean checkIfNodeExistsInList(tile_puzzle x ,Hashtable<tile_puzzle,Boolean> set) {	
		for (tile_puzzle temp : set.keySet()) {
			if (x.puzzle== temp.puzzle) {
				return true;
			}
		}
		return false;

	}

	public static boolean checkIfNodeExistsInList(tile_puzzle x ,Set<tile_puzzle> set) {	
		for (tile_puzzle temp : set) {
			if (x.puzzle== temp.puzzle) {
				return true;
			}
		}
		return false;

	}



	//Checks if the step is valid
	public boolean canMove(DIRECTION direction) {
		switch (direction) {
		case DOWN :

			if (zeroY != puzzle.length - 1 ) {
				if((black!=null && black.contains(""+puzzle[zeroY+1][zeroX]) )|| previousStep=="UP")	
					return false;


				return true;
			}
			break;
		case UP:

			if (zeroY != 0 ) {
				if((black!=null && black.contains(""+puzzle[zeroY-1][zeroX])) || previousStep=="DOWN")	
					return false;

				return true;
			}
			break;
		case RIGHT:
			if (zeroX != puzzle[zeroY].length - 1 ) {
				if((black!=null && black.contains(""+puzzle[zeroY][zeroX+1]))|| previousStep=="LEFT")
					return false;

				return true;
			}
			break;
		case LEFT:
			if (zeroX != 0 ) {
				if((black!=null && black.contains(""+puzzle[zeroY][zeroX-1]) )|| previousStep=="RIGHT")	
					return false;

				return true;
			}
			break;
		}
		return false;
	}


	public tile_puzzle move(DIRECTION direction) {
		int price ;
		String x;
		switch (direction) {
		case DOWN:{
			price= 1;
			x=""+ getPuzzle()[zeroY+1][zeroX];
			if (red != null && red.contains(x) ) {
				price = 30;
			}
			path +="-"+ puzzle[zeroY+1][zeroX]+"U";
			this.price+=price;
			swap(zeroY, zeroX, (zeroY + 1), zeroX);
			previousStep="DOWN";

			return this;
		}
		case UP:{
			price = 1;

			x=""+ this.getPuzzle()[zeroY-1][zeroX];
			if (red != null && red.contains(x)) {
				price = 30;
			}
			path +="-"+puzzle[zeroY-1][zeroX]+ "D";	
			this.price+=price;
			swap(zeroY, zeroX, (zeroY - 1), zeroX);

			previousStep="UP";

			return this;

		}
		case RIGHT:{
			price = 1;
			x=""+ this.getPuzzle()[zeroY][zeroX+1];
			if (red != null && red.contains(x)) {
				price = 30;
			}
			path += "-"+puzzle[zeroY][zeroX+1] +"L";	
			this.price+=price;
			swap(zeroY, zeroX, zeroY, (zeroX + 1));


			previousStep="RIGHT";

			return this;
		}
		case LEFT:{
			price = 1;

			x=""+this.getPuzzle()[zeroY][zeroX-1];
			if (red != null && red.contains(x)){
				price = 30;
			}
			path +="-"+ puzzle[zeroY][zeroX-1]+"R";	
			this.price+=price;
			swap(zeroY, zeroX, zeroY, (zeroX - 1));


			previousStep="LEFT";

			return this;	
		}
		default: {
			return null;
		}
		}
	}


	private void swap(int y1, int x1, int y2, int x2) {
		int previous = getTile(y1, x1);
		setTile(y1, x1, getTile(y2, x2));
		setTile(y2, x2, previous);
		zeroY = y2;
		zeroX = x2;
	}





	//Checks if there are black blocks that make it impossible to solve
	public boolean PossibleToSolve() {
		for (int i = 0; i < correctPuzzle.length; i++) {
			for (int j = 0; j < correctPuzzle[0].length; j++) {

				if((correctPuzzle[i][j]!= this.puzzle[i][j]) && this.puzzle[i][j]!=0) {
					if((black!=null && black.contains(""+this.puzzle[i][j]) ))	
						return false;
				}
			}
		}

		return true;	
	}

	////////Getters and Setters////////
	private void setTile(int y, int x, int tile) {
		puzzle[y][x] = tile;
	}

	private int getTile(int y, int x) {
		return puzzle[y][x];
	}

	private void findEmptyTile() {
		for (int y = 0; y < puzzle.length; ++y) {
			for (int x = 0; x < puzzle[y].length; ++x) {
				if (puzzle[y][x] == 0) {
					zeroY = y;
					zeroX = x;
				}
			}
		}
	}


	public String getPath() {
		String ans="";
		for (int i = 1; i < path.length(); i++) {
			ans+=path.charAt(i);
		}
		return ans;
	}

	public int getPrice() {
		return price;
	}

	public int [][] GetCorrectPuzzle(){
		return correctPuzzle;
	}


	public int getNum() {
		return num;
	}

	public int[][] getPuzzle() {
		return puzzle;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (int y = 0; y < puzzle.length; ++y) {
			for (int x = 0; x < puzzle[y].length; ++x) {
				if(puzzle[y][x]==0)
					output.append("_").append(" ");
				else	
					output.append(puzzle[y][x]).append(" ");
			}
			output.append(System.lineSeparator());
		}
		return output.toString();
	}


}