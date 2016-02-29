package utilities;

import models.Token;
import models.User;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by Ejub on 03/02/16.
 * Provides useful methods for manipulating User class.
 */
public class UserHelper {

    /**
     * Default constructor for UserHelper class.
     */
    public UserHelper() {
    }

    /**
     * Creates a token object and initializes it.
     * Authentication token is created randomly and expiration day is set to one day ahead.
     */
    public static void initializeUser(User user){
        if(user != null){
            user.setAuthToken(new Token());
            user.getAuthToken().generateToken();
            user.getAuthToken().setEmail(user.getEmail());
            user.getAddress().setEmail(user.getEmail());
            user.setConfirmed(false);
        }
    }

    /**
     * Check if email already exist in database.
     *
     * @return true if user getUserFromSession, and false otherwise
     */
    public boolean ifEmailExists(User user){
        User oldUser = PersistenceManager.getUserByEmail(user.getEmail());
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

}



