package CassandraDataAccess;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.utils.UUIDs;

import exceptions.AuthenticationException;

import Model.Comment;
import Model.Game;
import Model.Gamer;
import Model.Post;

public class CassandraClient {

	CrudUtilities crud = new CrudUtilities();

	public boolean createNewGamerAccount(String gamerID, String gamerName,
			String gamerSurname, String gamerLocation, String gamerEmail,
			String password) {
		Gamer gamer = crud.selectGamer(gamerID);
		if (gamer != null)
			return false;
		crud.insertAuthentification(gamerID, password);
		crud.insertGamer(new Gamer(gamerID, gamerName, gamerSurname,
				gamerLocation, gamerEmail));
		return true;
	}

	public boolean addNewGame(String gamerID, String gameID, String gameType,
			String gameWebsite, String[] plateforms, String password) {
		if (!crud.authentificate(gamerID, password))
			throw new AuthenticationException();
		Game game = crud.selectGame(gameID);
		if (game != null)
			return false;
		Gamer gamer = crud.selectGamer(gamerID);
		if (gamer == null)
			return false;
		game = new Game(gameID, gameType, gameWebsite, plateforms);
		crud.insertGame(game);
		crud.insertGameGamer(gameID, gamer);
		crud.insertGamerGame(gamerID, game);
		crud.incrementGameSubscriptions(gameID, 1);
		crud.incrementGamerSubscriptions(gamerID, 1);
		return true;
	}

	public boolean subscribeToGame(String gamerID, String gameID,
			String password) {
		if (!crud.authentificate(gamerID, password))
			throw new AuthenticationException();
		Gamer gamer = crud.selectGamer(gamerID);
		if (gamer == null)
			return false;
		Game game = crud.selectGame(gameID);
		if (game == null)
			return false;
		crud.insertGameGamer(gameID, gamer);
		crud.insertGamerGame(gamerID, game);
		crud.incrementGamerSubscriptions(gamerID, 1);
		crud.incrementGameSubscriptions(gameID, 1);
		Post[] gamePosts = crud.getGamePosts(gameID);
		crud.insertTimelines(gamerID, gamePosts);
		return true;
	}

	public void publishPost(String gamerID, String gameID, String postBody,
			String password) {
		if (!crud.authentificate(gamerID, password))
			throw new AuthenticationException();
		Post post = new Post(UUIDs.timeBased(), postBody, gamerID, gameID,
				new Date());
		crud.insertPost(post);
		Gamer[] gamers = crud.getGameGamers(gameID);
		String[] gamerIDs = new String[gamers.length];
		for (int i = 0; i < gamers.length; i++)
			gamerIDs[i] = gamers[i].getGamerID();
		crud.insertTimelines(gamerIDs, post);
	}
	
	public Post[] getGamerTimeline(String gamerID, String password) {
		if (!crud.authentificate(gamerID, password))
			throw new AuthenticationException();
		return crud.getGamerTimeline(gamerID);
	}
	
	public void publishComment(String gamerID, String password, UUID postID, String commentBody) {
		if (!crud.authentificate(gamerID, password))
			throw new AuthenticationException();
		Comment comment = new Comment(postID, UUIDs.timeBased(), gamerID, commentBody, new Date());
		crud.insertComment(comment);
	}
	
	public Gamer getProfile(String gamerID) {
		Gamer g = crud.selectGamer(gamerID);
		g.setSubscriptionsCount(crud.getGamerSubscriptionCount(gamerID));
		return g;
	}

}
