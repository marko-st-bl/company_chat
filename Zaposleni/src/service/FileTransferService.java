package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.FileTransferI;

public class FileTransferService {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(FileTransferService.class.getName());
	private static final Properties PROPS=PropertiesUtil.loadProperties();
	
	private FileTransferI fileTransfer;
	
	public FileTransferService() {
		super();
		
		System.setProperty("java.security.policy",PROPS.getProperty("client.policyfile"));
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Registry registry = LocateRegistry.getRegistry(Integer.parseInt(PROPS.getProperty("registry.port")));
			fileTransfer=(FileTransferI) registry.lookup(PROPS.getProperty("file.transfer.server.name"));
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public boolean sendFile(File file,String id) {
		try(FileInputStream in=new FileInputStream(file)){
			byte data[]=new byte[(int)file.length()];
			in.read(data, 0, data.length);
			return fileTransfer.sendFile(data, file.getName(), id);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return false;
	}
	
	public List<String> getUserFiles(String id){
		List<String> userFiles=new ArrayList<>();
		try {
			userFiles=fileTransfer.getUserFiles(id);
		} catch (RemoteException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return userFiles;
	}
	
	
	
	public FileTransferI getFileTransfer() {
		return fileTransfer;
	}
	public void setFileTransfer(FileTransferI fileTransfer) {
		this.fileTransfer = fileTransfer;
	}

}
