// Test cases for CharGrid -- a few basic tests are provided.
package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class CharGridTest {
	//
	// charArea
	//
	@Test
	// basic case with small dimension
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};

		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
		assertEquals(0, cg.charArea('w'));
	}
	
	
	@Test
	// basic case with enough dimension
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}
	
	@Test
	// basic case with enlarged area
	public void testCharArea3() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'},
				{' ', 'y', 'c'},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(12, cg.charArea('c'));
		assertEquals(12, cg.charArea(' '));
	}
	
	//
	// countPlus
	//
	@Test
	// base case with just enough dimension
	public void testCountPlus1() {
		char[][] grid = new char[][] {
				{' ', 'b', ' '},
				{'b', 'b', 'b'},
				{' ', 'b', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}
	
	@Test
	// base case with enlarged dimension
	public void testCountPlus2() {
		char[][] grid = new char[][] {
				{' ', 'b', ' ', ' '},
				{'b', 'b', 'b', ' '},
				{' ', 'b', 'a', ' '},
				{' ', ' ', 'a', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}
	
	@Test
	// case with extra 'b' on one arm
	public void testCountPlus3() {
		char[][] grid = new char[][] {
				{' ', 'b', ' ', ' '},
				{'b', 'b', 'b', 'b'},
				{' ', 'b', 'a', ' '},
				{' ', ' ', 'a', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}
	
	@Test
	// case with two check on the same line but only one cross
	public void testCountPlus4() {
		char[][] grid = new char[][] {
				{' ', 'b', ' ', ' ', ' ', 'b', ' ', ' '},
				{'b', 'b', 'b', ' ', 'b', 'b', 'b', 'b'},
				{' ', 'b', 'a', ' ', ' ', 'b', 'a', ' '},
				{' ', ' ', 'a', ' ', ' ', ' ', 'a', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}
	
	@Test
	// case with two crosses on the same line
	public void testCountPlus5() {
		char[][] grid = new char[][] {
				{' ', 'b', ' ', ' ', ' ', 'b', ' ', ' '},
				{'b', 'b', 'b', ' ', 'b', 'b', 'b', ' '},
				{' ', 'b', 'a', ' ', ' ', 'b', 'a', ' '},
				{' ', ' ', 'a', ' ', ' ', ' ', 'a', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(2, cg.countPlus());
	}
	
	@Test
	// altered book case with ' ' cross inserted
	public void testCountPlus6() {
		char[][] grid = new char[][] {
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
				{'p', 'p', 'p', 'p', 'p', 'y', 'x', 'x', 'x'},
				{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
				{' ', ' ', 'p', ' ', 'y', 'y', 'y', ' ', 'z'},
				{' ', ' ', ' ', ' ', ' ', 'y', ' ', ' ', ' '},
				{' ', 'z', 'z', 'z', ' ', ' ', 'z', ' ', 'z'},
			};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(3, cg.countPlus());
	}
	
	@Test
	// case of cross touching bottom right
	public void testCountPlus7() {
		char[][] grid = new char[][] {
				{' ', 'b', ' ', ' '},
				{'b', 'b', 'a', ' '},
				{' ', 'a', 'a', 'a'},
				{' ', ' ', 'a', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}
	
	@Test
	// case of cross touching bottom left
	public void testCountPlus8() {
		char[][] grid = new char[][] {
				{' ', 'b', ' ', ' '},
				{'b', 'a', 'b', ' '},
				{'a', 'a', 'a', 'b'},
				{' ', 'a', 'b', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}
	
	@Test
	// case of cross touching upper right, two crosses sharing elements
	public void testCountPlus9() {
		char[][] grid = new char[][] {
				{' ', 'b', 'a', ' '},
				{'b', 'a', 'a', 'a'},
				{'a', 'a', 'a', 'b'},
				{' ', 'a', 'b', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(2, cg.countPlus());
	}
	
	@Test
	// case of all-around-the-world
	public void testCountPlus10() {
		char[][] grid = new char[][] {
				{'a', 'a', 'a', 'a'},
				{'a', ' ', ' ', 'a'},
				{'a', ' ', ' ', 'a'},
				{'a', 'a', 'a', 'a'},
			};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}
}
