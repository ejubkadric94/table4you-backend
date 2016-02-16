package utilities;

/**
 * Created by Ejub on 02/02/16.
 * Class contains all the needed strings for application.
 * It is an utility class and it cannot be instantiated.
 */
public final class Resources {
    public static final String BAD_REQUEST_INVALID_DATA = "Invalid input data";
    public static final String BAD_REQUEST_EMAIL_EXISTS = "Email already exists";
    public static final String BAD_REQUEST_WRONG_CONFIRMATION_TOKEN = "Wrong confirmation authToken";
    public static final String UNAUTHORIZED_INPUT_DOES_NOT_MATCH = "Email and password do not match";
    public static final String BAD_REQUEST_NO_RESTAURANT = "No restaurant found with specified restaurant Id";
    public static final String SERVER_NAME = "localhost:9000"; //"api.table4you" when deployed
    public static final String VERSION = "v1";

    private Resources(){}
}
/*
example of json
{
"email":"ejubkadric@gmail.com",
"password":"test",
"passwordConfirmation":"test",
"firstName":"Ejub",
"lastName":"Kadric",
"address":{
"streetName":"ulica",
"city":"Sarajevo",
"country":"BiH"},
"phone":"062292868",
"gender":"male",
"birthdate":"16/01/1994"
}
 */