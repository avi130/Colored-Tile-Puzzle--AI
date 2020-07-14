

import java.io.BufferedReader;
import java.io.FileReader;
// ? 2019 TheFlyingKeyboard and released under MIT License
// theflyingkeyboard.net
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class puzzleLoader {

static int count;



	public String loadAlgoType(String fileName) {
		BufferedReader br;
		String line;
		try {
			br = new BufferedReader(new FileReader(fileName));
			line = br.readLine();
			br.close();
			return line;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public boolean loadAlgoWhithTime(String fileName) {
		BufferedReader br;
		String line;
		try {
			br = new BufferedReader(new FileReader(fileName));
			line = br.readLine();
			line = br.readLine();

			if(line.equals("no time")) {
				br.close();
				return false;
			}
			else if(line.equals("whith time")) {
				br.close();
				return true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public boolean whithOpen(String fileName) {
		BufferedReader br;
		String line;
		try {
			br = new BufferedReader(new FileReader(fileName));
			line = br.readLine();
			line = br.readLine();
			line = br.readLine();
			if(line.equals("no open") ) {
				br.close();
				return false;
			}
			else if(line.equals("with open")) {
				br.close();
				return true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}


//////////////////////////////load puzzle and colors////////////////////////////
	
	public int[][] load(String fileName) {
		int[][] puzzle;
		BufferedReader br;


		String line;
		String cvsSplitBy = "x";
		try {
			br = new BufferedReader(new FileReader(fileName));
			line = br.readLine();
			for (int i = 0; i < 3; i++) {
				line = br.readLine();
			}
			String[] size = line.split(cvsSplitBy);
			puzzle = new int[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
			count= puzzle[0].length*puzzle.length; 
			int puzzleLine = 0;
			cvsSplitBy = ",";

			for (int i = 0; i < 2; i++) {
				line = br.readLine();
			}
			String temp="_";
			while ((line = br.readLine()) != null) {
				String[] tiles = line.split(cvsSplitBy);
				for (int i = 0; i < puzzle[0].length; ++i) {
					if(tiles[i].equals(temp)) {
						tiles[i]="0";
					}
					puzzle[puzzleLine][i] = Integer.parseInt(tiles[i]);
				}
				++puzzleLine;
			}
			br.close();

			return puzzle;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Set<String> loadBlack(String fileName) {
		try {
			BufferedReader br ;
			br = new BufferedReader(new FileReader(fileName));
			String line=null;
			String cvsSplitBy = ":";

			for (int i = 0; i < 5; i++) {
				line = br.readLine();
			}

			String[] fullLine ;
			fullLine = line.split( cvsSplitBy );
			if(fullLine.length>1) {
				String lastOne = fullLine[fullLine.length-1];
				cvsSplitBy = ",";
				String[] black= lastOne.split(cvsSplitBy);
				Set<String> black2 = new HashSet<>();
				for (int i = 0; i < black.length; i++) {
					black2.add(black[i]);
					count--;
				}
				return black2;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Set<String> loadRed(String fileName) {
		try {
			BufferedReader br ;
			br = new BufferedReader(new FileReader(fileName));
			String line=null;
			String cvsSplitBy = " ";

			for (int i = 0; i < 6; i++) {
				line = br.readLine();
			}

			String[] fullLine ;
			fullLine = line.split( cvsSplitBy );
			if(fullLine.length>1) {
				String lastOne = fullLine[fullLine.length-1];
				cvsSplitBy = ",";
				String[] red= lastOne.split(cvsSplitBy);			
				Set<String> red2 = new HashSet<>();
				for (int i = 0; i < red.length; i++) {
					red2.add(red[i]);
				}


				return red2;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public Set<String> loadGreen(String fileName) {
		try {

			int[][] temp=load(fileName);
			Set<String> red2 = new HashSet<>();
			Set<String> black2 = new HashSet<>();
			Set<String> green = new HashSet<>();

			red2=loadRed(fileName);
			black2=loadBlack(fileName);


			for (int i = 0; i < temp.length; i++) {
				for (int j = 0; j < temp[0].length; j++) {
					if(black2!=null && red2!=null ) {
						if(!red2.contains(""+temp[i][j]) && !black2.contains(""+temp[i][j]) ) {
							green.add(""+temp[i][j]);
						}	
					}
					else if(black2!=null && red2==null) {
						if( !black2.contains(""+temp[i][j]) ) {
							green.add(""+temp[i][j]);
						}	
					}
					else if(black2==null && red2!=null) {
						if(!red2.contains(""+temp[i][j])  ) {
							green.add(""+temp[i][j]);
						}	
					}

				}
			}


			return green;




		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}