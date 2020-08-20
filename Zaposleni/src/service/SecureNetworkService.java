package service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

import protocol.Protocol;

public class SecureNetworkService {

	private static final Properties PROP = PropertiesUtil.loadProperties();
	private static final Logger LOGGER = LoggerFactory.getLogger(SecureNetworkService.class.getName());

	private Socket socket;
	public ObjectOutputStream out;
	public ObjectInputStream in;

	public SecureNetworkService() {
		System.setProperty("javax.net.ssl.trustStore", PROP.getProperty("key.store.path"));
		System.setProperty("javax.net.ssl.trustStorePassword", PROP.getProperty("key.store.password"));

		try {
			SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			socket = ssf.createSocket(PROP.getProperty("chat.serverip"),
					Integer.parseInt(PROP.getProperty("chat.server.port")));
			LOGGER.config("Socket created");

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (ConnectException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, "Couldnt connect to server!");
		} catch (UnknownHostException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void disconnect() {
			try {
				out.writeObject(Protocol.END);
				socket.close();
				in.close();
				out.close();
				LOGGER.config("Socked closed.");
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
	}

}
