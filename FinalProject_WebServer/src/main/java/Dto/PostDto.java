package Dto;

import java.time.LocalDateTime;

public class PostDto {

	    private int displayNumber;     // 게시판 내 번호
	    private int postId;
	    private String title;
	    private String content;
	    private String userName;
	    private LocalDateTime createdAt;
	    private int viewCount;


	    public int getDisplayNumber() { return displayNumber; }
	    public void setDisplayNumber(int displayNumber) { this.displayNumber = displayNumber; }

	    public int getPostId() { return postId; }
	    public void setPostId(int postId) { this.postId = postId; }

	    public String getTitle() { return title; }
	    public void setTitle(String title) { this.title = title; }

	    public String getContent() { return content; }
	    public void setContent(String content) { this.content = content; }

	    public String getUserName() { return userName; }
	    public void setUserName(String userName) { this.userName = userName; }

	    public LocalDateTime getCreatedAt() { return createdAt; }
	    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

	    public int getViewCount() { return viewCount; }
	    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
}
