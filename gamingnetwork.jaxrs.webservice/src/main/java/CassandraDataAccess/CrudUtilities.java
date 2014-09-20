package CassandraDataAccess;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import Model.Comment;
import Model.Game;
import Model.Gamer;
import Model.Post;

public class CrudUtilities {

	public Gamer selectGamer(String gamerID) {
		Gamer g = null;
		ResultSet gamerRS = ConnectionFactory.getSession().execute(
				"SELECT * FROM gaming_network.gamers WHERE gamer_id='"
						+ gamerID + "';");
		if (!gamerRS.isExhausted()) {
			Row gamerRow = gamerRS.iterator().next();
			String gamer_name = gamerRow.getString("gamer_name");
			String gamer_surname = gamerRow.getString("gamer_surname");
			String gamer_location = gamerRow.getString("gamer_location");
			String gamer_email = gamerRow.getString("gamer_email");
			g = new Gamer(gamerID, gamer_name, gamer_surname, gamer_location,
					gamer_email);
		}
		return g;
	}

	public void insertGamer(Gamer gamer) {
		ConnectionFactory
				.getSession()
				.execute(
						"INSERT INTO gaming_network.gamers(gamer_id, gamer_name, gamer_surname, gamer_location, gamer_email) VALUES ('"
								+ gamer.getGamerID()
								+ "', '"
								+ gamer.getGamerName()
								+ "', '"
								+ gamer.getGamerSurname()
								+ "', '"
								+ gamer.getGamerLocation()
								+ "', '"
								+ gamer.getGamerEmail() + "');");
	}

	public Game selectGame(String gameID) {
		Game g = null;
		ResultSet rs = ConnectionFactory.getSession().execute(
				"SELECT * FROM gaming_network.games WHERE game_id='" + gameID
						+ "';");
		if (!rs.isExhausted()) {
			Row gameRow = rs.iterator().next();
			String gameType = gameRow.getString("game_type");
			String gameWebsite = gameRow.getString("game_website");
			Set<String> plateforms = gameRow.getSet("game_plateforms",
					String.class);
			String[] gamePlateforms = plateforms.toArray(new String[plateforms
					.size()]);
			g = new Game(gameID, gameType, gameWebsite, gamePlateforms);
		}
		return g;
	}

	public void insertGame(Game game) {
		String gamePlateforms = parseStringsArray(game.getGamePlateforms());
		ConnectionFactory
				.getSession()
				.execute(
						"INSERT INTO gaming_network.games (game_id, game_type, game_website, game_plateforms) VALUES ('"
								+ game.getGameID()
								+ "','"
								+ game.getGameType()
								+ "','"
								+ game.getGameWebsite()
								+ "', "
								+ gamePlateforms + ");");
	}

	// Parses a string array to a JSON-like string suitable for Cassandra
	// set<text> columns
	private String parseStringsArray(String[] strings) {
		String gamePlateforms = null;
		if (strings.length > 0) {
			gamePlateforms = "{'" + strings[0] + "'";
			for (int i = 1; i < strings.length; i++) {
				gamePlateforms += ", '" + strings[i] + "'";
			}
			gamePlateforms += "}";
		}
		return gamePlateforms;
	}

	public void insertGamerGame(String gamerID, Game game) {
		String gamePlateforms = parseStringsArray(game.getGamePlateforms());
		ConnectionFactory
				.getSession()
				.execute(
						"INSERT INTO gaming_network.gamers_games (gamer_id, game_id, game_type, game_website, game_plateforms) VALUES ('"
								+ gamerID
								+ "', '"
								+ game.getGameID()
								+ "', '"
								+ game.getGameType()
								+ "', '"
								+ game.getGameWebsite()
								+ "', "
								+ gamePlateforms + ");");
	}

	public void insertGameGamer(String game_id, Gamer gamer) {
		ConnectionFactory
				.getSession()
				.execute(
						"INSERT INTO gaming_network.games_gamers (game_id, gamer_id, gamer_name, gamer_surname, gamer_location, gamer_email) VALUES ('"
								+ game_id
								+ "', '"
								+ gamer.getGamerID()
								+ "', '"
								+ gamer.getGamerName()
								+ "', '"
								+ gamer.getGamerSurname()
								+ "', '"
								+ gamer.getGamerLocation()
								+ "', '"
								+ gamer.getGamerEmail() + "');");
	}

	public void incrementGamerSubscriptions(String gamerID, int addedValue) {
		String s = "";
		if (addedValue < 0)
			s += "- " + addedValue;
		else
			s += "+ " + addedValue;
		ConnectionFactory
				.getSession()
				.execute(
						"UPDATE gaming_network.gamers_subscription_counts SET gamer_subscription_count = gamer_subscription_count "
								+ s + "  WHERE gamer_id='" + gamerID + "';");
	}

	public void incrementGameSubscriptions(String gameID, int addedValue) {
		String s = "";
		if (addedValue < 0)
			s += "- " + addedValue;
		else
			s += "+ " + addedValue;
		ConnectionFactory
				.getSession()
				.execute(
						"UPDATE gaming_network.games_subscription_counts SET game_subscription_count = game_subscription_count "
								+ s + "  WHERE game_id='" + gameID + "';");
	}

	public Post[] getGamePosts(String gameID) {
		ResultSet rs = ConnectionFactory.getSession().execute(
				"SELECT * FROM gaming_network.posts WHERE post_game_id='"
						+ gameID + "';");
		if (!rs.isExhausted()) {
			List<Row> rows = rs.all();
			Post[] gamePosts = new Post[rows.size()];
			for (int i = 0; i < rows.size(); i++) {
				Row r = rows.get(i);
				gamePosts[i] = new Post(r.getUUID("post_id"),
						r.getString("post_body"),
						r.getString("author_gamer_id"), gameID,
						r.getDate("latest_change"));
			}
			return gamePosts;
		}
		return null;
	}

