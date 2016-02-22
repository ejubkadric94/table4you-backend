package utilities;

import com.avaje.ebean.RawSql;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Address;
import models.Coordinates;
import models.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ejub on 16/02/16.
 * Class RestaurantSerializer can be used for serializing Restaurant objects.
 * It provides methods for restaurant serialization specifically.
 */
public class RestaurantSerializer {
    /**
     * Serializes all details of a specified Restaurant.
     *
     * @param id the restaurantId
     * @return the JSON string containing a serialized Restaurant
     */
    public static String serializeAllDetails(long id){
        Restaurant restaurant = PersistenceManager.getRestaurantById(id);
        restaurant.setCoordinates(PersistenceManager.getCoordinatesOfRestaurant(restaurant));
        restaurant.setAddress(PersistenceManager.getAddressOfRestaurant(restaurant));

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

    /**
     * Serializes the basic details of all restaurants, using provided pagination offset and limit, and/or
     * provided filter.
     *
     * @param offset the pagination offset
     * @param limit the pagination limit
     * @param filter the pagination filter
     * @return the JSON string
     */
    public static String serializeBasicDetails(int offset, int limit, String filter) {
        List<Restaurant> restaurantList = PersistenceManager.getAllRestaurantList();

        if(limit != 0 ){
            restaurantList = Restaurant.find.findPagedList(offset, limit).getList();
        }
        if(filter != null){
            List<String> list = new ArrayList<String>(Arrays.asList(filter.split(",")));

        }

        String json = "[";

        int restaurantCounter = restaurantList.size();
        for(Restaurant restaurant : restaurantList){
            restaurant.setCoordinates(Coordinates.find.where().eq("restaurantId", restaurant.getRestaurantId()).findUnique());
            restaurant.setAddress(Address.find.where().eq("restaurantId", restaurant.getRestaurantId()).findUnique());

            ObjectNode restaurantJson = JsonNodeFactory.instance.objectNode();
            ObjectNode address = JsonNodeFactory.instance.objectNode();

            address.put("streetName",restaurant.getAddress().getStreetName());
            address.put("city",restaurant.getAddress().getCity());
            address.put("country",restaurant.getAddress().getCountry());

            restaurantJson.put("restaurantId", restaurant.getRestaurantId());
            restaurantJson.put("name", restaurant.getName());
            restaurantJson.set("address", address);
            restaurantJson.put("phone", restaurant.getPhone());
            restaurantJson.put("workingHours", restaurant.getWorkingHours());
            restaurantJson.put("rating", restaurant.getRating());

            json += (restaurantJson.toString());

            if(restaurantCounter != 1){
                json += (",");
            }
            restaurantCounter--;
        }
        json += ("]");
        return json;
    }
}

