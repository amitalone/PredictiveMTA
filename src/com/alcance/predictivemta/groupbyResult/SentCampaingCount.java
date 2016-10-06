package com.alcance.predictivemta.groupbyResult;

import java.util.ArrayList;
import java.util.List;

public class SentCampaingCount {
	private long count;
	private String targetDomain;
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getTargetDomain() {
		return targetDomain;
	}
	public void setTargetDomain(String targetDomain) {
		this.targetDomain = targetDomain;
	}
	@Override
	public String toString() {
		return "SentCampaingCount [count=" + count + ", targetDomain="
				+ targetDomain + "]";
	}
	

}
