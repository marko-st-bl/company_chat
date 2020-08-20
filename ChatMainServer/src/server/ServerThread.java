package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Conversation;
import model.Message;
import model.User;
import service.RouterSender;
import util.ConversationFactory;
import util.LoggerUtil;

public class ServerThread extends Thread {

	private static final Logger LOGGER = LoggerUtil.getLogger(ServerThread.class.getName());

	private User user;
	private Socket sock;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public ServerThread(Socket s) {
		this.sock = s;
		try {
			in = new ObjectInputStream(sock.getInputStream());
			out = new ObjectOutputStream(sock.getOutputStream());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void run() {
		String request = "";
		try {
			while (!Protocol.END.equals(request=(String) in.readObject())) {
	
				if (request != null) {
					request = request.trim();

					if (request.startsWith(Protocol.GET_MESSAGES)) {
						String[] params = request.split(Protocol.SEPARATOR);
						String conversationID = Conversation.calculateId(params[1], params[2]);
						ArrayList<Message> messages = ConversationFactory.getConversation(conversationID).getMessages();
						out.writeUnshared(messages);
					}
					if (request.startsWith(Protocol.LOGIN)) {
						String params[] = request.split(Protocol.SEPARATOR);
						boolean status = false;
						if (params.length == 4) {
							status = login(params[1], params[2], params[3]);
						}
						if (!status) {
							out.writeObject(new String(Protocol.INVALID_LOGIN));
						} else {
							out.writeObject(new String(Protocol.OK));
						}
					}
					if (request.startsWith(Protocol.SEND)) {
						String[] params = request.split(Protocol.SEPARATOR);
						Message message = new Message(params[3], params[1]);
						RouterSender.sendMessage(params[2], message);
						out.writeObject(new String(Protocol.OK));
					}
					if (request.startsWith(Protocol.MONITOR)) {
						String[] params = request.split(Protocol.SEPARATOR);
						if (!ChatServer.streams.containsKey(user.getId())) {
							ChatServer.streams.put(user.getId(), new ScreenReceiver());
							ChatServer.streamPairs.put(user.getId(), params[1]);
							for (User client : ChatServer.onlineUsers) {
								if (params[1].equals(client.getId())) {
									client.getOutputStream().writeObject(Protocol.START+Protocol.SEPARATOR+user.getId());
								}
							}
						}
					}
					if (request.startsWith(Protocol.STREAMING)) {
						String[] params=request.split(Protocol.SEPARATOR);
						for(User admin:ChatServer.onlineUsers) {
							if(admin.getId().equals(params[1])) {
								ScreenReceiver sr=ChatServer.streams.get(params[1]);
								sr.setClientAddress(sock.getInetAddress());
								sr.start();
								admin.getOutputStream().writeObject(Protocol.STREAMING);
							}
						}
					}
					if(request.startsWith(Protocol.STOP)) {
						String userId=ChatServer.streamPairs.get(user.getId());
						for(User client:ChatServer.onlineUsers) {
							if(client.getId().equals(userId)) {
								client.getOutputStream().writeObject(Protocol.STOP);
								ChatServer.streams.get(user.getId()).stopStreaming();
								ChatServer.streams.remove(user.getId());
								ChatServer.streamPairs.remove(user.getId());
							}
						}
					}
					if (request.startsWith(Protocol.END)) {
						if(ChatServer.streamPairs.containsValue(user.getId())) {
							for(String key:ChatServer.streamPairs.keySet()) {
								if((ChatServer.streamPairs.get(key)).equals(user.getId())) {
									ChatServer.streamPairs.remove(key);
									ChatServer.streams.get(key).stopStreaming();
									ChatServer.streams.remove(key);
									for(User admin: ChatServer.onlineUsers) {
										if(admin.getId().equals(key)) {
											admin.getOutputStream().writeObject(Protocol.STOP);
										}
									}
								}
							}
						}
					}
				}
			}
			if (user != null) {
				ChatServer.onlineUsers.remove(user);
			}
			in.close();
			out.close();
			sock.close();
			LOGGER.config("User disconected.");
		} catch (SocketException e) {
			LOGGER.config("User disconnected.");
		} catch(EOFException e) {
			LOGGER.config("User disconnected.");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			logout();
		}

	}

	private void logout() {
		ChatServer.onlineUsers.remove(user);
		LOGGER.config(user + " logged out");
	}

	private boolean login(String id, String firstName, String lastName) {
		User newUser = new User(id, firstName, lastName, out);
		if (!ChatServer.onlineUsers.contains(newUser)) {
			ChatServer.onlineUsers.add(newUser);
			this.user = newUser;
			LOGGER.config(newUser + " logged in");
			return true;
		}
		LOGGER.config(newUser + " couldnt log in");
		return false;
	}

}
