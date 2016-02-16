package utilities;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.Address;
import models.Coordinates;
import models.Restaurant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 16/02/16.
 */
public class RestaurantSerializer {
    public static String serializeAllDetails(long id){
        Restaurant restaurant = Restaurant.find.where().eq("restaurantId", id).findUnique();
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
    public static String serializeBasicDetails(int offset, int limit, String filter) {
        List<Restaurant> restaurantList = null;

        if(offset != 0 && limit != 0){
            // TO DO
            /*
            List<News> allNews = News.find("order by date desc").from(start).fetch(size);
             */
            restaurantList = Restaurant.find.findPagedList(offset, limit).getList();
        }
        if(filter != null){
            // TO DO
        }


        restaurantList = Restaurant.find.all();

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

/*
[{
restaurantId: <restaurantId2>, name: <name2>, address: { streetName: <streetName2>, city: <city2>, country: <country2> },
phone: <phone2>, workingHours: <workingHours2>, rating: <rating2> },
...]
 */