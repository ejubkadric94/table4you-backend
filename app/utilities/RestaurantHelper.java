package utilities;

import java.util.*;

/**
 * Created by Ejub on 15/02/16.
 * Class RestaurantHelper is used to manipulate Restaurant objects.
 */
public class RestaurantHelper {
    private long restaurantId;
    /**
     * Produces HashMap of filter keys and filter values.
     *
     * @param rawFilter the String containing unprocessed filters
     * @return the processed filters in form of HashMap
     */
    public static HashMap<String, String> getFiltersMap(String rawFilter) {
        HashMap<String, String> map = new HashMap<>();
        String[] array = rawFilter.split(",");
        for(String element : array){
            String[] tempElement = element.split(":");
            map.put(tempElement[0], tempElement[1]);
        }
        return map;
    }

    /**
     * Validates the Url parameters specified for restaurants.
     *
     * @param filter the filter
     * @param order the order
     * @return true if parameters are valid
     */
    public static boolean validateRestaurantUrlParameters(String filter, String order){
        if(order != null && filter != null){
            boolean returnValue = order.contains("name") || order.contains("rating") || order.contains("reservationPrice") ||
                    order.contains("streetName") || order.contains("city") || order.contains("country") ||
                    order.contains("phone");
            if(returnValue == false){
                return false;
            }
            Map<String, String> filtersMap = RestaurantHelper.getFiltersMap(filter);
            for(String key : filtersMap.keySet()){
                returnValue = key.contains("name") || key.contains("rating") || key.contains("reservationPrice") ||
                        key.contains("streetName") || key.contains("city") || key.contains("country");
            }
            return returnValue;
        } else if(order != null){
            return order.contains("name") || order.contains("rating") || order.contains("reservationPrice") ||
                    order.contains("streetName") || order.contains("city") || order.contains("country") ||
                    order.contains("phone");
        } else if(filter != null){
            Map<String, String> filtersMap = RestaurantHelper.getFiltersMap(filter);
            for(String key : filtersMap.keySet()){
                return key.contains("name") || key.contains("rating") || key.contains("reservationPrice") ||
                        key.contains("streetName") || key.contains("city") || key.contains("country");
            }
        }
        return true;
    }

    public RestaurantHelper(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
