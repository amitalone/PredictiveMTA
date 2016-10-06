package com.alcance.predictivemta.infrastructure.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//import org.apache.log4j.Logger;

import com.alcance.predictivemta.dao.GlobalMessageHistoryDAO;

public class LogActivityManager {
	
	private static LogActivityManager _instance = null;
	ExecutorService _executorService = null;
	private List<Future<Integer>> _futures = new ArrayList<Future<Integer>>();
	//private final Logger log = Logger.getLogger(LogActivityManager.class);
	
	private LogActivityManager() {
		_executorService = Executors.newFixedThreadPool(100);
	}
	
	public static LogActivityManager getInstance() {
		if(null == _instance) {
			_instance = new LogActivityManager();
		}
		return _instance;
	}
	
	public void submitActivity(Activity activity) {
		////log.debug("Activity Submited");	
		_futures.add(_executorService.submit(activity));
	}
	
	public void shutdown() {
		////log.debug("Shutdown requested");	
		_executorService.shutdown();
		_instance = null;
	}
	
	public void waitForFuture() throws Exception{
		////log.debug("Wating for future");	
		for(Future<Integer> f : _futures) {
			f.get();
		}
	}
}
