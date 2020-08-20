package util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitConnectionFactoryUtil {
	
	public static Connection createConnection() throws TimeoutException,IOException{
		ConnectionFactory factory=new ConnectionFactory();
		return factory.newConnection();
	}
}
