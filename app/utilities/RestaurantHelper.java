package utilities;

import java.util.*;

/**
 * Created by Ejub on 15/02/16.
 * Class RestaurantHelper is used to manipulate Restaurant objects.
 */
public class RestaurantHelper {

    public static HashMap<String, String> getFiltersMap(String rawFilter) {
        HashMap<String, String> map = new HashMap<>();

        String[] array = rawFilter.split(",");
        for(String element : array){
            String[] tempElement = element.split(":");
            map.put(tempElement[0], tempElement[1]);
        }
        return map;
    }

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
                System.out.println("KEY IS "+key);
                System.out.println(key);
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
                System.out.println(key);
                return key.contains("name") || key.contains("rating") || key.contains("reservationPrice") ||
                        key.contains("streetName") || key.contains("city") || key.contains("country");
            }
        }
        return true;
    }
}
