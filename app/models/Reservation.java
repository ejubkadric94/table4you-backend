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
    private long reservationId;
    @Column(name = "restaurantId", columnDefinition = "BIGINT")
    @JsonIgnore
    private long restaurantId;
    @Column(name = "dateTime" , columnDefinition = "DATETIME")
    @JsonFormat(pattern = "dd/mm/yyyy HH:mm:ss")
    private Timestamp dateTime;
    @Column(name = "guestCount")
    private int guestCount;
    @Column(length = 300)
    private String note;


    public static Reservation.Finder<String, Reservation> find = new Model.Finder<String, Reservation>(String.class, Reservation.class);


    /**
     * Validates the reservation details.
     *
     * @return true if everything is valid
     */
    @Override
    @JsonView(View.AdditionalDetails.class)
    public boolean isValid() {
        return  guestCount != 0;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
