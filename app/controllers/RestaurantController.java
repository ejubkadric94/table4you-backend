package controllers;

import models.Restaurant;
import play.mvc.Controller;
import play.mvc.Result;
import utilities.Error;
import utilities.JsonSerializer;
import utilities.Resources;
import utilities.RestaurantHelper;

/**
 * Created by root on 15/02/16.
 */
public class RestaurantController extends Controller{
    public Result getRestaurantDetails(int id) {
        Restaurant restaurant = RestaurantHelper.getRestaurantById(id);
        if(restaurant == null){
            return badRequest(JsonSerializer.serialize(new Error(Resources.BAD_REQUEST_NO_RESTAURANT)));
        }
        return ok(JsonSerializer.serializeRestaurantDetails(restaurant));
    }
}
