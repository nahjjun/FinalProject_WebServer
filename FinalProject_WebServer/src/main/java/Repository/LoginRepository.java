package Repository;

import Dto.LoginRequestDto;
import Util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
