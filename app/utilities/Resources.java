package utilities;

/**
 * Created by Ejub on 02/02/16.
 * Class Resources contains all application outputs.
 */
public final class Resources {
    public static final String BAD_REQUEST_INVALID_DATA = "Invalid input data";
    public static final String BAD_REQUEST_EMAIL_EXISTS = "Email already exists";
    public static final String BAD_REQUEST_WRONG_CONFIRMATION_TOKEN = "Wrong confirmation authToken";
    public static final String UNAUTHORIZED_INPUT_DOES_NOT_MATCH = "Email and password do not match";
    public static final String NO_RESTAURANT = "No restaurant found with specified restaurant Id";
    public static final String NO_PHOTO = "No photo found with specified photo Id";
    public static final String BAD_REQUEST_INVALID_PARAMETERS = "Invalid parameters specified";
    public static final String NO_UPLOAD_HEADER = "No upload header received";
    public static final String TOO_LARGE_FILE = "File is larger than 1 megabyte";
    public static final String UNRELATED_PHOTO = "Photo does not belong to the specified restaurant";
    public static final String SERVER_NAME = "default-environment.z6gkicxids.us-west-2.elasticbeanstalk.com"; //"api.table4you" when deployed
    public static final String VERSION = "v1";

    private Resources(){}
}

