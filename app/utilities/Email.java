package utilities;

import org.apache.commons.mail.*;

/**
 * Created by Ejub on 1.2.2016.
 * Class Email can be used for dealing with emails.
 * Everything related to emails is found in this class.
 */
public final class Email {

    private Email(){}

    /**
     * Send confirmation email to newly registered user, containing link for activation.
     *
     * @param emailAddress email address of the user for whom the email is dedicated
     */
    public static void sendConfirmationEmail(String emailAddress, String link){
        String activationMailText = "<h3>Welcome to table4you.<br>" +
                "Please follow this link to finish your registration:<br> <a href=\"" + link + "\">" + link + "</a>" +
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
