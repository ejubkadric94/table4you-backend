package models;

/**
 * Created by Ejub on 31.1.2016.
 */
import play.data.format.Formats;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "abh_user")
public class User extends com.avaje.ebean.Model {
    @Id
    public String email;
    public String lastName;
    public String firstName;
    public String password;
    @OneToOne()
    @JoinColumn(name="email")
    public Token authToken;
    public boolean isConfirmed;
    public int phone;
    public String gender;
    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date birthdate;
    @OneToOne()
    @JoinColumn(name="email")
    public Address address;

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

        this.authToken.token =  UUID.randomUUID().toString();
        this.authToken.expirationDate = createExpirationDate();

        this.isConfirmed = false;
    }

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

    @Entity
    @Table(name = "abh_user_address")
    public class Address {
        @Id
        public String email;
        public String streetName;
        public String city;
        public String country;

        public Address(Address address){
            this.streetName = address.streetName;
            this.city = address.city;
            this.country = address.country;
        }
    }

    @Entity
    @Table(name = "abh_user_token")
    public class Token {
        @Id
        public String email;
        public String token;
        public Date expirationDate;
    }

    /**
     * Creates a date which is one day ahead of current date
     * @return returns newly created date
     */
    private Date createExpirationDate(){
        Date newDate = new Date();

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 1);
        newDate = date.getTime();

        return newDate;
    }
}
//{"name":"John","surname":"Johnson","email":"ejubkadric@gmail.com","password":"test"}