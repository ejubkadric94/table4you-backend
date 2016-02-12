package ControllerTests;

import models.Address;
import models.Token;
import models.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import utilities.UserHelper;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;
import static org.junit.Assert.*;

/**
 * Created by root on 11/02/16.
 */
public class UserConfirmTest {

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
            user.setConfirmed(false);

            //System.out.println("CONFIRM    "+user.isConfirmed());

            user.setAuthToken(token);
            user.setAddress(address);
            user.save();
        });
    }

    @Test
    public void testConfirmationWithValidToken() {
        running(fakeApplication(),()-> {
            User user = User.find.where().eq("email", "test@test.com").findUnique();
            assertFalse(user.isConfirmed());

            String link = "/v1/registration/confirm/" + UserHelper.encodeToken(user.getAuthToken().getToken());
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri(link);

            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
            user = User.find.where().eq("email", "test@test.com").findUnique();
            assertTrue( user.isConfirmed());
        });
    }

    @Test
    public void testConfirmationWithInvalidToken() {
        running(fakeApplication(),()-> {
            Token token = new Token();
            token.generateToken();
            String link = "/v1/registration/confirm/" + UserHelper.encodeToken(token.getToken());
            Http.RequestBuilder rb = new Http.RequestBuilder().method(GET).uri(link);

            Result result = route(rb);
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
