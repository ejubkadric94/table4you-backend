package models;

/**
 * Created by Ejub on 31.1.2016.
 */
import java.util.List;
import javax.persistence.*;

import com.avaje.ebean.*;
import play.data.validation.*;
import play.db.ebean.*;

@Entity
public class Users extends com.avaje.ebean.Model {
    @Id
    public String email;
    public String surname;
    public String name;
    public String password;
    public String token;
    public boolean isConfirmed;

    public static Finder<String, Users> find = new Finder<String, Users>(String.class, Users.class);
}
//{"name":"John","surname":"Johnson","email":"ejubkadric@gmail.com","password":"test"}