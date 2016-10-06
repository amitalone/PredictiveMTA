package com.alcance.predictivemta.infrastructure;

public class IPScore {

	private long mailSent;
	private long mailClicked;
	private long mailRead;
	private long mailUnsubscribed;
	private int acceptanceRatio;
	
	
	public long getMailSent() {
		return mailSent;
	}
	public void setMailSent(long mailSent) {
		this.mailSent = mailSent;
	}
	public long getMailClicked() {
		return mailClicked;
	}
	public void setMailClicked(long mailClicked) {
		this.mailClicked = mailClicked;
	}
	public long getMailRead() {
		return mailRead;
	}
	public void setMailRead(long mailRead) {
		this.mailRead = mailRead;
	}
	public long getMailUnsubscribed() {
		return mailUnsubscribed;
	}
	public void setMailUnsubscribed(long mailUnsubscribed) {
		this.mailUnsubscribed = mailUnsubscribed;
	}
	
	
}
