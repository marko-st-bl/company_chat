package service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import view.Main;

public class StreamUtil extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class.getName());
	private static final Properties PROPS = PropertiesUtil.loadProperties();
	public static boolean STREAMING=false;
	private ServerSocket server;
	
	public StreamUtil() {
		super();
		STREAMING=true;
	}
	

	@Override
	public void run() {
		LOGGER.config("Starting stream...");
		try {
			server=new ServerSocket(Integer.parseInt(PROPS.getProperty("streaming.port")));
			while(STREAMING && Main.isRunning) {
				Socket socket=server.accept();
				ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
				BufferedImage img=PrintScreenUtil.printScreen();
				img=PrintScreenUtil.compressImg(img);
				ImageIO.write(img, "jpg", out);
				socket.close();
			}
			server.close();
			LOGGER.config("Stream stopped.");
		} catch(BindException e) {
			LOGGER.config("Stream continued");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
