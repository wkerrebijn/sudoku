import java.util.ArrayList;

public class Sudoku {
	
	int width;
	int height;
	
	boolean madeProgress;
	
	Cell[][] field;
		
	public Sudoku(int w, int h) {
		width = w;
		height = h;
		
		reset();
	}
	
	public void reset() {
		madeProgress = true;
		
		field = new Cell[width][height];
		
		for(int j = 1 ; j <= height ; ++j) {
			for(int i = 1 ; i <= width ; ++i) {
				field[i-1][j-1] = new Cell(i, j, 9, false);
			}
		}
	}
	
	public boolean fill(String s) {
		reset();
		if(s.length() != width*height) {
			System.out.println("Incorrect number of input elements!\n");
			return false;
		} else {
			int number;
			for(int i = 0 ; i < s.length() ; ++i) {
				number = Character.getNumericValue(s.charAt(i));
				if(number < 0) {
					System.out.println("Non-positive integer element detected!\n");
					return false;
				} else {
					setElement(number,i);
				}
			}
		}
		System.out.println("      S u d o k u :\n");
		print();
		return true;
	}
	
	public void setElement(int element, int place) {
		int x = place%width;
		int y = place/height;
		
		if(element == 0) {
			field[x][y] = new Cell(x+1 , y+1, 9, false);
		} else {
			field[x][y] = new Cell(x+1 , y+1, element, true);
		}
	}
	
	public void solve(String input) {
		if(!fill(input)) {
			return;
		}
		while(true) {
			if(isSolved()) {
				System.out.println("    S o l u t i o n :\n");
				print();				
				return;
			} else {
				if(!madeProgress) {
					System.out.println("    S o l u t i o n :\n\nCannot be solved (any further) by conventional methods!\n");
					return;
				} else {
					madeProgress = false;
					removeStep();
					solveStep();				
				}
			}
		} 
	}
	
	public boolean isSolved() {
		int sum = 0;
		int max = width*height;
		
		for(int j = 0 ; j < height ; ++j) {
			for(int i = 0 ; i < width ; ++i) {
				int size = field[i][j].getOptions().size();
				sum += size;
				if(sum > max) {
					return false;
				}				
			}
		}
		return true;
	}
	
	public void removeStep() {
		for(int j = 0 ; j < height ; ++j) {
			for(int i = 0 ; i < width ; ++i) {
				ArrayList<Integer> options = field[i][j].getOptions();
				if(options.size() == 1) {
					int option = options.get(0);
					removeOptionFromBlock(i, j, option);
					removeOptionFromHorizontalLine(i, j, option);
					removeOptionFromVerticalLine(i, j, option);					
				}
			}
		}
	}
	
	public void removeOptionFromBlock(int i, int j, int option) {
		int x = i - (i%3);
		int y = j - (j%3);
		for(int n = y ; n <= y+2 ; ++n) {
			for(int m = x ; m <= x+2 ; ++m) {
				if(!(i == m && j == n)) {
					if(field[m][n].getOptions().size() > 1) {
						if(field[m][n].removeOption((Integer)option)) { // remove option from cells in the same 3x3 block of cells
							madeProgress = true;
						}
					}
				}
			}
		}
	}
	
	public void removeOptionFromHorizontalLine(int i, int j, int option) {
		for(int k = 0 ; k < width ; ++k) {
			if(k != i) {
				if(field[k][j].getOptions().size() > 1) {
					if(field[k][j].removeOption((Integer)option)) { // remove option from cells on the same horizontal position
						madeProgress = true;
					}
				}  
			}
		}
	}
	
	public void removeOptionFromVerticalLine(int i, int j, int option) {
		for(int l = 0 ; l < height ; ++l) {
			if(l != j) {
				if(field[i][l].getOptions().size() > 1) {
					if(field[i][l].removeOption((Integer)option)) { // remove option from cells on the same vertical position
						madeProgress = true;
					}
				} 
			}
		}
	}
	
	public void solveStep() {
		for(int j = 0 ; j < height ; ++j) {
			for(int i = 0 ; i < width ; ++i) {
				if(field[i][j].getOptions().size() > 1) {					
					Cell cell = field[i][j];				
					solveCell(cell, getBlock(cell));
					solveCell(cell, getLineHorizontal(cell));
					solveCell(cell, getLineVertical(cell));
				}			
			}
		}
	}
	
	public ArrayList<Cell> getBlock(Cell cell) {
		int x = cell.x-1;
		int y = cell.y-1;
		
		int x0 = x - (x%3);
		int y0 = y - (y%3);
		
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		
		for(int j = y0 ; j <= y0+2 ; ++j) {
			for(int i = x0 ; i <= x0+2 ; ++i) {
				Cell neighbor = field[i][j];
				if(!(neighbor.x == cell.x && neighbor.y == cell.y)) {
					neighbors.add(neighbor);
				}
			}
		}
		return neighbors;
	}
	
	public ArrayList<Cell> getLineHorizontal(Cell cell) {
		int x = cell.x-1;
		int y = cell.y-1;
		
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		
		for(int k = 0 ; k < width ; ++k) {
			if(k != x) {
				Cell neighbor = field[k][y];
				neighbors.add(neighbor); // remove option from cells on the same horizontal position 
			}
		}		
		return neighbors;
	}
	
	public ArrayList<Cell> getLineVertical(Cell cell) {
		int x = cell.x-1;
		int y = cell.y-1;
		
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		
		for(int l = 0 ; l < height ; ++l) {
			if(l != y) {
				Cell neighbor = field[x][l];
				neighbors.add(neighbor); // remove option from cells on the same horizontal position
			}
		}		
		return neighbors;
	}
	
	public void solveCell(Cell cell, ArrayList<Cell> neighbors) {
		int x = cell.x-1;
		int y = cell.y-1;
		
		for(int option : cell.getOptions()) {
			if(!isInNeighbors(option, neighbors)) {
				field[x][y].setFinalOption(option);
				madeProgress = true;
				break;
			}
		}
	}
	
	public boolean isInNeighbors(int option, ArrayList<Cell> neighbors) {
		for(Cell neighbor : neighbors) {
			if(neighbor.getOptions().contains((Integer)option)) {
				return true;
			}
		}
		return false;
	}

	public void print() {
		System.out.print("  X ");
		for(int k = 1 ; k <= width ; ++k) {
			System.out.print(k + " ");
		}
		System.out.println();
		System.out.print("Y   ");
		for(int k = 1 ; k <= width ; ++k) {
			System.out.print("_ ");
		}
		System.out.println();
		for(int j = 1 ; j <= height ; ++j) {
			System.out.print(j + "  |");
			for(int i = 1 ; i <= width ; ++i) {
				int optionSize = field[i-1][j-1].getOptions().size();
				if(optionSize > 1) {
					System.out.print("_|");
				} else {
					if(optionSize == 1) {
						int number = field[i-1][j-1].getOptions().get(0);
						System.out.print(number + "|");
					} else {
						System.out.print("*|");
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
