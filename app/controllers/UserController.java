package controllers;

/**
 * Created by Ejub on 31.1.2016.
 */
import play.api.libs.json.JsPath;
import play.mvc.*;
import views.html.*;
import utilities.Email;
import play.api.libs.json.Json;
import play.api.libs.*;
import play.data.validation.Validation;
import play.data.validation.ValidationError;
import play.data.validation.Constraints;
import play.api.data.validation.*;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.BodyParser;

import models.Users;
public class UserController extends Controller {
    /**
     * Registers the user
     * @return
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result register(String jsonInput) {

        JsonNode json2 = request().body().asJson();
        String name = json2.findPath("name").textValue();
        String email = json2.findPath("email").textValue();
        String surname = json2.findPath("surname").textValue();
        String password = json2.findPath("password").textValue();
        boolean isConfirmed = false;

        Users user = new Users();
        user.email = email;
        user.name = name;
        user.surname = surname;
        user.password = password;

        user.save();
/*
        if(name == null) {
            return badRequest("Missing parameter [name]");
        } else {
            return ok("Hello " + name);
        }
*/
        //Email.sendConfirmationEmail("ejubkadric@gmail.com", "link");

        //response().setContentType("application/json");
        return ok(index.render(name));
    }

    /**
     * Function sends confirmation mail with activation link using Email utility class
     * @param emailAddress email of user
     */
    private void sendConfirmationMail(String emailAddress){

        Email.sendConfirmationEmail(emailAddress, "blabla");
    }

    public Result test(String t){

        return ok(index.render(t+" is the message"));
    }
}

