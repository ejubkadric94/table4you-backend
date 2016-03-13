package controllers;

import models.Restaurant;
import play.mvc.Controller;
import play.mvc.Result;
import utilities.*;
import utilities.Error;

import java.util.List;

/**
 * Created by Ejub on 15/02/16.
 * Controller for restaurant routes.
 * All routes in this controller require authentication.
 */
public class RestaurantController extends Controller{
    private PersistenceManager manager = new PersistenceManager();

    /**
     * Retrieves details of a restaurant specified by Id.
     * Method checks if restaurant exists, and then proceeds furhter if restaurant is found, or an error is thrown.
     *
     * @param id the restaurantId
     * @return Returns a response with restaurant details rendered as JSON
     */
    public Result getRestaurantDetails(int id) {
        response().setContentType("application/json");
        Restaurant restaurant = PersistenceManager.getRestaurantById(id);
        if(restaurant == null){
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.NO_RESTAURANT)));
        }
        return ok(JsonSerializer.serializeAllDetails(restaurant));
    }

    /**
     * Retrieves all restaurants.
     * Multiple parameters might be used, and they are all optional, which means that any parameter does not depend on
     * others.
     *
     * @param offset the optional offset for pagination
     * @param limit the optional limit for pagination
     * @param filter the optional filter for restaurants
     * @param order the optional order for restaurants (place minus sign in front of order value to get descending
     *              results).
     * @return the response with all restaurants rendered as JSON
     */
    public Result getAllRestaurants(int offset, int limit, String filter,String order) {
        response().setContentType("application/json");
        if(!RestaurantHelper.validateRestaurantUrlParameters(filter, order)){
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.BAD_REQUEST_INVALID_PARAMETERS)));
        }
        List<Restaurant> restaurantList = PersistenceManager.getAllRestaurants(offset, limit, filter, order);
        return ok(JsonSerializer.serializeBasicDetails(restaurantList));
    }


}
