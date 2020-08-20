package server;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import util.LoggerUtil;
import util.PropertiesUtil;

public class ScreenReceiver extends Thread {
	
	private static final Logger LOGGER=LoggerUtil.getLogger(ScreenReceiver.class.getName());
	private static final Properties PROPS=PropertiesUtil.loadProperties();

	private InetAddress clientAddress;
	private Socket socket;
	private ServerSocket ss;
	private boolean streaming;
	
	public ScreenReceiver() {
		super();
		this.streaming=true;
	}
	
	public ScreenReceiver(InetAddress addr) {
		super();
		this.clientAddress=addr;
		streaming=true;
	}
	
	public boolean isStreaming() {
		return streaming;
	}

	public void stopStreaming() {
		this.streaming = false;
	}
	
	public InetAddress getClientAddress() {
		return clientAddress;
	}
	
	public void setClientAddress(InetAddress addr) {
		this.clientAddress=addr;
	}



	@Override
	public void run() {
		try {
			ss = new ServerSocket(Integer.parseInt(PROPS.getProperty("admin.streaming.port")));
			LOGGER.config("Starting broadcasting");
			while (streaming) {
				socket = new Socket(clientAddress, Integer.parseInt(PROPS.getProperty("client.streaming.port")));
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				BufferedImage img = ImageIO.read(in);
				socket.close();
				
				Socket s=ss.accept();
				ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
				ImageIO.write(img, "jpg", out);
				s.close();
			}
			ss.close();
			LOGGER.config("Broadcasting finished.");
		} catch(SocketException e) {
			LOGGER.config("Connection interupted.");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
