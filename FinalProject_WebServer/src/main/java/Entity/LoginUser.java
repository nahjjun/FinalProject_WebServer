package Entity;

// 로그인 전용 객체. session에 저장할 사용자 객체
public class LoginUser {
	private final String email;
	private final String name;
	private final String userClass;
	private final int userId;
	
	public LoginUser(String email, String name, String userClass, int userId) {
		this.email = email;
		this.name = name;
		this.userClass = userClass;
		this.userId = userId;
	}
	public final String getName() {
		return name;
	}
	public final String getUserClass() {
		return userClass;
	}
	public final int getUserId() {
		return userId;
	}
	public String getEmail() {
		return email;
	}
	
}
