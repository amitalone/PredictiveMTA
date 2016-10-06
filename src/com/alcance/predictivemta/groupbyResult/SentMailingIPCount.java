package com.alcance.predictivemta.groupbyResult;

public class SentMailingIPCount {
	private String ipAddress;
	private String targetDomain;
	private long count;
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getTargetDomain() {
		return targetDomain;
	}
	public void setTargetDomain(String targetDomain) {
		this.targetDomain = targetDomain;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "SentMailingIPCount [ipAddress=" + ipAddress + ", targetDomain="
				+ targetDomain + ", count=" + count + "]";
	}
	
	
}
