package com.alcance.predictivemta.infrastructure;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alcance.predictivemta.CommonUtil;
import com.alcance.predictivemta.dvo.Subscriber;

@Document(collection = "MailingIP")
public class MailingIP {

	@Id
	private String Id;
	private String mailingIP;
	private String serverIP;
	private String mailingIPDomain;
	
	private IPScore ipScore;
	private String serverName;
	private boolean active = true;
	private Date introDate = new Date();
	private long openCount;
	private long clickCount;
	
	//Audits
	private long mailingCount;
 	// <Date, SendingHistory>
	private Map<String, SendingHistory> history = new HashMap<String, SendingHistory>();
	// <Domain Key, History Value>
	private Map<String, Long> totalDomainCounts = new HashMap<String, Long>();
	//<Domain, DomainUtilization>
	private Map<String, DomainUtilization> domainUtilizationDayCount = new HashMap<String, DomainUtilization>(); 
	
	@Transient
	private Map<String, Long> maxAllowdDomainMailCount = new HashMap<String, Long>();
	@Transient
	private Map<String, Queue<Subscriber>> emailQueue = new HashMap<String, Queue<Subscriber>>();
	@Transient
	private String trsansactionID;
	
	public void updateUtilizationDayCount(String domain) {
		DomainUtilization utilization = domainUtilizationDayCount.get(domain);
		if(null == utilization) {
			utilization = new DomainUtilization();
			domainUtilizationDayCount.put(domain, utilization);
		} else {
			String lastUpdated = utilization.getLastUpdated();
			if(!lastUpdated.equalsIgnoreCase(CommonUtil.formatDate(new Date()))) {
				utilization.setDayCount(utilization.getDayCount() + 1);
			}
		}
	}
	
	public int getUtilizationDayCount(String domain) {
		if(!domainUtilizationDayCount.containsKey(domain)) {
			return 0;
		}
		return domainUtilizationDayCount.get(domain).getDayCount();
	}
	
	public Map<String, Long> getTotalDomainCounts() {
		return totalDomainCounts;
	}

	public void addTotalDomainCounts(String domainName, long count) {
		if(totalDomainCounts.containsKey(domainName)) {
			long oldCount = totalDomainCounts.get(domainName);
			totalDomainCounts.put(domainName, oldCount +count);
		}else {
			totalDomainCounts.put(domainName, count);
		}
	}
	
	public long getDomainCountForDay(String date, String domain) {
		if(null == history.get(date)){
			return 0;
		}else {
			long result =0;
			try {
				result = history.get(date).getDomainCounts().get(domain);

			}catch(NullPointerException npe) {
				result =0;
			}
			return result;
		}
	}
	public SendingHistory getHistory(String date) {
		return history.get(date);
	}
	
	public void setHistory(Map<String, SendingHistory> history) {
		this.history = history;
	}
	
	public void addHistory(String date, SendingHistory sendingHistory) {
		history.put(date, sendingHistory);
	}
	
	public String getTrsansactionID() {
		return trsansactionID;
	}
	public void setTrsansactionID(String trsansactionID) {
		this.trsansactionID = trsansactionID;
	}
	
	/*public List<SendingHistory> getDailyHistory() {
		return dailyHistory;
	}

	public void addDailyHistory(SendingHistory dailyHistory) {
		this.dailyHistory.add(dailyHistory);
	}
	
	public Map<String, SendingHistory> getTotalDomainCounts() {
		return totalDomainCounts;
	}

	public void addTotalDomainCounts(String domainName, long count) {
		SendingHistory history = totalDomainCounts.get(domainName);
		if(null == history) {
			history = new SendingHistory();
			history.setSentCount(count);
			totalDomainCounts.put(domainName, history);
		}
		history.setSentCount(count + history.getSentCount());
		
	}
	
	public Map<String, List<SendingHistory>> getDailyDomainHistory() {
		return dailyDomainHistory;
	}
	 
	public List<SendingHistory> getDailyDomainHistory(String domainName) {
		return dailyDomainHistory.get(domainName);
	}
	
	public void addDailyDomainHistory(String domain, SendingHistory dayDomainHistory) {
		List<SendingHistory> historyList = dailyDomainHistory.get(domain);
		if(null  == historyList) {
			historyList = new ArrayList<SendingHistory>();
			dailyDomainHistory.put(domain, historyList);
		}
		historyList.add(dayDomainHistory);
	}*/
	
	
	/*public Map<String, List<SendingHistory>> getDomainSendingHistory() {
		return domainSendingHistory;
	}
	public void setDomainSendingHistory(
			Map<String, List<SendingHistory>> domainSendingHistory) {
		this.domainSendingHistory = domainSendingHistory;
	}
	
	public void setOverallSendingHistory(List<SendingHistory> overallSendingHistory) {
		this.overallSendingHistory = overallSendingHistory;
	}
	public List<SendingHistory> getOverallSendingHistory() {
		return overallSendingHistory;
	}
	public void addOverallSendingHistory(SendingHistory sendingHistory) {
		this.overallSendingHistory.add(sendingHistory);
	}
	
	public List<SendingHistory> getDomainSendingHistory(String domain) {
		return domainSendingHistory.get(domain);
	}
	
	public void addDomainSendingHistory(String domain, SendingHistory sendingHistory) {
		if(null != domainSendingHistory) {
			List<SendingHistory> history = domainSendingHistory.get(domain);
			if(null == history) {
				history = new ArrayList<SendingHistory>();
			}
			history.add(sendingHistory);
			domainSendingHistory.put(domain, history);
		}
	}
*/

	
	
