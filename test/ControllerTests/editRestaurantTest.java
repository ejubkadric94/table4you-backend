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
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

/**
 * Created by root on 01/04/16.
 */
public class editRestaurantTest {
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
    public void testWithNoAdmin(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(PUT).uri("/v1/restaurants/1601994");
            Result result = route(rb);

            assertEquals(Http.Status.UNAUTHORIZED, result.status());
        });
    }

    @Test
    public void testWithInvalidRestaurant(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(PUT).uri("/v1/restaurants/666666").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "admin@table4you.com").findUnique().
                            getToken());
            Result result = route(rb);

            assertEquals(Http.Status.NOT_FOUND, result.status());
        });
    }

    @Test
    public void testWithInvalidData(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(PUT).uri("/v1/restaurants/666666").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "admin@table4you.com").findUnique().
                            getToken());
            Result result = route(rb);

            assertEquals(Http.Status.NOT_FOUND, result.status());
        });
    }

    @Test
    public void testWithValidData(){
        running(fakeApplication(),()-> {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            ObjectNode address = JsonNodeFactory.instance.objectNode(); // the child
            address.put("streetName", "Saraci 14");
            address.put("city", "EDIT TEST");
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


            Http.RequestBuilder rb = new Http.RequestBuilder().method(PUT).uri("/v1/restaurants/1601994").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "admin@table4you.com").findUnique().
                            getToken()).bodyJson(json);
            Result result = route(rb);

            assertEquals(Http.Status.OK, result.status());
            assertTrue(PersistenceManager.getRestaurantById(1601994).getAddress().getCity().equals("EDIT TEST"));
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
