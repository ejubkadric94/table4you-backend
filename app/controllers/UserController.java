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

        User user = (User) JsonSerializer.deserialize(request(), User.class);

        if(!user.isValid()){
            return badRequest(JsonSerializer.serialize(new Error(Resources.BAD_REQUEST_INVALID_DATA)));
        }
        if(userHelper.ifEmailExists(user)){
            return badRequest(JsonSerializer.serialize(new Error(Resources.BAD_REQUEST_EMAIL_EXISTS)));
        }
        manager.createUser(user);
        return ok(JsonSerializer.serialize(new TokenHelper(user.getAuthToken().getToken())));
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
            return badRequest(JsonSerializer.serialize(new Error(Resources.BAD_REQUEST_WRONG_CONFIRMATION_TOKEN)));
        }
        return ok(JsonSerializer.serialize(new TokenHelper(user.getAuthToken().getToken())));
    }

    /**
     * Provides the authToken when user logs in.
     * If the input information are related to existing user, and if information is valid, the authToken will be
     * returned.
     *
     * @return JSON which contains authToken is returned if successful.
     */
    public Result login(){
        UserSession userSession = (UserSession) JsonSerializer.deserialize(request(), UserSession.class);

        if(!userSession.isValid()){
            return badRequest(JsonSerializer.serialize(new Error(Resources.BAD_REQUEST_INVALID_DATA)));
        }
        User user = manager.getUserFromSession(userSession);
        if(user == null){
            return unauthorized(JsonSerializer.serialize(new Error(Resources.UNAUTHORIZED_INPUT_DOES_NOT_MATCH)));
        }
        return ok(JsonSerializer.serialize(new TokenHelper(user.getAuthToken().getToken())));
    }
}