	public Map<String, Queue<Subscriber>> getEmailQueue() {
		return emailQueue;
	}
	
	public  Queue<Subscriber> getEmailQueue(String domainName) {
		return emailQueue.get(domainName);
	}
	
	public void addToEmailQueue(String domainName, Subscriber subscriber) {
		Queue<Subscriber>  subscribers = emailQueue.get(domainName);
		if(null == subscribers) {
			subscribers = new LinkedList<Subscriber>();
			emailQueue.put(domainName, subscribers);
		}
		subscribers.add(subscriber);
	}
	
	public boolean hasMailQueues() {
		return emailQueue.keySet().size()  > 0;  
	}
	
	public void addToEmailQueue(String domainName, List<Subscriber> subscribers) {
		Queue<Subscriber>  queue = emailQueue.get(domainName);
		if(null == queue) {
			queue = new LinkedList<Subscriber>();
			emailQueue.put(domainName, queue);
		}
		queue.addAll(subscribers);
	}
	
	public long getDomainQueueCount(String domainName) {
		Queue<Subscriber>  subscribers = emailQueue.get(domainName);
		if(null == subscribers) {
			return 0;
		}
		return subscribers.size();
	}
	
	public long getMailingCount() {
		return mailingCount;
	}
	public void setMailingCount(long mailingCount) {
		this.mailingCount = mailingCount;
	}
	public long getOpenCount() {
		return openCount;
	}
	public void setOpenCount(long openCount) {
		this.openCount = openCount;
	}
	public long getClickCount() {
		return clickCount;
	}
	public void setClickCount(long clickCount) {
		this.clickCount = clickCount;
	}
	
	
 
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getMailingIP() {
		return mailingIP;
	}
	public void setMailingIP(String mailingIP) {
		this.mailingIP = mailingIP;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public String getMailingIPDomain() {
		return mailingIPDomain;
	}
	public void setMailingIPDomain(String mailingIPDomain) {
		this.mailingIPDomain = mailingIPDomain;
	}
	public IPScore getIpScore() {
		return ipScore;
	}
	public void setIpScore(IPScore ipScore) {
		this.ipScore = ipScore;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
/*	public List<MailingHistory> getHistory() {
		return history;
	}
	public void setHistory(List<MailingHistory> history) {
		this.history = history;
	}*/
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public Date getIntroDate() {
		return introDate;
	}
	public void setIntroDate(Date introDate) {
		this.introDate = introDate;
	}
	
	
	public long getMaxAllowdDomainMailCount(String domain) {
		return maxAllowdDomainMailCount.get(domain);
	}
	public void setMaxAllowdDomainMailCount(String domain, long count) {
		this.maxAllowdDomainMailCount.put(domain, count);
	}
	
	public boolean queueExists(String domain) {
		return emailQueue.containsKey(domain);
	}
	
	@Override
	public String toString() {
		return "MailingIP [Id=" + Id + ", mailingIP=" + mailingIP
				+ ", serverIP=" + serverIP + ", mailingIPDomain="
				+ mailingIPDomain + ", ipScore=" + ipScore + ", serverName="
				+ serverName + ", active=" + active + ", introDate="
				+ introDate + ", mailingCount=" + mailingCount + ", openCount="
				+ openCount + ", clickCount=" + clickCount + ", history="
				+ history + "] + [" + emailQueue + " ]";
	}
	
	
}
