package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.format.Formats;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by test on 04/02/16.
 */
@Entity
@Table(name = "abh_user_token")
public class Token {
  //  @Id
   // @Column(length = 80)
  //  @JsonIgnore
 //   private String email;

    @OneToOne()
    @JoinColumn(name = "email")
    @JsonIgnore
    private User user;

    @Column(name = "token",length = 32)
    private String token;
    @Column(name = "expirationDate")
    @Formats.DateTime(pattern="dd/MM/yyyy")
    private Date expirationDate;

    public Token(){
      //  email = "";
        token = "";
        expirationDate = new Date();
    }

    public static Model.Finder<String, Token> find = new Model.Finder<String, Token>(String.class, Token.class);

    /**
     * Creates a date which is one day ahead of current date
     * @return returns newly created date
     */
    public static Date createExpirationDate(){
        Date newDate = new Date();

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 1);
        newDate = date.getTime();

        return newDate;
    }

    public void generateToken(){
        this.token =  UUID.randomUUID().toString();
        this.expirationDate = Token.createExpirationDate();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

  /*  public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}