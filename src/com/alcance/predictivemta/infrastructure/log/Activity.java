package com.alcance.predictivemta.infrastructure.log;

import java.util.concurrent.Callable;

import com.alcance.predictivemta.PMTAService;
import com.alcance.predictivemta.PMTAServiceImpl;

public abstract class Activity implements Callable<Integer>{
	private PMTAService _pmtaService;
	public Activity() {
		_pmtaService = new PMTAServiceImpl();
	}
	
	public PMTAService getService() {
		return _pmtaService;
	}
}
