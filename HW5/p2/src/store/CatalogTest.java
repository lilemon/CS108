package store;

import static org.junit.Assert.*;

import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

public class CatalogTest {

	@Test
	public void test() throws SQLException {
		Map<String, Product> data = Catalog.load();
		assert(data != null);
	}
}
