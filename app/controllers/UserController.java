package controllers;

/**
 * Created by Ejub on 31.1.2016.
 */

import models.User;
import play.libs.Json;
import play.mvc.*;
import utilities.JsonOutput;
import utilities.Resources;
import utilities.UserHelper;
import views.html.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.BodyParser;


public class UserController extends Controller {
    /**
     * Registers the user
     * @return the authorization authToken in JSON format
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result register() {
        if(UserHelper.ifEmailExists(request().body().asJson())){
            return badRequest(jsonOutput.render(JsonOutput.getError(Resources.BAD_REQUEST_EMAIL_EXISTS)));
        }
        UserHelper userHelper = new UserHelper(request().body().asJson());
        if(!userHelper.initializeUser()){
            return badRequest(jsonOutput.render(JsonOutput.getError(Resources.BAD_REQUEST_COULD_NOT_INITIALIZE)));
        }
        if(!userHelper.validateUser()){
            return badRequest(jsonOutput.render(JsonOutput.getError(Resources.BAD_REQUEST_INVALID_DATA)));
        }
        userHelper.createUser();
        return ok(jsonOutput.render(JsonOutput.getAuthToken(userHelper)));
    }


    public Result confirm(String registrationToken){
        if(UserHelper.confirmUser(registrationToken)) {
            return ok(jsonOutput.render(JsonOutput.getAuthToken(registrationToken)));
        }
        return badRequest(jsonOutput.render(JsonOutput.getError(Resources.BAD_REQUEST_WRONG_CONFIRMATION_TOKEN)));
    }

    public Result login(){
        String email = request().body().asJson().path("email").asText();
        String password = request().body().asJson().path("password").asText();
        if( email == "" ||
                password == "") {
            return badRequest(jsonOutput.render(JsonOutput.getError(Resources.BAD_REQUEST_INSUFFICIENT_DATA)));
        }
        if( UserHelper.isValidLoginInfo(email, password)) {
            return ok(jsonOutput.render(JsonOutput.getAuthToken(email, password)));
        }
        return unauthorized(jsonOutput.render(JsonOutput.getError(Resources.UNAUTHORIZED_INPUT_DOES_NOT_MATCH)));
    }
}

