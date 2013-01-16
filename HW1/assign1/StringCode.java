package assign1;

import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if (str == null) return 0;
		int len = str.length();
		if (len <= 1) return len; // handle short strings
		char prv = str.charAt(0);
		int ret = 1;
		int rev = 1;
		for (int i = 1; i < len; i++) {
			if (str.charAt(i) == prv) {
				++rev;
				prv = str.charAt(i);
				if (i + 1 == len) {
					if (rev > ret) ret = rev;
				}
			} else {
				if (rev > ret) ret = rev;
				rev = 1;
				prv = str.charAt(i);
			}
		}
		// System.out.println("input = " + str);
		// System.out.println("output = " + ret);
		return ret;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		// System.out.println("input = " + str);
		if (str == null) return null;
		int len = str.length();
		String ret = new String();
		for (int i = 0; i < len; i++) {
		    char c = str.charAt(i);
		    if (Character.isDigit(c)) {
		    	if (i == len - 1) break; // handle the case when last char is digit
		    	int rep = (int) (c - '0');
		    	char nxt = str.charAt(i + 1);
		    	for (int j = 0; j < rep; j++) {
		    		ret = ret + nxt;
		    	}
		    } else {
		    	ret = ret + c;
		    }
		}
		// System.out.println("output = " + ret);
		return ret;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		if (a == null || b == null) return (len == 0);
		int len_a = a.length();
		int len_b = b.length();
		if (len_a < len || len_b < len) return false;
		Set<String> sub_a = new HashSet<String>();
		Set<String> sub_b = new HashSet<String>();
		for (int i = 0; i < len_a - len + 1; i++) {
			sub_a.add(a.substring(i, i + len));
		}
		for (int i = 0; i < len_b - len + 1; i++) {
			sub_b.add(b.substring(i, i + len));
		}
		Set<String> inter_ab = new HashSet<String>(sub_a);
		inter_ab.retainAll(sub_b);
		return !inter_ab.isEmpty(); // TO DO ADD YOUR CODE HERE
	}
}
