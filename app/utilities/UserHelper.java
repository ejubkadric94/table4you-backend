package utilities;

import models.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;

/**
 * Created by test on 03/02/16.
 */
public class UserHelper {
    public String email;
    public String password;
    public String passwordConfirmation;
    public String firstName;
    public String lastName;
    public User.Address address;
    public int phone;
    public String gender;
    public Date birthdate;

    public UserHelper(){}

    /**
     * Creates a User with specified information. Password is hashed using MD5.
     * Authentication token is created randomly and expiration day is set to one day ahead.
     * Finally, confirmation mail is sent to specified email address.
     */
    public void createUser(){
        User newUser = new User(email, DigestUtils.md5Hex(password), firstName, lastName, address, phone, gender,
                birthdate);



        newUser.save();

        Email.sendConfirmationEmail(newUser.email, "/"+ Resources.SERVER_NAME+ "/" +Resources.VERSION+"/confirm/"
                + encodeToken(newUser.authToken.token));
    }

    public boolean confirmUser(String encodedToken){
        String token = decodeToken(encodedToken);
        //User user = User.find.where().eq("")
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


    public String getToken(){
        User user = User.find.where().eq("email", email).findUnique();
        return user.authToken.token;
    }
}



