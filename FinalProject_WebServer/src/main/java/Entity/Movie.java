package Entity;

public class Movie {
	private String title;
	private String genre;
	private String duration;
	private String description;
	private String poster_url;
	private float review_point;
	
	public Movie(String title, String genre, String duration, String description, String poster_url, float review_point) {
		this.title = title;
		this.genre = genre;
		this.duration = duration;
		this.description = description;
		this.poster_url = poster_url;
		this.review_point = review_point;
	}
	
	public Movie(String title, String genre, String duration) {
		this.title = title;
		this.genre = genre;
		this.duration = duration;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPoster_url() {
		return poster_url;
	}

	public void setPoster_url(String poster_url) {
		this.poster_url = poster_url;
	}

	public float getReview_point() {
		return review_point;
	}

	public void setReview_point(float review_point) {
		this.review_point = review_point;
	}
	

}
