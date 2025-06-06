package Dto;

public class ReviewRegisterDto {
	private final int user_id;
	private final int movie_id;
	private final String name;
	private final String email;
	private final String user_class;
	private final String context;
	private final int rating;

	public ReviewRegisterDto(int user_id, int movie_id, String name, String email, String user_class, String context,
			int rating) {
		this.user_id = user_id;
		this.movie_id = movie_id;
		this.name = name;
		this.email = email;
		this.user_class = user_class;
		this.context = context;
		this.rating = rating;
	}
	public ReviewRegisterDto(ReviewRegisterDto dto) {
		this.user_id = dto.getUser_id();
		this.movie_id = dto.getMovie_id();
		this.name = dto.getName();
		this.email = dto.getEmail();
		this.user_class = dto.getUser_class();
		this.context = dto.getContext();
		this.rating = dto.getRating();
	}

	public final int getUser_id() {
		return user_id;
	}

	public final int getMovie_id() {
		return movie_id;
	}

	public final String getName() {
		return name;
	}

	public final String getEmail() {
		return email;
	}

	public final String getUser_class() {
		return user_class;
	}

	public final String getContext() {
		return context;
	}

	public final int getRating() {
		return rating;
	}

}
