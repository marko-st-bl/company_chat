package com.marko.rest;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.marko.dao.UserDAO;
import com.marko.util.LoggerUtil;

@Path("/admin")
public class AdministratorService {
	
	private static Logger LOGGER=LoggerUtil.getLogger(AdministratorService.class.getName());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("login/{username}&{password}")
	public Response login(@PathParam("username") String username, @PathParam("password") String password) {
		String id = UserDAO.loginAdmin(username, password);
		if (id != null) {
			LOGGER.config("Administrator:"+id+" logged in");
			return Response.status(200).entity(id).build();
		} else {
			LOGGER.config("Cant log in administrator.");
			return Response.status(404).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getOnlineUsers/{id}")
	public Response getOnlineUsers(@PathParam("id") String id) {
		ArrayList<Map<String, String>> users = UserDAO.getOnlineUsers(id);
		if (users != null) {
			//LOGGER.config("Returning list of online users.");
			return Response.status(200).entity(users).build();
		} else {
			LOGGER.config("Error rettreiving list of online users.");
			return Response.status(404).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("activity/{id}")
	public Response getUserActivity(@PathParam("id") String id) {
		ArrayList<Map<String, String>> sessions = new ArrayList<>();
		sessions=UserDAO.getUserActivity(id);
		if(sessions!=null) {
			LOGGER.config("Returning list of user: "+id+" sessions.");
			return Response.status(200).entity(sessions).build();
		}else {
			LOGGER.config("Error retreving user activity.");
			return Response.status(500).entity("Error retreving user activity.").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		ArrayList<Map<String, String>> allUsers = new ArrayList<>();
		allUsers = UserDAO.getAll();
		if (allUsers != null) {
			//LOGGER.config("Returning list of all users");
			return Response.status(200).entity(allUsers).build();
		} else {
			LOGGER.config("Error retreving list of users.");
			return Response.status(500).entity("Error retreving list of users.").build();
		}
	}

}
