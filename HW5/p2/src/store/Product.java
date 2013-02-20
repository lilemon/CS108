package store;

import java.util.*;

public class Product {
	
	private final int ID = 0;
	private final int NAME = 1;
	private final int IMG = 2;
	private final int PRICE = 3;
	
	private String id;
	private String name;
	private String img;
	private Double price;
	
	public Product (List<String> product) {
		id = product.get(ID);
		name = product.get(NAME);
		img = product.get(IMG);
		price = Double.parseDouble(product.get(PRICE));
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getImg() {
		return img;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public String toString() {
		return ("ID:" + id + " NAME:" + name + " PRICE:" + price);
	}
}
