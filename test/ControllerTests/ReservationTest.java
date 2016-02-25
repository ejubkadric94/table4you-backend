package ControllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import org.junit.AfterClass;
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

    public static void prepareRestaurant() {
        running(fakeApplication(),()-> {

            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantId(1601994);
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
            token.save();

            User user = new User();
            user.setEmail("test@testtest.com");
            user.setAuthToken(token);
            user.setConfirmed(true);


            user.save();

        });
    }

    @Test
    public void testMakingReservationWithValidData() {
        running(fakeApplication(),()-> {
            JsonNode json = play.libs.Json.newObject()
                    .put("restaurantId", 1)
                    .put("dateTime", "10/04/2015 20:30:00")
                    .put("guestCount",5)
                    .put("note", "None");

            Token token = Token.find.where().eq("userEmail", "test@testtest.com").findUnique();

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/1601994/reservations").
                    bodyJson(json).header("USER-ACCESS-TOKEN", token.getToken());
            Result result = route(rb);

            Restaurant restaurant = Restaurant.find.where().eq("restaurantId", 1601994).findUnique();
            String restaurantId = Long.toString(restaurant.getRestaurantId());

            assertEquals(Http.Status.OK, result.status());
            assertTrue(contentAsString(result).contains(restaurantId));
        });
    }

    @AfterClass
    public static void removeRestaurant(){
        running(fakeApplication(),()-> {
            Restaurant restaurant = Restaurant.find.where().eq("restaurantId", 1601994).findUnique();
            restaurant.delete();

            Token token = Token.find.where().eq("email", "test@testtest.com").findUnique();
            User user = User.find.where().eq("email", "test@testtest.com").findUnique();

            user.delete();
            token.delete();
        });
    }
}

