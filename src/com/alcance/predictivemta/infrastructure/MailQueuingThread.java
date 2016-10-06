package com.alcance.predictivemta.infrastructure;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.Callable;

import javax.mail.Message;
import javax.mail.Session;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

import com.alcance.predictivemta.dvo.Campaing;
import com.alcance.predictivemta.dvo.Subscriber;
import com.alcance.predictivemta.infrastructure.log.LogActivityManager;
import com.alcance.predictivemta.infrastructure.log.MailingIPActivity;

public class MailQueuingThread implements Callable<Integer> {

	private MailingIP _mailingIP;
	private Campaing _campaign;
 	private static final Logger log = Logger.getLogger(MailQueuingThread.class);
	
	public MailQueuingThread(MailingIP mailingIP, Campaing campaign) {
		log.debug("Creating thread for IP " + mailingIP.getMailingIP());
		_mailingIP = mailingIP;
		_campaign = campaign;
	}
	
	public Integer call() throws Exception {
		start();
		return 1;
	}
	
	private void start() {
		log.debug("start() starts");
		try {
			Session session = JMailUtil.getMailSession(_mailingIP.getServerIP());
			Message message = JMailUtil.getMessage(session, _campaign, _mailingIP);
			Collection<Queue<Subscriber>> queues = _mailingIP.getEmailQueue().values();
			
			for(Queue<Subscriber> queue : queues) {
				for(Subscriber subscriber : queue) {
					JMailUtil.sendMail(message, _mailingIP, subscriber, _campaign);
					_campaign.updateEmailSent();
				}
			}
			
			LogActivityManager.getInstance().submitActivity(new MailingIPActivity(_mailingIP));

		}catch(Exception ex) {
			ex.printStackTrace();
		log.debug("Exception in start()", ex); 
		}
		log.debug("start() ends");
		
	}
	

}
