package Entity;

import java.time.LocalDateTime;

public class Comment {
	private int commentId;
    private int postId;
    private int userId;
    private String content;
    private LocalDateTime createdAt;
    private int goodNum;
    private int badNum;
    private Integer parentCommentId; // null 가능
    private String userName; // JOIN으로 사용자 이름 조회용
    

	public Comment() {
		
	}


	public int getCommentId() {
		return commentId;
	}


	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}


	public int getPostId() {
		return postId;
	}


	public void setPostId(int postId) {
		this.postId = postId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public int getGoodNum() {
		return goodNum;
	}


	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}


	public int getBadNum() {
		return badNum;
	}


	public void setBadNum(int badNum) {
		this.badNum = badNum;
	}


	public Integer getParentCommentId() {
		return parentCommentId;
	}


	public void setParentCommentId(Integer parentCommentId) {
		this.parentCommentId = parentCommentId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
