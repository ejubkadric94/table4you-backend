package controllers;

import models.User;
import play.mvc.*;
import utilities.*;
import play.mvc.BodyParser;

/**
 * Created by Ejub on 31.1.2016.
 * Controller for User routes.
 * Routes specified in this controller do not require authentication.
 */
public class UserController extends Controller {
    private PersistenceManager manager = new PersistenceManager();
    private UserHelper userHelper = new UserHelper();

    /**
     * Registers the user after validating the input.
     * If validation fails, a suitable error will be returned as response.
     *
     * @return the authorization authToken of newly registered user in JSON format
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result register() {
        User user = (User) JsonSerializer.deserialize(request(), User.class);

        if(!user.isValid()){
            return badRequest(JsonSerializer.serializeError(Resources.BAD_REQUEST_INVALID_DATA));
        }
        if(userHelper.ifEmailExists(user)){
            return badRequest(JsonSerializer.serializeError(Resources.BAD_REQUEST_EMAIL_EXISTS));
        }
        manager.createUser(user);
        return ok(JsonSerializer.serializeToken(user.getAuthToken().getToken()));
    }

    /**
     * Confirms the user with specified registration token.
     * Method decodes the token, and checks if specified token is valid, and if it is, the user is confirmed,
     * and otherwise, a suitable error is returned as response.
     *
     * @param registrationToken The encoded registration token
     * @return the authorization token in JSON format
     */
    public Result confirm(String registrationToken){
        User user = new User(registrationToken);
        if(!user.confirmUser(registrationToken)) {
            return badRequest(JsonSerializer.serializeError(Resources.BAD_REQUEST_WRONG_CONFIRMATION_TOKEN));
        }
        return ok(JsonSerializer.serializeToken(user.getAuthToken().getToken()));
    }

    /**
     * Provides the authToken when user logs in.
     * Method validates the input, and if the input is not valid, a suitable error will be returned as response.
     *
     * @return the authorization token in JSON format
     */
    public Result login(){
        UserSession userSession = (UserSession) JsonSerializer.deserialize(request(), UserSession.class);

        if(!userSession.isValid()){
            return badRequest(JsonSerializer.serializeError(Resources.BAD_REQUEST_INVALID_DATA));
        }
        User user = manager.getUserFromSession(userSession);
        if(user == null){
            return unauthorized(JsonSerializer.serializeError(Resources.UNAUTHORIZED_INPUT_DOES_NOT_MATCH));
        }
        return ok(JsonSerializer.serializeToken(user.getAuthToken().getToken()));
    }
}
