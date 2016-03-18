package models;


import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.io.FilenameUtils;
import play.Logger;
import com.avaje.ebean.Model;
import play.mvc.Http;
import plugins.S3Plugin;
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
public class Photo extends Model{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.BasicDetails.class)
    private long id;
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
    private long restaurantId;

    public Photo() {

    }

    public void preparePhoto(Http.MultipartFormData.FilePart upload, int restaurantId){
        sizeType = "original";
        String fileExtension = FilenameUtils.getExtension(upload.getFilename());
        name = UUID.randomUUID().toString() + "." + fileExtension;
        file = upload.getFile();
        this.restaurantId = restaurantId;

        //TODO is_only photo implementation
        isDefault = false;
    }

    public URL getUrl() throws MalformedURLException {
        return new URL("https://s3.amazonaws.com/" + bucket + "/" + getActualFileName());
    }

    private String getActualFileName() {
        return "photos/restaurants/" + restaurantId + "/" + id + "/" + sizeType + "/" + name;
    }

    @Override
    public void save() {


        if (S3Plugin.amazonS3 == null) {
            Logger.error("Could not save because amazonS3 was null");
            throw new RuntimeException("Could not save");
        }
        else {
            this.bucket = S3Plugin.s3Bucket;

            super.save(); // assigns an id

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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
