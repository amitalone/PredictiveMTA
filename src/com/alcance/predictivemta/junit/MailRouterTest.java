package com.alcance.predictivemta.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alcance.predictivemta.ApplicationException;
import com.alcance.predictivemta.PMTAServiceImpl;
 
import com.alcance.predictivemta.dao.ESPMailingResourceDAO;
import com.alcance.predictivemta.dvo.Campaing;
import com.alcance.predictivemta.infrastructure.DomainCap;
import com.alcance.predictivemta.infrastructure.MailRouter;

public class MailRouterTest {

	@Test
	public void testGetMailingIPs() {
		//new MailRouter().getMailingIPs("YAHOO");
		DomainCap cap = new DomainCap();
		cap.setDomainName("HOTMAIL");
		cap.setLowerDay(0);
		cap.setUpperDay(10);
		cap.setAllowedCount(100);
		new PMTAServiceImpl().getESPMailingResourceDAO().addDomainCap(cap);
		cap = new DomainCap();
		cap.setDomainName("HOTMAIL");
		cap.setLowerDay(11);
		cap.setUpperDay(20);
		cap.setAllowedCount(200);
		new PMTAServiceImpl().getESPMailingResourceDAO().addDomainCap(cap);
		cap = new DomainCap();
		cap.setDomainName("HOTMAIL");
		cap.setLowerDay(21);
		cap.setUpperDay(30);
		cap.setAllowedCount(300);
		new PMTAServiceImpl().getESPMailingResourceDAO().addDomainCap(cap);
		cap = new DomainCap();
		cap.setDomainName("HOTMAIL");
		cap.setLowerDay(31);
		cap.setUpperDay(40);
		cap.setAllowedCount(400);
		new PMTAServiceImpl().getESPMailingResourceDAO().addDomainCap(cap);
		cap = new DomainCap();
		cap.setDomainName("HOTMAIL");
		cap.setLowerDay(41);
		cap.setUpperDay(50);
		cap.setAllowedCount(500);
		new PMTAServiceImpl().getESPMailingResourceDAO().addDomainCap(cap); 
		
	}
	
	@Test
	public void testGetDayCap() {
		
		ESPMailingResourceDAO dao = new PMTAServiceImpl().getESPMailingResourceDAO();
		try {
			assertEquals(200, dao.getDayCap("YAHOO", 15).getAllowedCount());
			assertEquals(100, dao.getDayCap("YAHOO", 1).getAllowedCount());
			assertEquals(500, dao.getDayCap("YAHOO", 43).getAllowedCount());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@Test 
	public void testMailRouting() throws ApplicationException {
		Campaing c = new Campaing();
		c.addList("SampleList1");
		c.addList("multiDomainList");
		new MailRouter().route(c);
	}
}
