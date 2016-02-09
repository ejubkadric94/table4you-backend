package utilities;

import models.Token;
import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by test on 02/02/16.
 */
public class UserAuthenticator extends Security.Authenticator{
    @Override
    public String getUsername(Http.Context ctx) {
        String token = getTokenFromHeader(ctx);
        if (token != null) {
            Token tempToken = Token.find.where().eq("token", token).findUnique();
            if (tempToken == null) {
                return null;
            }
            User user = User.find.where().eq("email", tempToken.getEmail()).findUnique();
            if (user != null) {
                return user.getEmail();
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

    private String getTokenFromHeader(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get("X-AUTH-TOKEN");
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            return authTokenHeaderValues[0];
        }
        return null;
    }
}
