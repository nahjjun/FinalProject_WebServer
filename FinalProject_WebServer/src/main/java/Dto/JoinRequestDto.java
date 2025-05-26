package Dto;

public class JoinRequestDto {
   private final String email;
   private final String password;
   private final String name;
   private final String birth;

   public JoinRequestDto(String email, String password, String name, String birth) {
      this.email = email;
      this.password = password;
      this.name = name;
      this.birth = birth;
   }

   public String getEmail() {
      return this.email;
   }

   public String getPassword() {
      return this.password;
   }

   public String getName() {
      return this.name;
   }

   public String getBirth() {
      return this.birth;
   }
}
