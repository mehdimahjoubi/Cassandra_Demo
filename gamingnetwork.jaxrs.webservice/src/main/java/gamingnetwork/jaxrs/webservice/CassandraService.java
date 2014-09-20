package gamingnetwork.jaxrs.webservice;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import exceptions.AuthenticationException;

import CassandraDataAccess.CassandraClient;
import Model.Post;

@Path("/cassandra")
public class CassandraService {

	CassandraClient cassandraClient = new CassandraClient();

	@GET
	@Path("/subscribe_to_game")
	public String subscrbeToGame(@QueryParam("gamer_id") String gamerID,
			@QueryParam("game_id") String gameID,
			@QueryParam("password") String password) {
		try {
			boolean b = cassandraClient.subscribeToGame(gamerID, gameID,
					password);
			if (b)
				return "success";
			return "not_found";
		} catch (AuthenticationException e) {
			return "auth_error";
		} catch (Exception e) {
			return "error";
		}
	}

	@POST
	@Path("/create_account")
	public String createAccount(@FormParam("gamer_id") String gamerID,
			@FormParam("gamer_name") String gamerName,
			@FormParam("gamer_surname") String gamerSurname,
			@FormParam("gamer_location") String gamerLocation,
			@FormParam("gamer_email") String gamerEmail,
			@FormParam("gamer_password") String password) {
		try {
			boolean success = cassandraClient.createNewGamerAccount(gamerID,
					gamerName, gamerSurname, gamerLocation, gamerEmail,
					password);
			if (success)
				return "success";
			return "failure";
		} catch (Exception e) {
			return "error";
		}
	}

	@GET
	@Path("/connect")
	@Produces(MediaType.APPLICATION_JSON)
	public Object[] getTimeline(@QueryParam("gamer_id") String gamerID,
			@QueryParam("password") String password) {
		try {
			Post[] posts = cassandraClient.getGamerTimeline(gamerID, password);
			return posts;
		} catch (AuthenticationException e) {
			return new String[] { "authentication_error" };
		} catch (Exception e) {
			return new String[] { "error" };
		}
	}

	@GET
	@Path("/get_profile")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getGamerProfile(@QueryParam("gamer_id") String gamerID) {
		try {
			return cassandraClient.getProfile(gamerID);
		} catch (Exception e) {
			return "error";
		}
	}

}
