package com.alcance.predictivemta.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alcance.predictivemta.ApplicationException;
import com.alcance.predictivemta.infrastructure.DomainCap;

public class ESPMailingResourceDAO {

	MongoOperations _operations;
	
	public ESPMailingResourceDAO(MongoOperations operations) {
		_operations = operations;
	}
	
	/*public List<MailingIP> getESPMailingIPS(String domainName) {
		Query query = new Query(Criteria.where("domainName").is(domainName));
		ESPMailingResource resource =	_operations.findOne(query, ESPMailingResource.class);
		if(null !=resource) {
			return resource.getMailingIPs();
		}
		return null;
	}*/
	
	/*public void addESPMailingResource(ESPMailingResource resource) {
		_operations.insert(resource);
	}*/
	
	public void addDomainCap(DomainCap domainCap) {
		_operations.insert(domainCap);
	}
	
	public DomainCap getDayCap(String domainName, int day) throws ApplicationException {
		Query query = new Query(Criteria.where("domainName").is(domainName).and("lowerDay").lt(day).and("upperDay").gt(day));
		DomainCap cap = _operations.findOne(query, DomainCap.class);
		if(null == cap) {
			throw new ApplicationException("Domain Cap not defined for " + domainName);
		}
		return cap;
	}
	
}
