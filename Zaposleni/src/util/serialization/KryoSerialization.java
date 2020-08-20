package util.serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Output;

import model.Notification;
import service.LoggerFactory;
import service.PropertiesUtil;

public class KryoSerialization implements Serialization{
	
	private static final Properties PROP=PropertiesUtil.loadProperties();
	private static final Logger LOGGER=LoggerFactory.getLogger(KryoSerialization.class.getName());
	@Override
	public void serialize(Notification notification) {
		try (Output output = new Output(new FileOutputStream(PROP.getProperty("notification.dir")+
				File.separator+"kryo"+notification.getId()+".kryo"))){
		Kryo kryo=new Kryo();
		kryo.register(Notification.class);
		kryo.writeClassAndObject(output, notification);
		output.close();
		} catch (KryoException e) {
			LOGGER.log(Level.SEVERE,e.getMessage(),e);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE,e.getMessage(),e);
		}	
	}
	
	

}
