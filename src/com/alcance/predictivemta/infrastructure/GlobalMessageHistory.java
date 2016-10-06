package com.alcance.predictivemta.infrastructure;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "GlobalMessageHistory")
public class GlobalMessageHistory {

	@Id
	private String id;
	private String listName;
	private String campaingName;
	private String ipAddress;
	private String subject;
	private String from;
	private String fromText;
	private Date sentDate = new Date();
	private String emailTo;
	private String transactionID;
	private String targetDomain;
	
	
	public String getTargetDomain() {
		return targetDomain;
	}
	public void setTargetDomain(String targetDomain) {
		this.targetDomain = targetDomain;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
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
	public String getCampaingName() {
		return campaingName;
	}
	public void setCampaingName(String campaingName) {
		this.campaingName = campaingName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFromText() {
		return fromText;
	}
	public void setFromText(String fromText) {
		this.fromText = fromText;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	
	
}
