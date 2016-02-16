package utilities;


import com.google.gson.*;
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

    public static String serialize(Error error){
        return new Gson().toJson(error);
    }

    public static String serialize(TokenHelper token){
        return new Gson().toJson(token);
    }

    public static String serializeRestaurantDetails(Restaurant restaurant) {
        return RestaurantSerializer.serializeAllDetails(restaurant.getRestaurantId());
    }


    public static String serializeAllRestaurants(int offset, int limit, String filter){
        return RestaurantSerializer.serializeBasicDetails(offset, limit, filter);
    }



}
