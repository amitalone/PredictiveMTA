package com.alcance.predictivemta.dvo;

import java.util.ArrayList;
import java.util.List;

public class Subscription {

	private boolean status;
	private List<String> unsubscribedCampaign = new ArrayList<String>();
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<String> getUnsubscribedCampaign() {
		return unsubscribedCampaign;
	}
	public void setUnsubscribedCampaign(List<String> unsubscribedCampaign) {
		this.unsubscribedCampaign = unsubscribedCampaign;
	}
	
}
