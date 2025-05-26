package Service;

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
}
