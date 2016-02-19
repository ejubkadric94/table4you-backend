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
            Token token = new Token();
            token.generateToken();
            token.setEmail("test@testtest.com");

            User user = new User();
            user.setEmail("test@testtest.com");
            user.setAuthToken(token);
            user.setConfirmed(true);

            token.save();
            user.save();

        });
    }

    @Test
    public void testGetAllRestaurants(){
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri("/v1/restaurants").
                    header("USER-ACCESS-TOKEN", Token.find.where().eq("email", "test@testtest.com").findUnique().
                            getToken());
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
        });
    }

    @AfterClass
    public static void removeRestaurant(){
        running(fakeApplication(),()-> {
            Token token = Token.find.where().eq("email", "test@testtest.com").findUnique();
            User user = User.find.where().eq("email", "test@testtest.com").findUnique();

            user.delete();
            token.delete();
        });
    }
    */
}
