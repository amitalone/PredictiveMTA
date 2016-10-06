package com.alcance.predictivemta.dao;

import java.util.Iterator;


import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;

import com.alcance.predictivemta.dvo.Campaing;
import com.alcance.predictivemta.groupbyResult.SentCampaingCount;
import com.alcance.predictivemta.groupbyResult.SentCampaingCountResult;
import com.alcance.predictivemta.groupbyResult.SentMailingIPCount;
import com.alcance.predictivemta.groupbyResult.SentMailingIPCountResult;
import com.alcance.predictivemta.infrastructure.GlobalMessageHistory;
import com.alcance.predictivemta.infrastructure.MailingIP;

public class GlobalMessageHistoryDAO {
	MongoOperations _operations;
	private final Logger log = Logger.getLogger(GlobalMessageHistoryDAO.class);
	
	public GlobalMessageHistoryDAO(MongoOperations operations) {
		_operations = operations;
	}
	
	public String saveMessage(GlobalMessageHistory message) {
		_operations.save(message);
		return message.getId();
	}
	
	 public SentCampaingCountResult getSentCampaingCounts(Campaing campaing) {
		log.debug("getSentCampaingCounts() starts");
		// GroupByResults<Subscriber> result = _operations.group(Criteria.where("listName").is(listName), "Subscriber", GroupBy.key("emailDomain").initialDocument("{}").reduceFunction("function(curr, result) {  }"), Subscriber.class);
		// _operations.group(criteria, inputCollectionName, groupBy, entityClass)
		 Criteria criteria = Criteria.where("transactionID").is(campaing.getTrsansactionID());
		 criteria.and("campaingName").is(campaing.getName());
		 
		 GroupByResults<SentCampaingCount> result = _operations.group(criteria, "GlobalMessageHistory", GroupBy.key("targetDomain").initialDocument("{count: 0}").reduceFunction("function(curr, result) {result.count += 1  }"), SentCampaingCount.class);
		 Iterator<SentCampaingCount> iter = result.iterator();
		 SentCampaingCountResult campaingCountResult = new  SentCampaingCountResult();
		 campaingCountResult.setTotalCount((long)result.getCount());
		 while(iter.hasNext()) {
			 campaingCountResult.addResults(iter.next());
		 }
		 return campaingCountResult;
	 }
	 
	 public SentMailingIPCountResult getSentMailingIpCounts(MailingIP mailingIP) {
		log.debug("getSentMailingIpCounts starts");
		 Criteria criteria = Criteria.where("transactionID").is(mailingIP.getTrsansactionID());
		 criteria.and("ipAddress").is(mailingIP.getMailingIP());
		 
		 GroupByResults<SentMailingIPCount> result = _operations.group(criteria, "GlobalMessageHistory", GroupBy.key("ipAddress", "targetDomain").initialDocument("{count: 0}").reduceFunction("function(curr, result) {result.count += 1  }"), SentMailingIPCount.class);
		 SentMailingIPCountResult ipCountResult = new SentMailingIPCountResult();
		 ipCountResult.setTotalCount((long)result.getCount());
		 
		 Iterator<SentMailingIPCount> itertor = result.iterator();
		 while(itertor.hasNext()) {
			 ipCountResult.addResults(itertor.next());
		 }
		return ipCountResult;
	 }
}
