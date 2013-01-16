/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/
package assign1;

import java.util.*;

public class Taboo<T> {
	
	private Map<T, Set<T> > taboo;
	
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		if (rules == null || rules.isEmpty() || rules.size() < 2) taboo = Collections.emptyMap();
		else {
			taboo = new HashMap<T, Set<T> >();
			T last = rules.get(0);
			for (int i = 1; i < rules.size(); i++) {
				T cur = rules.get(i);
				if (cur != null) {
					if (!taboo.containsKey(last))
						taboo.put(last, new HashSet<T>());
					taboo.get(last).add(cur);
				}
				last = cur;
			}
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if (elem == null) return Collections.emptySet();
		if (taboo.containsKey(elem)) return taboo.get(elem);
		return Collections.emptySet();
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		if (!(list == null || list.isEmpty() || list.size() < 2)) {
			T last = list.get(0);
			for (int i = 1; i < list.size(); i++) {
				if (noFollow(last).contains(list.get(i))) {
					list.remove(i);
					i = i - 1;
				} else {
					last = list.get(i);
				}
			}
		}
	}
}
