package com.alcance.predictivemta.dvo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "RedirectionPage")
public class RedirectionPage {
	@Id
	private String id;
	private String redirectTo;
	private String campaingName;
	
	public String getCampaingName() {
		return campaingName;
	}
	public void setCampaingName(String campaingName) {
		this.campaingName = campaingName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRedirectTo() {
		return redirectTo;
	}
	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}
	
	
}
