package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.json.JSONArray;


public class RESTService {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(RESTService.class.getName());
	private static final Properties PROPS=PropertiesUtil.loadProperties();

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static String login(String username, String password) {
		InputStream is;
		String jsonText="";
		try {
			is = new URL(PROPS.getProperty("rest.service.base.url") +"login/"+ username+"&"+password).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			jsonText = readAll(rd);
			System.out.println(jsonText);
			is.close();
		}catch(FileNotFoundException e) {
			LOGGER.config("Wrong username/password.");
			return null;
		} catch (MalformedURLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return jsonText;
	}
	
	public static JSONArray getAllUsers(){
		JSONArray json=null;
		try {
			InputStream is = new URL(PROPS.getProperty("rest.service.base.url")).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			json = new JSONArray(jsonText);
			is.close();
			rd.close();
		}catch(IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return json;
	}
	
	public static JSONArray getUserSessions(String id) {
		JSONArray json=null;
		try {
			InputStream is = new URL(PROPS.getProperty("rest.service.base.url")+"activity/"+id).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			json = new JSONArray(jsonText);
			is.close();
			rd.close();
		}catch(IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return json;
	}

	public static void logout(String id) {
		try {
			URL url = new URL(PROPS.getProperty("rest.service.base.url") + "logout/"+id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("DELETE");
			OutputStream os = conn.getOutputStream();
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			os.close();
			conn.disconnect();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public static boolean changePassword(String currentPassword, String newPassword,String id) {
		try {
			URL url = new URL(PROPS.getProperty("rest.service.base.url") + 
					"changePassword/"+currentPassword+"&"+newPassword+"&"+id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			OutputStream os = conn.getOutputStream();
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}else if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
				return true;
			}
			os.close();
			conn.disconnect();
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(),e);
		}
		return false;
	}
}
