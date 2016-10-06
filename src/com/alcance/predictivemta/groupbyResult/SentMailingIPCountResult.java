package com.alcance.predictivemta.groupbyResult;

import java.util.ArrayList;
import java.util.List;

public class SentMailingIPCountResult {
	private long totalCount;
	private List<SentMailingIPCount> results = new ArrayList<SentMailingIPCount>();
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<SentMailingIPCount> getResults() {
		return results;
	}
	public void addResults(SentMailingIPCount value) {
		this.results.add(value);
	}
	@Override
	public String toString() {
		return "SentMailingIPCountResult [totalCount=" + totalCount
				+ ", results=" + results + "]";
	}
	
	
}
