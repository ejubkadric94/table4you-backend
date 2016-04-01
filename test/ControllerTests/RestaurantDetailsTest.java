package ControllerTests;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import models.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;
import static play.test.Helpers.contentAsString;

/**
 * Created by root on 16/02/16.
 */
public class RestaurantDetailsTest {

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
        });
    }

    @Test
    public void testWrongRestaurantId() {
        running(fakeApplication(), () -> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants/9999999");

            Result result = route(rb);
            assertEquals(Http.Status.BAD_REQUEST, result.status());
            assertTrue(contentAsString(result).contains("error"));
        });
    }

    @Test
    public void testValidRestaurantId() {
        running(fakeApplication(), () -> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants/1601994");

            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
            assertTrue(contentAsString(result).contains("name"));
        });
    }

    @AfterClass
    public static void removeRestaurant(){
        running(fakeApplication(),()-> {
            Restaurant restaurant = Restaurant.find.where().eq("restaurantId", 1601994).findUnique();
            restaurant.delete();
        });
    }

}

