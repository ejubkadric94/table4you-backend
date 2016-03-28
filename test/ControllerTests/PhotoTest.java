package ControllerTests;


import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import utilities.PersistenceManager;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

/**
 * Created by Ejub on 28.3.2016.
 */
public class PhotoTest {
    @Test
    public void uploadPhotoTest() {
        running(fakeApplication(),()-> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("upload", "D:\\Programiranje\\table4you\\restaurant.jpg");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/7/photos").bodyForm(map);
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

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/444/photos").bodyForm(map);
            Result result = route(rb);

            assertEquals(Http.Status.NOT_FOUND, result.status());
        });
    }

    @Test
    public void uploadPhotoToWithNoUploadForm() {
        running(fakeApplication(),()-> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("something", "D:\\Programiranje\\table4you\\restaurant.jpg");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/444/photos").bodyForm(map);
            Result result = route(rb);

            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }
}
