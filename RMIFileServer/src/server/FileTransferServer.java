package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.FileTransferI;
import util.LoggerUtil;
import util.PropertiesUtil;

public class FileTransferServer implements FileTransferI {

	private static final Logger LOGGER = LoggerUtil.getLogger(FileTransferServer.class.getName());
	private static final Properties PROP = PropertiesUtil.loadProperties();

	public FileTransferServer() {
		super();
	}

	@Override
	public List<String> getUserFiles(String id) throws RemoteException {
		List<String> fileNames = new ArrayList<>();
		File userDir = new File(PROP.getProperty("user.files") + File.separator + id);
		userDir.mkdirs();
		File[] userFiles = userDir.listFiles();
		if (userFiles != null) {
			for (File f : userFiles) {
				if (f.isFile()) {
					fileNames.add(f.getName());
				}
			}
		}
		return fileNames;
	}

	@Override
	public byte[] downloadFile(String name, String id) throws RemoteException {
		byte[] data;
		File file = new File(PROP.getProperty("user.files") + File.separator + id + File.separator + name);
		data = new byte[(int) file.length()];
		try (FileInputStream in = new FileInputStream(file)) {
			in.read(data, 0, data.length);
			LOGGER.config("File: "+name+ " loaded");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return data;
	}

	@Override
	public boolean sendFile(byte[] data, String name, String id) throws RemoteException {
		File file = new File(PROP.getProperty("user.files") + File.separator + id + File.separator + name);
		try (FileOutputStream out = new FileOutputStream(file)) {
			out.write(data);
			LOGGER.config("File: " + name + " saved...");
			return true;
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		LOGGER.config("File cant be saved!");
		return false;
	}

	public static void main(String[] args) {
		System.setProperty("java.security.policy", PROP.getProperty("server.policyfile"));
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			FileTransferServer server = new FileTransferServer();
			FileTransferI stub = (FileTransferI) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(Integer.parseInt(PROP.getProperty("server.port.number")));
			registry.rebind("FileTransferServer", stub);
			LOGGER.config("FileTransferServer is running on port: " + PROP.getProperty("server.port.number"));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

	}

}
