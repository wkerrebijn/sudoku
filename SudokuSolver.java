public class SudokuSolver {
	
	public static Sudoku sudoku;

	public static void main(String[] args) {
		sudoku = new Sudoku(9, 9);
		
		String string1 = "000820090"
					   + "500000000"
					   + "308040007"
					   + "100000040"
					   + "006402503"
					   + "000090010"
					   + "093004000"
					   + "004035200"
					   + "000700900";
		
		String string2 = "053004009"
				       + "604810003"
				       + "809003000"
				       + "070000198"
				       + "040008070"
				       + "198570002"
				       + "007060981"
				       + "465001237"
				       + "001230465";
		
		String string3 = "800000000"
			           + "003600000"
			           + "070090200"
			           + "050007000"
			           + "000045700"
			           + "000100030"
			           + "001000068"
			           + "008500010"
			           + "090000400";
		
		String string4 = "002008050"
				       + "000040070"
				       + "480072000"
				       + "008000031"
				       + "600080005"
				       + "570000600"
				       + "000960048"
				       + "090020000"
				       + "030800900";
			
		sudoku.solve(string1);
		sudoku.solve(string2);
		sudoku.solve(string3);
		sudoku.solve(string4);	
	}
}