package models;



import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.io.FilenameUtils;
import play.Logger;
import com.avaje.ebean.Model;
import play.mvc.Http;
import utilities.AWSManager;
import utilities.PersistenceManager;
import utilities.Validation;
import utilities.View;
import javax.persistence.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Ejub on 18/03/16.
 */
@Entity
@Table(name = "abh_photo")
public class Photo extends Model implements Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.BasicDetails.class)
    private long photoId;
    @JsonView(View.AdditionalDetails.class)
    private String bucket;
    @JsonView(View.AllDetails.class)
    private String name;
    @JsonView(View.AllDetails.class)
    private String sizeType;
    @JsonView(View.AllDetails.class)
    private boolean isDefault;
    @JsonView(View.AllDetails.class)
    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;
    @Transient
    private File file;

    public Photo(Http.Request request, int restaurantId) {
        sizeType = "original";
        String fileExtension = FilenameUtils.getExtension(request.body().asMultipartFormData().getFile("upload").getFilename());
        name = UUID.randomUUID().toString() + "." + fileExtension;
        this.restaurant = PersistenceManager.getRestaurantById(restaurantId);


        if(restaurant.getPhotos().size() == 0){
            try {
                PersistenceManager.getRestaurantById(restaurantId).setImage(getUrl().toString());
                isDefault = true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            isDefault = false;
        }
        file = request.body().asMultipartFormData().getFile("upload").getFile();

    }

	@JsonView(View.AllDetails.class)
    public URL getUrl() throws MalformedURLException {
        return new URL("https://s3.amazonaws.com/" + bucket + "/" + getActualFileName());
    }

    private String getActualFileName() {
        return "photos/restaurants/" + restaurant.getRestaurantId() + "/" + photoId + "/" + sizeType + "/" + name;
    }

    @Override
    public void save() {
        if (!AWSManager.isAWSConnected()) {
            Logger.error("Could not save because amazonS3 was null");
            throw new RuntimeException("Could not save");
        }
        else {
            this.bucket = AWSManager.getBucket();

            super.save(); // assigns an photoId

            AWSManager.savePhoto(bucket,  getActualFileName(), file);

        }
    }

    @Override
    public void delete() {
        if (!AWSManager.isAWSConnected()) {
            Logger.error("Could not delete because amazonS3 was null");
            throw new RuntimeException("Could not delete");
        }
        else {
            AWSManager.deletePhoto(bucket, getActualFileName());
            super.delete();
        }
    }

	@JsonView(View.BasicDetails.class)
    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }
	
	@JsonView(View.AdditionalDetails.class)
    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

	@JsonView(View.AllDetails.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@JsonView(View.AllDetails.class)
    public String getSizeType() {
        return sizeType;
    }

    public void setSizeType(String sizeType) {
        this.sizeType = sizeType;
    }

	@JsonView(View.AllDetails.class)
    public boolean isDefault() {
        return isDefault;
    }
	
    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

	@JsonView(View.AllDetails.class)
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    @JsonView(View.AdditionalDetails.class)
    public boolean isValid() {
        //return stream. < 1048576;
        return true;
    }
}
