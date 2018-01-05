package util;

import org.apache.commons.codec.digest.DigestUtils;

import dao.User;

public class DigestService 
{
   public static String getPasswordHash(User user)
   {
       if (user == null)
           throw new NullPointerException();
       
       return saltAndHash(user.getPassword() + user.getLogin());
   }
   
   public static String encrypt(String password)
   {
       return DigestUtils.md5(password).toString();
   }
   
   private static String saltAndHash(String password)
   {
       return DigestUtils.sha256Hex(password);
   }
}
