package service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MulticastConnection {
	
	private static final Logger LOGGER=Logger.getLogger(NotificationService.class.getName());
	private static final Properties PROPS=PropertiesUtil.loadProperties();
	
	private static MulticastConnection connection=null;
	
	private InetAddress addr;
	
	public static MulticastConnection getInstance() {
		if(connection==null) {
			connection= new MulticastConnection();
		}
		return connection;
	}
	
	private MulticastConnection() {
		try {
			addr=InetAddress.getByName(PROPS.getProperty("multicast.ip.address"));
		} catch (UnknownHostException e) {
			LOGGER.log(Level.SEVERE,e.getMessage(),e);
		}
		
	}
	
	public boolean sendNotification(String message) {
		byte[] buffer = message.getBytes();
		try(DatagramSocket socket=new DatagramSocket()){
			DatagramPacket packet = 
					new DatagramPacket(buffer, buffer.length, addr, Integer.parseInt(PROPS.getProperty("multicast.port.number")));
			socket.send(packet);
			return true;
		} catch (SocketException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(),e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(),e);
		}
		return false;
	}

}
