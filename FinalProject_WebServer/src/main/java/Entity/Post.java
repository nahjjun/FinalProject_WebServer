package Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private int postId;
    private int userId;
    private Integer movieId; // 자유게시판일 경우 null
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private boolean isWatched;
    private int viewCount;
    private String boardType = "free"; // 'free' or 'movie'
    private LocalDateTime updatedAt;
    private String imagePath; // DB에는 없지만 영화게시판에서만 쓸 예정이라 포함시켜 둠
    private String userName;

    
    public Post() 
	{
    	this.boardType = "free";
	}

    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdAt.format(formatter);
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isWatched() { return isWatched; }
    public void setWatched(boolean isWatched) { this.isWatched = isWatched; }

    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public String getBoardType() { return boardType; }
    public void setBoardType(String boardType) { this.boardType = boardType; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
