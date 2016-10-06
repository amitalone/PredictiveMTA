package com.alcance.predictivemta;

public class ConfigurationBean {
	private String clickLink;
	private String trackinkingPixelLink;
	private String unsubscribeLink;
	
	public String getUnsubscribeLink() {
		return unsubscribeLink;
	}
	public void setUnsubscribeLink(String unsubscribeLink) {
		this.unsubscribeLink = unsubscribeLink;
	}
	public String getClickLink() {
		return clickLink;
	}
	public void setClickLink(String clickLink) {
		this.clickLink = clickLink;
	}
	public String getTrackinkingPixelLink() {
		return trackinkingPixelLink;
	}
	public void setTrackinkingPixelLink(String trackinkingPixelLink) {
		this.trackinkingPixelLink = trackinkingPixelLink;
	}
}
