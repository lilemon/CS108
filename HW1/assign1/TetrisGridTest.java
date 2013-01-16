package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class TetrisGridTest {
	
	// Provided simple clearRows() test
	// width 2, height 3 grid
	@Test
	public void testClear1() {
		// basic 2x3 case
		boolean[][] before =
		{	
			{true, true, false, },
			{false, true, true, }
		};
		
		boolean[][] after =
		{	
			{true, false, false},
			{false, true, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear2() {
		// basic case in hangout
		boolean[][] before =
		{	
			{true, true, true, false, true, false, false},
			{true, true, true, true, false, false, false},
			{true, false, true, true, false, false, false}
		};
		
		boolean[][] after =
		{	
			{true, false, true, false, false, false, false},
			{true, true, false, false, false, false, false},
			{false, true, false, false, false, false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear3() {
		// case with 1x1 grid
		boolean[][] before =
		{	
			{true}
		};
		
		boolean[][] after =
		{	
			{false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear4() {
		// case with 1x1 grid
		boolean[][] before =
		{	
			{false}
		};
		
		boolean[][] after =
		{	
			{false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear5() {
		// case with no removal
		boolean[][] before =
		{	
			{true, true, true, false, true, false, false},
			{false, true, false, true, false, false, false},
			{true, false, true, true, false, false, false}
		};
		
		boolean[][] after =
		{	
			{true, true, true, false, true, false, false},
			{false, true, false, true, false, false, false},
			{true, false, true, true, false, false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	// case with extra wide board
	public void testClear6() {
		boolean[][] before =
		{	
			{true, false},
			{true, false},
			{true, true},
			{true, false},
			{true, false},
		};
		
		boolean[][] after =
		{	
			{false, false},
			{false, false},
			{true, false},
			{false, false},
			{false, false},
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	/*
	@Test
	public void testClear7() {
		// case with false removal (in case calcualted while some block is still in air)
		boolean[][] before =
		{	
			{true, true, true, false, true, false, false},
			{false, true, false, false, false, false, false},
			{true, false, true, false, false, false, false}
		};
		
		boolean[][] after =
		{	
			{true, true, true, true, false, false, false},
			{false, true, false, false, false, false, false},
			{true, false, true, false, false, false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}*/
}
