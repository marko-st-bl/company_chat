package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import view.Main;

public class ActivityUtil {

	public static String[] getActivity(String id) {
		JSONArray sessions = RESTService.getUserSessions(Main.USERID);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String[] tableData;
		ArrayList<String> list=new ArrayList<>();

		for (int i = 0; i < sessions.length(); i++) {
			JSONObject obj = sessions.getJSONObject(i);
				if(obj.has("loginTime")) {
				list.add(Integer.toString(i));
				list.add(sdf.format(new Date(obj.getLong("loginTime"))));
				list.add(sdf.format(new Date(obj.getLong("logoutTime"))));
				list.add(IntervalInMilisToString(obj.getLong("logoutTime")-obj.getLong("loginTime")));
				
				
			}
		}
		tableData=list.toArray(new String[list.size()]);
		return tableData;
	}
	
	public static String IntervalInMilisToString(Long durationInMillis) {
		long second = (durationInMillis / 1000) % 60;
		long minute = (durationInMillis / (1000 * 60)) % 60;
		long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

}
