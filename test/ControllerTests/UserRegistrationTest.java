package ControllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import models.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

/**
 * Created by root on 11/02/16.
 */
public class UserRegistrationTest {

    @Test
    public void testRegistrationWithValidInfo() {
        running(fakeApplication(), () -> {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            ObjectNode child = JsonNodeFactory.instance.objectNode(); // the child
            child.put("streetName", "Test Street");
            child.put("city", "Test City");
            child.put("country", "Test Country");
            node.put("email", "user@test.com");
            node.put("password", "test");
            node.put("passwordConfirmation", "test");
            node.put("firstName", "Test");
            node.put("lastName", "Test");
            node.set("address", child);
            node.put("phone", "062292868");
            node.put("gender", "male");
            node.put("birthdate", "01/01/1990");

            JsonNode json = null;
            try {
                json = (JsonNode) new ObjectMapper().readTree(node.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/registration").bodyJson(json);
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());
            User user = User.find.where().eq("email", "user@test.com").findUnique();
            assertTrue(contentAsString(result).contains(user.getAuthToken().getToken()));
        });
    }


    @Test
    public void testRegistrationWithTakenEmail() {
        running(fakeApplication(), () -> {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            ObjectNode child = JsonNodeFactory.instance.objectNode(); // the child
            child.put("streetName", "Test Street");
            child.put("city", "Test City");
            child.put("country", "Test Country");
            node.put("email", "user@test.com");
            node.put("password", "test");
            node.put("passwordConfirmation", "test");
            node.put("firstName", "Test");
            node.put("lastName", "Test");
            node.set("address", child);
            node.put("phone", "+38762292868");
            node.put("gender", "male");
            node.put("birthdate", "01/01/1990");
            JsonNode json = null;
            try {
                json = (JsonNode) new ObjectMapper().readTree(node.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/registration").bodyJson(json);
            Result result = route(rb);
            assertEquals(Http.Status.OK, result.status());


            ObjectNode node2 = JsonNodeFactory.instance.objectNode();
            ObjectNode child2 = JsonNodeFactory.instance.objectNode(); // the child
            child.put("streetName", "Test Street");
            child.put("city", "Test City");
            child.put("country", "Test Country");
            node.put("email", "user@test.com");
            node.put("password", "test");
            node.put("passwordConfirmation", "test");
            node.put("firstName", "Test");
            node.put("lastName", "Test");
            node.set("address", child);
            node.put("phone", "062292868");
            node.put("gender", "Female");
            node.put("birthdate", "01/01/1990");
            JsonNode json2 = null;
            try {
                json2 = (JsonNode) new ObjectMapper().readTree(node.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Http.RequestBuilder rb2 = new Http.RequestBuilder().method(POST).uri("/v1/registration").bodyJson(json2);
            Result result2 = route(rb2);
            assertEquals(Http.Status.BAD_REQUEST, result2.status());
            assertTrue(contentAsString(result2).contains("error"));

        });
    }

    @Test
    public void testRegistrationWithInvalidInfo() {
        running(fakeApplication(), () -> {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            ObjectNode child = JsonNodeFactory.instance.objectNode(); // the child
            child.put("streetName", "Test Street");
            child.put("city", "Test City");
            child.put("country", "Test Country");
            node.put("email", "user@test.com");
            node.put("password", "test");
            node.put("passwordConfirmation", "test");
            node.put("firstName", "test");
            node.put("lastName", "Test");
            node.set("address", child);
            node.put("phone", "+38762292868");
            node.put("gender", "Male");
            node.put("birthdate", "01/01/1990");

            JsonNode json = null;
            try {
                json = (JsonNode) new ObjectMapper().readTree(node.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/registration").bodyJson(json);
            Result result = route(rb);
            assertEquals(Http.Status.BAD_REQUEST, result.status());
            assertTrue(contentAsString(result).contains("error"));
        });
    }

    @After
    public void deleteRegisteredUser() {
        running(fakeApplication(), () -> {
            User user = User.find.where().eq("email", "user@test.com").findUnique();
            if(user != null){
                user.delete();
            }
        });
    }

}
