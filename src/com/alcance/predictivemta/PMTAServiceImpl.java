package com.alcance.predictivemta;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;


import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.alcance.predictivemta.dao.CampaingDAO;
import com.alcance.predictivemta.dao.ESPMailingResourceDAO;
import com.alcance.predictivemta.dao.GlobalMessageHistoryDAO;
import com.alcance.predictivemta.dao.SubscriberDAO;
import com.alcance.predictivemta.dao.SubscriberListDAO;
import com.alcance.predictivemta.infrastructure.MailingIPDAO;

public class PMTAServiceImpl implements PMTAService {
 
	static {
		try {
			Properties props = new Properties();
			System.out.println("========= " +PMTAServiceImpl.class.getResource("").getPath());
			try {
			props.load(new FileInputStream("conf/log4j.properties"));
			}catch(FileNotFoundException fnf) {
				props.load(PMTAServiceImpl.class.getResourceAsStream("log4j.properties"));
			}
			PropertyConfigurator.configure(props);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	final ApplicationContext context;
	
	public PMTAServiceImpl() {
		context = new GenericXmlApplicationContext("SpringConfig.xml"); 
	}

	@Override
	public SubscriberListDAO getSubscriberListDAO() {
		// TODO Auto-generated method stub
		return (SubscriberListDAO)context.getBean("subscriberListDAO");
	}

	@Override
	public CampaingDAO getCampaignDAO() {
		// TODO Auto-generated method stub
		return  (CampaingDAO)context.getBean("campaingDAO");
	}

	@Override
	public SubscriberDAO getSubscriberDAO() {
		// TODO Auto-generated method stub
		return  (SubscriberDAO)context.getBean("subscriberDAO");
	}

	@Override
	public MailingIPDAO getMailingIPDAO() {
		// TODO Auto-generated method stub
		return  (MailingIPDAO)context.getBean("mailingIPDAO");
	}
	
	@Override
	public ESPMailingResourceDAO getESPMailingResourceDAO() {
		// TODO Auto-generated method stub
		return  (ESPMailingResourceDAO)context.getBean("espMailingResourceDAO");
	}

	@Override
	public GlobalMessageHistoryDAO getMessageHistoryDAO() {
		return  (GlobalMessageHistoryDAO)context.getBean("globalMessageHistory");
	}

	@Override
	public ConfigurationBean getAppConfig() {
		return  (ConfigurationBean)context.getBean("configBean");
	}
}
