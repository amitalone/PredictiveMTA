package com.alcance.predictivemta.junit;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

//import org.apache.log4j.PropertyConfigurator;

import com.alcance.predictivemta.ApplicationException;
import com.alcance.predictivemta.CommonUtil;
import com.alcance.predictivemta.PMTAService;
import com.alcance.predictivemta.PMTAServiceImpl;
import com.alcance.predictivemta.dao.SubscriberListDAO;
import com.alcance.predictivemta.dvo.Campaing;
import com.alcance.predictivemta.dvo.EmailBody;
import com.alcance.predictivemta.groupbyResult.SentMailingIPCountResult;
import com.alcance.predictivemta.infrastructure.MailRouter;
import com.alcance.predictivemta.infrastructure.MailingIP;
import com.alcance.predictivemta.infrastructure.SendingHistory;
import com.alcance.predictivemta.infrastructure.log.MailingIPActivity;

public class TestMainClass {

	/**
	 * @param args
	 * @throws ApplicationException 
	 */
	public static void main(String[] args) throws ApplicationException, Exception {

		 PMTAService service = new PMTAServiceImpl();
		 Campaing c = service.getCampaignDAO().getCampaing("smallCamp");
	 
	 	/*c.setTotalSentCount(0);
		c.setTotalDomainCounts(null);
		c.setHistory(null);
		service.getCampaignDAO().saveCampaing(c); */
		
		 
		 new MailRouter().route(c);  
		 
		/*Calendar cal = Calendar.getInstance();
		cal.set(2013, 10, 15);
		Date date = cal.getTime();
		//imCpaImport.importRecord(cal.getTime());
		 
		System.out.println("====================");*/
		 
		
		/*c.setName("MyTester");
		
		c.setFromName("Amit");
		c.setReplyTo("amit@common.com");
		EmailBody b = new EmailBody();
		b.setHtmlPart("html n stuff");
		b.setTextPart("Text n stuff");
		c.setEmailBody(b);
	 
	 	
		
		
		System.out.println("All Done");*/
		
		/* MailingIP ip = service.getMailingIPDAO().getMailingIP("66.135.61.176");
		 ip.setTrsansactionID("824b5f37-d2c0-4ef2-8b3f-470782aa2565");
		 System.out.println(ip);
		 new MailingIPActivity(ip).call();*/
	}

}
