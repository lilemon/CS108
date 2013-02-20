package login;

import java.util.*;

public class Manager {
	
	private static Map<String, String> userdata;
	
	public Manager() {
		userdata = new HashMap<String, String>();
	}
	
	public boolean add(String key, String value) {
		if (userdata.containsKey(key)) return false;
		userdata.put(key, value);
		return true;
	}
	
	public boolean check(String key, String value) {
		return (userdata.containsKey(key) && userdata.get(key).equals(value));
	}
	
}
