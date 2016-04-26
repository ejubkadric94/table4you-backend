package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import utilities.Validation;
import utilities.View;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Created by Ejub on 16/02/16.
 * The Reservation class is used for storing reservations.
 */
@Entity
@Table(name = "abh_reservation")
public class Reservation extends Model implements Validation {
    @Id
    @Column(name = "reservationId", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(View.BasicDetails.class)
    private long reservationId;
    @Column(name = "dateTime" , columnDefinition = "DATETIME")
    @JsonFormat(pattern = "dd/mm/yyyy HH:mm:ss")
	@JsonView(View.BasicDetails.class)
    private Timestamp dateTime;
    @Column(name = "guestCount")
	@JsonView(View.BasicDetails.class)
    private int guestCount;
    @Column(length = 300)
	@JsonView(View.BasicDetails.class)
    private String note;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonView(View.AllDetails.class)
    public Restaurant restaurant;


    public static Reservation.Finder<String, Reservation> find = new Model.Finder<String, Reservation>(String.class, Reservation.class);


    /**
     * Validates the reservation details.
     *
     * @return true if everything is valid
     */
    @Override
    @JsonIgnore
    public boolean isValid() {
        return  guestCount != 0;
    }

    @JsonIgnore
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

	@JsonView(View.BasicDetails.class)
    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

	@JsonView(View.BasicDetails.class)
    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

	@JsonView(View.BasicDetails.class)
    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

	@JsonView(View.BasicDetails.class)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
