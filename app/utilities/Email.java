package utilities;

/**
 * Created by Ejub on 1.2.2016.
 * Class for dealing with emails. Everything related to emails is found in this class.
 */

import org.apache.commons.mail.*;

public class Email {
    public static String activationMailText;
    /**
     * Send confirmation email to newlz registered user, containing link for activation.
     * @param emailAddress email address of user for whom the email is dedicated.
     */
    public static void sendConfirmationEmail(String emailAddress, String link){

        activationMailText = "<h3>Welcome to table4you.<br>" +
                "Please follow this link to finish your registration: <a href=\"" + link + "\">" + link + "</a>" +
                "<br>Do not reply on this email. If you have any quesions, please visit our website.</h3>";

        org.apache.commons.mail.HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("table4you.praksa@gmail.com", "table4you"));
        email.setSSLOnConnect(true);
        try {
            email.setFrom("NO REPLY<table4you.praksa@gmail.com>");
            email.setSubject("Table4you activation");
            email.setHtmlMsg(activationMailText);
            email.addTo(emailAddress);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }


}
