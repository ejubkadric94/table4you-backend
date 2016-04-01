package ControllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.AfterClass;
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
 * Created by Ejub on 28.3.2016.
 */
public class defaultPhoto {
    @Test
    public void testWithInvalidRestaurant() {
        running(fakeApplication(), () -> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/4567/photos/5/default");

            Result result = route(rb);
            assertEquals(Http.Status.NOT_FOUND, result.status());
        });
    }

    @Test
    public void testWithInvalidPhoto(){
        running(fakeApplication(), () -> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/11/photos/5/default");
            Result result = route(rb);
            assertEquals(Http.Status.NOT_FOUND, result.status());
        });
    }

    @Test
    public void testWithValidPhoto(){
        running(fakeApplication(), () -> {
            assertTrue(PersistenceManager.getRestaurantById(11).getPhotos().get(0).isDefault());
            assertTrue(!PersistenceManager.getRestaurantById(11).getPhotos().get(1).isDefault());

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/11/photos/2/default");
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());

            assertTrue(!PersistenceManager.getRestaurantById(11).getPhotos().get(0).isDefault());
            assertTrue(PersistenceManager.getRestaurantById(11).getPhotos().get(1).isDefault());
        });
    }
}
