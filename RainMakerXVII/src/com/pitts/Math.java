package com.pitts;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Math {
	static final Logger logger = LogManager.getLogger(Math.class.getName());
	
	public boolean johnCena() {
		logger.entry();
		logger.error("Error!");
		return logger.exit(false);
	}

}