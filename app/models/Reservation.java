package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


import play.data.format.Formats;
import utilities.Validation;


import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Created by root on 16/02/16.
 */
@Entity
@Table(name = "abh_reservation")
public class Reservation extends Model implements Validation {
    @Id
    @Column(name = "reservationId", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long reservationId;
    @Column(name = "restaurantId", columnDefinition = "BIGINT")
    private long restaurantId;
    @Column(name = "dateTime" , columnDefinition = "DATETIME")
    @JsonFormat(pattern = "dd/mm/yyyy HH:mm:ss")
    @Formats.DateTime( pattern = "dd/mm/yyyy HH:mm:ss")
    private Timestamp dateTime;
    @Column(name = "guestCount")
    private int guestCount;
    @Column(length = 300)
    private String note;

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

    @Override
    public boolean isValid() {
        return dateTime != null;
    }
}
