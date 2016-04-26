package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.io.FilenameUtils;
import play.mvc.Http;
import utilities.AWSManager;
import utilities.Validation;
import utilities.View;
import javax.persistence.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Ejub on 2.4.2016.
 * Class Menu can be used for dealing with Menu objects.
 */
@Entity
@Table(name = "abh_menu")
public class Menu extends Model implements Validation{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.BasicDetails.class)
    private long menuId;
    @JsonIgnore
    @ManyToOne()
    private Restaurant restaurant;
    @Transient
    @JsonIgnore
    private File file;
    private String name;

    public static Model.Finder<String, Menu> find = new Model.Finder<String, Menu>(String.class, Menu.class);

    /**
     * Constructs the Menu object using information from request.
     *
     * @param request the http request
     * @param restaurant the restaurant related to the menu
     */
    public Menu(Http.Request request, Restaurant restaurant) {
        this.restaurant = restaurant;
        file = request.body().asMultipartFormData().getFile("upload").getFile();
        String fileExtension = FilenameUtils.getExtension(request.body().asMultipartFormData().getFile("upload").getFilename());
        name = UUID.randomUUID().toString() + "." + fileExtension;
    }

    /**
     * Retrieves the full AWS S3 name of the menu.
     * This field is transient and thus not saved into the database.
     * This field is ignored from all JSON related operations.
     *
     * @return name string
     */
    @Transient
    @JsonIgnore
    public String getFileName(){
        return "menus/restaurants/" + restaurant.getRestaurantId() + "/" + menuId + "/" + name;
    }

    /**
     * Retrieves the URL of the menu stored on AWS S3.
     *
     * @return URL
     * @throws MalformedURLException URL expection is thrown if operation is unsuccessful
     */
    @JsonView(View.BasicDetails.class)
    public URL getUrl() throws MalformedURLException {
        return new URL("https://s3.amazonaws.com/" + AWSManager.getBucket() + "/" + getFileName());
    }

    @JsonView(View.AllDetails.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    @Transient
    @JsonIgnore
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @JsonIgnore
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * Validates the menu properties.
     *
     * @return true if validation is successful
     */
    @Override
    @JsonIgnore
    public boolean isValid() {
        if(file != null){
            if(file.length() > 1048576){
                return false;
            }
            return true;
        }
        return false;
    }

}