package store;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ProductTest {

	@Test
	public void test() {
		List<String> list = new ArrayList<String>();
		list.add("id");
		list.add("name");
		list.add("img");
		list.add("0.0");
		
		Product product = new Product(list);
		
		assert(product.getId().equals("id"));
		assert(product.getName().equals("name"));
		assert(product.getImg().equals("img"));
		assert(product.getPrice().equals(0.0));
		
	}

}
