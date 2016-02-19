package utilities;

import com.google.gson.*;
import models.Restaurant;
import play.libs.Json;
import play.mvc.Http.Request;

/**
 * Created by Ejub on 15/02/16.
 * Class JsonSerializer can be used for serialization and deserialization of input and output.
 * More specifically, it provides methods for:
 *      serialization of Model classes to JSON format
 *      deserialization of JSON into the class specified
 */
public class JsonSerializer {
    /**
     * Deserializes the JSON object from request body.
     *
     * @param request the http request
     * @param classname the name of class to which the JSON Object should be deserialized
     * @return the deserialized object
     */
    public static Object deserialize(Request request, Class classname){
        return Json.fromJson(request.body().asJson(), classname);
    }

    /**
     * Serializes the error message.
     *
     * @param error the String containing the error
     * @return the JSON Object containing error
     */
    public static String serializeError(String error){
        JsonObject object = new JsonObject();
        object.addProperty("error", error);
        return new Gson().toJson(object);
    }

    /**
     * Serializes the reservation.
     *
     * @param reservationId the reservationdId of a Reservation object
     * @return the JSON Object containing reservationId
     */
    public static String serializeReservation(long reservationId){
        JsonObject object = new JsonObject();
        object.addProperty("reservationId", reservationId);
        return new Gson().toJson(object);
    }

    /**
     * Serializes the authentication token.
     *
     * @param token the authentication token
     * @return the JSON Object containing authentication token
     */
    public static String serializeToken(String token){
        JsonObject object = new JsonObject();
        object.addProperty("authToken", token);
        return new Gson().toJson(object);
    }

    /**
     * Serializes the restaurant details.
     *
     * @param restaurant the Restaurant to be serialized
     * @return the JSON containing restaurant details
     */
    public static String serializeRestaurantDetails(Restaurant restaurant) {
        return RestaurantSerializer.serializeAllDetails(restaurant.getRestaurantId());
    }

    /**
     * Serializes all Restaurant objects.
     *
     * @param offset the value used for pagination offset
     * @param limit the value used for pagination limit
     * @param filter the filters string
     * @return the JSON containing restaurants
     */
    public static String serializeAllRestaurants(int offset, int limit, String filter){
        return RestaurantSerializer.serializeBasicDetails(offset, limit, filter);
    }
}
