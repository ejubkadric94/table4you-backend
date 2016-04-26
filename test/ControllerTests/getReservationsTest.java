package ControllerTests;

import models.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import utilities.PersistenceManager;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;
import static play.test.Helpers.contentAsString;

/**
 * Created by root on 01/04/16.
 */
public class getReservationsTest {

    @BeforeClass
    public static void prepareRestaurant() {
        running(fakeApplication(),()-> {
            Coordinates coordinates = new Coordinates();
            coordinates.setRestaurantId(1601994);
            coordinates.setLatitude(5.5);
            coordinates.setLongitude(1.1);
            coordinates.save();

            Address address = new Address();
            address.setEmail("user@testtest.com");
            address.setCity("Test City");
            address.setCountry("Test Country");
            address.setStreetName("Test street");
            address.setRestaurantId(1601994);
            address.save();

            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantId(1601994);
            restaurant.setAddress(address);
            restaurant.setCoordinates(coordinates);
            restaurant.setName("Test restaurant");
            restaurant.setDeals("");
            restaurant.setPhone(2252);
            restaurant.setRating(2);
            restaurant.setReservationPrice(3);
            restaurant.setWorkingHours("");

            restaurant.save();

            Reservation reservation = new Reservation();

            DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = (Date) formatter.parse("10/04/2015 20:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
            reservation.setDateTime(new Timestamp(date.getTime()));
            reservation.setGuestCount(2);
            reservation.setNote("Nista");
            reservation.setRestaurant(restaurant);
            reservation.setReservationId(555);
            reservation.save();
        });
    }

    @Test
    public void getReservations(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants/1601994/reservations");
            Result result = route(rb);

            assertEquals(Http.Status.OK, result.status());
            assertTrue(contentAsString(result).contains("Nista"));
        });
    }

    @Test
    public void getReservationsForInvalidRestaurant(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants/454/reservations");
            Result result = route(rb);

            assertEquals(Http.Status.NOT_FOUND, result.status());
        });
    }

    @AfterClass
    public static void removeEverything() {
        running(fakeApplication(),()-> {
            Restaurant restaurant = PersistenceManager.getRestaurantById(1601994);
            if(restaurant != null){
                restaurant.delete();
            }
        });
    }

}
