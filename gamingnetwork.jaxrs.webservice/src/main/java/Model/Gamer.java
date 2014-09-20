package Model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Gamer {

	private String gamerID;
	private String gamerName;
	private String gamerSurname;
	private String gamerLocation;
	private String gamerEmail;
	private long subscriptionsCount;
	
	public Gamer() {
		super();
	}

	public Gamer(String gamerID, String gamerName, String gamerSurname,
			String gamerLocation, String gamerEmail) {
		super();
		this.gamerID = gamerID;
		this.gamerName = gamerName;
		this.gamerSurname = gamerSurname;
		this.gamerLocation = gamerLocation;
		this.gamerEmail = gamerEmail;
	}
	
	public Gamer(String gamerID, String gamerName, String gamerSurname,
			String gamerLocation, String gamerEmail, long subscriptionsCount) {
		super();
		this.gamerID = gamerID;
		this.gamerName = gamerName;
		this.gamerSurname = gamerSurname;
		this.gamerLocation = gamerLocation;
		this.gamerEmail = gamerEmail;
		this.subscriptionsCount = subscriptionsCount;
	}

	public String getGamerID() {
		return gamerID;
	}
	public void setGamerID(String gamerID) {
		this.gamerID = gamerID;
	}
	public String getGamerName() {
		return gamerName;
	}
	public void setGamerName(String gamerName) {
		this.gamerName = gamerName;
	}
	public String getGamerSurname() {
		return gamerSurname;
	}
	public void setGamerSurname(String gamerSurname) {
		this.gamerSurname = gamerSurname;
	}
	public String getGamerLocation() {
		return gamerLocation;
	}
	public void setGamerLocation(String gamerLocation) {
		this.gamerLocation = gamerLocation;
	}
	public String getGamerEmail() {
		return gamerEmail;
	}
	public void setGamerEmail(String gamerEmail) {
		this.gamerEmail = gamerEmail;
	}

	public long getSubscriptionsCount() {
		return subscriptionsCount;
	}

	public void setSubscriptionsCount(long subscriptionsCount) {
		this.subscriptionsCount = subscriptionsCount;
	}
	
}
