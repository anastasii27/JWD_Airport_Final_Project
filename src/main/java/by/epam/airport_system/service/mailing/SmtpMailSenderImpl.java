package by.epam.airport_system.service.mailing;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SmtpMailSenderImpl implements MailSender{

    @Override
    public void sendMail() {
        String to = "rodnovanastya@gmail.com";
        String from = "blue.sky.airp@gmail.com";
        final String username = "blue.sky.airp@gmail.com";
        final String password = "12345nastya";

        System.out.println("TLSEmail Start");
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");


        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {

                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        //compose the message
        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject("subject");
            message.setText("Hello, aas is sending email ");

            Transport.send(message);
            System.out.println("Yo it has been sent..");
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
