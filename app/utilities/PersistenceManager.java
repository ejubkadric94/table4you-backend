package utilities;

import models.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

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
        reservation.save();
    }

    public static Restaurant getRestaurantById(long id){
        return Restaurant.find.where().eq("restaurantId", id).findUnique();
    }

    public static Coordinates getCoordinatesOfRestaurant(Restaurant restaurant){
        return Coordinates.find.where().eq("restaurantId", restaurant.getRestaurantId()).findUnique();
    }

    public static Address getAddressOfRestaurant(Restaurant restaurant){
        return Address.find.where().eq("restaurantId", restaurant.getRestaurantId()).findUnique();
    }

    public static List<Restaurant> getAllRestaurantList(){
        return Restaurant.find.all();
    }
}
