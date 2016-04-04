package controllers;

import models.Restaurant;
import models.Photo;
import play.mvc.BodyParser;
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
    @BodyParser.Of(value = BodyParser.MultipartFormData.class, maxLength = 1024 * 1048576)
    public Result addPhoto(int restaurantId) {
        response().setContentType("application/json");
        Restaurant restaurant = PersistenceManager.getRestaurantById(restaurantId);
        if(restaurant == null){
            return notFound(JsonSerializer.serializeObject(new Error(Resources.NO_RESTAURANT)));
        }

        System.out.println("1111");
        if (request().body().asMultipartFormData().getFile("upload") == null) {
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.NO_UPLOAD_HEADER)));
        }
        System.out.println("2222");
        Photo photo = new Photo(request(), restaurantId);
        System.out.println("3333");
        PersistenceManager.savePhoto(photo);
        System.out.println("4444");
        return ok(JsonSerializer.serializeBasicDetails(photo));
    }
}
