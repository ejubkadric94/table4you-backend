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
 * Created by test on 03/02/16.
 */
public class UserHelper {

    private User user;
    private JsonNode json;

    public UserHelper(JsonNode json){
        user = Json.fromJson(json, User.class);
        this.json = json;
    }

    /**
     * Creates a User with specified information. Password is hashed using MD5.
     * Finally, confirmation mail is sent to specified email address.
     */
    public void createUser(){
        if(user != null){
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            user.setPasswordConfirmation(DigestUtils.md5Hex(user.getPasswordConfirmation()));
            Email.sendConfirmationEmail(user.getEmail(), Resources.SERVER_NAME + "/" + Resources.VERSION
                    + "/registration/confirm/" + encodeToken(user.getAuthToken().getToken()));

            user.getAddress().save();
            user.getAuthToken().save();
            user.save();
        }
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
            user.getAuthToken().setEmail(user.getEmail());
            user.getAddress().setEmail(user.getEmail());
            user.setConfirmed(false);
            return true;
        }
        return false;
    }

    /**
     * Confirms the user after the link in confirmation email is opened.
     * @param encodedToken encoded authToken of user
     * @return returns true
     */
    public static boolean confirmUser(String encodedToken){
        String token = decodeToken(encodedToken);

        System.out.println(">>>>>>TOKEN IS \n" + token);

        Token tempToken = Token.find.where().eq("token", token).findUnique();
        User userWithToken = User.find.where().eq("email", tempToken.getEmail()).findUnique();

        if(userWithToken == null)
            return false;

        userWithToken.setConfirmed(true);
        userWithToken.save();
        return true;
    }

    /**
     * Check if email already exist in database
     * @param json - request body in as json
     * @return true if user exists, and false otherwise
     */
    public static boolean ifEmailExists(JsonNode json){
        List<User> oldUsers = User.find.where().eq("email", json.path("email").asText()).findList();
        if( oldUsers.size() >1){
            return true;
        }
        for(User temp : oldUsers){
            temp.getAddress().setEmail("");
            temp.getAuthToken().setEmail("");
            temp.setEmail("");
        }
        return false;
    }

    /**
     * Check if email and password match
     * @param email email of the user
     * @param password password of the user
     * @return true if email and password match, false otherwise
     */
    public static boolean isValidLoginInfo(String email, String password){
        String md5password = DigestUtils.md5Hex(password);
        User user = User.find.where().eq("email", email).eq("password", md5password).findUnique();
        if(user != null)
            return true;
        return false;
    }

    /**
     * Decodes data using BASE64 decoding
     * @param token is the string to be decoded
     * @return decoded string
     */
    public static String decodeToken(String token){
        byte[] valueDecoded= Base64.decodeBase64(token.getBytes());
        return new String(valueDecoded);
    }

    /**
     * encode data  using BASE64 hashing
     * @param token is string to be encoded
     * @return encoded string
     */
    public static String encodeToken(String token){
        byte[]   bytesEncoded = Base64.encodeBase64(token .getBytes());
        return new String(bytesEncoded );
    }

    /**
     * Validate user information during the registration
     * @return true if everything is valid, false otherwise
     */
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
}



