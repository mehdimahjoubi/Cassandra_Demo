package Model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Game {

	private String gameID;
	private String gameType;
	private String gameWebsite;
	private String[] gamePlateforms;
	private long subscriptionsCount;
	
	public Game() {
		super();
	}
	public Game(String gameID, String gameType, String gameWebsite,
			String[] gamePlateforms) {
		super();
		this.gameID = gameID;
		this.gameType = gameType;
		this.gameWebsite = gameWebsite;
		this.gamePlateforms = gamePlateforms;
	}
	
	public Game(String gameID, String gameType, String gameWebsite,
			String[] gamePlateforms, long subscriptionsCount) {
		super();
		this.gameID = gameID;
		this.gameType = gameType;
		this.gameWebsite = gameWebsite;
		this.gamePlateforms = gamePlateforms;
		this.subscriptionsCount = subscriptionsCount;
	}
	public String getGameID() {
		return gameID;
	}
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public String getGameWebsite() {
		return gameWebsite;
	}
	public void setGameWebsite(String gameWebsite) {
		this.gameWebsite = gameWebsite;
	}
	public String[] getGamePlateforms() {
		return gamePlateforms;
	}
	public void setGamePlateforms(String[] gamePlateforms) {
		this.gamePlateforms = gamePlateforms;
	}
	public long getSubscriptionsCount() {
		return subscriptionsCount;
	}
	public void setSubscriptionsCount(long subscriptionsCount) {
		this.subscriptionsCount = subscriptionsCount;
	}
	
}
