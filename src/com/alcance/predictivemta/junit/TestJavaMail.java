package com.alcance.predictivemta.junit;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestJavaMail {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Properties properties = System.getProperties();
		  properties.put("mail.host", "66.135.61.172");
		  properties.put("mail.smtp.port", "3535");
		/*  properties.put("mail.user", "alcance");
		  properties.put("mail.password", "Newuser123");*/
		  properties.put("mail.smtp.auth", "true"); 
		  
		   
		  
		  Session session = Session.getDefaultInstance(properties,  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("alcance", "Newuser123");
				}
			  });
		  
		  session.setDebug(true);
		  
		  MimeMessage message = new MimeMessage(session);
		  message.addHeader("x-virtual-mta", "66.135.57.11");
		  message.setFrom(new InternetAddress("root@localhost.com"));
		  message.addRecipient(Message.RecipientType.TO,
                  new InternetAddress("amitalone@gmail.com"));
		  message.setSubject("This is the Subject Line!");
		  message.setText("This is actual message");
		  
		  
		  Transport.send(message);
		  
		  
	}

}
