package models;

/**
 * Created by Ejub on 31.1.2016.
 */
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "abh_user")
public class User extends com.avaje.ebean.Model {
    @Id
    public String email;
    public String lastName;
    public String firstName;
    public String password;
    public String authToken;
    public boolean isConfirmed;
    public int phone;
    public String gender;
    public Date birthdate;
    public String streetName;
    public String city;
    public String country;


    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);
}
//{"name":"John","surname":"Johnson","email":"ejubkadric@gmail.com","password":"test"}
