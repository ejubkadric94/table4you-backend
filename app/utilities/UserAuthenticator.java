package utilities;

import models.Token;
import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by Ejub on 02/02/16.
 * Class UserAuthenticator can be used to protect specific routes by checking if user is authenticated.
 */
public class UserAuthenticator extends Security.Authenticator{
    /**
     * Retrieves the user out of the http context.
     * Method firstly checks if authentication token found in context is valid, and whether it matches to a single user.
     * It also checks if user is confirmed.
     *
     * @param ctx the http context
     * @return the user is returned if token is valid and if user is confirmed
     */
    @Override
    public String getUsername(Http.Context ctx) {
        String token = getTokenFromHeader(ctx);
        if (token != null) {
            Token tempToken = PersistenceManager.getToken(token);
            if (tempToken == null) {
                return null;
            }
            User user = PersistenceManager.getUserByEmail(tempToken.getEmail());
            if (user != null) {
                return user.isConfirmed() ? user.getEmail() : null;
            }
            if(!user.isConfirmed()) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return super.onUnauthorized(context);
    }

    /**
     * Retrieves the authentication token from http context.
     *
     * @param ctx the http context
     * @return the authentication token
     */
    private String getTokenFromHeader(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get("USER-ACCESS-TOKEN");
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            return authTokenHeaderValues[0];
        }
        return null;
    }
}
