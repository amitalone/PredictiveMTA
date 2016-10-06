package com.alcance.predictivemta.dao;

import java.util.List;
import java.util.UUID;


import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alcance.predictivemta.CommonUtil;
import com.alcance.predictivemta.ConfigurationBean;
import com.alcance.predictivemta.PMTAServiceImpl;
import com.alcance.predictivemta.TrackingVariables;
import com.alcance.predictivemta.dvo.Campaing;
import com.alcance.predictivemta.dvo.EmailBody;
import com.alcance.predictivemta.dvo.RedirectionPage;
import com.alcance.predictivemta.dvo.SupperesionAddress;
import com.alcance.predictivemta.dvo.TrackingOptions;

public class CampaingDAO {
	
	MongoOperations _operations;
	private final Logger log = Logger.getLogger(CampaingDAO.class);
	
	private ConfigurationBean configBean = null;
	public CampaingDAO(MongoOperations operations) {
		_operations = operations;
		 
	}
	
	//[CLICKLINK]?H=[MESSAGEID]&R=[REDIRECTION]&E=[EMAILID]&X=[LINKHASH]
	//[OPENLINK]?H=[MESSAGEID]&R=[REDIRECTION]&E=[EMAILID]&X=[LINKHASH]
	public void createCampaign(Campaing campaign) {
		configBean = new PMTAServiceImpl().getAppConfig();
	log.debug("createCampaign start");
		TrackingOptions options = campaign.getTracking();
		EmailBody body = campaign.getEmailBody();
		
		 if(options.isTrackPlainTextClick()) {
			String textPart = body.getTextPart();
			List<String> links = CommonUtil.pullLinks(textPart);
			for(String link : links) {
				RedirectionPage page = new RedirectionPage();
				page.setRedirectTo(link);
				saveRedirectionLink(page);
				String newLink = configBean.getClickLink()+"?H="+TrackingVariables.MESSAGEID+"&R="+page.getId()+"&E="+TrackingVariables.EMAILADDRESS+"&X="
				+TrackingVariables.RANDOMUID;
				textPart = textPart.replace(link, newLink);
			}
			body.setTextPart(textPart);
		}
		
		if(options.isTrackClicks()) {
			String htmlContent = body.getHtmlPart();
			Document doc = Jsoup.parse(htmlContent);
			Elements links = doc.select("a[href]");
			for(int i=0; i< links.size(); i++) {
				Element link  = links.get(i);
				String linkText = link.text().toLowerCase();
				if(linkText.contains("unsub") || linkText.contains("remove") || linkText.contains("out")) {
						// Possible Unsubscribe
					RedirectionPage page = new RedirectionPage();
					String oldLink = link.attr("href");
					page.setRedirectTo(oldLink);
					saveRedirectionLink(page);
					String newLink = configBean.getUnsubscribeLink()+"?H="+TrackingVariables.MESSAGEID+"&R="+page.getId()+"&E="+TrackingVariables.EMAILADDRESS+"&X="+TrackingVariables.RANDOMUID;
					htmlContent = htmlContent.replace(oldLink, newLink);
				} else {
					// click link
					RedirectionPage page = new RedirectionPage();
					String oldLink = link.attr("href");
					page.setRedirectTo(oldLink);
					saveRedirectionLink(page);
					String newLink = configBean.getClickLink()+"?H="+TrackingVariables.MESSAGEID+"&R="+page.getId()+"&E="+TrackingVariables.EMAILADDRESS+"&X="+TrackingVariables.RANDOMUID;
					htmlContent = htmlContent.replace(oldLink, newLink);
				}
			}
			body.setHtmlPart(htmlContent);
		}
		
		if(options.isTrackOpen()) {
			String pixelLink = configBean.getTrackinkingPixelLink()+"?H="+TrackingVariables.MESSAGEID+"&R="+UUID.randomUUID()+"&E="+TrackingVariables.EMAILADDRESS+"&X="+TrackingVariables.RANDOMUID;
			String htmlContent = body.getHtmlPart();
			htmlContent = htmlContent.replace("</body>", "<img src=\"" + pixelLink + "\"/> </body>");
			body.setHtmlPart(htmlContent);
		} 
		_operations.insert(campaign);
		log.debug("createCampaign ends");
	}
	
	public boolean campaignNameExists(String campaignName) {
	log.debug("campaignNameExists start");
		Query query = new Query(Criteria.where("name").is(campaignName));
		long count = _operations.count(query, "SubscriberList");
	log.debug("campaignNameExists ends");
		return (count > 0) ? true: false;
	}
	
	public List<Campaing> getCampaingList() {
	log.debug("getCampaingList()");
		return _operations.findAll(Campaing.class);
	}
	
	public Campaing getCampaing(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		return _operations.findOne(query, Campaing.class);
	}
	
	public void saveCampaing(Campaing campaing) {
		_operations.save(campaing);
	}
	
	public void addSuppressionAddress(SupperesionAddress address) {
		_operations.insert(address);
	}
	
	public boolean isSuppressed(String address, String campaignName) {
		Query query = new Query(Criteria.where("campaignName").is(campaignName).and("emailAddress").is(address));
		SupperesionAddress result = _operations.findOne(query, SupperesionAddress.class);
		return (null == result) ? false : true;
	}
	 
	public void saveRedirectionLink(RedirectionPage page) {
		_operations.insert(page);
	}
	
}
