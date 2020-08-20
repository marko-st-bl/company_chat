package com.marko.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.marko.dao.UserDAO;
import com.marko.util.LoggerUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

@Path("/user")
public class UserService {
	
	private static Logger LOGGER = LoggerUtil.getLogger(UserService.class.getName());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("login/{username}&{password}")
	public Response login(@PathParam("username") String username, @PathParam("password") String password) {
		String id = UserDAO.login(username, password);
		if (id != null) {
			LOGGER.config("User: "+id+" logged in.");
			return Response.status(200).entity(id).build();
		} else {
			LOGGER.config("Wrong username password.");
			return Response.status(404).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("logout/{id}")
	public Response logout(@PathParam("id") String id) {
		if (UserDAO.logout(id)) {
			LOGGER.config("User: "+id+" logged out.");
			return Response.status(200).build();
		} else {
			LOGGER.config("Error logging out user: "+id);
			return Response.status(500).entity("Error while trying to logout").build();
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
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("changePassword/{currentPassword}&{newPassword}&{id}")
	public Response changePassword(@PathParam("currentPassword") String currentPassword,
			@PathParam("newPassword") String newPassword, @PathParam("id") String id) {
		boolean status=false;
		status=UserDAO.changePassword(currentPassword, newPassword, id);
		if(status==true) {
			LOGGER.config("User:"+id+" changed password.");
			return Response.status(200).build();
		}else if(status==false) {
			LOGGER.config("Error changing password.");
			return Response.status(401).build();
		}
		return Response.status(500).entity("Error changing password").build();
	}
	

}
