package controllers;

import models.Restaurant;
import models.Review;
import play.mvc.Controller;
import play.mvc.Result;
import utilities.Error;
import utilities.JsonSerializer;
import utilities.PersistenceManager;
import utilities.Resources;

import java.util.List;

/**
 * Created by Ejub on 13.3.2016.
 */
public class ReviewController extends Controller {
    public Result addReview(int restaurantId) {
        Review review = (Review) JsonSerializer.deserialize(request(), Review.class);
        if(!review.isValid()){
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.BAD_REQUEST_INVALID_DATA)));
        }

        Restaurant restaurant = PersistenceManager.getRestaurantById(restaurantId);
        if(restaurant == null) {
            return notFound(JsonSerializer.serializeObject(new Error(Resources.NO_RESTAURANT)));
        }
        restaurant.addReview(review);
        return ok();
    }

    public Result getReviews(int restaurantId){
        response().setContentType("application/json");
        Restaurant restaurant = PersistenceManager.getRestaurantById(restaurantId);
        if(restaurant == null) {
            return notFound(JsonSerializer.serializeObject(new Error(Resources.NO_RESTAURANT)));
        }
        List<Review> list = restaurant.getReviews();
        return ok(JsonSerializer.serializeBasicDetails(list));
    }
}
