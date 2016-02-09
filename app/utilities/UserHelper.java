package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import models.Token;
import models.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import play.libs.Json;


/**
 * Created by Ejub on 03/02/16.
 * Provides useful methods for manipulating User class.
 */
public class UserHelper {

    private User user;
    private JsonNode json;


    public UserHelper() {

    }

    /**
     * Constructs the class with a JsonNode object.
     *
     * @param json the json containing all the info about user
     */
    public UserHelper(JsonNode json){
        user = Json.fromJson(json, User.class);
        this.json = json;
    }


    /**
     * Creates a token object and initializes it.
     * Authentication authToken is created randomly and expiration day is set to one day ahead.
     *
     * @return true if successful, false if unsuccessful
     */
    public boolean initializeUser(User user){
        if(user != null){
            user.setAuthToken(new Token());
            user.getAuthToken().generateToken();
            user.getAuthToken().setEmail(user.getEmail());
            user.getAddress().setEmail(user.getEmail());
            user.setConfirmed(false);
            return true;
        }
        return false;
    }

    /**
     * Check if email already exist in database.
     *
     * @return true if user getUserFromSession, and false otherwise
     */
    public boolean ifEmailExists(User user){
        User oldUser = User.find.where().eq("email", user.getEmail()).findUnique();
        return oldUser != null;
    }

    /**
     * Decodes data using BASE64 decoding.
     *
     * @param token is the string to be decoded
     * @return decoded string
     */
    public static String decodeToken(String token){
        byte[] valueDecoded= Base64.decodeBase64(token.getBytes());
        return new String(valueDecoded);
    }

    /**
     * Encode data  using BASE64 hashing.
     *
     * @param token is string to be encoded
     * @return encoded string
     */
    public static String encodeToken(String token){
        byte[]   bytesEncoded = Base64.encodeBase64(token .getBytes());
        return new String(bytesEncoded );
    }


    /**
     * Gets the user.
     *
     * @return the user object
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the user object to be set.
     */
    public void setUser(User user) {
        this.user = user;
    }
}



