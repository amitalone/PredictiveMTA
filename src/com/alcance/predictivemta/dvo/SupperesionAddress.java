package com.alcance.predictivemta.dvo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SupperesionAddress")
public class SupperesionAddress {
	
	private String campaignName;
	private String emailAddress;
	
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
