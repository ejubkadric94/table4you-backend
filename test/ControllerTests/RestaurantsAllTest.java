package ControllerTests;

import models.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * Created by root on 18/02/16.
 */
public class RestaurantsAllTest {
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
    public void testGetAllRestaurantsWithNoUrlParameters(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithWrongParameters(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?order=doesntExist");
            Result result = route(rb);
            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithLimit(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?offset=0&limit=5");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithOrder(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?order=name");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithReverseOrder(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?order=-name");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithFilter(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?filter=name:Test%20restaurant");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithLimitAndOrder(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?offset=0&limit=5&order=name");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithLimitAndInverseOrder(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?offset=0&limit=5&order=-name");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithLimitAndFilter(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?offset=0&limit=5&filter=name:Test%20restaurant");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithOrderAndFilter(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?order=rating&filter=name:Test%20restaurant");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithReverseOrderAndFilter(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?order=-rating&filter=name:Test%20restaurant");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithAllUrlParameters(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?offset=0&limit=10&order=rating&filter=name:Test%20restaurant");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @Test
    public void testGetAllRestaurantsWithInverseUrlParameters(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?offset=0&limit=10&order=-rating&filter=name:Test%20restaurant");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }


/*

    @Test
    public void testGetAllRestaurantsWithParamete(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants?order=doesntExist");
            Result result = route(rb);
            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }

*/

/*

    @AfterClass
    public static void removeRestaurant(){
        running(fakeApplication(),()-> {
            Restaurant restaurant = Restaurant.find.where().eq("restaurantId", 1601994).findUnique();
            restaurant.delete();
        });
    }
*/
}
