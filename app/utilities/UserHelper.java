package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import models.Address;
import models.Token;
import models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.ParseException;
import play.libs.Json;


/**
 * Created by test on 03/02/16.
 */
public class UserHelper {

    private User user;

    public UserHelper(JsonNode json){
        System.out.println(">>>>>>json " + json);
        user = Json.fromJson(json, User.class);
    }

    /**
     * Creates a User with specified information. Password is hashed using MD5.
     * Finally, confirmation mail is sent to specified email address.
     */
    public void createUser(){
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setPasswordConfirmation(DigestUtils.md5Hex(user.getPasswordConfirmation()));
        Email.sendConfirmationEmail(user.getEmail(), Resources.SERVER_NAME + "/" + Resources.VERSION
                + "/registration/confirm/" + encodeToken(user.getAuthToken().getToken()));
        user.save();
    }

    /**
     * Confirms the user after the link in confirmation email is opened.
     * @param encodedToken encoded authToken of user
     * @return returns true
     */
    public boolean confirmUser(String encodedToken){
        String token = decodeToken(encodedToken);
        User user = User.find.where().like("abh_user_token", token).findUnique();
        if(user == null)
            return false;

        user.setConfirmed(true);
        return true;
    }

    /**
     * Decodes data using BASE64 decoding
     * @param token is the string to be decoded
     * @return decoded string
     */
    public String decodeToken(String token){
        byte[] valueDecoded= Base64.decodeBase64(token.getBytes());
        return new String(valueDecoded);
    }

    /**
     * encode data  using BASE64 hashing
     * @param token is string to be encoded
     * @return encoded string
     */
    private String encodeToken(String token){
        byte[]   bytesEncoded = Base64.encodeBase64(token .getBytes());
        return new String(bytesEncoded );
    }

    /**
     * Creates a token object and initializes it.
     * Authentication authToken is created randomly and expiration day is set to one day ahead.
     * @return true if successful, false if unsuccessful
     */
    public boolean initializeUser(){
        if(user != null){
            user.setAuthToken(new Token());
            user.getAuthToken().generateToken();
            user.setConfirmed(false);
            return true;
        }
        return false;
    }

    public boolean validateUser(){
        if(validateEmail(user.getEmail()) && validateFirstName(user.getFirstName()) && validateLastName(user.getLastName())
                && validateGender(user.getGender()) && validatePasswords()){
            return true;
        }
        return false;
    }

    private boolean validateFirstName( String firstName ){
        return firstName.matches( "[A-Z][a-zA-Z]*" );
    }
    private boolean validateLastName( String lastName ) {
        return lastName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );
    }
    private boolean validateEmail(String mail){
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(mail);
        return  matcher.matches();
    }
    private boolean validateGender(String gender) {
        if (gender.equals("male") || gender.equals("female") || gender.equals("MALE") || gender.equals("FEMALE")
                || gender.equals("Male") || gender.equals("Female")) {
            return true;
        }
        return false;
    }
    public boolean validatePasswords(){
        return user.getPassword().equals(user.getPasswordConfirmation());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }




/*
    public static boolean isValidLoginInfo(JsonNode json){
        User user = User.find.where().eq("email", userEmail).findUnique();
        if(user != null && DigestUtils.md5Hex(userPassword).equals(user.password))
            return true;
        return false;
    }
    */

}



