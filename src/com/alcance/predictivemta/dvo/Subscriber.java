package com.alcance.predictivemta.dvo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alcance.predictivemta.infrastructure.SendingHistory;

@Document(collection = "Subscriber")
public class Subscriber {
	
	@Id
	private String id;
	private String emailAddress;
	private String emailDomain;
	private SubscriberAttributes attributes = new SubscriberAttributes();
	private List<String> segments = new ArrayList<String>();
	private String status;
	private int clickCount;
	private int openCount;
	private Subscription subscription;
	private String listName;
	 
	
	// Audits
	private long globalSentCount =0;
	// <date, List<messageID>> 
	private Map<String, List<String>> messageSentHistory = new HashMap<String, List<String>>();
	// <date, List<campaingName> >
	private Map<String, List<String>> campaingSentHistory = new HashMap<String, List<String>>();
	
	
	
	

	public Subscriber(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public void addMessageHistory(String date, String messageID) {
		List<String> messageNames = messageSentHistory.get(date);
		if(null == messageNames) {
			messageNames = new ArrayList<String>();
			messageSentHistory.put(date, messageNames);
		}
		messageNames.add(messageID);
	}
	
	public void addCampaingHistory(String date, String campaingName) {
		List<String> campaingNames = campaingSentHistory.get(date);
		if(null == campaingNames) {
			campaingNames = new ArrayList<String>();
			campaingSentHistory.put(date, campaingNames);
		}
		campaingNames.add(campaingName);
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getEmailDomain() {
		return emailDomain;
	}
	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}
	
	
	public SubscriberAttributes getAttributes() {
		return attributes;
	}
	
	public void addAttribute(String key, String value) {
		attributes.addOtherAttribute(key, value);
	}
 
	@Override
	public String toString() {
		/*return "Subscriber [id=" + id + ", emailAddress=" + emailAddress
				+ ", emailDomain=" + emailDomain + ", attributes=" + attributes
				+ ", segments=" + segments + ", status=" + status
				+ ", clickCount=" + clickCount + ", openCount=" + openCount
				+ ", subscription=" + subscription + "]";*/
		return emailAddress;
	}

	public List<String> getSegments() {
		return segments;
	}

	public void setSegments(List<String> segments) {
		this.segments = segments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public long getGlobalSentCount() {
		return globalSentCount;
	}

	public void setGlobalSentCount(long globalSentCount) {
		this.globalSentCount = globalSentCount;
	}
    
	
}
