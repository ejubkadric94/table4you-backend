package controllers;

import play.mvc.*;
import views.html.*;

/**
 * Controller Application provides basic results for application.
 */
public class Application extends Controller {
    /**
     * Retrieves the index view with welcome message.
     *
     * @return Welcome string inside
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
