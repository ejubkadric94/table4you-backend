package controllers;

import models.Restaurant;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utilities.*;
import utilities.Error;

/**
 * Created by root on 15/02/16.
 */
public class RestaurantController extends Controller{

    @Security.Authenticated(UserAuthenticator.class)
    public Result getRestaurantDetails(int id) {
        Restaurant restaurant = RestaurantHelper.getRestaurantById(id);
        if(restaurant == null){
            return badRequest(JsonSerializer.serialize(new Error(Resources.BAD_REQUEST_NO_RESTAURANT)));
        }
        return ok(JsonSerializer.serializeRestaurantDetails(restaurant));
    }

    @Security.Authenticated(UserAuthenticator.class)
    public Result getAllRestaurants(int offset, int limit, String filter) {
        return ok(JsonSerializer.serializeAllRestaurants(offset, limit, filter));
    }
}
