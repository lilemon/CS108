package assign3;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.

	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
			"1 6 4 0 0 0 0 0 2", "2 0 0 4 0 3 9 1 0", "0 0 5 0 8 0 4 0 7",
			"0 9 0 0 0 6 5 0 0", "5 0 0 1 0 2 0 0 8", "0 0 8 9 0 0 0 3 0",
			"8 0 9 0 4 0 2 0 0", "0 7 3 5 0 9 0 0 1", "4 0 0 0 0 0 6 7 9");

	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid("530070000",
			"600195000", "098000060", "800060003", "400803001", "700020006",
			"060000280", "000419005", "000080079");

	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
			"3 7 0 0 0 0 0 8 0", "0 0 1 0 9 3 0 0 0", "0 4 0 7 8 0 0 0 3",
			"0 9 3 8 0 0 0 1 2", "0 0 0 0 4 0 0 0 0", "5 2 0 0 0 6 7 9 0",
			"6 0 0 0 2 1 0 4 0", "0 0 0 5 3 0 9 0 0", "0 3 0 0 0 0 0 5 1");

	public static final int SIZE = 9; // size of the whole 9x9 puzzle
	public static final int PART = 3; // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;

	// Provided various static utility methods to
	// convert data formats to int[][] grid.

	/**
	 * Returns a 2-d grid parsed from strings, one string per row. The "..." is
	 * a Java 5 feature that essentially makes "rows" a String[] array.
	 * (provided utility)
	 * 
	 * @param rows
	 *            array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row < rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}

	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid. Skips
	 * all the non-numbers in the text. (provided utility)
	 * 
	 * @param text
	 *            string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE * SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:"
					+ nums.length);
		}

		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}

	/**
	 * Given a string containing digits, like "1 23 4", returns an int[] of
	 * those digits {1 2 3 4}. (provided utility)
	 * 
	 * @param string
	 *            string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i = 0; i < string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i + 1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}

	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		System.out.println(sudoku);

		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}

	public static final int COUNT = 9;
	public static final int TOTAL = 55;

	public List<Spot> pList;
	public int[][] board;
	public String solution;
	public boolean solved;
	public static int solCount;
	public List<HashSet<Integer>> rows;
	public List<HashSet<Integer>> cols;
	public List<HashSet<Integer>> grids;

	public long start;
	public long end;

	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		pList = new ArrayList<Spot>();
		rows = new ArrayList<HashSet<Integer>>();
		cols = new ArrayList<HashSet<Integer>>();
		grids = new ArrayList<HashSet<Integer>>();

		board = ints.clone();
		solved = false;

		for (int x = 0; x < COUNT; x++) {
			HashSet<Integer> tempRow = new HashSet<Integer>();
			HashSet<Integer> tempCol = new HashSet<Integer>();
			HashSet<Integer> tempGrid = new HashSet<Integer>();
			for (int y = 0; y < COUNT; y++) {
				if (board[x][y] != 0 && tempRow.contains(board[x][y]))
					throw new IllegalArgumentException();
				else
					tempRow.add(board[x][y]);
				if (board[y][x] != 0 && tempCol.contains(board[y][x]))
					throw new IllegalArgumentException();
				else
					tempCol.add(board[y][x]);
				if (board[(x % 3) * 3 + (y % 3)][(x / 3) * 3 + (y / 3)] != 0
						&& tempGrid
								.contains(board[(x % 3) * 3 + (y % 3)][(x / 3)
										* 3 + (y / 3)]))
					throw new IllegalArgumentException();
				else
					tempGrid.add(board[(x % 3) * 3 + (y % 3)][(x / 3) * 3
							+ (y / 3)]);
			}
			rows.add(tempRow);
			cols.add(tempCol);
			grids.add(tempGrid);
		}

		for (int x = 0; x < COUNT; x++) {
			for (int y = 0; y < COUNT; y++) {
				Spot spot = new Spot(x, y, (x / 3) + (y / 3) * 3, board[x][y]);
				if (spot.getVal() == 0) {
					spot.count = getOptions(spot).size();
					pList.add(spot);
				}
			}
		}
		Collections.sort(pList);
		solCount = 0;
	}

	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		start = System.currentTimeMillis();
		recSolve(pList);
		end = System.currentTimeMillis();
		return solCount; // YOUR CODE HERE
	}

	private void recSolve(List<Spot> l) {
		if (solCount >= MAX_SOLUTIONS)
			return;
		Spot spot = l.get(0);
		List<Integer> options = getOptions(spot);

		if (l.size() == 1) {
			if (options.size() > 0) {
				if (!solved) {
					solved = true;
					board[spot.getX()][spot.getY()] = options.get(0);
					solution = this.toString();
					board[spot.getX()][spot.getY()] = 0;
				}
				solCount++;
			}
		} else {
			List<Spot> nxtL = l.subList(1, l.size());
			for (int option : options) {
				board[spot.getX()][spot.getY()] = option;
				rows.get(spot.getX()).add(option);
				cols.get(spot.getY()).add(option);
				grids.get(spot.getG()).add(option);
				recSolve(nxtL);
				board[spot.getX()][spot.getY()] = 0;
				rows.get(spot.getX()).remove(option);
				cols.get(spot.getY()).remove(option);
				grids.get(spot.getG()).remove(option);
			}
		}
	}

	public List<Integer> getOptions(Spot spot) {
		HashSet<Integer> tempSet = new HashSet<Integer>();
		tempSet.addAll(rows.get(spot.getX()));
		tempSet.addAll(cols.get(spot.getY()));
		tempSet.addAll(grids.get(spot.getG()));

		List<Integer> ret = new ArrayList<Integer>();
		for (int i = 1; i <= COUNT; i++) {
			if (!tempSet.contains(i)) {
				ret.add(i);
			}
		}
		return ret;
	}

	public String getSolutionText() {
		if (solved)
			return solution;
		else
			return "no solution found\n";
	}

	public long getElapsed() {
		return end - start;
	}

	public class Spot implements Comparable<Spot> {
		private int x;
		private int y;
		private int g;
		private int val;
		public int count;

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getG() {
			return g;
		}

		public int getVal() {
			return val;
		}

		public void set(int val) {
			this.val = val;
		}

		public Spot(int x, int y, int g, int val) {
			this.x = x;
			this.y = y;
			this.g = g;
			this.val = val;
			count = 0;
		}

		public int compareTo(Spot s) {
			if (count == s.count) {
				if (x == s.getX()) {
					return (y < s.getY()) ? -1 : 1;
				}
				return (x < s.getX()) ? -1 : 1;
			}
			return (count < s.count) ? -1 : 1;
		}
	}

	public String toString() {
		return intsToString(board);
	}

	private String intsToString(int[][] ints) {
		String ret = "";
		try {
			for (int x = 0; x < COUNT; x++) {
				for (int y = 0; y < COUNT; y++) {
					ret = ret + ints[x][y] + " ";
				}
				ret = ret + "\n";
			}
		} catch (Exception e) {

		}
		return ret;
	}
}
