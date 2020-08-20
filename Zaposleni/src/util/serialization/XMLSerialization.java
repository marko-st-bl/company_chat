package util.serialization;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Notification;
import service.LoggerFactory;
import service.PropertiesUtil;

public class XMLSerialization implements Serialization{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(XMLSerialization.class.getName());
	private static final Properties PROP=PropertiesUtil.loadProperties();

	@Override
	public void serialize(Notification notification) {
		try {
			XMLEncoder encoder = new XMLEncoder(new FileOutputStream(new File(PROP.getProperty("notification.dir")+
					File.separator+"xml"+notification.getId()+".xml")));
			encoder.writeObject(notification);
			encoder.close();
		}catch(IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		
	}
	
	

}
