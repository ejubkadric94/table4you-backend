package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonView;
import utilities.RestaurantViews;
import javax.persistence.*;

/**
 * Created by Ejub on 15/02/16.
 * Class Restaurant can be used for storing restaurants.
 */
@Entity
@Table(name = "abh_restaurant")
public class Restaurant extends Model{
    @Id
    @Column(name = "restaurantId", columnDefinition = "BIGINT")
    @JsonView(RestaurantViews.BasicDetails.class)
    private long restaurantId;
    @Column(length = 100)
    @JsonView(RestaurantViews.BasicDetails.class)
    private String name;


    @OneToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "restaurantId")
    @JsonView(RestaurantViews.BasicDetails.class)
    private Address address;

    @OneToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "restaurantId")
    @JsonView(RestaurantViews.AllDetails.class)
    private Coordinates coordinates;

    @Column(columnDefinition = "BIGINT")
    @JsonView(RestaurantViews.BasicDetails.class)
    private long phone;
    @Column(name = "workingHours",length = 20)
    @JsonView(RestaurantViews.BasicDetails.class)
    private String workingHours;
    @JsonView(RestaurantViews.BasicDetails.class)
    private double rating;
    @Column(name = "reservationPrice")
    @JsonView(RestaurantViews.AllDetails.class)
    private double reservationPrice;
    @Column(length = 200)
    @JsonView(RestaurantViews.AllDetails.class)
    private String deals;
    @JsonView(RestaurantViews.BasicDetails.class)
    private String image;

    public static Model.Finder<String, Restaurant> find = new Model.Finder<String, Restaurant>(String.class, Restaurant.class);

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getReservationPrice() {
        return reservationPrice;
    }

    public void setReservationPrice(double reservationPrice) {
        this.reservationPrice = reservationPrice;
    }

    public String getDeals() {
        return deals;
    }

    public void setDeals(String deals) {
        this.deals = deals;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
