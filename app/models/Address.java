package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by test on 04/02/16.
 */

@Entity
@Table(name = "abh_user_address")
public class Address {
 //   @Id
 //   @Column(length = 80)
 //   @JsonIgnore
  //  private String email;
 // @Id
 @OneToOne()
 @JoinColumn(name = "email")
 private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

   /* public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}

