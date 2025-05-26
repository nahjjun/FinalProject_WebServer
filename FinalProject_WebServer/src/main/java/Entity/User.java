package Entity;

public class User {
   private String email;
   private String encodedPassword;
   private String name;
   private String birth;

   public User() {
   }

   public User(String email, String encodedPassword, String name, String birth) {
      this.email = email;
      this.encodedPassword = encodedPassword;
      this.name = name;
      this.birth = birth;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getEncodedPassword() {
      return this.encodedPassword;
   }

   public void setEncodedPassword(String encodedPassword) {
      this.encodedPassword = encodedPassword;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getBirth() {
      return this.birth;
   }

   public void setBirth(String birth) {
      this.birth = birth;
   }
}
