package com.alcance.predictivemta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CommonUtil {
	private final static SimpleDateFormat _dateFormater = new  SimpleDateFormat("dd-MM-yyyy");
	
	public static String formatDate(Date date) {
		return _dateFormater.format(date);
	}
	
	public static String htmlToText(String html) {
		Document doc = Jsoup.parse(html);
		return doc.text();
	}
	
	public static ArrayList<String> pullLinks(String text) {
		ArrayList<String> links = new ArrayList<String>();
		 
		String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while(m.find()) {
		String urlStr = m.group();
		if (urlStr.startsWith("(") && urlStr.endsWith(")"))
		{
		urlStr = urlStr.substring(1, urlStr.length() - 1);
		}
		links.add(urlStr);
		}
		return links;
		
	}
}
