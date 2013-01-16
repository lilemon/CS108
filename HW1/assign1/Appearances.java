package assign1;

import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		if (a == null || b == null || a.isEmpty() || b.isEmpty()) return 0;
		Map<T, Integer> count_a = new HashMap<T, Integer>();
		Map<T, Integer> count_b = new HashMap<T, Integer>();
		for (T elem:a) {
			if (!count_a.containsKey(elem)) {
				count_a.put(elem, 0);
			}
			count_a.put(elem, count_a.get(elem) + 1);
		}
		for (T elem:b) {
			if (!count_b.containsKey(elem)) {
				count_b.put(elem, 0);
			}
			count_b.put(elem, count_b.get(elem) + 1);
		}
		int ret = 0;
		for (T key:count_a.keySet()) {
			if (count_b.containsKey(key) && count_b.get(key).equals(count_a.get(key)))
				ret++;
		}
		return ret; // TODO ADD CODE HERE
	}
}
