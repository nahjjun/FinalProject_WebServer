package Entity;

// 로그인 전용 객체. session에 저장할 사용자 객체
public class LoginUser {
	private final String email;
	public LoginUser(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	
}
