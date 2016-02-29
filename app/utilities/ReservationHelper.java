package utilities;

/**
 * Created by root on 22/02/16.
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
