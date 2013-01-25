package tetris;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece l1, l2, l3, l4;
	private Piece s, sRotated;
	private Piece[] pieces;

	@Before
	public void setUp() throws Exception {
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		l1 = new Piece(Piece.L1_STR);
		l2 = l1.computeNextRotation();
		l3 = l2.computeNextRotation();
		l4 = l3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		pieces = Piece.getPieces();
	}
	
	// Here are some sample tests to get you started
	
	@Test
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		assertEquals(3, pyr3.getWidth());
		assertEquals(2, pyr3.getHeight());
		
		assertEquals(2, pyr4.getWidth());
		assertEquals(3, pyr4.getHeight());

		// Check size of l piece
		assertEquals(2, l1.getWidth());
		assertEquals(3, l1.getHeight());

		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(3, l2.getWidth());
		assertEquals(2, l2.getHeight());

		assertEquals(2, l3.getWidth());
		assertEquals(3, l3.getHeight());

		assertEquals(3, l4.getWidth());
		assertEquals(2, l4.getHeight());

		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
		
		// Now try with a different piece
		assertEquals(3, s.getWidth());
		assertEquals(2, s.getHeight());

		assertEquals(2, sRotated.getWidth());
		assertEquals(3, sRotated.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	@Test
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, pyr2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, pyr4.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0}, l1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, l2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {2, 0}, l3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1, 1}, l4.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
	}
	
	// Test quick rotate for all pieces
	@Test
	public void testQuickRotate() {
		// Stick
		assertTrue(!pieces[0].fastRotation().equals(pieces[0]));
		assertTrue(pieces[0].fastRotation().fastRotation().equals(pieces[0]));
		
		// L1
		assertTrue(!pieces[1].fastRotation().equals(pieces[1]));
		assertTrue(!pieces[1].fastRotation().fastRotation().equals(pieces[1]));
		assertTrue(!pieces[1].fastRotation().fastRotation().fastRotation().equals(pieces[1]));
		assertTrue(pieces[1].fastRotation().fastRotation().fastRotation().fastRotation().equals(pieces[1]));
		
		// L2
		assertTrue(!pieces[2].fastRotation().equals(pieces[2]));
		assertTrue(!pieces[2].fastRotation().fastRotation().equals(pieces[2]));
		assertTrue(!pieces[2].fastRotation().fastRotation().fastRotation().equals(pieces[2]));
		assertTrue(pieces[2].fastRotation().fastRotation().fastRotation().fastRotation().equals(pieces[2]));

		// S1
		assertTrue(!pieces[3].fastRotation().equals(pieces[3]));
		assertTrue(pieces[3].fastRotation().fastRotation().equals(pieces[3]));
		
		// S2
		assertTrue(!pieces[4].fastRotation().equals(pieces[4]));
		assertTrue(pieces[4].fastRotation().fastRotation().equals(pieces[4]));

		// Square
		assertTrue(pieces[5].fastRotation().equals(pieces[5]));
		
		// Pyramid
		assertTrue(!pieces[6].fastRotation().equals(pieces[6]));
		assertTrue(!pieces[6].fastRotation().fastRotation().equals(pieces[6]));
		assertTrue(!pieces[6].fastRotation().fastRotation().fastRotation().equals(pieces[6]));
		assertTrue(pieces[6].fastRotation().fastRotation().fastRotation().fastRotation().equals(pieces[6]));
	}
	
	
}
