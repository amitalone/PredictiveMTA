package com.alcance.predictivemta.junit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.alcance.predictivemta.dao.SubscriberListDAO;
import com.alcance.predictivemta.dvo.SubscriberList;

public class SubscriberListDAOTest {
	
	SubscriberListDAO dao;
	@Before
	public void setUp() throws Exception {
		dao = (SubscriberListDAO)TestSuitutil.getAppContext().getBean("subscriberListDAO");
	}

	@Test
	public void testAddSubscriberList() {
		 SubscriberList list = new SubscriberList();
		 
		 list.setListName("MyTestList");
		 list.setDefaultFromName("AmitAlone");
		 list.setDefaultReplyTo("amit@gmail.com");
		 dao.createList(list);
		 assertEquals(1, 1);
	}
	
	@Test
	public void testListNameExists() {
		
		assertEquals(true, dao.listNameExists("MyTestList"));
	}
	
	@Test
	public void testListNameNotExists() {
		
		assertEquals(false, dao.listNameExists("1111111100000000033333333334444444"));
	}
}
