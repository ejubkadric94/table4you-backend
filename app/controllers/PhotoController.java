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

/**
 * Created by root on 18/03/16.
 */
public class PhotoController extends Controller {

    public Result addPhoto(int registrationId) {
        response().setContentType("application/json");
        Restaurant restaurant = PersistenceManager.getRestaurantById(registrationId);
        if(restaurant == null){
            return notFound(JsonSerializer.serializeObject(new Error(Resources.NO_RESTAURANT)));
        }

        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart upload = body.getFile("upload");
        if (upload == null) {
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.NO_UPLOAD_HEADER)));
        }

        Photo photo = new Photo(upload, registrationId);
        if(!photo.isValid()){
            return badRequest(JsonSerializer.serializeObject(new Error(Resources.TOO_LARGE_FILE)));
        }

        PersistenceManager.savePhoto(photo);
        return ok(JsonSerializer.serializeBasicDetails(photo));
    }
}
