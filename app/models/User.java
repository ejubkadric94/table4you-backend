package models;

/**
 * Created by Ejub on 31.1.2016.
 */
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.format.Formats;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "abh_user")
public class User extends com.avaje.ebean.Model {
    @Id
    @Column(length = 80)
    private String email;
    @Column(length = 100)
    private String password;
    @Column(name = "passwordConfirmation", length = 100)
    private String passwordConfirmation;
    @Column(name = "firstName", length = 25)
    private String firstName;
    @Column(name = "lastName", length = 25)
    private String lastName;
    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "user")
   // @JoinColumn(name="email")
    private Address address;
    private int phone;
    @Column(length = 6)
    private String gender;
    @Column(columnDefinition = "date")
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date birthdate;
    @OneToOne()
    //@JoinColumn(name = "email")
    @JsonIgnore
    private Token authToken;
    @Column(name = "isConfirmed")
    @JsonIgnore
    private boolean isConfirmed;


    public User(String email, String password, String firstName, String lastName, Address address, int phone,
                String gender, Date birthdate){
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = new Address(address);
        this.phone = phone;
        this.gender = gender;
        this.birthdate = birthdate;

        this.authToken = new Token();
        this.authToken.generateToken();

        this.isConfirmed = false;
    }

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

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
}
//{"name":"John","surname":"Johnson","email":"ejubkadric@gmail.com","password":"test"}