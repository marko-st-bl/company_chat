package util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {
	
	private static final Properties PROP=PropertiesUtil.loadProperties();

	public static Logger getLogger(String classname) {
		Logger logger=Logger.getLogger(classname);
		Handler consoleHandler = null;
        Handler fileHandler  = null;
        try {
        	fileHandler  = new FileHandler(PROP.getProperty("log.file"));
        	consoleHandler = new ConsoleHandler();
        	logger.addHandler(fileHandler);
        	logger.addHandler(consoleHandler);
        	consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            logger.setLevel(Level.ALL);
        }catch(IOException e) {
        	logger.log(Level.SEVERE, e.getMessage(),e);
        }
        return logger;
	}
	
}
