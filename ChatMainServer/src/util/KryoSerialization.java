package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Output;

import model.Message;
import util.LoggerUtil;
import util.PropertiesUtil;

public class KryoSerialization {
	
	private static final Properties PROP=PropertiesUtil.loadProperties();
	private static final Logger LOGGER=LoggerUtil.getLogger(KryoSerialization.class.getName());
	
	public void serialize(Message message,String receiverID) {
		File userDir=new File(PROP.getProperty("messages.dir")+File.separator+receiverID);
		userDir.mkdirs();
		try (Output output = new Output(new FileOutputStream(userDir.getPath()+File.separator+message.getTimeInMillis()+".kryo"))){
		Kryo kryo=new Kryo();
		kryo.register(Message.class);
		kryo.writeClassAndObject(output, message);
		output.close();
		} catch (KryoException e) {
			LOGGER.log(Level.SEVERE,e.getMessage(),e);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE,e.getMessage(),e);
		}	
	}
	
	

}
