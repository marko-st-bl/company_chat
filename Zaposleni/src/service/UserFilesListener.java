package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JList;

import view.Main;

public class UserFilesListener extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserFilesListener.class.getName());
	private static final Properties PROPS=PropertiesUtil.loadProperties();

	private FileTransferService fileTransfer;
	private JList<String> receivedFiles;

	public UserFilesListener(JList<String> receivedFiles) {
		this.receivedFiles = receivedFiles;
		fileTransfer = new FileTransferService();
	}

	@Override
	public void run() {
		List<String> fileNames = new ArrayList<>();
		while (Main.isRunning) {
			try {
				fileNames = fileTransfer.getUserFiles(Main.USERID);
				if (fileNames != null) {
					if (receivedFiles.getModel().getSize() != fileNames.size()) {
						receivedFiles.removeAll();
						receivedFiles.setListData(fileNames.toArray(new String[fileNames.size()]));
					}
				}

				Thread.sleep(Long.parseLong(PROPS.getProperty("user.files.refresh.timer")));
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

}
