//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.
package assign1;

public class TetrisGrid {
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	private boolean[][] grid;
	private int width;
	private int height;
	
	private void printGrid() {
		int width = grid.length;
		int height = grid[0].length; 
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				System.out.print(grid[i][j]);
				System.out.print(" ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public TetrisGrid(boolean[][] grid) {
		assert(grid != null);
		this.grid = grid;
		this.width = grid.length;
		this.height = grid[0].length;
	}
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		// clearBoard();
		for (int y = 0; y < height; y++) {
			boolean clear = true;
			for (int x = 0; x < width; x++)
				clear = clear && grid[x][y];
			if (clear) {
				for (int i = y; i < height - 1; i++) {
					for (int j = 0; j < width; j++)
					grid[j][i] = grid[j][i + 1];
				}
				for (int j = 0; j < width; j++)
					grid[j][height - 1] = false;
				y = y - 1;
			} 
		}
	}
	
	// this is to handle when there's an empty row. Since Professor Young says that there will not be any empty row, this method is never used
	private void clearBoard() {
		for (int y = 0; y < height; y++) {
			boolean keep = false;
			for (int x = 0; x < width; x++)
				keep = keep || grid[x][y];
			if (!keep) {
				for (int i = y + 1; i < height; i++) {
					for (int j = 0; j < width; j++) {
						keep = keep || grid[j][i];
					}
				}
				if (!keep) return;
				for (int i = y; i < height - 1; i++) {
					for (int j = 0; j < width; j++)
					grid[j][i] = grid[j][i + 1];
				}
				for (int j = 0; j < width; j++)
					grid[j][height - 1] = false;
				y = y - 1;
				keep = false;
			} 
		}
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid;
	}
}
