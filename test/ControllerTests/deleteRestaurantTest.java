package ControllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Address;
import models.Coordinates;
import models.Restaurant;
import models.Token;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import utilities.PersistenceManager;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;
import static play.test.Helpers.contentAsString;

/**
 * Created by root on 01/04/16.
 */
public class deleteRestaurantTest {
/*
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
        });
    }

    @Test
    public void deleteValidRestaurant(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(DELETE).uri("/v1/restaurants/1601994").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "admin@table4you.com").findUnique().getToken());
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
            assertEquals(null, PersistenceManager.getRestaurantById(1601994));
        });
    }

    @Test
    public void deleteInvalidRestaurant(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(DELETE).uri("/v1/restaurants/33333").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "admin@table4you.com").findUnique().
                            getToken());
            Result result = route(rb);
            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }

    @Test
    public void testWithNoAdmin(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(DELETE).uri("/v1/restaurants/1601994");
            Result result = route(rb);

            assertEquals(Http.Status.UNAUTHORIZED, result.status());
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
*/
}
