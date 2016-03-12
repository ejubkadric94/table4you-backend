package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Ejub on 04/02/16.
 * Contains the address manipulation data for users and restaurant.
 */
@Entity
@Table(name = "abh_user_address")
public class Address extends Model{
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressId;
    @Column(name="userEmail", columnDefinition = "VARCHAR(80) DEFAULT 'test@test.com'")
    @JsonIgnore
    private String email;
    @Column(name = "streetName", length = 100)
    private String streetName;
    @Column(length = 100)
    private String city;
    @Column(length = 100)
    private String country;

    @Column(name = "restaurantId", columnDefinition = "BIGINT")
    private long restaurantId;

    /**
     * Default constructor.
     * Constructs the object with no parameters.
     */
    public Address() {

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

    /**
     * Returns the restaurantId when used in code, but JsonIgnore annotation makes sure that restaurantId will be
     * ignored from serialization.
     *
     * @return the restaurantId
     */
    @JsonIgnore
    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
}

