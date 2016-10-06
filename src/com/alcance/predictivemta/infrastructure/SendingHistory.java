package com.alcance.predictivemta.infrastructure;

import java.util.HashMap;
import java.util.Map;


public class SendingHistory {
	private long sentCount;
	private Map<String, Long> domainCounts = new HashMap<String, Long>();
	public long getSentCount() {
		return sentCount;
	}
	public void setSentCount(long sentCount) {
		this.sentCount = sentCount;
	}
	public Map<String, Long> getDomainCounts() {
		return domainCounts;
	}
	public void setDomainCounts(Map<String, Long> domainCounts) {
		this.domainCounts = domainCounts;
	}
	@Override
	public String toString() {
		return "SendingHistory [sentCount=" + sentCount + ", domainCounts="
				+ domainCounts + "]";
	}
	
	
 }
