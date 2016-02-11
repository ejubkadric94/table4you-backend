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
import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class UserLoginTest {

    @BeforeClass
    public static void prepareUser() {
        running(fakeApplication(),()-> {
            User user = new User();
            user.setEmail("test@test.com");
            user.setPassword(DigestUtils.md5Hex("test"));

            Address address = new Address();
            address.setCity("Test City");
            address.setCountry("Test Country");
            address.setEmail("test@test.com");
            address.setStreetName("Test Street");
            address.save();

            Token token = new Token();
            token.setEmail("test@test.com");
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
                    .put("email", "test@test.com")
                    .put("password", "test");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/login").bodyJson(json);

            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
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
            assertEquals(Http.Status.UNAUTHORIZED, result.status());
        });
    }

    @Test
    public void testLoginWithWrongPassword(){
        running(fakeApplication(), ()-> {
            JsonNode json = play.libs.Json.newObject()
                    .put("email", "test@test.com")
                    .put("password", "eee");
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/login").bodyJson(json);

            Result result = route(rb);
            assertEquals(Http.Status.UNAUTHORIZED, result.status());
        });
    }

    @Test
    public void testLoginWithInsufficientParameter(){
        running(fakeApplication(), ()-> {
            JsonNode json = play.libs.Json.newObject()
                    .put("email", "test@test.com");
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/login").bodyJson(json);
            Result result = route(rb);
            assertEquals(Http.Status.BAD_REQUEST, result.status());


            json = play.libs.Json.newObject()
                    .put("password", "test@test.com");
            rb = new Http.RequestBuilder().method(POST).uri("/v1/login").bodyJson(json);
            result = route(rb);
            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }

    @AfterClass
    public static void removeUser() {
        running(fakeApplication(),()-> {
            User user = User.find.where().eq("email", "test@test.com").findUnique();
            user.delete();
            user.getAddress().delete();
            user.getAuthToken().delete();
        });
    }

}
