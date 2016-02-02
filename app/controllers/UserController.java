package controllers;

/**
 * Created by Ejub on 31.1.2016.
 */


import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import models.User;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.mvc.*;
import utilities.Resources;
import views.html.*;
import utilities.Email;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.BodyParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.*;
import org.apache.commons.codec.digest.DigestUtils;
import java.security.SecureRandom;

public class UserController extends Controller {
    /**
     * Registers the user
     * @return the authorization token in JSON format
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result register() {
        JsonNode json2 = request().body().asJson();

        String firstName = json2.findPath("name").textValue();
        String email = json2.findPath("email").textValue();
        String lastName = json2.findPath("surname").textValue();
        String password = json2.findPath("password").textValue();
        String passwordConfirmation = json2.findPath("passwordConfirmation").textValue();
        String streetName = json2.findPath("streetName").textValue();
        String city = json2.findPath("city").textValue();
        String country = json2.findPath("country").textValue();
        int phone = json2.findPath("phone").asInt();
        String gender = json2.findPath("gender").textValue();
        String birthdateString = json2.findPath("birthdate").textValue();

        boolean isConfirmed = false;

        //EmailValidator emailValidator = EmailValidator.getInstance();
        //DateValidator dateValidator = DateValidator.getInstance();


        if( !(isValidEmail(email) && isValidDate(birthdateString)
                && password.equals(passwordConfirmation)  && validateFirstName(firstName) && validateLastName(lastName)
                && validatePhoneNumber(Integer.toString(phone)) && validateGender(gender)) )
            //return badRequest(index.render(Resources.BAD_REQUEST));
        {  }


        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        DateTime dt = formatter.parseDateTime(json2.findPath("birthdate").textValue());
        Date birthdate = dt.toDate();

        User user = new User();

        user.email = email;
        user.firstName = firstName;
        user.lastName = lastName;
        user.password = DigestUtils.md5Hex(password);
        user.streetName = streetName;
        user.city = city;
        user.country = country;
        user.phone = phone;
        user.gender = gender;
        user.birthdate = birthdate;

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = DigestUtils.md5Hex(bytes.toString() + UUID.randomUUID().toString().toUpperCase());

        user.save();
        Email.sendConfirmationEmail(user.email, "/"+ Resources.SERVER_NAME+ "/"
                +Resources.APP_NAME+"/"+Resources.VERSION+"/" +"/"+ DigestUtils.md5Hex(user.authToken));

        String outputString = "{\"authToken\":\"" + user.authToken + "\"}";


        return ok(index.render("dsfasdf"));
    }


    public Result confirm(String registrationToken){

        User user = User.find.where().eq("authToken", registrationToken).findUnique();
        user.isConfirmed = true;
        return null;
    }


    private boolean validateFirstName( String firstName )
    {
        return firstName.matches( "[A-Z][a-zA-Z]*" );
    }

    private boolean validateLastName( String lastName )
    {
        return lastName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );
    }

    private boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;
    }

    private boolean isValidDate(String date)
    {
        String errorMessage;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        }
        catch (ParseException e)

        {
            errorMessage = "the date you provided is in an invalid date" +
            " format.";
            return false;
        }

        if (!sdf.format(testDate).equals(date))
        {
            errorMessage = "The date that you provided is invalid.";
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String mail){
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    private boolean validateGender(String gender){
        if(gender.equals("male") || gender.equals("female") || gender.equals("MALE") || gender.equals("FEMALE")
                || gender.equals("Male") || gender.equals("Female"))
            return true;
        return false;
    }

}

