// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

package assign1;

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
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
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		if (grid == null) return 0;
		int left = 0;
		int right = 0;
		int top = 0;
		int bottom = 0;
		int height = grid.length;
		int width = grid[0].length;
		boolean exist = false;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++){
				if (grid[i][j] == ch) { 
					if (!exist) {
						exist = true;
						left = j;
						right = j;
						top = i;
						bottom = i;
					} else {
						if (left > j) left = j;
						if (right < j) right = j;
						if (top > i) top = i;
						if (bottom < i) bottom = i;
					}
				}
			}
		}
		if (!exist) return 0;
		return (right - left + 1) * (bottom - top + 1);
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		if (grid == null) return 0;
		int height = grid.length;
		int width = grid[0].length;
		if (height < 3 || width < 3) return 0;
		int ret = 0;
		for (int i = 0; i < height; i++) {
			char cur_char = grid[i][0];
			int cur_count = 0;
			for (int j = 0; j < width; j++) {
				if (grid[i][j] == cur_char) { 
					cur_count ++;
				} else if (cur_count >= 3) {
					if (cur_count % 2 != 0) {
						int len = cur_count / 2;
						if (isPlus(j - len - 1, i, width, height, len, cur_char)) ret++;
					}
					cur_count = 1;
					cur_char = grid[i][j];
				} else {
					cur_count = 1;
					cur_char = grid[i][j];
				}
				if (j == width - 1 && cur_count >= 3) {
					if (cur_count % 2 != 0) {
						int len = cur_count / 2;
						if (isPlus(j - len, i, width, height, len, cur_char)) ret++;
					}
				}
			}
		}
		return ret;
	}
	
	private boolean isPlus(int x, int y, int width, int height, int len, char ch) {
		if (x < len || y < len || (width - x - 1 < len) || (height - y - 1 < len)) return false;
		for (int i = x - len; i < x + len; i++) {
			if (grid[y][i] != ch) return false;
		}
		if (x - len > 0 && grid[y][x - len - 1] == ch) return false;
		for (int j = y - len; j < y + len; j++) {
			if (grid[j][x] != ch) return false;
		}
		if (y - len > 0 && grid[y - len - 1][x] == ch) return false;
		return true;
	}
}
