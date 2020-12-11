package services;

import dataaccess.UserDB;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class AccountService {
    
    public User login(String email, String password, String path) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                // Log activity
                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Successful login by {0}", email);
                
                // Send E-mail
//                GmailService.sendMail(email, "Your account has logged in.", "Your account has logged in.", true);

                String to = user.getEmail();
                String subject = "Notes App Login";
                String template = path + "/emailtemplates/login.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());
                
                GmailService.sendMail(to, subject, template, tags);
				

                return user;
            }
        } catch (Exception e) {
        }
        
        return null;
    }
    
    public void resetPassword(String email, String path, String url) {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
         
        String uuid = UUID.randomUUID().toString();
        user.setResetPasswordUUID(uuid);
        userDB.update(user);
        String link = url + "?uuid=" + uuid;
        
        String to = user.getEmail();
        String subject = "Reset Password App Login";
        String template = path + "/emailtemplates/resetpassword.html";

        HashMap<String, String> tags = new HashMap<>();
        tags.put("firstname", user.getFirstName());
        tags.put("lastname", user.getLastName());
        tags.put("username", user.getFirstName());
        tags.put("link", link);
        tags.put("date", (new java.util.Date()).toString());

        try {
            GmailService.sendMail(to, subject, template, tags);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean changePassword(String uuid, String password) {
        try {
            UserDB ur = new UserDB();
            User user = ur.getByUUID(uuid);
            user.setPassword(password);
            user.setResetPasswordUUID(null);
            ur.update(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}