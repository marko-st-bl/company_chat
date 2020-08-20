package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PropertiesUtil {
	
	private static final String CONFIG="conf"+File.separator+"config.properties";
	private static final Logger LOGGER=Logger.getLogger(PropertiesUtil.class.getName());
	
	public static Properties loadProperties() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(CONFIG));
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		LOGGER.config("Properties loaded.");
		return prop;
	}
}
