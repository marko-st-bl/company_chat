package util.serialization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import model.Notification;
import service.LoggerFactory;
import service.PropertiesUtil;

public class GsonSerialization implements Serialization{
	
	private static final Properties PROP=PropertiesUtil.loadProperties();
	private static final Logger LOGGER=LoggerFactory.getLogger(GsonSerialization.class.getName());

	@Override
	public void serialize(Notification notification) {
		Gson gson=new Gson();
		try {
			FileWriter out = new FileWriter(new File(PROP.getProperty("notification.dir")+File.separator+
					"gson"+notification.getId()+".json"));
			out.write(gson.toJson(notification));
			out.close();
		} catch (JsonIOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	

}
