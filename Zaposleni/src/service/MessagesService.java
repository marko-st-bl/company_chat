package service;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import model.Message;
import view.Main;

public class MessagesService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessagesService.class.getName());
	
	private JTextPane textPane;
	private JTextField txtMessage;
	
	public MessagesService(JTextPane textPane, JTextField txtMessage) {
		super();
		this.textPane = textPane;
		this.txtMessage = txtMessage;
	}
	
	public void updateMessageList(ArrayList<Message> messages) {
		textPane.setText("");
		
		StyledDocument doc = textPane.getStyledDocument();
		
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		
		for(Message msg: messages) {
			try {
				if(msg.getSender().equals(Main.USERID)) {
					
					int length = doc.getLength();
				    doc.insertString(doc.getLength(), "\n"+msg.getContent(), null);
				    doc.setParagraphAttributes(length+1, 1, right, false);
				}else {
					
					int length = doc.getLength();
				    doc.insertString(doc.getLength(), "\n"+msg.getContent(), null);
				    doc.setParagraphAttributes(length+1, 1, left, false);
				}
			}catch(Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(),e);
			}
		}
	}

}
