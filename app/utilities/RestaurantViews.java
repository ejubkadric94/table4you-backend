package utilities;

/**
 * Created by Ejub on 23/02/16.
 * Class RestaurantViews is used with JsonView annotation to specify which Restaurant properties will be shown in
 * specific serialization.
 */
public class RestaurantViews {
    /**
     * Basic details are:
     * - restaurantId
     * - name
     * - address
     * - phone
     * - workingHours
     * - rating
     * - image
     */
    public static class BasicDetails {
    }

    /**
     * Properties added on top of basic details:
     * - Coordinates
     * - reservationPrice
     * - deals
     */
    public static class AllDetails extends BasicDetails {
    }
}
