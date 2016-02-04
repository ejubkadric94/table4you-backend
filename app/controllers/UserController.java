package controllers;

/**
 * Created by Ejub on 31.1.2016.
 */


import models.User;
import play.libs.Json;
import play.mvc.*;
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
        UserHelper userHelper = new UserHelper(request().body().asJson());

        if(!userHelper.initializeUser()){
            return badRequest(index.render(Resources.BAD_REQUEST_COULD_NOT_INITIALIZE));
        }
        if(!userHelper.validateUser()){
            return badRequest(index.render(Resources.BAD_REQUEST_INVALID_DATA));
        }

        userHelper.createUser();

        //response().setContentType("application/json");
        return ok(index.render( "{\"authToken\":\"" + userHelper.getUser().getAuthToken().getToken() + "\"}"));
    }


    public Result confirm(String registrationToken){

        //UserHelper userHelper = new UserHelper();
        //if(!userHelper.confirmUser(registrationToken))
        //    return badRequest(index.render(Resources.BAD_REQUEST_WRONG_CONFIRMATION_TOKEN));

        User user = User.find.where().eq("authToken", registrationToken).findUnique();
        return ok(index.render("{\"authToken\":\"" + user.getAuthToken().getToken()));
    }

    public Result login(){
        JsonNode json = Json.parse(request().body().asJson().asText());


        //if(UserHelper.isValidLoginInfo(json))
        return ok();
    }
}

