// TabooTest.java
// Taboo class tests -- nothing provided.
package assign1;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.*;

public class TabooTest {
	
	//
	// noFollow
	//
	@Test
	public void testNoFollow1() {
		// basic case regarding normal rules
		List<String> rules = Arrays.asList("a", "c", "a", "b");
		Taboo<String> tab = new Taboo<String>(rules);
		
		Set<String> ans1 = new HashSet<String>(Arrays.asList("c", "b"));
		assertEquals(ans1, tab.noFollow("a"));
		
		Set<String> ans2 = new HashSet<String>(Arrays.asList("a"));
		assertEquals(ans2, tab.noFollow("c"));
		
		Set<String> ans3 = Collections.emptySet();
		assertEquals(ans3, tab.noFollow("b"));
		
		Set<String> ans4 = Collections.emptySet();
		assertEquals(ans4, tab.noFollow("x"));
	}
	
	@Test
	public void testNoFollow2() {
		// case with "null" involved
		List<String> rules = Arrays.asList("a", "c", null, "a", "b");
		Taboo<String> tab = new Taboo<String>(rules);
		
		Set<String> ans1 = new HashSet<String>(Arrays.asList("c", "b"));
		assertEquals(ans1, tab.noFollow("a"));
		
		Set<String> ans2 = Collections.emptySet();
		assertEquals(ans2, tab.noFollow("c"));
		
		Set<String> ans3 = Collections.emptySet();
		assertEquals(ans3, tab.noFollow("b"));
		
		Set<String> ans4 = Collections.emptySet();
		assertEquals(ans4, tab.noFollow("x"));
		
		Set<String> ans5 = Collections.emptySet();
		assertEquals(ans5, tab.noFollow(null));
	}
	
	//
	// testReduce
	//
	@Test
	public void testReduce1() {
		// case
		List<String> rules = Arrays.asList("a", "c", "a", "b");
		Taboo<String> tab = new Taboo<String>(rules);
		
		List<String> in1 = new ArrayList<String>();
		in1.addAll(Arrays.asList("a", "c", "b", "x", "c", "a"));
		List<String> out1 = Arrays.asList("a", "x", "c");
		tab.reduce(in1);
		assertEquals(out1, in1);
		
		List<String> in2 = new ArrayList<String>();
		in2.addAll(Arrays.asList("a", "c", "b", "c", "b", "c"));
		List<String> out2 = Arrays.asList("a");
		tab.reduce(in2);
		assertEquals(out2, in2);
		
		List<String> in3 = new ArrayList<String>();
		in3.addAll(Arrays.asList("a", "c", "b", null, "x", "c", "a"));
		List<String> out3 = Arrays.asList("a", null, "x", "c");
		tab.reduce(in3);
		assertEquals(out3, in3);
	}
	
	public void testReduce2() {
		List<String> rules = Arrays.asList("a", "c", "a", "b");
		Taboo<String> tab = new Taboo<String>(rules);
		
		List<String> in1 = new ArrayList<String>();
		List<String> out1 = new ArrayList<String>();
		tab.reduce(in1);
		assertEquals(out1, in1);
		
		List<String> in2 = null;
		List<String> out2 = null;
		tab.reduce(in2);
		assertEquals(out2, in2);
		
		List<String> in3 = new ArrayList<String>();
		in3.addAll(Arrays.asList(""));
		List<String> out3 = Arrays.asList("");
		tab.reduce(in3);
		assertEquals(out3, in3);
	}
	
	public void testReduce3() {
		List<String> rules = Arrays.asList("a", "c", null, "a", "b");
		Taboo<String> tab = new Taboo<String>(rules);
		
		List<String> in1 = new ArrayList<String>();
		in1.addAll(Arrays.asList("a", "c", null, "b", "x", "c", "a"));
		List<String> out1 = Arrays.asList("a", null, "x", "c", "a");
		tab.reduce(in1);
		assertEquals(out1, in1);
	}
}
