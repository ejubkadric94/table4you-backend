package utilities;

import models.Restaurant;

/**
 * Created by root on 15/02/16.
 */
public class RestaurantHelper {
    public static Restaurant getRestaurantById(int id){
        return Restaurant.find.where().eq("restaurantId", id).findUnique();
    }
}
