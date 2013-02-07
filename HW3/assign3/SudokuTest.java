package assign3;

import java.util.*;

import assign3.Sudoku.Spot;
import junit.framework.TestCase;

public class SudokuTest extends TestCase {

	private Sudoku sudoku = new Sudoku(easyGrid);

	public static final int[][] easyGrid = Sudoku.stringsToGrid(
			"1 6 4 7 0 0 0 0 2", "2 8 7 4 0 3 9 1 0", "0 3 5 0 8 1 4 6 7",
			"0 9 0 0 0 6 5 0 4", "5 0 0 1 0 2 7 0 8", "0 0 8 9 0 0 0 3 0",
			"8 0 9 0 4 0 2 5 0", "6 7 3 5 0 9 0 0 1", "4 0 0 0 0 0 6 7 9");
	
	public static final int[][] easierGrid = Sudoku.stringsToGrid(
			"1 6 4 0 0 0 0 0 2", "2 0 0 4 0 3 9 1 0", "0 0 5 0 8 0 4 0 7",
			"0 9 0 0 0 6 5 0 0", "5 0 0 1 0 2 0 0 8", "0 0 8 9 0 0 0 3 0",
			"8 0 9 0 4 0 2 0 0", "0 7 3 5 0 9 0 0 1", "4 0 0 0 0 0 6 7 9");
	
	public void testSpot() {
		System.out.println(sudoku);
		for (Spot spot : sudoku.pList) {
			System.out.print(sudoku.pList.indexOf(spot) + " x: " + spot.getX() + " y: " + spot.getY()
					+ " g: " + spot.getG() + " count: " + spot.count);
			List<Integer> options = sudoku.getOptions(spot);
			System.out.print(" options: ");
			for (int option : options) {
				System.out.print(option + " ");
			}
			System.out.println("");
		}
	}

	/*
	 * public void testStringsToGrid() { fail("Not yet implemented"); }
	 * 
	 * public void testTextToGrid() { fail("Not yet implemented"); }
	 * 
	 * public void testStringToInts() { fail("Not yet implemented"); }
	 * 
	 * public void testMain() { fail("Not yet implemented"); }
	 * 
	 * public void testSudoku() { fail("Not yet implemented"); }
	 * 
	 * public void testSolve() { fail("Not yet implemented"); }
	 * 
	 * public void testGetSolutionText() { fail("Not yet implemented"); }
	 * 
	 * public void testGetElapsed() { fail("Not yet implemented"); }
	 * 
	 * public void testToString() { fail("Not yet implemented"); }
	 */

}
