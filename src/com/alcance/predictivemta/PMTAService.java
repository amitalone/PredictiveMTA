package com.alcance.predictivemta;

import com.alcance.predictivemta.dao.CampaingDAO;
import com.alcance.predictivemta.dao.ESPMailingResourceDAO;
import com.alcance.predictivemta.dao.GlobalMessageHistoryDAO;
import com.alcance.predictivemta.dao.SubscriberDAO;
import com.alcance.predictivemta.dao.SubscriberListDAO;
import com.alcance.predictivemta.infrastructure.GlobalMessageHistory;
import com.alcance.predictivemta.infrastructure.MailingIPDAO;

public interface PMTAService {
	public SubscriberListDAO getSubscriberListDAO();
	public CampaingDAO getCampaignDAO();
	public SubscriberDAO getSubscriberDAO();
	public MailingIPDAO getMailingIPDAO(); 
	public ESPMailingResourceDAO getESPMailingResourceDAO();
	public GlobalMessageHistoryDAO getMessageHistoryDAO();
	public ConfigurationBean getAppConfig();
}
