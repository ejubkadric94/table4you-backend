package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import play.data.validation.Constraints;
import utilities.UserHelper;
import utilities.View;
import utilities.Validation;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;
import com.avaje.ebean.Model;

/**
 * Created by Ejub on 31.1.2016.
 * Class User can be used to manipulate users.
 */
@Entity
@Table(name = "abh_user")
public class User extends Model implements Validation {
    @Id
    @Column(length = 80)
    @Constraints.Required
    @JsonView(View.BasicDetails.class)
    private String email;
    @Column(length = 100)
    @Constraints.Required
    @JsonView(View.AllDetails.class)
    private String password;
    @Column(name = "passwordConfirmation", length = 100)
    @JsonView(View.AllDetails.class)
    private String passwordConfirmation;
    @Column(name = "firstName", length = 25)
    @JsonView(View.BasicDetails.class)
    private String firstName;
    @Column(name = "lastName", length = 25)
    @JsonView(View.BasicDetails.class)
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(referencedColumnName = "userEmail")
    @JsonView(View.BasicDetails.class)
    private Address address;
    @Column(columnDefinition = "BIGINT")
    @JsonView(View.BasicDetails.class)
    private long phone;
    @Column(length = 6)
    @JsonView(View.BasicDetails.class)
    private String gender;
    @Column(columnDefinition = "date")
    @JsonFormat(pattern = "dd/mm/yyyy")
    @JsonView(View.BasicDetails.class)
    private Date birthdate;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(referencedColumnName = "userEmail")
    @JsonIgnore
    @JsonView(View.AllDetails.class)
    private Token authToken;
    @Column(name = "isConfirmed")
    @JsonIgnore
    @JsonView(View.AllDetails.class)
    private boolean isConfirmed;
    @JsonIgnore
    private boolean isAdmin;

    public User(){

    }

    public User(String decodedToken){
        authToken = new Token();
        authToken.setToken(UserHelper.decodeToken(decodedToken));
    }

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

    /**
     * Extends the token expiration date by one day.
     */
    public void extendTokenExpiration(){
        this.authToken.setExpirationDate(Token.generateExpirationDate());
    }

    /**
     * Confirms the user.
     *
     * @param encodedToken encoded authToken of user
     * @return returns true if everything is valid
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
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

    @JsonIgnore
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * Validates the user information.
     *
     * @return true if everything is valid, and false otherwise
     */
    @Override
    public boolean isValid() {
        if(password == null || passwordConfirmation == null || firstName == null || lastName == null || address == null
                || phone == 0 || gender == null || birthdate == null){
            return false;
        }
        if(validateEmail(getEmail()) && validateFirstName(getFirstName()) && validateLastName(getLastName())
                && validateGender(getGender()) && validatePasswords() ){
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
