package service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;

import model.Notification;
import view.Main;

public class NotificationListener extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationListener.class.getName());
	private static final Properties PROPS = PropertiesUtil.loadProperties();

	JLabel labelNotification;

	public NotificationListener(JLabel labelNotification) {
		super();
		this.labelNotification = labelNotification;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[256];
		try (MulticastSocket socket = new MulticastSocket(
				Integer.parseInt(PROPS.getProperty("multicast.port.number")))) {
			InetAddress groupAddress = InetAddress.getByName(PROPS.getProperty("multicast.ip.address"));
			socket.joinGroup(groupAddress);
			while (Main.isRunning) {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String message = new String(packet.getData(), packet.getOffset(), packet.getLength());
				LOGGER.config("Notification received: ");
				labelNotification.setText(message);
				LOGGER.config("Notification: " + message + " is displayed.");
				new Notification(message).serialize();
				LOGGER.config("Notification: " + message + " is serialized.");
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
