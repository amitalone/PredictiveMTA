package com.alcance.predictivemta.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alcance.predictivemta.PMTAServiceImpl;
import com.alcance.predictivemta.dvo.SubscriberList;

public class SubscriberListDAO {

	MongoOperations _operations;
	
	public SubscriberListDAO(MongoOperations operations) {
		_operations = operations;
	}
	
	public void createList(SubscriberList list) {
		_operations.insert(list);
	}
	
	public boolean listNameExists(String listName) {
		Query query = new Query(Criteria.where("listName").is(listName));
		long count = _operations.count(query, "SubscriberList");
		return (count > 0) ? true: false;
	}
	
	public List<SubscriberList> getLists() {
		
		 List<SubscriberList> list = _operations.findAll(SubscriberList.class, "SubscriberList");
		 if(!list.isEmpty()){
			 SubscriberDAO dao = new PMTAServiceImpl().getSubscriberDAO();
			 for(SubscriberList slist : list) {
				 slist.setListCount(dao.getCountByListname(slist.getListName())); 
			 }
		 }
		 return list;
	}
	
	public List<SubscriberList> getListNames() {
		return _operations.findAll(SubscriberList.class);
	}
	
	 
}
