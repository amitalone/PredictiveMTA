package com.alcance.predictivemta.infrastructure;

 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

import com.alcance.predictivemta.ApplicationException;
import com.alcance.predictivemta.CommonUtil;
import com.alcance.predictivemta.MemoryStore;
import com.alcance.predictivemta.PMTAService;
import com.alcance.predictivemta.PMTAServiceImpl;
import com.alcance.predictivemta.dao.CampaingDAO;
import com.alcance.predictivemta.dao.ESPMailingResourceDAO;
import com.alcance.predictivemta.dao.GlobalMessageHistoryDAO;
import com.alcance.predictivemta.dao.SubscriberDAO;
import com.alcance.predictivemta.dvo.Campaing;
import com.alcance.predictivemta.dvo.Subscriber;
import com.alcance.predictivemta.groupbyResult.SentCampaingCount;
import com.alcance.predictivemta.groupbyResult.SentCampaingCountResult;
import com.alcance.predictivemta.infrastructure.log.CampaingActivity;
import com.alcance.predictivemta.infrastructure.log.LogActivityManager;
import com.alcance.predictivemta.infrastructure.log.MailingIPActivity;
 

//TODO: Make it a singleton bean
public class MailRouter {
	private PMTAService _service;
	private ESPMailingResourceDAO _espResourceDao;
	private SubscriberDAO _subscriberDAO;
	private List<MailingIP> _mailingIPPool;
	private CampaingDAO _campaingDAO;
	
	private final Logger log = Logger.getLogger(MailRouter.class);
	
	public MailRouter() {
		_service = new PMTAServiceImpl();
		_espResourceDao = _service.getESPMailingResourceDAO();
		_subscriberDAO = _service.getSubscriberDAO();
		_mailingIPPool = new ArrayList<MailingIP>();
		_campaingDAO = _service.getCampaignDAO();
	}
	
	public void route(Campaing campaing) throws ApplicationException {
		
		log.debug("route start");
		
		// Initialize Global IP Pool
		String transactionID = UUID.randomUUID().toString();
		campaing.setTrsansactionID(transactionID);
		initMailingIPPool();
		
		List<String> listNames = campaing.getListNames();
		campaing.setProjectedMailCount(0);

		for(String listName : listNames) {
			
			// get subscribers by listName grouped by domains
			Map<String, List<Subscriber>> domainMap = _subscriberDAO.getSubscribersByList(listName);
		
			Set<String> domainNames = domainMap.keySet();
			log.debug("Identified domains for "+listName+ " [" + domainNames + "]" );
			
			// Get mailing Ips for each domain
			for(String domainName : domainNames) {
			 
				// Get mailingLimit;
				allocateDomainCap(domainName);
				
				List<Subscriber> subscriberList = domainMap.get(domainName);
				//campaing.addProjectedMailCount(subscriberList.size());
				
				distributeMails(subscriberList, domainName, campaing);
			}
			
		}
		
		try {
			queueMailsToServer(campaing);
		}catch(Exception ex) {
			log.error("queueMailsToServer throwed", ex);
			ex.printStackTrace();
		}
		//campaing.setCurrentSentCount(campaing.getprojectedMailCount());
		log.debug("route end");
	}
	
	private void queueMailsToServer(Campaing campaign) throws Exception {
		log.debug("queueMailsToServer start");
		MailQueueManager manager = MailQueueManager.getInstance();
		LogActivityManager logActivityManager = LogActivityManager.getInstance();
		
		for(MailingIP mailingIP : _mailingIPPool) {
			if(mailingIP.hasMailQueues()) {
				mailingIP.setTrsansactionID(campaign.getTrsansactionID());
				manager.submitMailQueue(new MailQueuingThread(mailingIP, campaign));
				//logActivityManager.submitActivity(new MailingIPActivity(mailingIP));
			}
		}
		
		// Stop MailQueueManager from accepting any tasks
		manager.shutdown();
		// Wait for execution
		manager.waitForFuture();
		
		//Log Campaing 
		logActivityManager.submitActivity(new CampaingActivity(campaign));
		
		
		// Stop LOGActivity Manager
		LogActivityManager.getInstance().shutdown();
		LogActivityManager.getInstance().waitForFuture();
		
		//TODO: Shutdown all logging services;
		log.debug("queueMailsToServer ends");	
		
	}
	 
	
	private void distributeMails(List<Subscriber> subscribers, String domain, Campaing campaing) {
		log.debug("distributeMails start");
		//TODO change logic, filter from DB itself
		for(Subscriber s : subscribers) {
			if(_campaingDAO.isSuppressed(s.getEmailAddress(), campaing.getName())){
				subscribers.remove(s);
			}
		}
		for(MailingIP mailingIP : _mailingIPPool) {
			if(!subscribers.isEmpty()) {
				long allowedMailingCount = mailingIP.getMaxAllowdDomainMailCount(domain);
				long mailingQueSize = mailingIP.getDomainQueueCount(domain); 
				int toBeQueued = (int) (allowedMailingCount - mailingQueSize);
				if(toBeQueued > subscribers.size()) {
					toBeQueued = subscribers.size();
				}
				List<Subscriber> subList = subscribers.subList(0, toBeQueued);
				mailingIP.addToEmailQueue(domain, subList);
				campaing.addProjectedMailCount(subList.size());
				subscribers.removeAll(subList);
			} else {
				break;
			}
			
		}
		log.debug("distributeMails ends");
	}
	
	
	public void allocateDomainCap(String domain) throws ApplicationException {
		log.debug("allocateDomainCap start");
		String date = CommonUtil.formatDate(new Date());
		for(MailingIP ip : _mailingIPPool) {
			int daysUtlized = ip.getUtilizationDayCount(domain);
			long todaysCount = ip.getDomainCountForDay(date, domain);
			if(daysUtlized == 0) {
				if(!ip.queueExists(domain)) {
					ip.setMaxAllowdDomainMailCount(domain, _espResourceDao.getDayCap(domain, 1).getAllowedCount() - todaysCount);
				}
			}else  {
				if(!ip.queueExists(domain)) {
					ip.setMaxAllowdDomainMailCount(domain, _espResourceDao.getDayCap(domain, daysUtlized).getAllowedCount() - todaysCount);
				}
			}
			
		}
		log.debug("allocateDomainCap ends");
	}
	
	public void initMailingIPPool() {
		log.debug("initMailingIPPool start");
		_mailingIPPool = _service.getMailingIPDAO().getIPList();
		log.debug("initMailingIPPool ends");
	}
	
}
