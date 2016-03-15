package utilities;

import models.Token;
import models.User;
import play.Play;
import play.mvc.Http;

/**
 * Created by Ejub on 02/02/16.
 * Class AdminAuthenticator can be used to protect specific routes by checking if administrator is authenticated.
 */
public class AdminAuthenticator extends UserAuthenticator{
    /**
     * Retrieves the user out of the http context.
     * Method firstly checks if authentication token found in context is valid, and whether it matches to a specified administrator.
     *
     * @param ctx the http context
     * @return the user is returned if token is valid and if user is confirmed
     */
    @Override
    public String getUsername(Http.Context ctx) {
        System.out.println(super.getUsername(ctx));
        User user = PersistenceManager.getUserByEmail(super.getUsername(ctx));
        if(user == null){
            return null;
        }
        return user.isAdmin() ? user.getEmail() : null;
    }
}
