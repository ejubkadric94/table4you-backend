package utilities;

/**
 * Created by root on 09/02/16.
 */
public class UserSession {
    private String email;
    private String password;

    public boolean isReadyForLogin() {
        return email != null && password != null;
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
