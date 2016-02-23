package utilities;

import models.Restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        return PersistenceManager.getRestaurantById(id);
    }

    public static void filterRestaurants(List<Restaurant> restaurantList, String filterName, String filterValue){
        List<Restaurant> newList = new ArrayList<Restaurant>();
        //restaurantLi
    }

    public static void sortRestaurants(List<Restaurant> restaurantList, String order) {

        if(order.contains("name")){
            if(order.contains("-")) {
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    public int compare(Restaurant s1, Restaurant s2) {
                        String name1 = s1.getName().toUpperCase();
                        String name2 = s2.getName().toUpperCase();
                        //descending order
                        return name2.compareTo(name1);
                    }});
            }
            else {
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    public int compare(Restaurant s1, Restaurant s2) {
                        String name1 = s1.getName().toUpperCase();
                        String name2 = s2.getName().toUpperCase();
                        //ascending order
                        return name1.compareTo(name2);
                    }});
            }
        }

        else if(order.contains("rating")){
            if(order.contains("-")){
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2) {
                        return Double.compare(r2.getRating(), r1.getRating());
                    }
                });
            }
            else {
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2) {
                        return Double.compare(r1.getRating(), r2.getRating());
                    }
                });
            }
        }

        else if(order.contains("reservationPrice")){
            if(order.contains("-")){
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2) {
                        return Double.compare(r2.getReservationPrice(), r1.getReservationPrice());
                    }
                });
            }
            else {
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2) {
                        return Double.compare(r1.getReservationPrice(), r2.getReservationPrice());
                    }
                });
            }
        }

        else if(order.contains("country")){
            if(order.contains("-")){
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    public int compare(Restaurant s1, Restaurant s2) {
                        String name1 = s1.getAddress().getCountry().toUpperCase();
                        String name2 = s2.getAddress().getCountry().toUpperCase();
                        //descending order
                        return name2.compareTo(name1);
                    }});
            } else {
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    public int compare(Restaurant s1, Restaurant s2) {
                        String name1 = s1.getAddress().getCountry().toUpperCase();
                        String name2 = s2.getAddress().getCountry().toUpperCase();
                        //descending order
                        return name1.compareTo(name2);
                    }});
            }
        }

        else if(order.contains("city")){
            if(order.contains("-")){
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    public int compare(Restaurant s1, Restaurant s2) {
                        String name1 = s1.getAddress().getCity().toUpperCase();
                        String name2 = s2.getAddress().getCity().toUpperCase();
                        //descending order
                        return name2.compareTo(name1);
                    }});
            } else {
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    public int compare(Restaurant s1, Restaurant s2) {
                        String name1 = s1.getAddress().getCity().toUpperCase();
                        String name2 = s2.getAddress().getCity().toUpperCase();
                        //descending order
                        return name1.compareTo(name2);
                    }});
            }
        }

        else if(order.contains("streetName")){
            if(order.contains("-")){
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    public int compare(Restaurant s1, Restaurant s2) {
                        String name1 = s1.getAddress().getStreetName().toUpperCase();
                        String name2 = s2.getAddress().getStreetName().toUpperCase();
                        //descending order
                        return name2.compareTo(name1);
                    }});
            } else {
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    public int compare(Restaurant s1, Restaurant s2) {
                        String name1 = s1.getAddress().getStreetName().toUpperCase();
                        String name2 = s2.getAddress().getStreetName().toUpperCase();
                        //descending order
                        return name1.compareTo(name2);
                    }});
            }
        }

        else if(order.contains("phone")){
            if(order.contains("-")){
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2) {
                        return Long.compare(r2.getPhone(), r1.getPhone());
                    }
                });
            }
            else {
                Collections.sort(restaurantList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2) {
                        return Long.compare(r1.getPhone(), r2.getPhone());
                    }
                });
            }
        }
    }
}
