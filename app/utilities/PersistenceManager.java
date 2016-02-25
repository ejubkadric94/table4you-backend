package utilities;

import com.avaje.ebean.Model;
import models.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ejub on 08/02/16.
 * Provides useful methods for persisting data into database.
 */
public class PersistenceManager {
    /**
     * Inserts a User with specified information into the database.
     * Password is hashed using MD5.
     * Finally, confirmation mail is sent to specified email address.
     *
     * @param user the user to be saved
     */
    public void createUser(User user){
        if(user != null){

            UserHelper.initializeUser(user);

            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            user.setPasswordConfirmation(DigestUtils.md5Hex(user.getPasswordConfirmation()));
            Email.sendConfirmationEmail(user.getEmail(), Resources.SERVER_NAME + "/" + Resources.VERSION
                    + "/registration/confirm/" + UserHelper.encodeToken(user.getAuthToken().getToken()));

            user.getAddress().save();
            user.getAuthToken().save();
            user.save();
        }
    }

    /**
     * Retrieves the user from UserSession.
     * Validation is firstly done on UserSession object.
     *
     * @param userSession the UserSession object
     * @return The User retrieved
     */
    public User getUserFromSession(UserSession userSession){
        User user = User.find.where().eq("email", userSession.getEmail()).findUnique();
        if(user.getPassword().equals(DigestUtils.md5Hex(userSession.getPassword()))) {
            user.extendTokenExpiration();
            return user;
        }
        return null;
    }

    /**
     * Saves the reservation into the database.
     *
     * @param reservation the reservation to be saved
     */
    public void createReservation(Reservation reservation) {
        System.out.println(reservation.getRestaurantId());
        reservation.save();
    }

    public static Restaurant getRestaurantById(long id){
        return Restaurant.find.where().eq("restaurantId", id).findUnique();
    }

    public static User getUserByEmail(String email) {
        return User.find.where().eq("email", email).findUnique();
    }

    public static Token getToken(String token){
        return Token.find.where().eq("token", token).findUnique();
    }

    public static List<Restaurant> getAllRestaurants(int offset, int limit, String filter,String order){
        List<Restaurant> list = Restaurant.find.all();

        if(order != null && filter != null && limit != 0){
            Map<String, String> filtersMap = RestaurantHelper.getFiltersMap(filter);
            if(order.contains("-")){
                order.replace("-", "");
                list = Restaurant.find.where().allEq((Map)filtersMap).orderBy(order + " desc").findPagedList(offset, limit).getList();
            } else{
                list = Restaurant.find.where().allEq((Map)filtersMap).orderBy(order + " asc").findPagedList(offset, limit).getList();
            }
        } else if (order != null && filter != null){
            Map<String, String> filtersMap = RestaurantHelper.getFiltersMap(filter);
            if(order.contains("-")){
                order.replace("-", "");
                list = Restaurant.find.where().allEq((Map)filtersMap).orderBy(order + " desc").findList();
            } else{
                list = Restaurant.find.where().allEq((Map)filtersMap).orderBy(order + " asc").findList();
            }
        } else if(limit != 0 && filter != null){
            Map<String, String> filtersMap = RestaurantHelper.getFiltersMap(filter);
            list = Restaurant.find.where().allEq((Map)filtersMap).findPagedList(offset, limit).getList();
        } else if(limit != 0 && order != null){
            if(order.contains("-")){
                order.replace("-", "");
                list = Restaurant.find.where().orderBy(order + " desc").findPagedList(offset, limit).getList();
            } else{
                list = Restaurant.find.where().orderBy(order + " asc").findPagedList(offset, limit).getList();
            }
        } else if (order != null){
            if(order.contains("-")){
                order = order.replace("-", "");
                list = Restaurant.find.order(order + " desc").findList();

            } else{
                list = Restaurant.find.order(order + " asc").findList();
            }
        } else if (limit != 0) {
            list = Restaurant.find.findPagedList(offset, limit).getList();
        } else if (filter != null) {
            Map<String, String> filtersMap = RestaurantHelper.getFiltersMap(filter);
            list = Restaurant.find.where().allEq((Map)filtersMap).findList();
        }

        return list;
    }
}
