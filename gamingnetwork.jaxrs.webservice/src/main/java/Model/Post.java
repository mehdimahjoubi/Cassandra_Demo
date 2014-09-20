package Model;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Post {

	private UUID postID;
	private String postBody;
	private String authorGamerID;
	private String postGameID;
	private Date latestChange;

	public Post() {
		super();
	}

	public Post(UUID postID, String postBody, String authorGamerID,
			String postGameID, Date latestChange) {
		super();
		this.postID = postID;
		this.postBody = postBody;
		this.authorGamerID = authorGamerID;
		this.postGameID = postGameID;
		this.latestChange = latestChange;
	}

	public UUID getPostID() {
		return postID;
	}

	public void setPostID(UUID postID) {
		this.postID = postID;
	}

	public String getPostBody() {
		return postBody;
	}

	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}

	public String getAuthorGamerID() {
		return authorGamerID;
	}

	public void setAuthorGamerID(String authorGamerID) {
		this.authorGamerID = authorGamerID;
	}

	public String getPostGameID() {
		return postGameID;
	}

	public void setPostGameID(String postGameID) {
		this.postGameID = postGameID;
	}

	public Date getLatestChange() {
		return latestChange;
	}

	public void setLatestChange(Date latestChange) {
		this.latestChange = latestChange;
	}

}
