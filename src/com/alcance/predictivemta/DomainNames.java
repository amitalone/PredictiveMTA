package com.alcance.predictivemta;

import java.util.ArrayList;
import java.util.List;

public class DomainNames {

	 private static  List<String> domains = null;
	 
	 static {
		domains = new ArrayList<String>();
		domains.add("GMAIL");
		domains.add("HOTMAIL");
		domains.add("AOL");
		domains.add("COMCAST");
		domains.add("YAHOO");
	 }
	 
	 public static List<String> getDomainNames() {
		 return domains;
	 }
	 
	 public static String resolve(String emailAddress) {
		 
		 emailAddress = emailAddress.toLowerCase();
		 String values[] =  emailAddress.split("@");
		 if(values.length !=2) {
			 return null;
		 }
		 
		 
		 if(values[1].contains("gmail")) {
			 return domains.get(0);
		 }else if(values[1].contains("hotmail") || 
				 values[1].contains("live") || 
				 values[1].contains("msn") ||
				 values[1].contains("outlook")) {
			 return domains.get(1);
		 
		 }else if(values[1].contains("aol")) {
			 return domains.get(2);
		 }else if(values[1].contains("comcast")) {
			 return domains.get(3);
		 }else if(values[1].contains("yahoo")) {
			 return domains.get(4);
		 }
		 return null;
	 }

}
