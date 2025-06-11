package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Entity.User;
import Util.DBUtil;

public class UserRepository {

   public User findByEmail(String email) {
      User user = null;
      String sql = "SELECT * FROM User WHERE email = ?";
      try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, email);
         ResultSet rs = pstmt.executeQuery();
          if (rs.next()) {
                  user = new User();
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
          
      } catch (Exception e) {
      e.printStackTrace();
   }
   return user;
}

   public void updateByEmail(String email, String phone, String encodedPassword) {
      String sql = "UPDATE User SET phone = ?, password = ? WHERE email = ?";
      try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, phone);
           pstmt.setString(2, encodedPassword);
           pstmt.setString(3, email);
         pstmt.executeUpdate();
         

      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public void updateProfileImage(String email, String fileName) {
       String sql = "UPDATE User SET profile_image = ? WHERE email = ?";
       try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, fileName);
           pstmt.setString(2, email);
           pstmt.executeUpdate();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   public void deleteByEmail(String email) {
      String sql = "DELETE FROM User WHERE email = ?";
      try (Connection conn = DBUtil.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, email);
         pstmt.executeUpdate();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   
   
}
