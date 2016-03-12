package utilities;

import models.*;
import org.apache.commons.codec.digest.DigestUtils;
import play.api.Play;
import play.mvc.*;
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

    public static User getUserFromRequest(Http.Request request){
        String tokenString = request.getHeader("USER-ACCESS-TOKEN");
        Token token = Token.find.where().eq("token", tokenString).findUnique();
        return User.find.where().eq("email", token.getEmail()).findUnique();
    }

    /**
     * Retrieves the user from UserSession.
     * F irstly the validation is done on UserSession object.
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
        reservation.save();
    }

    public static Restaurant getRestaurantById(long id){
        return Restaurant.find.where().eq("restaurantId", id).findUnique();
    }

    /**
     * Retrieves the user from database according to specified email address.
     *
     * @param email the email address
     * @return the User, or null if user does not exist
     */
    public static User getUserByEmail(String email) {
        return User.find.where().eq("email", email).findUnique();
    }

    /**
     * Retrieves the authentication token from database.
     * @param token the token to be found
     * @return token or null if there is no token
     */
    public static Token getToken(String token){
        return Token.find.where().eq("token", token).findUnique();
    }

    /**
     * Retrieves all restaurants from database.
     *
     * @param offset the offset for pagination
     * @param limit the limit for pagination
     * @param filter the filter in format <filterName>:<filterValue> where multiple filters will be separated by comma
     * @param order the order for restaurants
     * @return the List of restaurants found according to specified parameters
     */
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
