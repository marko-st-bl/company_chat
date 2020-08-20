package com.marko.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {
	
	

	public static Logger getLogger(String classname) {
		Logger logger=Logger.getLogger(classname);
		Handler consoleHandler = null;
        consoleHandler = new ConsoleHandler();
		logger.addHandler(consoleHandler);
		consoleHandler.setLevel(Level.ALL);
		logger.setLevel(Level.ALL);
        return logger;
	}
	
}

