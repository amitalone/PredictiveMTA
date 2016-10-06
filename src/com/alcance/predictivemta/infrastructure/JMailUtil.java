package com.alcance.predictivemta.infrastructure;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;


import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils;

import com.alcance.predictivemta.ApplicationException;
import com.alcance.predictivemta.CommonUtil;
import com.alcance.predictivemta.PMTAServiceImpl;
import com.alcance.predictivemta.TrackingVariables;
import com.alcance.predictivemta.dvo.Campaing;
import com.alcance.predictivemta.dvo.Subscriber;
import com.alcance.predictivemta.infrastructure.log.LogActivityManager;
import com.alcance.predictivemta.infrastructure.log.SubscriberActivity;
import com.sun.mail.smtp.SMTPMessage;

public class JMailUtil {
	
 private static final Logger log = Logger.getLogger(JMailUtil.class);
	
	private static Map<String, Session> _mailSessions = new HashMap<String, Session>();
	
	public static Session getMailSession(String serverIP) {
	log.debug("getMailSession() starts"); 
		Session session = _mailSessions.get(serverIP);
		if(null == session) {
			 Properties props = System.getProperties();
			 props.put("mail.host", serverIP);
			 props.put("mail.smtp.port", "3535");
			 props.put("mail.smtp.auth", "true"); 
			 session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("alcance", "Newuser123");
					}
				  });
			
			 _mailSessions.put(serverIP, session);
		}
	log.info("Mail Session for " +serverIP + "Returned");
	log.debug("getMailSession() ends");
		return session;
	}
	
	 public static Message getMessage(Session session, Campaing campaing, MailingIP ip) throws Exception{
	log.debug("getMessage() starts"); 
		Message message = new SMTPMessage(session);
		message.setFrom(new InternetAddress(campaing.getReplyTo(), campaing.getFromName()));
		message.setSubject(campaing.getSubject());
		message.addHeader("x-virtual-mta", ip.getMailingIP());
		message.setSentDate(new Date());
		message.saveChanges();
	log.debug("getMessage() ends");
		return message;
	}
	
	 public static void sendMail(Message message, MailingIP ip, Subscriber subscriber, Campaing campaing) throws Exception
	 {
		// System.out.println("Sending mail to " + message.getRecipients(Message.RecipientType.TO)[0].toString());
		// log.debug("[" +ip.getMailingIP() +"] sendMail() to " + subscriber.getEmailAddress()); 
		 
		 if( null == ip.getTrsansactionID()) {
			throw new ApplicationException("Can not send mails without transaction ID"); 
		 }
		 
		 GlobalMessageHistory history = new GlobalMessageHistory();
		 history.setCampaingName(campaing.getName());
		 history.setFrom(campaing.getReplyTo());
		 history.setFromText(campaing.getFromName());
		 history.setIpAddress(ip.getMailingIP());
		 history.setListName(subscriber.getListName());
		 history.setEmailTo(subscriber.getEmailAddress());
		 history.setTransactionID(ip.getTrsansactionID());
		 history.setTargetDomain(subscriber.getEmailDomain());
		 
		 String trackerID = new PMTAServiceImpl().getMessageHistoryDAO().saveMessage(history);
		 message.addHeader("X-Track", trackerID);
		 
		 setContent(message, campaing, subscriber, trackerID);
		// Transport.send(message); 
		 log.debug("[" +ip.getMailingIP() +"] sendMail() to " + subscriber.getEmailAddress()); 
		 String date = CommonUtil.formatDate(new Date());
		 subscriber.addMessageHistory(date, trackerID);
		 subscriber.addCampaingHistory(date, campaing.getName());
		 LogActivityManager.getInstance().submitActivity(new SubscriberActivity(subscriber));
	 }
	 
	private static void setContent(Message message, Campaing campaing, Subscriber subscriber, String trackerID) throws Exception {
	log.debug("setContent() start");
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(subscriber.getEmailAddress()));
		MimeMultipart alternative = new MimeMultipart();
		MimeBodyPart plainTextBodyPart = new MimeBodyPart();
        String text = campaing.getEmailBody().getTextPart();
        subtituteVariables(text, trackerID, subscriber.getEmailAddress());
		plainTextBodyPart.setText(text);
        alternative.addBodyPart(plainTextBodyPart);
        
        MimeBodyPart htmlBodyPart = new MimeBodyPart();
        String htmlText = campaing.getEmailBody().getHtmlPart();
        subtituteVariables(htmlText, trackerID, subscriber.getEmailAddress());
        htmlBodyPart.setContent(htmlText, "text/html");
        alternative.addBodyPart(htmlBodyPart);
        
        Multipart mixed = new MimeMultipart();
        MimeBodyPart wrap = new MimeBodyPart();
        wrap.setContent(alternative);
        mixed.addBodyPart(wrap);
        
        message.setContent(mixed);
        message.saveChanges();
   log.debug("setContent() ends");
	}
	
	private static void subtituteVariables(String textContent, String trackingID, String emailAddress) {
		 textContent = textContent.replace(TrackingVariables.MESSAGEID+"", trackingID);
		 textContent = textContent.replace(TrackingVariables.EMAILADDRESS+"", emailAddress);
		 textContent = textContent.replace(TrackingVariables.RANDOMUID+"", new String(DigestUtils.md5Digest(UUID.randomUUID().toString().getBytes())));
	}
	
 
	 
}
