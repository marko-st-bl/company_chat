package service;

import java.util.Properties;

import util.serialization.KryoSerialization;
import util.serialization.GsonSerialization;
import util.serialization.JavaSerialization;
import util.serialization.Serialization;
import util.serialization.XMLSerialization;

public class SerializationUtil {
	
	public static final Properties PROP=PropertiesUtil.loadProperties();

	public static Serialization getSerialization(int id) {
			int result=id%4;
			switch(result) {
			case 0:
				return new JavaSerialization();
			case 1:
				return new KryoSerialization();
			case 2:
				return new GsonSerialization();
			case 3:
				return new XMLSerialization();
			default:
				return new JavaSerialization();
		}
		
	}

}
