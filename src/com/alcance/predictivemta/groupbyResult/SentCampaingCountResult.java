package com.alcance.predictivemta.groupbyResult;

import java.util.ArrayList;
import java.util.List;

public class SentCampaingCountResult {
	private long totalCount;
	private List<SentCampaingCount> results = new ArrayList<SentCampaingCount>();
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<SentCampaingCount> getResults() {
		return results;
	}
	public void addResults(SentCampaingCount result) {
		this.results.add(result);
	}
	
}
