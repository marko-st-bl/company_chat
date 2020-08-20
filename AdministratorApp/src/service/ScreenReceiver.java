package service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import model.ImagePanel;
import view.Monitor;

public class ScreenReceiver extends Thread{
	
	private static final Properties PROPS=PropertiesUtil.loadProperties();
	private static final Logger LOGGER=LoggerFactory.getLogger(ScreenReceiver.class.getName());
	
	private Socket socket;
	
	public static boolean STREAMING;
	private String idFrom;
	
	public ScreenReceiver() {
		super();
		STREAMING=true;
	}

	public String getIdFrom() {
		return idFrom;
	}

	@Override
	public void run() {	
		Monitor monitor=new Monitor();
		ImagePanel panel = new ImagePanel();
		monitor.setResizable(true);
		monitor.add(panel);
		monitor.pack();
		monitor.setVisible(true);
		while(STREAMING) {
			try {
				socket=new Socket(PROPS.getProperty("server.address"),
						Integer.parseInt(PROPS.getProperty("server.streaming.port")));
				ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
				BufferedImage img=ImageIO.read(in);
				panel.setImg(img);
				panel.repaint();
				socket.close();
			} catch(SocketException e) {
				LOGGER.config("Stream interupted.");
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		monitor.setVisible(false);
		monitor.dispose();
	}
}
