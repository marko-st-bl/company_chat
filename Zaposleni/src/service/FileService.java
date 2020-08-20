package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.Main;

public class FileService {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(FileService.class.getName());

	public static boolean sendFile(File file, String id) {
		FileTransferService fileTransfer = new FileTransferService();
		if (file != null && id != null) {
			boolean status = fileTransfer.sendFile(file, id);
			if (status) {
				JOptionPane.showMessageDialog(null, "File: " + file.getName() + " sent.");
				LOGGER.config("File "+file.getName()+ " sent.");
				return status;
			} else {
				JOptionPane.showMessageDialog(null, "File cant be sent.");
				return status;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bad request.");
			return false;
		}
	}

	public static boolean downloadFile(String name, File dir) {
		if (name != null) {
			FileTransferService fileTransfer = new FileTransferService();
			File file = new File(dir, name);
			try {
				byte[] data = fileTransfer.getFileTransfer().downloadFile(name, Main.USERID);
				FileOutputStream out = new FileOutputStream(file);
				out.write(data);
				out.close();
				JOptionPane.showMessageDialog(new JFrame(), "File succesufuly downloaded.");
				LOGGER.config("File succesufuly downloaded.");
				return true;
			} catch (RemoteException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return false;
	}

}
