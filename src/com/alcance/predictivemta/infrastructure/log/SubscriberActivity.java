package com.alcance.predictivemta.infrastructure.log;


import com.alcance.predictivemta.dvo.Subscriber;

public class SubscriberActivity extends Activity{

	private Subscriber _subscriber;
	public  SubscriberActivity(Subscriber subscriber) {
		_subscriber = subscriber;
	}
	
	public Integer call() throws Exception {
		long sentCount = _subscriber.getGlobalSentCount();
		sentCount++;
		_subscriber.setGlobalSentCount(sentCount);
		getService().getSubscriberDAO().updateSubscriber(_subscriber);
		return 1;
	}
}
