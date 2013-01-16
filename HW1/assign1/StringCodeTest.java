// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.

package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class StringCodeTest {
	//
	// blowup
	//
	@Test
	public void testBlowup1() {
		// basic cases
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
	}
	
	@Test
	public void testBlowup2() {
		// things with digits
		
		// digit at end
		assertEquals("axxx", StringCode.blowup("a2x3"));
		
		// digits next to each other
		assertEquals("a33111", StringCode.blowup("a231"));
		
		// try a 0
		assertEquals("aabb", StringCode.blowup("aa0bb"));
		
		// try negative
		assertEquals("aa-bbb", StringCode.blowup("aa-1bb"));
		
	}
	
	@Test
	public void testBlowup3() {
		// weird chars, empty string, trivial case
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
		assertEquals("", StringCode.blowup(""));
		assertEquals(null, StringCode.blowup(null));		
		
		// string with only digits
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));
	}
	
	//
	// maxRun
	//
	@Test
	public void testRun1() {
		// test first appearance and late appearance
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
	}
	
	@Test
	public void testRun2() {
		// test equal appearance, last digit, empty string, trivial cases
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(4, StringCode.maxRun("hhhooppoooo"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(0, StringCode.maxRun(null));
	}
	
	@Test
	public void testRun3() {
		// "evolve" technique -- make a series of test cases
		// where each is change from the one above.
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(3, StringCode.maxRun("1112233"));
		assertEquals(4, StringCode.maxRun("111223333"));
	}
	
	//
	// stringIntersect
	//
	@Test
	public void testInt1() {
		// basic cases
		assertEquals(true, StringCode.stringIntersect("abcdefghijkl", "abcde", 3));
		assertEquals(true, StringCode.stringIntersect("abcdefghijkl", "abcde", 5));
		assertEquals(true, StringCode.stringIntersect("abcdefghijkl", "acbde", 2));
		assertEquals(false, StringCode.stringIntersect("abcdefghijkl", "acbde", 3));
		assertEquals(true, StringCode.stringIntersect("abcdefghijkl", "hijkl", 3));
		assertEquals(true, StringCode.stringIntersect("abcdefghijkl", "hijkl", 5));
		assertEquals(true, StringCode.stringIntersect("abcdefghijkl", "efgh", 3));
		assertEquals(true, StringCode.stringIntersect("abcdefghijkl", "efgh", 3));
	}
	
	@Test
	public void testInt2() {
		// trivial cases
		assertEquals(true, StringCode.stringIntersect("abc", "abc", 3));
		assertEquals(false, StringCode.stringIntersect("abc", "ab", 3));
		assertEquals(true, StringCode.stringIntersect("abcd", "abc", 3));
		assertEquals(false, StringCode.stringIntersect("", "", 1));
		assertEquals(true, StringCode.stringIntersect("", "", 0));
		assertEquals(false, StringCode.stringIntersect(null, null, 1));
		assertEquals(true, StringCode.stringIntersect(null, null, 0));
	}
	
	@Test
	public void testInt3() {
		// with non-alphas
		assertEquals(true, StringCode.stringIntersect("a09&*&^ s jlks", "^ s j", 3));
		assertEquals(true, StringCode.stringIntersect("a09&*&^ s jlks", "^ s j", 4));
		assertEquals(true, StringCode.stringIntersect("a09&*&^ s jlks", "^ s j", 5));	
		assertEquals(false, StringCode.stringIntersect("a09&*&^ s jlks", "^ s j", 6));				
	}
}
