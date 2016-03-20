package models;


import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.io.FilenameUtils;
import play.Logger;
import com.avaje.ebean.Model;
import play.mvc.Http;
import plugins.S3Plugin;
import utilities.PersistenceManager;
import utilities.Validation;
import utilities.View;

import javax.persistence.*;
import java.io.File;
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
    @Transient
    @JsonView(View.AdditionalDetails.class)
    private File file;
    @JsonView(View.AllDetails.class)
    private boolean isDefault;
    @JsonView(View.AllDetails.class)
    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    public Photo(Http.MultipartFormData.FilePart upload, int restaurantId) {
        sizeType = "original";
        String fileExtension = FilenameUtils.getExtension(upload.getFilename());
        name = UUID.randomUUID().toString() + "." + fileExtension;
        file = upload.getFile();
        this.restaurant = PersistenceManager.getRestaurantById(restaurantId);
        isDefault = restaurant.getPhotos().size() == 0 ? true : false;
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
        if (S3Plugin.amazonS3 == null) {
            Logger.error("Could not save because amazonS3 was null");
            throw new RuntimeException("Could not save");
        }
        else {
            this.bucket = S3Plugin.s3Bucket;

            super.save(); // assigns an photoId

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, getActualFileName(), file);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
            S3Plugin.amazonS3.putObject(putObjectRequest); // upload file
        }
    }

    @Override
    public void delete() {
        if (S3Plugin.amazonS3 == null) {
            Logger.error("Could not delete because amazonS3 was null");
            throw new RuntimeException("Could not delete");
        }
        else {
            S3Plugin.amazonS3.deleteObject(bucket, getActualFileName());
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
        return file.length() < 1048576;
    }
}
