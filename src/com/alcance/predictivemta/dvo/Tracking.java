package com.alcance.predictivemta.dvo;

import org.springframework.data.annotation.Id;

public class Tracking {
	
	@Id
	private String Id;
	private String subscriberId;
	private String listName;
	private String campaignName;
	
}
