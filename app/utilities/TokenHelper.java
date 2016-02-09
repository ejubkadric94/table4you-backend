package utilities;

/**
 * Created by Ejub on 09/02/16.
 * Class authToken helps provides methods which are meant to help with manipulating authentication tokens.
 */
public class TokenHelper {
    private String authToken;

    /**
     * Constructs an object with given token.
     *
     * @param token the token String to be saved
     */
    public TokenHelper(String token) {
        this.authToken = token;
    }

    /**
     * Gets the saved token string.
     *
     * @return the token string
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the token string.
     *
     * @param authToken the token string to be set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
