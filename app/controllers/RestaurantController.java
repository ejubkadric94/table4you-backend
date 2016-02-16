package controllers;

import models.Reservation;
import models.Restaurant;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utilities.*;

/**
 * Created by root on 15/02/16.
 */
public class RestaurantController extends Controller{
    private PersistenceManager manager = new PersistenceManager();

    @Security.Authenticated(UserAuthenticator.class)
    public Result getRestaurantDetails(int id) {
        Restaurant restaurant = RestaurantHelper.getRestaurantById(id);
        if(restaurant == null){
            return badRequest(JsonSerializer.serializeError(Resources.BAD_REQUEST_INVALID_DATA));
        }
        return ok(JsonSerializer.serializeRestaurantDetails(restaurant));
    }

    @Security.Authenticated(UserAuthenticator.class)
    public Result getAllRestaurants(int offset, int limit, String filter) {
        return ok(JsonSerializer.serializeAllRestaurants(offset, limit, filter));
    }

    @Security.Authenticated(UserAuthenticator.class)
    public Result makeReservation(int id){
        Restaurant restaurant = RestaurantHelper.getRestaurantById(id);
        Reservation reservation = (Reservation) JsonSerializer.deserialize(request(), Reservation.class);

        if(restaurant == null || reservation == null || !reservation.isValid()) {
            return badRequest(JsonSerializer.serializeError(Resources.BAD_REQUEST_INVALID_DATA));
        }

        manager.createReservation(reservation);
        return created(JsonSerializer.serializeReservation(reservation.getReservationId()));
    }
}
