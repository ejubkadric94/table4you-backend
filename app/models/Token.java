package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import play.data.format.Formats;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Ejub on 04/02/16.
 * Class Token is used for manipulating and storing tokens which are necessary for authentication.
 */
@Entity
@Table(name = "abh_user_token")
public class Token extends Model {
    @Id
    @Column(name="userEmail", columnDefinition = "VARCHAR(80) DEFAULT 'test@test.com'")
    @JsonIgnore
    private String email;
    @Column(name = "token",length = 200)
    @SerializedName("authToken")
    private String token;
    @Column(name = "expirationDate")
    @Formats.DateTime(pattern="dd/MM/yyyy")
    private Date expirationDate;

    public Token(){}

    public static Model.Finder<String, Token> find = new Model.Finder<String, Token>(String.class, Token.class);

    /**
     * Creates a date which is one day ahead of current date.
     *
     * @return returns newly created date
     */
    public static Date generateExpirationDate(){
        Date newDate = new Date();

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 1);
        newDate = date.getTime();

        return newDate;
    }

    /**
     * Generates the token randomly, and sets the expiration date to one day ahead in the same time.
     */
    public void generateToken(){
        this.token =  UUID.randomUUID().toString();
        this.expirationDate = Token.generateExpirationDate();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}