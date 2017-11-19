package util;

import org.apache.commons.codec.digest.DigestUtils;

import dao.User;

public class DigestService 
{
   public static String getPasswordHash(String passwordAndSalt)
   {
       return saltAndHash(passwordAndSalt);
   }
   
   public static String getPasswordHash(User user)
   {
       if (user == null)
           throw new NullPointerException();
       
       return saltAndHash(user.getPassword() + user.getLogin());
   }
   
   private static String saltAndHash(String password)
   {
       return DigestUtils.sha256Hex(password);
   }
   
   public static boolean isEqualsHash(User firstUser, User secondUser)
   {   
       if (firstUser == null || secondUser == null ||
               firstUser.getPassword() == null || secondUser.getPassword() == null)
           throw new NullPointerException();
       
       return firstUser.getPassword().equals(secondUser.getPassword());
   }
}
