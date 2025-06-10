package Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtil {
   public static String encode(String password) {
      try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hashBytes = digest.digest(password.getBytes());
         return Base64.getEncoder().encodeToString(hashBytes);
      } catch (NoSuchAlgorithmException var3) {
         var3.printStackTrace();
         return null;
      }
   }


// 평문 비번과 암호화된 비번 비교
   public static boolean matches(String rawPassword, String encodedPassword) {
      return encode(rawPassword).equals(encodedPassword);
   }

   public static int effectivenessConfirm(String email, String password, String name, String birth) {

	   // 2. 비밀번호 제약조건
	   // 영문자(대소문자) 최소 1개 이상 / 숫자 1개 이상 포함 / 특수문자 중 하나 포함 / 전체 길이 8자 이상
	   
      if (!email.equals("") && !password.equals("") && !name.equals("") && !birth.equals("")) {
         if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return 1;
         } else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$")) {
            return 2;
         } else if (!name.matches("^.{1,10}$")) {
            return 3;
         } else {
            return !birth.matches("^\\d{4}-\\d{2}-\\d{2}$") ? 4 : 0;
         }
      } else {
         return -1;
      }
   }

   public static int effectivenessConfirm(String email) {
      if (email.equals("")) {
         return -1;
      } else {
         return !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") ? 1 : 0;
      }
   }
}
