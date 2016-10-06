package com.alcance.predictivemta.junit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TestSuitutil {

	public static ApplicationContext getAppContext() {
		 return new GenericXmlApplicationContext("SpringConfig.xml"); 
	}
}
