package com.alcance.predictivemta.infrastructure.log;


import java.util.Date;
import java.util.Map;

import com.alcance.predictivemta.CommonUtil;
import com.alcance.predictivemta.dao.GlobalMessageHistoryDAO;
import com.alcance.predictivemta.groupbyResult.SentMailingIPCount;
import com.alcance.predictivemta.groupbyResult.SentMailingIPCountResult;
import com.alcance.predictivemta.infrastructure.MailingIP;
import com.alcance.predictivemta.infrastructure.SendingHistory;

public class MailingIPActivity extends Activity{

	private MailingIP _mailingIP;
	
	public MailingIPActivity(MailingIP mailingIP) {
		_mailingIP = mailingIP;
	}
	
	@Override
	public Integer call() throws Exception {
		 GlobalMessageHistoryDAO historyDAO = getService().getMessageHistoryDAO();
		 SentMailingIPCountResult result = historyDAO.getSentMailingIpCounts(_mailingIP);
		 
		 // Update ipTotal sent Count;
		 _mailingIP.setMailingCount(_mailingIP.getMailingCount()+result.getTotalCount());
		 
		 String date = CommonUtil.formatDate(new Date());
		 
		 //update daily History
		 SendingHistory todaysHistory = _mailingIP.getHistory(date);
		 if(null == todaysHistory) {
			todaysHistory = new SendingHistory();
			_mailingIP.addHistory(date, todaysHistory);	
		 }
		 todaysHistory.setSentCount(todaysHistory.getSentCount() + result.getTotalCount());
		 
		 Map<String, Long> todaysDomainCounts = todaysHistory.getDomainCounts();
		 
		 for(SentMailingIPCount ipCount: result.getResults()) {
			 String domainName = ipCount.getTargetDomain();
			 long count = ipCount.getCount();
			 _mailingIP.addTotalDomainCounts(domainName, count);
			 
			 // Update domainwise daily counts;
			 if(!todaysDomainCounts.containsKey(domainName)) {
				 todaysDomainCounts.put(domainName, count);
			 }else {
				 todaysDomainCounts.put(domainName, todaysDomainCounts.get(domainName) +count);
			 }
			 _mailingIP.updateUtilizationDayCount(domainName);
			// _mailingIP.addTotalDomainCounts(domainName, count);
		 }
		 
		 getService().getMailingIPDAO().saveMailingIP(_mailingIP);
		 
		return 1;
	}

	 
	
}
