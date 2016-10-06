package com.alcance.predictivemta.infrastructure;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MailingIPDAO {

	MongoOperations _operations;
	
	public MailingIPDAO(MongoOperations operations) {
		_operations = operations;
	}
	
	public void addMailingIP(MailingIP mailingIP) {
		_operations.insert(mailingIP);
	}
	
 
	
	public boolean mailingIPExists(String ipAddress) {
		Query query = new Query(Criteria.where("mailingIP").is(ipAddress));
		long count = _operations.count(query, "MailingIP");
		return (count > 0) ? true: false;
	}
	
	public List<MailingIP> getIPList() {
		return _operations.findAll(MailingIP.class);
	}
	
	public void saveMailingIP(MailingIP ip) {
		_operations.save(ip);
	}
	
	public MailingIP getMailingIP(String ipAddress) {
		Query query = new Query(Criteria.where("mailingIP").is(ipAddress));
		return _operations.findOne(query, MailingIP.class);
	}
}
