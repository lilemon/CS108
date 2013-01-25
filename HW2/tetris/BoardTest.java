package tetris;

import static org.junit.Assert.*;

import org.junit.*;

public class BoardTest {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	@Before
	public void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		b.place(pyr1, 0, 0);
	}
	
	// Check the basic width/height/max after the one placement
	@Test
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	@Test
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	
	// Place overlapping pieces into the board, then check errors
	@Test
	public void testSample3() {
		b.commit();
		int result;
		String ori = b.toString();
		
		result = b.place(pyr3, 0, 0);
		assertEquals(Board.PLACE_BAD, result);
		if (result == Board.PLACE_BAD) 
			b.undo();
		assertEquals(b.toString(), ori);
		
		result = b.place(pyr3, 1, 5);
		assertEquals(Board.PLACE_OUT_BOUNDS, result);
		if (result == Board.PLACE_OUT_BOUNDS)
			b.undo();
		assertEquals(b.toString(), ori);
			
		result = b.place(pyr3, -1, 0);
		assertEquals(Board.PLACE_OUT_BOUNDS, result);
		if (result == Board.PLACE_OUT_BOUNDS)
			b.undo();
		assertEquals(b.toString(), ori);
		
		result = b.place(pyr3, 0, -1);
		assertEquals(Board.PLACE_OUT_BOUNDS, result);
		if (result == Board.PLACE_OUT_BOUNDS)
			b.undo();
		assertEquals(b.toString(), ori);
		
		result = b.place(pyr3, 2, 6);
		assertEquals(Board.PLACE_OUT_BOUNDS, result);
		if (result == Board.PLACE_OUT_BOUNDS)
			b.undo();
		
		// Testing undo()
		assertEquals(b.toString(), ori);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(-1, b.getColumnHeight(3));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(-1, b.getRowWidth(7));
	}
	
	// Place with row clearing scenario
	@Test
	public void testSample4() {
		b.commit();
		int result;
		
		result = b.place(pyr3, 0, 2);
		assertEquals(Board.PLACE_ROW_FILLED, result);

		assertEquals(4, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));
		assertEquals(-1, b.getColumnHeight(3));
		assertEquals(4, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(3, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(-1, b.getRowWidth(7));
		
		result = b.clearRows();
		assertEquals(2, result);
		
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(-1, b.getColumnHeight(3));
		assertEquals(2, b.getMaxHeight());
		assertEquals(1, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(-1, b.getRowWidth(7));
		
		assertEquals(false, b.getGrid(0, 0));
		assertEquals(false, b.getGrid(0, 1));
		assertEquals(true, b.getGrid(1, 0));
		assertEquals(true, b.getGrid(1, 1));
		assertEquals(false, b.getGrid(2, 0));
		assertEquals(false, b.getGrid(2, 1));
	}
	
	// place with clear consecutive row scenario
	@Test
	public void testSample5() {
		b.commit();
		int result;
		
		result = b.place(pyr3, 0, 2);
		b.commit();
		result = b.place(pyr1, 0, 4);
		assertEquals(Board.PLACE_ROW_FILLED, result);

		assertEquals(5, b.getColumnHeight(0));
		assertEquals(6, b.getColumnHeight(1));
		assertEquals(5, b.getColumnHeight(2));
		assertEquals(-1, b.getColumnHeight(3));
		assertEquals(6, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(3, b.getRowWidth(3));
		assertEquals(-1, b.getRowWidth(7));
		
		result = b.clearRows();
		assertEquals(3, result);
		
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(-1, b.getColumnHeight(3));
		assertEquals(3, b.getMaxHeight());
		assertEquals(1, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(-1, b.getRowWidth(7));
		
		assertEquals(false, b.getGrid(0, 0));
		assertEquals(false, b.getGrid(0, 1));
		assertEquals(false, b.getGrid(0, 2));
		assertEquals(false, b.getGrid(0, 3));
		assertEquals(true, b.getGrid(1, 0));
		assertEquals(true, b.getGrid(1, 1));
		assertEquals(true, b.getGrid(1, 2));
		assertEquals(false, b.getGrid(1, 3));
		assertEquals(false, b.getGrid(2, 0));
		assertEquals(false, b.getGrid(2, 1));
		assertEquals(false, b.getGrid(2, 2));
		assertEquals(false, b.getGrid(2, 3));
	}
	
	// DropHeight test
	@Test
	public void testSample6() {
		b.commit();
		int result;
		result = b.dropHeight(sRotated, 0);
		assertEquals(2, result);

	}	
	
	// Make  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.
	
	
}
