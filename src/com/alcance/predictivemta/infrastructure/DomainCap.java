package com.alcance.predictivemta.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DomainCap")
public class DomainCap {
	private String domainName;
	private int lowerDay;
	private int upperDay;
	private long allowedCount;
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public int getLowerDay() {
		return lowerDay;
	}
	public void setLowerDay(int lowerDay) {
		this.lowerDay = lowerDay;
	}
	public int getUpperDay() {
		return upperDay;
	}
	public void setUpperDay(int upperDay) {
		this.upperDay = upperDay;
	}
	public long getAllowedCount() {
		return allowedCount;
	}
	public void setAllowedCount(long allowedCount) {
		this.allowedCount = allowedCount;
	}
	
	
	 
}
