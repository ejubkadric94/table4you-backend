package controllers;

import models.Reservation;
import models.Restaurant;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utilities.*;

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
    @Security.Authenticated(UserAuthenticator.class)
    public Result getRestaurantDetails(int id) {
        Restaurant restaurant = RestaurantHelper.getRestaurantById(id);
        if(restaurant == null){
            return badRequest(JsonSerializer.serializeError(Resources.BAD_REQUEST_NO_RESTAURANT));
        }
        return ok(JsonSerializer.serializeRestaurantDetails(restaurant));
    }

    /**
     * Retrieves all restaurants.
     * Optionally, offset and limit, as well as one or more filters, can be specified in request.
     *
     * @param offset the optional offset for pagination
     * @param limit the optional limit for pagination
     * @param filter the optional filter for restaurants
     * @return the response with all restaurants rendered as JSON
     */
    //@Security.Authenticated(UserAuthenticator.class)
    public Result getAllRestaurants(int offset, int limit, String filter) {
        return ok(JsonSerializer.serializeAllRestaurants(offset, limit, filter));
    }

    /**
     * Creates a reservation for a specific restaurant with reservation info.
     * Reservation info should be contained in http request header in JSON form.
     * Method will validate the request before creating reservation, and if validation fails, suitable error will
     * be sent as response.
     *
     * @param id the restaurantId of a restaurant
     * @return the response containing reservation Id
     */
    @Security.Authenticated(UserAuthenticator.class)
    public Result makeReservation(int id){
        Reservation reservation = (Reservation) JsonSerializer.deserialize(request(), Reservation.class);
        if(reservation == null || !reservation.isValid()) {
            return badRequest(JsonSerializer.serializeError(Resources.BAD_REQUEST_INVALID_DATA));
        }

        Restaurant restaurant = RestaurantHelper.getRestaurantById(id);
        if(restaurant == null){
            return badRequest(JsonSerializer.serializeError(Resources.BAD_REQUEST_NO_RESTAURANT));
        }

        manager.createReservation(reservation);
        return created(JsonSerializer.serializeReservation(reservation.getReservationId()));
    }
}
