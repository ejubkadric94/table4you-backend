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
 * Created by Ejub.
 * Controller for photo routes.
 */
public class PhotoController extends Controller {
    /**
     * Adds a photo for a certain restaurant.
     *
     * @param restaurantId the id of a restaurant
     * @return photoId, or suitable error if operation is not successful
     */
    @BodyParser.Of(value = BodyParser.MultipartFormData.class, maxLength = 1024 * 1048576)
    public Result addPhoto(int restaurantId) {
        response().setContentType("application/json");
        Restaurant restaurant = PersistenceManager.getRestaurantById(restaurantId);
        if(restaurant == null){
            return notFound(JsonSerializer.serializeObject(new Error(Resources.NO_RESTAURANT)));
        }

        if (request().body().asMultipartFormData() == null || request().body().asMultipartFormData()
                .getFile("upload") == null) {
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.NO_UPLOAD_HEADER)));
        }
        Photo photo = new Photo(request(), restaurantId);
        PersistenceManager.savePhoto(photo);
        return ok(JsonSerializer.serializeBasicDetails(photo));
    }

    /**
     * Marks a photo as default for a certain restaurant.
     *
     * @param registrationId the id of a restaurant
     * @param photoId the id of a photo
     * @return status ok, or suitable error if operation is not successful
     */
	public Result markAsDefault(int registrationId, int photoId) {
        response().setContentType("application/json");
        Restaurant restaurant = PersistenceManager.getRestaurantById(registrationId);
        if(restaurant == null){
            return notFound(JsonSerializer.serializeObject(new Error(Resources.NO_RESTAURANT)));
        }

        Photo photo = PersistenceManager.getPhotoFromId(photoId);
        if(photo == null) {
            return notFound(JsonSerializer.serializeObject(new Error(Resources.NO_PHOTO)));
        }
        if(!restaurant.containsPhoto(photo)){
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.UNRELATED_PHOTO)));
        }

        PersistenceManager.saveNewDefaultPhoto(restaurant, photo);
        return ok();
    }

}