package utilities;

/**
 * Created by Ejub on 22/02/16.
 * Class ReservationHelper can be used to manipulate Reservation objects.
 */
public class ReservationHelper {
    private long reservationId;

    public ReservationHelper(long reservationId) {
        this.reservationId = reservationId;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }
}
