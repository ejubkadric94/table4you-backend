package ControllerTests;

/**
 * Created by root on 10/02/16.
 */

import com.fasterxml.jackson.databind.JsonNode;
import models.Address;
import models.Token;
import org.apache.commons.codec.digest.DigestUtils;
import models.User;
import org.junit.*;
import play.mvc.Http;
import play.mvc.Result;
import play.twirl.api.Content;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class UserLoginTest {

    @BeforeClass
    public static void prepareUser() {
        running(fakeApplication(),()-> {
            User user = new User();
            user.setEmail("user@test.com");
            user.setPassword(DigestUtils.md5Hex("test"));

            Address address = new Address();
            address.setCity("Test City");
            address.setCountry("Test Country");
            address.setEmail("test@test.com");
            address.setStreetName("Test Street");
            address.save();

            Token token = new Token();
            token.setEmail("user@test.com");
            token.setExpirationDate(Token.generateExpirationDate());
            token.setToken("TESTtestTESTtest");
            token.save();

            user.setAuthToken(token);
            user.setAddress(address);
            user.save();
        });
    }

    @Test
    public void testLoginWithValidInfo(){
        running(fakeApplication(),()-> {
            JsonNode json = play.libs.Json.newObject()
                    .put("email", "user@test.com")
                    .put("password", "test");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/login").bodyJson(json);

            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());

            User user = User.find.where().eq("email", "user@test.com").findUnique();
            assertEquals(user.getPassword(), DigestUtils.md5Hex("test"));

            assertTrue(contentAsString(result).contains(user.getAuthToken().getToken()));
        });
    }


    @Test
    public void testLoginWithWrongEmail(){
        running(fakeApplication(), ()-> {
            JsonNode json = play.libs.Json.newObject()
                    .put("email", "test2@test.com")
                    .put("password", "test");
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/login").bodyJson(json);

            Result result = route(rb);
            assertEquals(Http.Status.BAD_REQUEST, result.status());
            assertTrue(contentAsString(result).contains("error"));
        });
    }

    @Test
    public void testLoginWithWrongPassword(){
        running(fakeApplication(), ()-> {
            JsonNode json = play.libs.Json.newObject()
                    .put("email", "user@test.com")
                    .put("password", "eee");
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/login").bodyJson(json);

            Result result = route(rb);
            assertEquals(Http.Status.UNAUTHORIZED, result.status());
            assertTrue(contentAsString(result).contains("error"));
        });
    }

    @Test
    public void testLoginWithInsufficientParameter(){
        running(fakeApplication(), ()-> {
            JsonNode json = play.libs.Json.newObject()
                    .put("email", "user@test.com");
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/login").bodyJson(json);
            Result result = route(rb);
            assertEquals(Http.Status.BAD_REQUEST, result.status());


            json = play.libs.Json.newObject()
                    .put("password", "user@test.com");
            rb = new Http.RequestBuilder().method(POST).uri("/v1/login").bodyJson(json);
            result = route(rb);
            assertEquals(Http.Status.BAD_REQUEST, result.status());
            assertTrue(contentAsString(result).contains("error"));
        });
    }

    @AfterClass
    public static void removeUser() {
        running(fakeApplication(),()-> {
            User user = User.find.where().eq("email", "user@test.com").findUnique();
            user.delete();
        });
    }

}
