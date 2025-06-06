package Repository;

import Dto.LoginRequestDto;
import Util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Entity.User;

public class LoginRepository {
   public boolean confirm(LoginRequestDto loginRequestDto) {
      String sql = "SELECT * FROM `User` WHERE email=? AND password=?";
      boolean result = false;

      try {
         Connection con = DBUtil.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql);
         pstmt.setString(1, loginRequestDto.getEmail());
         pstmt.setString(2, loginRequestDto.getPassword());
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
            result = true;
         }

         rs.close();
         pstmt.close();
         con.close();
         return result;
      } catch (SQLException var7) {
         var7.printStackTrace();
         return false;
      }
   }
   
   public User findByEmail(String email) {
	   String sql = "SELECT * FROM `User` WHERE email=?";
	   try {
	      Connection con = DBUtil.getConnection();
	      PreparedStatement pstmt = con.prepareStatement(sql);
	      pstmt.setString(1, email);
	      ResultSet rs = pstmt.executeQuery();

	      if (rs.next()) {
	    	  User user = new User();
	    	  user.setEmail(rs.getString("email"));
	         user.setEncodedPassword(rs.getString("password"));
	         user.setName(rs.getString("name"));
	         user.setBirth(rs.getString("birth"));
	         user.setPhone(rs.getString("phone"));
	         user.setJoinDate(rs.getDate("join_date"));
	         user.setUserClass(rs.getString("class"));
	         user.setProfileImage(rs.getString("profile_image"));
	         return user;
	      }

	      rs.close();
	      pstmt.close();
	      con.close();
	   } catch (SQLException e) {
	      e.printStackTrace();
	   }
	   return null;
	}
}
