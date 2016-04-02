package ControllerTests;

import models.Restaurant;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import utilities.PersistenceManager;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;
import static play.test.Helpers.POST;
import static play.test.Helpers.route;

/**
 * Created by Ejub on 2.4.2016.
 */
public class uploadMenuTest {
    @Test
    public void uploadMenuTest() {
        running(fakeApplication(),()-> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("upload", "D:\\Programiranje\\table4you\\restaurant.jpg");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/7/menu").bodyForm(map);
            Result result = route(rb);

            Restaurant restaurant = PersistenceManager.getRestaurantById(7);

            assertEquals(Http.Status.OK, result.status());
            assertEquals(restaurant.getPhotos().size(),1);
        });
    }

    @Test
    public void uploadPhotoToInvalidRestaurant() {
        running(fakeApplication(),()-> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("upload", "D:\\Programiranje\\table4you\\restaurant.jpg");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/444/menu").bodyForm(map);
            Result result = route(rb);

            assertEquals(Http.Status.NOT_FOUND, result.status());
        });
    }

    @Test
    public void uploadPhotoToWithNoUploadForm() {
        running(fakeApplication(),()-> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("something", "D:\\Programiranje\\table4you\\restaurant.jpg");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/6/menu").bodyForm(map);
            Result result = route(rb);

            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }
}
