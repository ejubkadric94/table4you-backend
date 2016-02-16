package utilities;


import com.google.gson.*;
import models.Reservation;
import models.Restaurant;
import play.libs.Json;
import play.mvc.Http.Request;


/**
 * Created by root on 15/02/16.
 */
public class JsonSerializer {
    public static Object deserialize(Request request, Class classname){
        return Json.fromJson(request.body().asJson(), classname);
    }

    public static String serializeError(String error){
        JsonObject object = new JsonObject();
        object.addProperty("error", error);
        return new Gson().toJson(object);
    }

    public static String serializeReservation(long reservation){
        JsonObject object = new JsonObject();
        object.addProperty("reservationId", reservation);
        return new Gson().toJson(object);
    }

    public static String serializeToken(String token){
        JsonObject object = new JsonObject();
        object.addProperty("authToken", token);
        return new Gson().toJson(object);
    }

    public static String serializeRestaurantDetails(Restaurant restaurant) {
        return RestaurantSerializer.serializeAllDetails(restaurant.getRestaurantId());
    }


    public static String serializeAllRestaurants(int offset, int limit, String filter){
        return RestaurantSerializer.serializeBasicDetails(offset, limit, filter);
    }



}
