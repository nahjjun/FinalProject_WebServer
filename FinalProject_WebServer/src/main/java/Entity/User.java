package Entity;

import java.sql.Date;

public class User {
   private String email;
   private String encodedPassword;
   private String name;
   private String birth;
   private String phone;
   private Date join_date;
   private String userClass;
   private String profile_image;

   public User() {
   }

   public User(String email, String encodedPassword, String name, String birth) {
      this.email = email;
      this.encodedPassword = encodedPassword;
      this.name = name;
      this.birth = birth;
      this.phone = phone;
      this.userClass = "BASIC";
      this. profile_image = "기본프로필.png";
      this.join_date=join_date;
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

	public Date getJoinDate() {
		return join_date;
	}

	public void setJoinDate(Date join_date) {
		this.join_date = join_date;
	}

	public String getUserClass() {
		return userClass;
	}

	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}

	public String getProfileImage() {
		return profile_image;
	}

	public void setProfileImage(String profie_image) {
		this.profile_image = profie_image;
	}
}
