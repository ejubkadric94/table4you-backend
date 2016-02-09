package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Ejub on 04/02/16.
 * Class Address represents abh_user_address table from database.
 * It contains all needed fields and methods needed for their manipulation.
 */

@Entity
@Table(name = "abh_user_address")
public class Address extends Model{
    @Id
    @Column(name="userEmail", columnDefinition = "VARCHAR(80) DEFAULT 'test@test.com'")
    private String email;
    @Column(name = "streetName", length = 100)
    private String streetName;
    @Column(length = 100)
    private String city;
    @Column(length = 100)
    private String country;

    public Address(Address address){
        this.streetName = address.streetName;
        this.city = address.city;
        this.country = address.country;
    }

    public static Model.Finder<String, Address> find = new Model.Finder<String, Address>(String.class, Address.class);

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}

