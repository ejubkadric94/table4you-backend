package ControllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Reservation;
import models.Restaurant;
import models.Token;
import org.junit.AfterClass;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import utilities.PersistenceManager;

import java.io.IOException;

import static play.test.Helpers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by root on 29/03/16.
 */
public class createRestaurantTest {

    @Test
    public void addNewRestaurant() {
        running(fakeApplication(),()-> {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            ObjectNode address = JsonNodeFactory.instance.objectNode(); // the child
            address.put("streetName", "Saraci 14");
            address.put("city", "Saraci 14");
            address.put("country", "BIH");
            ObjectNode coordinates = JsonNodeFactory.instance.objectNode(); // the child
            coordinates.put("latitude", 43.859335);
            coordinates.put("longitude", 43.859335);

            node.put("restaurantId", 1601994);
            node.put("name", "UnitTest");
            node.set("address", address);
            node.set("coordinates", coordinates);
            node.put("phone", 387454112);
            node.put("workingHours", "...");
            node.put("rating", 0);
            node.put("reservationPrice", 2);
            node.put("deals", "...");

            JsonNode json = null;
            try {
                json = (JsonNode) new ObjectMapper().readTree(node.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }


            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "admin@table4you.com").findUnique().
                            getToken()).bodyJson(json);
            Result result = route(rb);

            Restaurant restaurant = Restaurant.find.where().eq("restaurantId", 1601994).findUnique();
            String restaurantId = Long.toString(restaurant.getRestaurantId());

            assertEquals(Http.Status.CREATED, result.status());
            assertTrue(contentAsString(result).contains("1601994"));
        });
    }

    @Test
    public void testWithNoAdmin(){
        running(fakeApplication(),()-> {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            ObjectNode address = JsonNodeFactory.instance.objectNode(); // the child
            address.put("streetName", "Saraci 14");
            address.put("city", "Saraci 14");
            address.put("country", "");
            ObjectNode coordinates = JsonNodeFactory.instance.objectNode(); // the child
            coordinates.put("latitude", 43.859335);
            coordinates.put("longitude", 43.859335);

            node.put("restaurantId", 1601994);
            node.put("name", "UnitTest");
            node.set("address", address);
            node.set("coordinates", coordinates);
            node.put("phone", 387454112);
            node.put("workingHours", "...");
            node.put("rating", 0);
            node.put("reservationPrice", 2);
            node.put("deals", "...");

            JsonNode json = null;
            try {
                json = (JsonNode) new ObjectMapper().readTree(node.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants").bodyJson(json);
            Result result = route(rb);

            assertEquals(Http.Status.UNAUTHORIZED, result.status());
        });
    }

    @Test
    public void addNewRestaurantWithInvalidData() {
        running(fakeApplication(),()-> {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            ObjectNode address = JsonNodeFactory.instance.objectNode(); // the child
            address.put("streetName", "Saraci 14");
            address.put("city", "Saraci 14");
            address.put("country", "");
            ObjectNode coordinates = JsonNodeFactory.instance.objectNode(); // the child
            coordinates.put("latitude", 43.859335);
            coordinates.put("longitude", 43.859335);

            node.put("restaurantId", 1601994);
            node.put("name", "UnitTest");
            node.set("address", address);
            node.set("coordinates", coordinates);
            node.put("phone", 387454112);
            node.put("workingHours", "...");
            node.put("rating", 0);
            node.put("reservationPrice", 2);
            node.put("deals", "...");

            JsonNode json = null;
            try {
                json = (JsonNode) new ObjectMapper().readTree(node.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }


            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "admin@table4you.com").findUnique().
                            getToken()).bodyJson(json);
            Result result = route(rb);

            Restaurant restaurant = Restaurant.find.where().eq("restaurantId", 1601994).findUnique();
            String restaurantId = Long.toString(restaurant.getRestaurantId());

            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }


    @AfterClass
    public static void removeRestaurant(){
        running(fakeApplication(),()-> {
            Restaurant restaurant = PersistenceManager.getRestaurantById(1601994);
            restaurant.delete();
        });
    }

}

