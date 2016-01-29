package org.usfirst.frc.team360.robot.subsystems;

import com.pitts.Math;
// Imports log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Log {

	private static final Logger logger = (Logger) LogManager.getLogger(Logger.class);
	
	public static void main(final String... args) {
		
		// Anything within these brackets define what the logger will log.
		
		logger.trace("Beginning Logger.");
		Math Math = new Math();
		if (!Math.johnCena()) {
			logger.error("Test.");
		}
		logger.trace("Exiting Logger.");
	}
}