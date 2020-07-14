

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
public class Ex1 {

	static boolean OPEN;
	static int stepsCounterInNullCase=0;
	public static void main(String[] args) throws FileNotFoundException {
		//////////Read all the input from the file/////////////
		
		puzzleLoader puzzleLoader = new puzzleLoader();
		tile_puzzle puzzle ;
		int[][] correctPuzzle;
		int[][] puzzleToSolve;
		puzzleToSolve = puzzleLoader.load("input.txt");
		String type = puzzleLoader.loadAlgoType("input.txt");
		boolean whithTime = puzzleLoader.loadAlgoWhithTime("input.txt");
		OPEN = puzzleLoader.whithOpen("input.txt");
		
		//insert the solution into new tile_puzzle
		correctPuzzle = generateCorrectPuzzle(puzzleToSolve.length, puzzleToSolve[0].length);    
		puzzle = new tile_puzzle(puzzleToSolve, correctPuzzle);
		puzzle.black=puzzleLoader.loadBlack("input.txt");
		puzzle.red=puzzleLoader.loadRed("input.txt");
		puzzle.green=puzzleLoader.loadGreen("input.txt");

		long timeStart;
		long timeStop;
		tile_puzzle.DIRECTION[] strategy = {tile_puzzle.DIRECTION.LEFT ,tile_puzzle.DIRECTION.UP ,  tile_puzzle.DIRECTION.RIGHT , tile_puzzle.DIRECTION.DOWN};

		try {
			FileWriter myWriter = new FileWriter("output.txt");

			switch (type) {
			case "BFS": {	

				BFS solverBFS = new BFS();	
				timeStart = System.nanoTime();
				tile_puzzle solvedPuzzle = solverBFS.solve(puzzle, strategy);
				timeStop = System.nanoTime();
				if(solvedPuzzle==null) {
					myWriter.write("no path \n");
					myWriter.write("Num: "+stepsCounterInNullCase+"\n");
				}
				else {
					myWriter.write(solvedPuzzle.getPath()+"\n");
					myWriter.write("Num: "+solvedPuzzle.getNum()+"\n");
					myWriter.write("Cost: "+solvedPuzzle.getPrice()+"\n");
					if(whithTime==true)
						myWriter.write(((timeStop - timeStart) / 1000000000.0) + " seconds \n");
				}
				myWriter.close();

				break;
			}
			case "DFID": {
				DFID solveDFID = new DFID();
				timeStart = System.nanoTime();
				tile_puzzle solvedPuzzle2 = solveDFID.solveDFID(puzzle, strategy);
				timeStop = System.nanoTime();
				if(solvedPuzzle2==null) {
					myWriter.write("no path \n");
					myWriter.write("Num: "+stepsCounterInNullCase+"\n");
				}
				else {
					myWriter.write(solvedPuzzle2.getPath()+"\n");
					myWriter.write("Num: "+solvedPuzzle2.getNum()+"\n");
					myWriter.write("Cost: "+solvedPuzzle2.getPrice()+"\n");
					if(whithTime==true)
						myWriter.write(((timeStop - timeStart) / 1000000000.0) + " seconds \n");

				}
				myWriter.close();

				break;

			}
			case "A*": {
				A solveAstar = new A();
				timeStart = System.nanoTime();
				tile_puzzle solvedPuzzle2 = solveAstar.solveAstar(puzzle, strategy);
				timeStop = System.nanoTime();
				if(solvedPuzzle2==null) {
					myWriter.write("no path \n");
					myWriter.write("Num: "+stepsCounterInNullCase+"\n");
				}
				else {
					myWriter.write(solvedPuzzle2.getPath()+"\n");
					myWriter.write("Num: "+solvedPuzzle2.getNum()+"\n");
					myWriter.write("Cost: "+solvedPuzzle2.getPrice()+"\n");
					if(whithTime==true)
						myWriter.write(((timeStop - timeStart) / 1000000000.0) + " seconds \n");

				}
				myWriter.close();

				break;

			}
			case "IDA*": {
				IDA solveIDAstar = new IDA();
				timeStart = System.nanoTime();
				tile_puzzle solvedPuzzle2 = solveIDAstar.solveIDAstar(puzzle, strategy);
				timeStop = System.nanoTime();
				if(solvedPuzzle2==null) {
					myWriter.write("no path \n");
					myWriter.write("Num: "+stepsCounterInNullCase+"\n");
				}
				else {
					myWriter.write(solvedPuzzle2.getPath()+"\n");
					myWriter.write("Num: "+solvedPuzzle2.getNum()+"\n");
					myWriter.write("Cost: "+solvedPuzzle2.getPrice()+"\n");
					if(whithTime==true)
						myWriter.write(((timeStop - timeStart) / 1000000000.0) + " seconds \n");

				}
				myWriter.close();

				break;

			}
			case "DFBnB": {
				DFBnb solveDFBnb = new DFBnb();
				timeStart = System.nanoTime();
				tile_puzzle solvedPuzzle2 = solveDFBnb.solveDFBnb(puzzle, strategy);
				timeStop = System.nanoTime();

				if(solvedPuzzle2==null) {
					myWriter.write("no path \n");
					myWriter.write("Num: "+stepsCounterInNullCase+"\n");
				}
				else {
					myWriter.write(solvedPuzzle2.getPath()+"\n");
					myWriter.write("Num: "+solvedPuzzle2.getNum()+"\n");
					myWriter.write("Cost: "+solvedPuzzle2.getPrice()+"\n");
					if(whithTime==true)
						myWriter.write(((timeStop - timeStart) / 1000000000.0) + " seconds \n");

				}
				myWriter.close();


				break;

			}
			default:
				myWriter.close();
				throw new Error("there is no such algorithim ! ! !"); 
			}



		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	
	
	
	//func that find the right solution For comparison
	private static int[][] generateCorrectPuzzle(int xSize, int ySize) {
		int[][] correctPuzzle = new int[xSize][ySize];
		int counter = 1;       
		for (int x = 0; x < xSize; ++x) {
			for (int y = 0; y < ySize; ++y) {
				correctPuzzle[x][y] = counter;
				++counter;
			}
		}
		correctPuzzle[xSize - 1][ySize - 1] = 0;
		return correctPuzzle;
	}
}
