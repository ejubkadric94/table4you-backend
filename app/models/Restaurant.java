package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import utilities.RestaurantSerializer;

import javax.persistence.*;

/**
 * Created by root on 15/02/16.
 */
@Entity
@Table(name = "abh_restaurant")
public class Restaurant {
    @Id
    @Column(name = "restaurantId", columnDefinition = "BIGINT")
    private long restaurantId;
    @Column(length = 100)
    private String name;

    @OneToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "restaurantId")
    private Address address;
    @OneToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "restaurantId")
    private Coordinates coordinates;

    @Column(columnDefinition = "BIGINT")
    private long phone;
    @Column(name = "workingHours",length = 20)
    private String workingHours;
    private double rating;
    @Column(name = "reservationPrice")
    private double reservationPrice;
    @Column(length = 200)
    private String deals;

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
}
/*
{
restaurantId: <restaurantId>,
name: <name>,
address: {
streetName: <streetName>, city: <city>,
country: <country>
},
phone: <phone>, workingHours: <workingHours>,
rating: <rating>, reservationPrice: <price>,
deals: <deals>, coordinates: {
latitude : <latitude>, longitude: <longitude>
}
}
 */