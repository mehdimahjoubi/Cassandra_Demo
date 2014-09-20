package Model;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {

	private UUID postID;
	private UUID commentID;
	private String commentAuthorGamerID;
	private String commentBody;
	private Date commentLatestChange;

	public Comment() {
		super();
	}

	public Comment(UUID postID, UUID commentID, String commentAuthorGamerID,
			String commentBody, Date commentLatestChange) {
		super();
		this.postID = postID;
		this.commentID = commentID;
		this.commentAuthorGamerID = commentAuthorGamerID;
		this.commentBody = commentBody;
		this.commentLatestChange = commentLatestChange;
	}

	public UUID getPostID() {
		return postID;
	}

	public void setPostID(UUID postID) {
		this.postID = postID;
	}

	public UUID getCommentID() {
		return commentID;
	}

	public void setCommentID(UUID commentID) {
		this.commentID = commentID;
	}

	public String getCommentAuthorGamerID() {
		return commentAuthorGamerID;
	}

	public void setCommentAuthorGamerID(String commentAuthorGamerID) {
		this.commentAuthorGamerID = commentAuthorGamerID;
	}

	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}

	public Date getCommentLatestChange() {
		return commentLatestChange;
	}

	public void setCommentLatestChange(Date commentLatestChange) {
		this.commentLatestChange = commentLatestChange;
	}

}
