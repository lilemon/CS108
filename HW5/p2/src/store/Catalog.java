package store;

import java.sql.*;
import java.util.*;

import store.MyDB;

public class Catalog {

	private static final int ID = 1;
	private static final int NAME = 2;
	private static final int IMG = 3;
	private static final int PRICE = 4;
	
	public static Map<String, Product> load() throws SQLException {
		Connection con = MyDB.getConnection();
		Statement stmt = con.createStatement();
		Map<String, Product> data = new HashMap<String, Product>();
		ResultSet rs = stmt.executeQuery("SELECT * FROM products");
		rs.beforeFirst();
		while (rs.next()) {
			List<String> val = new ArrayList<String>();
			val.add(rs.getString(ID));
			val.add(rs.getString(NAME));
			val.add(rs.getString(IMG));
			val.add(rs.getString(PRICE));
			data.put(val.get(0), new Product(val));
		}
		con.close();
		return data;
	}
}
