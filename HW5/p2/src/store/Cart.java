package store;

import java.util.*;

public class Cart {

	private Map<Product, Integer> cart;

	public Cart() {
		cart = new HashMap<Product, Integer>();
	}
	
	public void update(String id, Integer count, Map<String, Product> catalog) {
		Product product = catalog.get(id);
		if (count == 0) {
			cart.remove(product);
		} else {
			cart.put(product, count);
		}
	}
	
	public int getCount(Product product) {
		if (cart.containsKey(product)) {
			return cart.get(product);
		} else {
			return 0;
		}
	}
	
	public Map<Product, Integer> getContent() {
		return cart;
	}
}
