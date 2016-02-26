package ControllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

/**
 * Created by root on 18/02/16.
 */
public class ReservationTest {

    @BeforeClass
    public static void prepareRestaurant() {
        running(fakeApplication(),()-> {

            Coordinates coordinates = new Coordinates();
            coordinates.setRestaurantId(1601994);
            coordinates.setLatitude(5.5);
            coordinates.setLongitude(1.1);
            coordinates.save();

            Address address = new Address();
            address.setEmail("test@testtest.com");
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

            Token token = new Token();
            token.generateToken();
            token.setEmail("test@testtest.com");

            User user = new User();
            user.setEmail("test@testtest.com");


            user.setAuthToken(token);
            user.setConfirmed(true);

            token.save();
            user.save();
        });
    }

    @Test
    public void testMakingReservationWithInvalidJson() {
        running(fakeApplication(),()-> {
            JsonNode json = play.libs.Json.newObject()
                    .put("restaurantId", 1601994)
                    .put("dateTime", "10/04/2015 20:30:00")
                    .put("note", "None");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/1601994/reservations").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "test@testtest.com").findUnique().
                            getToken()).bodyJson(json);
            Result result = route(rb);

            Restaurant restaurant = Restaurant.find.where().eq("restaurantId", 1601994).findUnique();
            String restaurantId = Long.toString(restaurant.getRestaurantId());

            assertEquals(Http.Status.BAD_REQUEST, result.status());
            assertTrue(contentAsString(result).contains("error"));
        });
    }

    @Test
    public void testMakingReservationWithInvalidRestaurant() {
        running(fakeApplication(),()-> {
            JsonNode json = play.libs.Json.newObject()
                    .put("restaurantId", 9999)
                    .put("dateTime", "10/04/2015 20:30:00")
                    .put("guestCount",2)
                    .put("note", "None");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/9999/reservations").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "test@testtest.com").findUnique().
                            getToken()).bodyJson(json);
            Result result = route(rb);

            Restaurant restaurant = Restaurant.find.where().eq("restaurantId", 1601994).findUnique();
            String restaurantId = Long.toString(restaurant.getRestaurantId());

            assertEquals(Http.Status.BAD_REQUEST, result.status());
            assertTrue(contentAsString(result).contains("error"));
        });
    }

    @Test
    public void testMakingReservationWithValidData() {
        running(fakeApplication(),()-> {
            JsonNode json = play.libs.Json.newObject()
                    .put("restaurantId", 1601994)
                    .put("dateTime", "10/04/2015 20:30:00")
                    .put("guestCount",2)
                    .put("note", "None");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/1601994/reservations").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "test@testtest.com").findUnique().
                            getToken()).bodyJson(json);
            Result result = route(rb);

            Restaurant restaurant = Restaurant.find.where().eq("restaurantId", 1601994).findUnique();
            String restaurantId = Long.toString(restaurant.getRestaurantId());

            assertEquals(Http.Status.CREATED, result.status());
            assertTrue(contentAsString(result).contains("reservationId"));
        });
    }

    @AfterClass
    public static void removeRestaurant(){
        running(fakeApplication(),()-> {
            Reservation.find.where().eq("restaurantId", 1601994).findUnique().delete();
            Restaurant restaurant = Restaurant.find.where().eq("restaurantId", 1601994).findUnique();
            restaurant.delete();
            restaurant.getAddress().delete();
            restaurant.getCoordinates().delete();

            Token token = Token.find.where().eq("email", "test@testtest.com").findUnique();
            User user = User.find.where().eq("email", "test@testtest.com").findUnique();

            user.delete();
            token.delete();
        });
    }
}

