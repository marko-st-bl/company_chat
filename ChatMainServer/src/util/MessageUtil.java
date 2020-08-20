package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Message;

public class MessageUtil {

	private static final Logger LOGGER = LoggerUtil.getLogger(MessageUtil.class.getName());

	public static byte[] toByteArray(Message message) {
		byte[] messageInBytes;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ObjectOutput out = new ObjectOutputStream(baos)) {
			out.writeObject(message);
			out.flush();
			messageInBytes = baos.toByteArray();
			return messageInBytes;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	public static Message getMessageFromBytes(byte[] data) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(bais);
			return (Message) is.readObject();
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

}
