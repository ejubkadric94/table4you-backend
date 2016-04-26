package controllers;

import models.User;
import play.mvc.*;
import utilities.*;
import play.mvc.BodyParser;
import utilities.Error;

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
        response().setContentType("application/json");
        User user = (User) JsonSerializer.deserialize(request(), User.class);
        if(!user.isValid()){
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.BAD_REQUEST_INVALID_DATA)));
        }
        if(userHelper.ifEmailExists(user)){
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.BAD_REQUEST_EMAIL_EXISTS)));
        }
        manager.createUser(user);
        return ok(JsonSerializer.serializeObject(user.getAuthToken()));
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
        response().setContentType("application/json");
        User user = new User(registrationToken);
        if(!user.confirmUser(registrationToken)) {
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.BAD_REQUEST_WRONG_CONFIRMATION_TOKEN)));
        }
        return ok(JsonSerializer.serializeObject(user.getAuthToken()));
    }

    /**
     * Provides the authentication token when user logs in.
     * Method validates the input, and if the input is not valid, a suitable error will be returned as response.
     *
     * @return the authorization token in JSON format
     */
    public Result login(){
        response().setContentType("application/json");
        UserSession userSession = (UserSession) JsonSerializer.deserialize(request(), UserSession.class);
        if(!userSession.isValid()){
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.BAD_REQUEST_INVALID_DATA)));
        }
        User user = manager.getUserFromSession(userSession);
        if(user == null){
            return unauthorized(JsonSerializer.serializeObject(new Error(Resources.UNAUTHORIZED_INPUT_DOES_NOT_MATCH)));
        }
        return ok(JsonSerializer.serializeObject(user.getAuthToken()));
    }

    /**
     * Retrieves information about the logged user.
     *
     * @return user information in JSON format or a suitable error if operation is unsuccessful
     */
    @Security.Authenticated(UserAuthenticator.class)
    public Result getCurrentUser(){
        response().setContentType("application/json");
        User user = PersistenceManager.getUserFromRequest(request());
        if(user == null) {
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.BAD_REQUEST_INVALID_DATA)));
        }
        return ok(JsonSerializer.serializeBasicDetails(user));
    }

    /**
     * Retrieves favourite restaurants of the logged user.
     *
     * @return Favourite restaurants in JSON form or a suitable error if operation is unsuccessful
     */
    @Security.Authenticated(UserAuthenticator.class)
    public Result getFavouriteRestaurants(){
        User user =(User) Http.Context.current().args.get("CurrentUser");
        return ok(JsonSerializer.serializeObject(user.getFavouriteRestaurants()));
    }
}
