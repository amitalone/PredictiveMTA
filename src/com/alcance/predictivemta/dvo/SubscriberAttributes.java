package com.alcance.predictivemta.dvo;

import java.util.HashMap;
import java.util.Map;

public class SubscriberAttributes {

	private Map<String, String> otherAttributes = new HashMap<String, String>();
	
	public SubscriberAttributes(){
		
	}
	
	
	public Map<String, String> getOtherAttributes() {
		return otherAttributes;
	}

	public void addOtherAttribute(String key, String value) {
		otherAttributes.put(key, value);
	}


	@Override
	public String toString() {
		return "SubscriberAttributes [otherAttributes=" + otherAttributes + "]";
	}
	
	
}