	public void insertTimeline(String gamerID, Post post) {
		ConnectionFactory
				.getSession()
				.execute(
						"INSERT INTO gaming_network.timeline (gamer_id, post_id, post_game_id, author_gamer_id, post_body, latest_change) VALUES ('"
								+ gamerID
								+ "', "
								+ post.getPostID()
								+ ", '"
								+ post.getPostGameID()
								+ "', '"
								+ post.getAuthorGamerID()
								+ "', '"
								+ post.getPostBody()
								+ "', '"
								+ new Timestamp(post.getLatestChange()
										.getTime()) + "');");
	}

	private BoundStatement prepareTimelineBoundStatement() {
		PreparedStatement statement = ConnectionFactory
				.getSession()
				.prepare(
						"INSERT INTO gaming_network.timeline "
								+ "(gamer_id, post_id, post_game_id, author_gamer_id, post_body, latest_change) "
								+ "VALUES (?, ?, ?, ?, ?, ?);");
		BoundStatement boundStatement = new BoundStatement(statement);
		return boundStatement;
	}

	public void insertTimelines(String gamerID, Post[] posts) {
		BoundStatement boundStatement = prepareTimelineBoundStatement();
		for (int i = 0; i < posts.length; i++) {
			Post p = posts[i];
			ConnectionFactory.getSession().execute(
					boundStatement.bind(gamerID, p.getPostID(),
							p.getPostGameID(), p.getAuthorGamerID(),
							p.getPostBody(), p.getLatestChange()));
		}
	}

	public void insertTimelines(String[] gamerIDs, Post p) {
		BoundStatement boundStatement = prepareTimelineBoundStatement();
		for (int i = 0; i < gamerIDs.length; i++) {
			String gamerID = gamerIDs[i];
			ConnectionFactory.getSession().execute(
					boundStatement.bind(gamerID, p.getPostID(),
							p.getPostGameID(), p.getAuthorGamerID(),
							p.getPostBody(), p.getLatestChange()));
		}
	}

	public void insertPost(Post post) {
		ConnectionFactory
				.getSession()
				.execute(
						"INSERT into gaming_network.posts (post_id, post_body, author_gamer_id, post_game_id, latest_change) VALUES ("
								+ post.getPostID()
								+ ", '"
								+ post.getPostBody()
								+ "', '"
								+ post.getAuthorGamerID()
								+ "', '"
								+ post.getPostGameID()
								+ "', '"
								+ new Timestamp(post.getLatestChange()
										.getTime()) + "');");
	}

	public Gamer[] getGameGamers(String gameID) {
		ResultSet rs = ConnectionFactory.getSession().execute(
				"SELECT * FROM gaming_network.games_gamers WHERE game_id='"
						+ gameID + "';");
		if (rs.isExhausted())
			return null;
		List<Row> rows = rs.all();
		Gamer[] gamers = new Gamer[rows.size()];
		for (int i = 0; i < rows.size(); i++) {
			Row r = rows.get(i);
			gamers[i] = new Gamer(r.getString("gamer_id"),
					r.getString("gamer_name"), r.getString("gamer_surname"),
					r.getString("gamer_location"), r.getString("gamer_email"));
		}
		return gamers;
	}

	public Post[] getGamerTimeline(String gamerID) {
		Post[] posts = null;
		ResultSet rs = ConnectionFactory.getSession().execute(
				"SELECT * FROM gaming_network.timeline WHERE gamer_id='"
						+ gamerID + "';");
		if (!rs.isExhausted()) {
			List<Row> rows = rs.all();
			posts = new Post[rows.size()];
			for (int i = 0; i < rows.size(); i++) {
				Row r = rows.get(i);
				posts[i] = new Post(r.getUUID("post_id"),
						r.getString("post_body"),
						r.getString("author_gamer_id"),
						r.getString("post_game_id"), r.getDate("latest_change"));
			}
		}
		return posts;
	}

	public void insertAuthentification(String gamerID, String password) {
		ConnectionFactory.getSession().execute(
				"INSERT INTO gaming_network.authentifications (gamer_id, password) VALUES ('"
						+ gamerID + "', '" + password + "');");
	}

	public boolean authentificate(String gamerID, String password) {
		ResultSet rs = ConnectionFactory.getSession().execute(
				"SELECT * FROM gaming_network.authentifications WHERE gamer_id='"
						+ gamerID + "' AND password='" + password + "';");
		return (!rs.isExhausted());
	}

	public void insertComment(Comment comment) {
		ConnectionFactory
				.getSession()
				.execute(
						"INSERT into gaming_network.comments (post_id, comment_id, comment_author_gamer_id, comment_body, comment_latest_change) VALUES ("
								+ comment.getPostID()
								+ ", "
								+ comment.getCommentID()
								+ ", '"
								+ comment.getCommentAuthorGamerID()
								+ "', '"
								+ comment.getCommentBody()
								+ "', '"
								+ new Timestamp(comment
										.getCommentLatestChange().getTime())
								+ "');");
	}

	public long getGamerSubscriptionCount(String gamerID) {
		long i = 0;
		ResultSet rs = ConnectionFactory
				.getSession()
				.execute(
						"SELECT * FROM gaming_network.gamers_subscription_counts WHERE gamer_id='"
								+ gamerID + "';");
		if(!rs.isExhausted()) {
			i = rs.iterator().next().getLong("gamer_subscription_count");
		}
		return i;
	}
}
