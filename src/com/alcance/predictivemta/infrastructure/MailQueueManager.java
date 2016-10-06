package com.alcance.predictivemta.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
//import org.apache.log4j.Logger;


public class MailQueueManager {
	private ExecutorService _executorService;
	private List<Future<Integer>> _futures = new ArrayList<Future<Integer>>();
	private static MailQueueManager _instance = null;
	//private final Logger log = Logger.getLogger(MailQueueManager.class);
	
	private MailQueueManager() {
		_executorService = Executors.newCachedThreadPool();
	}
	
	public static MailQueueManager getInstance() {
		if(null == _instance) {
			_instance = new MailQueueManager();
		}
		return _instance;
	}
	
	public void submitMailQueue(MailQueuingThread thread) {
	//	log.debug("Thread submited");
	  _futures.add(_executorService.submit(thread));
	}
	
	public void shutdown() throws Exception {
	//	log.debug("shutdown requested");
		_executorService.shutdown();
		_instance = null;
	}
	
	public void waitForFuture() throws Exception{
	//	log.debug("Wating for future");
		for(Future<Integer> future : _futures) {
			future.get();
		}
	}
}
