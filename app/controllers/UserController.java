package controllers;

/**
 * Created by Ejub on 31.1.2016.
 */


import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import models.User;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.libs.Json;
import play.mvc.*;
import utilities.Resources;
import utilities.UserHelper;
import views.html.*;
import utilities.Email;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.BodyParser;
import utilities.Resources;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.*;

import java.security.SecureRandom;

public class UserController extends Controller {
    /**
     * Registers the user
     * @return the authorization token in JSON format
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result register() {
        JsonNode json = Json.parse(request().body().asJson().asText());
        UserHelper userHelper = Json.fromJson(json, UserHelper.class);

        if(Resources.passwordCheck(userHelper))
            return badRequest(index.render(Resources.BAD_REQUEST_PASSWORD_MISMATCH));

        userHelper.createUser();

        return ok(index.render(userHelper.getToken()));
    }


    public Result confirm(String registrationToken){

        UserHelper userHelper = new UserHelper();
        if(userHelper.confirmUser(registrationToken))
            return badRequest(index.render(Resources.BAD_REQUEST_WRONG_CONFIRMATION_TOKEN));



        User user = User.find.where().eq("authToken", registrationToken).findUnique();
        user.isConfirmed = true;
        return null;
    }


}

