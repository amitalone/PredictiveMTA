package com.alcance.predictivemta.dvo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;

@Document(collection = "SubscriberList")
public class SubscriberList {
	@Id
	private String id;
	private String listName;
	private String defaultFromName;
	private String defaultReplyTo;
	private long listCount;
	private ListSearchCriteria searchCriteria;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getDefaultFromName() {
		return defaultFromName;
	}
	public void setDefaultFromName(String defaultFromName) {
		this.defaultFromName = defaultFromName;
	}
	public String getDefaultReplyTo() {
		return defaultReplyTo;
	}
	public void setDefaultReplyTo(String defaultReplyTo) {
		this.defaultReplyTo = defaultReplyTo;
	}
	public long getListCount() {
		return listCount;
	}
	public void setListCount(long listCount) {
		this.listCount = listCount;
	}
	
}
