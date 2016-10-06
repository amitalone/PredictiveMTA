package com.alcance.predictivemta.dvo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alcance.predictivemta.infrastructure.SendingHistory;

@Document(collection = "Campaign")
public class Campaing {
	
	
	@Id
	private String Id;
	private String name;
	 
	private String subject;
	private String fromName;
	private String replyTo;
	private TrackingOptions tracking;
	private EmailBody emailBody;
	private List<String> listNames = new ArrayList<String>();
	private Date creationDate;

	//private List<String> supperssionList = new ArrayList<String>();
	
	@Transient
	private String trsansactionID;
	@Transient
	private long currentSentCount =0;
	@Transient
	private long projectedMailCount=0;
	
	
	// Audits
	private long totalSentCount;
	// <Domain Key, History Value>
	private Map<String, Long> totalDomainCounts = new HashMap<String, Long>();
	// <Date, SendingHistory>
	private Map<String, SendingHistory> history = new HashMap<String, SendingHistory>();
	
	public void addHistory(String date, SendingHistory sendingHistory) {
		history.put(date, sendingHistory);
	}
	
	public SendingHistory getHistory(String date) {
		return history.get(date);
	}
	
	public void setHistory(Map<String, SendingHistory> history) {
		this.history = history;
	}
	
	public String getTrsansactionID() {
		return trsansactionID;
	}
	public void setTrsansactionID(String trsansactionID) {
		this.trsansactionID = trsansactionID;
	}
	
	
	public long getTotalSentCount() {
		return totalSentCount;
	}

	public void setTotalSentCount(long totalSentCount) {
		this.totalSentCount = totalSentCount;
	}

	public Map<String, Long> getTotalDomainCounts() {
		return totalDomainCounts;
	}

	public void addTotalDomainCounts(String domainName, long count) {
		if(totalDomainCounts.containsKey(domainName)) {
			long oldCount = totalDomainCounts.get(domainName);
			totalDomainCounts.put(domainName, oldCount +count);
		}else {
			totalDomainCounts.put(domainName, count);
		}
	}
	
	public void setTotalDomainCounts(Map<String, Long> domainCounts) {
		 totalDomainCounts = domainCounts;
	}

	public List<String> getListNames() {
		return listNames;
	}
	
	public void addList(String listNames) {
		this.listNames.add(listNames);
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
 
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	public TrackingOptions getTracking() {
		return tracking;
	}
	public void setTracking(TrackingOptions tracking) {
		this.tracking = tracking;
	}
	public EmailBody getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(EmailBody emailBody) {
		this.emailBody = emailBody;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setListNames(List<String> listNames) {
		this.listNames = listNames;
	}
	
	public synchronized void updateEmailSent() {
		currentSentCount++;
	}
	
	public void resetCurrentSentCount() {
		currentSentCount =0;
	}
	public void setCurrentSentCount(long value) {
		currentSentCount =value;
	}
	
	public long getCurrentSentCount() {
		return currentSentCount;
	}
	
	public void setProjectedMailCount(long count) {
		projectedMailCount = count;
	}
	
	public void addProjectedMailCount(long count) {
		projectedMailCount += count;
	}
	
	public long getprojectedMailCount() {
		return projectedMailCount;
	}
	
	public synchronized int getMailSentProgress() {
	 	if(projectedMailCount == 0) {
			 return 0;
		}
		return (int) ((currentSentCount * 100) / projectedMailCount);
	}
}
