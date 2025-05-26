package Repository;

import Entity.User;
import Util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinRepository {
   public boolean save(User user) {
      String sql = "INSERT INTO `User`(email, password, name, birth) VALUES(?, ?, ?, ?)";
      boolean var3 = false;

      try {
         Connection con = DBUtil.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql);
         pstmt.setString(1, user.getEmail());
         pstmt.setString(2, user.getEncodedPassword());
         pstmt.setString(3, user.getName());
         pstmt.setString(4, user.getBirth());
         int tmp = pstmt.executeUpdate();
         pstmt.close();
         con.close();
         return tmp > 0;
      } catch (SQLException var6) {
         var6.printStackTrace();
         return false;
      }
   }

   public boolean emailExist(String email) {
      String sql = "SELECT DISTINCT email FROM `User` WHERE email=?";

      try {
         Connection con = DBUtil.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql);
         pstmt.setString(1, email);
         ResultSet resultSet = pstmt.executeQuery();
         boolean exist = resultSet.next();
         resultSet.close();
         pstmt.close();
         con.close();
         return exist;
      } catch (SQLException var7) {
         var7.printStackTrace();
         return false;
      }
   }
}
