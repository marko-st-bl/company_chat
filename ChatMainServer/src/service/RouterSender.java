package service;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import model.Message;
import util.LoggerUtil;
import util.MessageUtil;
import util.PropertiesUtil;
import util.RabbitConnectionFactoryUtil;

public class RouterSender {
	
	private static final Properties PROPS=PropertiesUtil.loadProperties();
	private static final Logger LOGGER=LoggerUtil.getLogger(RouterSender.class.getName());
	
	public static void sendMessage(String receiverId,Message message) {
		try(Connection connection=RabbitConnectionFactoryUtil.createConnection()){
			Channel channel = connection .createChannel();
			channel.exchangeDeclare(PROPS.getProperty("exchange.name.direct"), BuiltinExchangeType.DIRECT);
			byte[] messageInBytes=MessageUtil.toByteArray(message);
			if(messageInBytes!=null) {
				channel.basicPublish(PROPS.getProperty("exchange.name.direct"), receiverId, null, messageInBytes);
				LOGGER.config("message: {"+message.getContent()+"} sent to userId: "+receiverId);
			}
			channel.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(),e);
		} catch (TimeoutException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(),e);
		}
	}
	
	public static void sendImage() {
		
	}
	


}
