package controllers;

import models.Restaurant;
import models.Photo;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utilities.Error;
import utilities.JsonSerializer;
import utilities.PersistenceManager;
import utilities.Resources;

import java.io.*;

/**
 * Created by root on 18/03/16.
 */
public class PhotoController extends Controller {

    public Result addPhoto(int restaurantId) {
        response().setContentType("application/json");
        Restaurant restaurant = PersistenceManager.getRestaurantById(restaurantId);
        if(restaurant == null){
            return notFound(JsonSerializer.serializeObject(new Error(Resources.NO_RESTAURANT)));
        }


        if (request().body().asMultipartFormData().getFile("upload") == null) {

            return badRequest(JsonSerializer.serializeObject(new Error(Resources.NO_UPLOAD_HEADER)));
        }

        Photo photo = new Photo(request(), restaurantId);


        PersistenceManager.savePhoto(photo);
        return ok(JsonSerializer.serializeBasicDetails(photo));
    }
}
