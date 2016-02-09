package models;

/**
 * Created by Ejub on 31.1.2016.
 * Class User represents and defines table abh_user in database.
 * It contains multiple instance variable which represent the basic information about the user.
 * Instance variable address references abh_user_address table.
 * Instance variable authToken references abh_user_token table.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.digest.DigestUtils;
import play.data.validation.Constraints;
import utilities.UserHelper;
import utilities.Validation;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;


@Entity
@Table(name = "abh_user")
public class User extends com.avaje.ebean.Model implements Validation {
    @Id
    @Column(length = 80)
    @Constraints.Required
    private String email;
    @Column(length = 100)
    @Constraints.Required
    private String password;
    @Column(name = "passwordConfirmation", length = 100)
    private String passwordConfirmation;
    @Column(name = "firstName", length = 25)
    private String firstName;
    @Column(name = "lastName", length = 25)
    private String lastName;
    @OneToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "userEmail")
    private Address address;
    private int phone;
    @Column(length = 6)
    private String gender;
    @Column(columnDefinition = "date")
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date birthdate;
    @OneToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "userEmail")
    private Token authToken;
    @Column(name = "isConfirmed")
    @JsonIgnore
    private boolean isConfirmed;


    public User(JsonNode json){
    }

    public User(String decodedToken){
        authToken = new Token();
        authToken.setToken(UserHelper.decodeToken(decodedToken));
    }

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);


    public void extendTokenExpiration(){
        this.authToken.setExpirationDate(Token.generateExpirationDate());
    }

    /**
     * Confirms the user after the link in confirmation email is opened.
     *
     * @param encodedToken encoded authToken of user
     * @return returns true
     */
    public static boolean confirmUser(String encodedToken){
        String token = UserHelper.decodeToken(encodedToken);

        Token tempToken = Token.find.where().eq("token", token).findUnique();
        if(tempToken == null){
            return false;
        }

        User userWithToken = User.find.where().eq("email", tempToken.getEmail()).findUnique();

        userWithToken.setConfirmed(true);
        userWithToken.save();
        return true;
    }

    /**
     * Check if email and password match
     * @return true if email and password match, false otherwise
     */
    public boolean isValidLoginInfo(){
        User user = User.find.where().eq("email", email).eq("password", DigestUtils.md5Hex(password)).findUnique();
        return user == null;
    }





    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Token getAuthToken() {
        return authToken;
    }

    public void setAuthToken(Token authToken) {
        this.authToken = authToken;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    @Override
    public boolean isValid() {
        if(email == null || firstName == null ||  lastName == null || gender == null){
            return false;
        }
        if(validateEmail(getEmail()) && validateFirstName(getFirstName()) && validateLastName(getLastName())
                && validateGender(getGender()) && validatePasswords()){
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
    private boolean validatePasswords(){
        return getPassword().equals(getPasswordConfirmation());
    }
}
