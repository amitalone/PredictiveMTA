package com.alcance.predictivemta.infrastructure.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alcance.predictivemta.CommonUtil;
import com.alcance.predictivemta.dao.GlobalMessageHistoryDAO;
import com.alcance.predictivemta.dvo.Campaing;
import com.alcance.predictivemta.groupbyResult.SentCampaingCount;
import com.alcance.predictivemta.groupbyResult.SentCampaingCountResult;
import com.alcance.predictivemta.infrastructure.SendingHistory;

public class CampaingActivity extends Activity {
	private Campaing _campaing;
	
	public CampaingActivity(Campaing campaing) {
		_campaing = campaing;
	}
	@Override
	public Integer call() throws Exception {
		 GlobalMessageHistoryDAO historyDAO = getService().getMessageHistoryDAO();
		 SentCampaingCountResult result = historyDAO.getSentCampaingCounts(_campaing);
		 
		 // Update campaingTotal sent Count;
		 _campaing.setTotalSentCount(_campaing.getTotalSentCount() + result.getTotalCount());
		 
		 //update daily History
		 String date = CommonUtil.formatDate(new Date());
		 
		 SendingHistory todaysHistory = _campaing.getHistory(date);
		 if(null == todaysHistory) {
			todaysHistory = new SendingHistory();
			_campaing.addHistory(date, todaysHistory);
		 }
		 
		 todaysHistory.setSentCount(todaysHistory.getSentCount() + result.getTotalCount());
		 Map<String, Long> todaysDomainCounts = todaysHistory.getDomainCounts();
		 
		 for(SentCampaingCount campaingCount : result.getResults()) {
			 String domainName = campaingCount.getTargetDomain();
			 long count = campaingCount.getCount();
			 
			 // Update domainwise total sending
			 _campaing.addTotalDomainCounts(domainName, count);
			 
			 // Update domainwise daily counts;
			 if(!todaysDomainCounts.containsKey(domainName)) {
				 todaysDomainCounts.put(domainName, count);
			 }else {
				 todaysDomainCounts.put(domainName, todaysDomainCounts.get(domainName) +count);
			 }
		 }
		 getService().getCampaignDAO().saveCampaing(_campaing);
		 
		 
		 
		return 1;
	}

}
