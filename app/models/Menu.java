package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.io.FilenameUtils;
import play.mvc.Http;
import utilities.AWSManager;
import utilities.PersistenceManager;
import utilities.Validation;
import utilities.View;

import javax.persistence.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Ejub on 2.4.2016.
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

    public Menu(Http.Request request, Restaurant restaurant) {
        this.restaurant = restaurant;
        file = request.body().asMultipartFormData().getFile("upload").getFile();
        String fileExtension = FilenameUtils.getExtension(request.body().asMultipartFormData().getFile("upload").getFilename());
        name = UUID.randomUUID().toString() + "." + fileExtension;
    }

    @Transient
    @JsonIgnore
    public String getFileName(){
        return "menus/restaurants/" + restaurant.getRestaurantId() + "/" + menuId + "/" + name;
    }

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
