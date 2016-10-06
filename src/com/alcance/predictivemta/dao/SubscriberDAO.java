package com.alcance.predictivemta.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alcance.predictivemta.DomainNames;
import com.alcance.predictivemta.dvo.Subscriber;
import com.alcance.predictivemta.dvo.SubscriberList;
import com.mongodb.DBCollection;

public class SubscriberDAO {
	MongoOperations _operations;
	public final List<String> columnNames = new ArrayList<String>();
	
	public SubscriberDAO(MongoOperations operations) {
		_operations = operations;
		
		columnNames.add("Email");
		columnNames.add("FirsName");
		columnNames.add("LastName");
		columnNames.add("SubscribeDate");
		columnNames.add("Source");
	}
	
	public void addSubscriber(Subscriber subscriber) {
		_operations.insert(subscriber);
	}
	
	public void addSubscribers(List<Subscriber> subscribers) {
		_operations.insert(subscribers, Subscriber.class);
	}
	
	public Subscriber checkSubscriber(String email) {
		Query query = new Query(Criteria.where("emailAddress").is(email));
		
		Subscriber result =  _operations.findOne(query, Subscriber.class);
		return result;
	}
	
	public List<String> getSystemCoulmnNames() {
		return columnNames;
	}
	
	public Map<String, List<Subscriber>> getSubscribersByList(String listName) {
		 
		GroupByResults<Subscriber> result = _operations.group(Criteria.where("listName").is(listName), "Subscriber", GroupBy.key("emailDomain").initialDocument("{}").reduceFunction("function(curr, result) {  }"), Subscriber.class);
		List<String> domains = new ArrayList<String>();
		Iterator<Subscriber> si = result.iterator();
		Subscriber s = null;
		while(si.hasNext()) {
			s = si.next();
			domains.add(s.getEmailDomain());
		}
		s = null;
		
		Map<String, List<Subscriber>> domainSubscriberMap = new HashMap<String, List<Subscriber>>();
		
		for(String domainName : domains) {
			Query query = new Query(Criteria.where("listName").is(listName));
			query.addCriteria(Criteria.where("emailDomain").is(domainName));
			List<Subscriber> subscribers = _operations.find(query, Subscriber.class);
			domainSubscriberMap.put(domainName, subscribers);
		}
		return domainSubscriberMap;
	}
	
/*	public Map<String, Long> getCounts() {
		List<String> domains = DomainNames.getDomainNames();
		Map<String, Long> countMap = new HashMap<String, Long>(); 
		for(String name : domains) {
			Query query = new Query(Criteria.where("emailDomain").is(name));
			long count =_operations.count(query, Subscriber.class);
			countMap.put(name, count);
		}
	
		return countMap;
	}*/
	
	 public long getCountByListname(String listName) {
		 Query query = new Query(Criteria.where("listName").is(listName));
		 
		return _operations.count(query, Subscriber.class);
	 }
	 
	 public void updateSubscriber(Subscriber subscriber) {
		 _operations.save(subscriber);
	 }
}
