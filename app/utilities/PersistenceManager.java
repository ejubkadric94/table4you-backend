package utilities;

import models.User;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Ejub on 08/02/16.
 * Provides useful methods for persisting data into database.
 */
public class PersistenceManager {
    /**
     * Inserts a User with specified information into the database.
     * Password is hashed using MD5.
     * Finally, confirmation mail is sent to specified email address.
     *
     * @param user the user to be saved
     */
    public void createUser(User user){
        if(user != null){
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            user.setPasswordConfirmation(DigestUtils.md5Hex(user.getPasswordConfirmation()));
            Email.sendConfirmationEmail(user.getEmail(), Resources.SERVER_NAME + "/" + Resources.VERSION
                    + "/registration/confirm/" + UserHelper.encodeToken(user.getAuthToken().getToken()));

            user.getAddress().save();
            user.getAuthToken().save();
            user.save();
        }
    }

    /**
     * Checks if there is a user in database with an email address which is same as the one stored in this user.
     *
     * @return the user if it getUserFromSession, or null if it does not exist
     */
    public User getUserFromSession(UserSession userSession){
        User newUser = User.find.where().eq("email", userSession.getEmail()).findUnique();
        return newUser;
    }
}


/*
DELETE FROM abh_user;
DELETE FROM abh_user_address;
DELETE FROM abh_user_token;
 */