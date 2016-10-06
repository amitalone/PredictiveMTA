package com.alcance.predictivemta.dvo;

public class TrackingOptions {
	private boolean trackOpen = false;
	private boolean trackClicks = false;
	private boolean trackPlainTextClick = false;
	private boolean clickTale = false;
	public boolean isTrackOpen() {
		return trackOpen;
	}
	public void setTrackOpen(boolean trackOpen) {
		this.trackOpen = trackOpen;
	}
	public boolean isTrackClicks() {
		return trackClicks;
	}
	public void setTrackClicks(boolean trackClicks) {
		this.trackClicks = trackClicks;
	}
	public boolean isTrackPlainTextClick() {
		return trackPlainTextClick;
	}
	public void setTrackPlainTextClick(boolean trackPlainTextClick) {
		this.trackPlainTextClick = trackPlainTextClick;
	}
	public boolean isClickTale() {
		return clickTale;
	}
	public void setClickTale(boolean clickTale) {
		this.clickTale = clickTale;
	}
	
	
}
