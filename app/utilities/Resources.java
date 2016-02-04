package utilities;

/**
 * Created by test on 02/02/16.
 */
public final class Resources {
    public static final String BAD_REQUEST_INVALID_DATA = "Error in input data";
    public static final String BAD_REQUEST_PASSWORD_MISMATCH = "Passwords do not match";
    public static final String BAD_REQUEST_WRONG_CONFIRMATION_TOKEN = "Wrong confirmation authToken";
    public static final String BAD_REQUEST_COULD_NOT_INITIALIZE = "Could not initialize user";
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