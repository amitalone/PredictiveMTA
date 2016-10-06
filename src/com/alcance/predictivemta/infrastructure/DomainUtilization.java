package com.alcance.predictivemta.infrastructure;

import java.util.Date;

import com.alcance.predictivemta.CommonUtil;

public class DomainUtilization {
	private int dayCount =  1;
	private String lastUpdated = CommonUtil.formatDate(new Date());
	
	public int getDayCount() {
		return dayCount;
	}
	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
}
