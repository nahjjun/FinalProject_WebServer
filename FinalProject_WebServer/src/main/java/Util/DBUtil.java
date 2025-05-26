package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
   private static String url = "jdbc:mysql://43.201.14.191:3306/webserver?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
   private static String id = "webuser";
   private static String password = "webuser123!";
   private static String driver = "com.mysql.cj.jdbc.Driver";

   public static Connection getConnection() throws SQLException {
      Connection con = null;

      try {
         Class.forName(driver);
         con = DriverManager.getConnection(url, id, password);
      } catch (ClassNotFoundException var2) {
         var2.printStackTrace();
      }

      return con;
   }
}
