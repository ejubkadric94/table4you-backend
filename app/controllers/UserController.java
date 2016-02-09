package controllers;

/**
 * Created by Ejub on 31.1.2016.
 */

import com.google.gson.Gson;
import models.User;
import play.libs.Json;
import play.mvc.*;
import utilities.*;
import utilities.Error;
import views.html.*;
import play.mvc.BodyParser;

/**
 * Controller for User model.
 */
public class UserController extends Controller {
    /**
     * Registers the user after validating the input.
     *
     * @return the authorization authToken in JSON format
     */
    private PersistenceManager manager = new PersistenceManager();
    private UserHelper userHelper = new UserHelper();


    @BodyParser.Of(BodyParser.Json.class)
    public Result register() {
        User user = Json.fromJson(request().body().asJson(), User.class);

        if(userHelper.ifEmailExists(user)){
            return badRequest(new Gson().toJson(new Error(Resources.BAD_REQUEST_EMAIL_EXISTS)));
        }
        if(!userHelper.initializeUser(user)){
            return badRequest(new Gson().toJson(new Error(Resources.BAD_REQUEST_COULD_NOT_INITIALIZE)));
        }
        if(!user.isValid()){
            return badRequest(new Gson().toJson(new Error(Resources.BAD_REQUEST_INVALID_DATA)));
        }

        manager.createUser(user);
        return ok(jsonOutput.render(new Gson().toJson(new TokenHelper(user.getAuthToken().getToken()))));
    }

    /**
     * Confirms the user with specified registration token.
     * Method checks if specified token is valid, and if it is, the user is confirmed.
     *
     * @param registrationToken The encoded registration token
     * @return The authorization token in JSON format, or badRequest if token is not valid.
     */
    public Result confirm(String registrationToken){
        User user = new User(registrationToken);
        if(!user.confirmUser(registrationToken)) {
            return badRequest(new Gson().toJson(new Error(Resources.BAD_REQUEST_WRONG_CONFIRMATION_TOKEN)));
        }

        return ok(jsonOutput.render(new Gson().toJson(new TokenHelper(user.getAuthToken().getToken()))));
    }

    /**
     * Provides the authToken when user logs in.
     * If the input information are related to existing user, and if information is valid, the authToken will be
     * returned.
     *
     * @return JSON which contains authToken is returned if successful.
     */
    public Result login(){
         UserSession userSession = play.libs.Json.fromJson(request().body().asJson(), UserSession.class);

        User user = manager.getUserFromSession(userSession);

        if(user == null){
            return unauthorized(new Gson().toJson(new Error(Resources.UNAUTHORIZED_NO_EMAIL)));
        }
        if( !user.isValidLoginInfo()) {
            return unauthorized(new Gson().toJson(new Error(Resources.UNAUTHORIZED_INPUT_DOES_NOT_MATCH)));
        }

        return ok(jsonOutput.render(new Gson().toJson(new TokenHelper(user.getAuthToken().getToken()))));
    }
}

