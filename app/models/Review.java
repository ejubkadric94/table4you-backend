package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import utilities.Validation;
import utilities.View;

import javax.persistence.*;

/**
 * Created by Ejub on 13.3.2016.
 */

@Entity
@Table(name = "abh_review")
public class Review extends Model  implements Validation {

    @Id
    @Column(columnDefinition = "BIGINT")
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.AdditionalDetails.class)
    private long reviewId;
    @JsonView(View.BasicDetails.class)
    private String text;
    @JsonView(View.BasicDetails.class)
    private double rating;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonView(View.AllDetails.class)
    public Restaurant restaurant;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @JsonView(View.AdditionalDetails.class)
    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    @JsonView(View.AdditionalDetails.class)
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean isValid() {
        return rating != 0;
    }

}
