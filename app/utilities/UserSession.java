package utilities;

import models.User;

/**
 * Created by Ejub on 09/02/16.
 * Class UserSession can be used to store temporary information about a user.
 */
public class UserSession {
    private String email;
    private String password;

    /**
     * Checks whether the user email an password are valid.
     *
     * @return true if email and password are valid
     */
    public boolean isValid(){
        if(email == null || password == null || User.find.where().eq("email", email).findUnique() == null){
            return false;
        }
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
