package com.alcance.predictivemta.infrastructure;

import java.util.Date;

public class MailingHistory {
	private Date sentDate;
	private long sentCount;
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public long getSentCount() {
		return sentCount;
	}
	public void setSentCount(long sentCount) {
		this.sentCount = sentCount;
	}
	
	
}
