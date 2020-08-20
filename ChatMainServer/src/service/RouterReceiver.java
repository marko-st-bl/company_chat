package service;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import model.Conversation;
import model.Message;
import util.ConversationFactory;
import util.LoggerUtil;
import util.MessageUtil;
import util.PropertiesUtil;
import util.RabbitConnectionFactoryUtil;

public class RouterReceiver {
	

	private static final Properties PROPS = PropertiesUtil.loadProperties();
	private static final Logger LOGGER = LoggerUtil.getLogger(RouterReceiver.class.getName());

	public static void getMessage(String id){
			Connection connection;
			try {
				connection = RabbitConnectionFactoryUtil.createConnection();
				Channel channel = connection.createChannel();

				channel.exchangeDeclare(PROPS.getProperty("exchange.name.direct"), BuiltinExchangeType.DIRECT);

				String queueName = channel.queueDeclare().getQueue();

				channel.queueBind(queueName, PROPS.getProperty("exchange.name.direct"), id);
				
				Consumer consumer = new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
							byte[] body) throws IOException {
						Message message = MessageUtil.getMessageFromBytes(body);
						LOGGER.config("Message received: "+message);
						ConversationFactory.getConversation(Conversation.calculateId(envelope.getRoutingKey(), message.getSender())).addMessage(message);		
					}
				};
				channel.basicConsume(queueName, true, consumer);
			} catch (TimeoutException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(),e);
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(),e);
			}
			
		
	}
	
	public static void main(String[] args) throws Exception {
		getMessage("0");
	}

}
