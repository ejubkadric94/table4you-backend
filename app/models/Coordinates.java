package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Ejub on 15/02/16.
 * Contains the coordinates manipulation data for restaurant.
 */
@Entity
@Table(name = "abh_coordinates")
public class Coordinates extends Model {
    @Id
    @Column(name = "restaurantId", columnDefinition = "BIGINT")
    private long restaurantId;
    private double latitude;
    private double longitude;

    public static Model.Finder<String, Coordinates> find = new Model.Finder<String, Coordinates>(String.class, Coordinates.class);

    @JsonIgnore
    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
