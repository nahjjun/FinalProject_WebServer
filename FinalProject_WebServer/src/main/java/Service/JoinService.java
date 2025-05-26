package Service;

import Dto.JoinRequestDto;
import Entity.User;
import Repository.JoinRepository;
import Util.PasswordUtil;

public class JoinService {
   private JoinRepository joinRepository = new JoinRepository();

   public boolean join(JoinRequestDto joinRequestDto) {
      System.out.println("JoinService/join() 실행됨");
      if (this.isDuplicated(joinRequestDto)) {
         return false;
      } else {
         try {
            String email = joinRequestDto.getEmail();
            String encodedPassword = PasswordUtil.encode(joinRequestDto.getPassword());
            String name = joinRequestDto.getName();
            String birth = joinRequestDto.getBirth();
            User user = new User(email, encodedPassword, name, birth);
            if (this.joinRepository.save(user)) {
               System.out.println("JoinService/join() 성공");
            }
         } catch (Exception var7) {
            var7.printStackTrace();
         }

         return true;
      }
   }

   private boolean isDuplicated(JoinRequestDto joinRequestDto) {
      System.out.println("JoinService/isDuplicated() 실행됨");
      return this.joinRepository.emailExist(joinRequestDto.getEmail());
   }
}
