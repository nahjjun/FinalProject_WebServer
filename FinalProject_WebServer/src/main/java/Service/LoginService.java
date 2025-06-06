package Service;

import java.util.Map;

import Dto.LoginRequestDto;
import Repository.LoginRepository;
import Util.PasswordUtil;

public class LoginService {
   private LoginRepository loginRepository = new LoginRepository();

   public boolean login(LoginRequestDto loginRequestDto) {
      try {
         String encodedPassword = PasswordUtil.encode(loginRequestDto.getPassword());
         LoginRequestDto lrd = new LoginRequestDto(loginRequestDto.getEmail(), encodedPassword);
         if (!this.loginRepository.confirm(lrd)) {
            System.out.println("LoginService/login() 실패");
            return false;
         } else {
            return true;
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         return false;
      }
   }
   
// 로그인 되어있는 경우, 영화 상세페이지에서 review를 작성할 때 사용하기 위한 User 정보를 가져오는 함수
	public Map<String, Object> getUserInfo(String email){
		return loginRepository.getUserInfo(email);
	}
}
