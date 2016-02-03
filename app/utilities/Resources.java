package utilities;

/**
 * Created by test on 02/02/16.
 */
public class Resources {
    public static final String BAD_REQUEST = "Error in input data";
    public static final String BAD_REQUEST_PASSWORD_MISMATCH = "Error in input data";
    public static final String BAD_REQUEST_WRONG_CONFIRMATION_TOKEN = "Wrong confirmation token";
    public static final String SERVER_NAME = "localhost:9000"; //"api.table4you" when deployed
    public static final String VERSION = "v1.0";

    public static boolean passwordCheck(UserHelper userHelper){
        if (userHelper.password.equals(userHelper.passwordConfirmation))
                return true;
        return false;
    }
   // http://<server_name>/<version>/<url_path>?<arbitrary_params>.
}
