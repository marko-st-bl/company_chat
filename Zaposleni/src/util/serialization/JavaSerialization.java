package util.serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Notification;
import service.LoggerFactory;
import service.PropertiesUtil;

public class JavaSerialization implements Serialization {
	
	private static final Properties PROP=PropertiesUtil.loadProperties();
	private static final Logger LOGGER=LoggerFactory.getLogger(JavaSerialization.class.getName());

	@Override
	public void serialize(Notification notification) {
		try {
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(PROP.getProperty("notification.dir")+
					File.separator+"java"+notification.getId()+".ser"));
			out.writeObject(notification);
			out.close();
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE,e.getMessage(),e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,e.getMessage(),e);
		}
		
	}
	
	

}
