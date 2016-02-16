package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import models.Address;
import models.Coordinates;
import models.Restaurant;
import play.libs.Json;
import play.mvc.Http.Request;

import java.io.IOException;

/**
 * Created by root on 15/02/16.
 */
public class JsonSerializer {
    public static Object deserialize(Request request, Class classname){
        return Json.fromJson(request.body().asJson(), classname);
    }

    public static String serialize(Error error){
        return new Gson().toJson(error);
    }

    public static String serialize(TokenHelper token){
        return new Gson().toJson(token);
    }

    public static String serializeRestaurantDetails(Restaurant restaurant) {
        restaurant.setCoordinates(Coordinates.find.where().eq("restaurantId", restaurant.getRestaurantId()).findUnique());
        restaurant.setAddress(Address.find.where().eq("restaurantId", restaurant.getRestaurantId()).findUnique());

        ObjectNode restaurantJson = JsonNodeFactory.instance.objectNode();
        ObjectNode address = JsonNodeFactory.instance.objectNode();
        ObjectNode coordinates = JsonNodeFactory.instance.objectNode();

        address.put("streetName",restaurant.getAddress().getStreetName());
        address.put("city",restaurant.getAddress().getCity());
        address.put("country",restaurant.getAddress().getCountry());

        coordinates.put("latitude", restaurant.getCoordinates().getLatitude());
        coordinates.put("longitude", restaurant.getCoordinates().getLongitude());

        restaurantJson.put("restaurantId", restaurant.getRestaurantId());
        restaurantJson.put("name", restaurant.getName());
        restaurantJson.set("address", address);
        restaurantJson.put("phone", restaurant.getPhone());
        restaurantJson.put("workingHours", restaurant.getWorkingHours());
        restaurantJson.put("rating", restaurant.getRating());
        restaurantJson.put("reservationPrice", restaurant.getReservationPrice());
        restaurantJson.put("deals", restaurant.getDeals());
        restaurantJson.set("coordinates", coordinates);

        return restaurantJson.toString();
    }

    public static String serializeAllRestaurants(){
        return null;
    }
}
