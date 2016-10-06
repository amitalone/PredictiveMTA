package com.alcance.predictivemta;

import java.util.HashMap;
import java.util.Map;

public class MemoryStore {
	private final static Map<String, Object> map = new HashMap<String, Object>();
	
	private MemoryStore() {
	}
	
	public static void put(String key, Object o ) {
		map.put(key, o);
	}
	
	public static Object get(String key) {
		return map.get(key);
	}
	
	public static void reset() {
		map.clear();
	}
}
