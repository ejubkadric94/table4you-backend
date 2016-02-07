package utilities;

import com.google.gson.Gson;
import models.User;

/**
 * Created by Ejub on 7.2.2016.
 */
public final class JsonOutput {
    private JsonOutput(){};

    /**
     * Get the token of specific user
     * @param userHelper UserHelper object which contains the desired user
     * @return authorization token
     */
    public static String getAuthToken(UserHelper userHelper){
        return "{\"authToken\":\"" + userHelper.getUser().getAuthToken().getToken() + "\"}";
    }

    /**
     * Get the token of specific user
     * @param encodedToken is the token ecnoded using encode() method
     * @return authorization token
     */
    public static String getAuthToken(String encodedToken){
        return "{\"authToken\":\"" + UserHelper.decodeToken(encodedToken) + "\"}";
    }

    /**
     * Get the token of specific user
     * @param email is the email of desired user
     * @param password is the password of desired user
     * @return authorization token
     */
    public static String getAuthToken(String email, String password){
        User user = User.find.where().eq("email", email).findUnique();
        return "{\"authToken\":\"" + user.getAuthToken().getToken() + "\"}";
    }

    /**
     * Get the error in json format
     * @param error the text of error
     * @return json format error
     */
    public static String getError(String error){
        return "{\"error\":\"" + error + "\"}";
    }
}
