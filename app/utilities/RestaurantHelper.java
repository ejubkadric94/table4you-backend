package utilities;

import models.Restaurant;

/**
 * Created by Ejub on 15/02/16.
 * Class RestaurantHelper is used to manipulate Restaurant objects.
 */
public class RestaurantHelper {

    /**
     * Retrieves the restaurant based on a restaurantId.
     *
     * @param id the restaurantId
     * @return the Restaurant object
     */
    public static Restaurant getRestaurantById(int id){
        return Restaurant.find.where().eq("restaurantId", id).findUnique();
    }
}
