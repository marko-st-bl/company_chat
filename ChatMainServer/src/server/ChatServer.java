package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import model.User;
import util.LoggerUtil;
import util.NewUserListener;
import util.PropertiesUtil;
import util.QueuesFactory;


public class ChatServer {

	private static final Logger LOGGER=LoggerUtil.getLogger(ChatServer.class.getName());
	private static final Properties PROPS=PropertiesUtil.loadProperties();
	
	public static ArrayList<User> onlineUsers=new ArrayList<>();
	public static HashMap<String, ScreenReceiver> streams=new HashMap<>();
	public static HashMap<String, String> streamPairs=new HashMap<>();

	public static void main(String[] args) {     
        
		System.setProperty("javax.net.ssl.keyStore", PROPS.getProperty("key.store.path"));
		System.setProperty("javax.net.ssl.keyStorePassword", PROPS.getProperty("key.store.password"));
		LOGGER.config("System Properties are set.");
		
		int port=Integer.parseInt(PROPS.getProperty("chat.server.port"));
		ServerSocket server;
		try {
			SSLServerSocketFactory factory= (SSLServerSocketFactory) 
					SSLServerSocketFactory.getDefault();
			server =  factory.createServerSocket(port);
			LOGGER.config("Server is started.");
			QueuesFactory.createQueues();
			new NewUserListener().start();
			LOGGER.config("Queues created.");
			while(true) {
				SSLSocket client=(SSLSocket) server.accept();
				ServerThread st=new ServerThread(client);
				st.start();
				LOGGER.config("User connected.");
			}
		}catch(IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
			
	}



}
