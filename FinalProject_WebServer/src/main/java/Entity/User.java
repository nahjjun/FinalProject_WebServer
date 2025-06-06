package Entity;

import java.sql.Date;
import java.time.LocalDate;

public class User {
   private String email;
   private String encodedPassword;
   private String name;
   private String birth;
   private Date joinDate;
   private String phone; 
   private String userClass; 
   private String profileImage; // 기본 이미지 설정

   public User() {
	   this.joinDate = Date.valueOf(LocalDate.now()); 
   }

   public User(String email, String encodedPassword, String name, String birth) {
      this.email = email;
      this.encodedPassword = encodedPassword;
      this.name = name;
      this.birth = birth;
      this.joinDate = Date.valueOf(LocalDate.now());
      this.userClass = userClass;
      this.profileImage = "기본프로필.png";
   }

	public String getProfileImage() {
	    return profileImage;
	}
	
	public void setProfileImage(String profileImage) {
	    this.profileImage = profileImage;
	}

   
   public Date getJoinDate() {
	   return joinDate;
	}

   public void setJoinDate(Date joinDate) {
	   this.joinDate = joinDate;
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
   
   public String getPhone() { 
       return phone;
   }
   public void setPhone(String phone) { 
       this.phone = phone;
   }
   
   public String getUserClass() {
	      return userClass;
	   }

	   public void setUserClass(String userClass) { 
	      this.userClass = userClass;
	   }
}
