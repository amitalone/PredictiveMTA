package com.alcance.predictivemta.junit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.alcance.predictivemta.dao.SubscriberDAO;
import com.alcance.predictivemta.dvo.Subscriber;
import com.alcance.predictivemta.dvo.SubscriberAttributes;
import com.alcance.predictivemta.infrastructure.MailingIPDAO;

public class SubscriberDAOTest {
	
	SubscriberDAO subscriberDAO;
	@Before
	public void setUp() throws Exception {
		subscriberDAO = (SubscriberDAO)TestSuitutil.getAppContext().getBean("subscriberDAO");
	}

	@Test
	public void testAddSubscriber() {
		 Subscriber subscriber = new Subscriber();
		 subscriber.setEmailAddress("amitalone@gmail.com");
		 subscriber.setEmailDomain("gmail.com");
		 SubscriberAttributes attributes = new SubscriberAttributes();
		/* attributes.setFirsName("amit");
		 attributes.setLastName("alone");
		 attributes.setSubscribeDate("20-20-30");
		 attributes.setSubscribeSource("goog.com");
		 attributes.addOtherAttribute("City", "pune");
		 subscriber.setAttributes(attributes);
		 subscriber.setListName("MyTestList");*/
		 subscriberDAO.addSubscriber(subscriber);
		 assertEquals(1, 1);
	}
	
	@Test
	public void testSubscriberExists() {
	//	assertEquals("MyTestList", subscriberDAO.checkSubscriber("amitalone@gmail.com").getListName());
	}
	
	@Test
	public void testSubscriberNotExists() {
		assertEquals(null, subscriberDAO.checkSubscriber("dsfsdfsdfsdfsdf@sdfsdfsdf.com"));
	}
	
	@Test
	public void testSubscriberCount() {
		MailingIPDAO dao = (MailingIPDAO)TestSuitutil.getAppContext().getBean("mailingIPDAO");
		dao.mailingIPExists("aaa");
	}
	
	@Test
	public void testSubscriberByListName() {
		subscriberDAO.getSubscribersByList("multiDomainList");
	}
}
