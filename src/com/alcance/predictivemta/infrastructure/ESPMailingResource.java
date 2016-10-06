package com.alcance.predictivemta.infrastructure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ESPMailingResource")
public class ESPMailingResource {
	private String id;
	private String domainName;
	private Date introDate;
	private long totalSent;
	private List<SendingHistory> sendingHistory = new ArrayList<SendingHistory>();
	
	
	public List<SendingHistory> getSendingHistory() {
		return sendingHistory;
	}
	public void addSendingHistory(SendingHistory sendingHistory) {
		this.sendingHistory.add(sendingHistory);
	}
	public long getTotalSent() {
		return totalSent;
	}
	public void setTotalSent(long totalSent) {
		this.totalSent = totalSent;
	}
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getIntroDate() {
		return introDate;
	}
	public void setIntroDate(Date introDate) {
		this.introDate = introDate;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	
	
}
