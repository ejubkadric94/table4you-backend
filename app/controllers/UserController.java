package controllers;

/**
 * Created by Ejub on 31.1.2016.
 */
import play.mvc.*;
import views.html.*;
import utilities.Email;
public class UserController extends Controller {
    /**
     * Registers the user
     * @return
     */
    public Result register() {
        sendConfirmationMail("ejubkadric@gmail.com");
        return ok(index.render("Mail sent"));
    }

    /**
     * Function sends confirmation mail with activation link using Email utility class
     * @param emailAddress email of user
     */
    private void sendConfirmationMail(String emailAddress){
        Email.sendConfirmationEmail(emailAddress, "blabla");
    }
}
