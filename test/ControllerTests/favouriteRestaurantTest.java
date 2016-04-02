package ControllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
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

/**
 * Created by Ejub on 2.4.2016.
 */
public class favouriteRestaurantTest {
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

            Token token = new Token();
            token.generateToken();
            token.setEmail("user@testtest.com");

            User user = new User();
            user.setEmail("user@testtest.com");


            user.setAuthToken(token);
            user.setConfirmed(true);

            token.save();
            user.save();
        });
    }

    @Test
    public void unauthorizedTest(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/1601994/favourite");
            Result result = route(rb);

            assertEquals(Http.Status.UNAUTHORIZED, result.status());
        });
    }

    @Test
    public void invalidRestaurantTest(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/5687/favourite")
                    .header("USER-ACCESS-TOKEN", PersistenceManager.getUserByEmail("user@testtest.com").getAuthToken()
                    .getToken());
            Result result = route(rb);

            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }

    @Test
    public void validTest(){
        running(fakeApplication(),()-> {
            assertTrue(PersistenceManager.getUserByEmail("user@testtest.com").getFavouriteRestaurants().size() == 0);

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/1601994/favourite")
                    .header("USER-ACCESS-TOKEN", PersistenceManager.getUserByEmail("user@testtest.com").getAuthToken()
                            .getToken());
            Result result = route(rb);

            assertEquals(Http.Status.OK, result.status());
            assertTrue(PersistenceManager.getUserByEmail("user@testtest.com").getFavouriteRestaurants().size() == 1);
        });
    }

    @AfterClass
    public static void removeEverything() {
        running(fakeApplication(),()-> {
            Restaurant restaurant = PersistenceManager.getRestaurantById(1601994);
            if(restaurant != null){
                restaurant.delete();
            }

            User user = User.find.where().eq("email", "user@testtest.com").findUnique();

            user.delete();
        });
    }
}
