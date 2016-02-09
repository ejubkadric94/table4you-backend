package utilities;

import com.google.gson.Gson;
import models.User;

/**
 * Created by Ejub on 7.2.2016.
 * Provides with useful functions to deal with error messages.
 */
public final class Error {
    private String error;

    /**
     * Constructs the Error object with specified error string.
     *
     * @param error the error string
     */
    public Error(String error){
        this.error = error;
    };

    /**
     * Gets the error in json format.
     *
     * @param error the text of error
     * @return the json format error
     */
    public String getError(String error){
        return error;
    }

    /**
     * Sets the error object.
     *
     * @param error the error message
     */
    public void setError(String error) {
        this.error = error;
    }
}
