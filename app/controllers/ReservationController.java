package controllers;

import models.Reservation;
import models.Restaurant;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utilities.*;
import utilities.Error;

import java.util.List;

/**
 * Created by Ejub on 9.3.2016.
 */
public class ReservationController extends Controller {
    PersistenceManager manager = new PersistenceManager();
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
        response().setContentType("application/json");
        Reservation reservation = (Reservation) JsonSerializer.deserialize(request(), Reservation.class);
        if(reservation == null || !reservation.isValid()) {
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.BAD_REQUEST_INVALID_DATA)));
        }

        Restaurant restaurant = PersistenceManager.getRestaurantById(id);
        if(restaurant == null){
            return notFound(JsonSerializer.serializeObject(new Error(Resources.NO_RESTAURANT)));
        }

        manager.createReservation(reservation, restaurant);
        return created(JsonSerializer.serializeObject(new ReservationHelper(reservation.getReservationId())));
    }

    public Result getAllReservations(int id) {
        Restaurant restaurant = PersistenceManager.getRestaurantById(id);
        if(restaurant == null){
            return notFound(JsonSerializer.serializeObject(new Error(Resources.NO_RESTAURANT)));
        }
        List<Reservation> list = PersistenceManager.getAllReservations(id);
        return ok(JsonSerializer.serializeObject(list));
    }
}
