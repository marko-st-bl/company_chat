package util;

import java.util.ArrayList;
import java.util.Properties;

import model.Conversation;

public class ConversationFactory {
	
	private static final Properties PROPS=PropertiesUtil.loadProperties();
	
	private static ArrayList<Conversation> conversations=new ArrayList<>();
	
	private ConversationFactory() {
		super();
		conversations.add(new Conversation(PROPS.getProperty("group.chat.id")));
	}
	public static Conversation getConversation(String id){
		for(Conversation convo:conversations) {
			if(convo.getId().equals(id)) {
				return convo;
			}
		}
		Conversation convo=new Conversation(id);
		conversations.add(convo);
		return convo;
	}

}
