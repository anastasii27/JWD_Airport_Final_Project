package by.epam.airport_system.service.mailing;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.service.ServiceException;
import lombok.extern.log4j.Log4j2;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.ResourceBundle;

@Log4j2
public class SmtpMailSender implements MailSender{
    private static SmtpMailSender instance;
    private final static String FROM = "blue.sky.airp@gmail.com";
    private final static String PASSWORD = "12345nastya";
    private final static String KEY_HOST = "mail.smtp.host";
    private final static String KEY_STARTTLS = "mail.smtp.starttls.enable";
    private final static String KEY_PORT = "mail.smtp.port";
    private final static String KEY_AUTH = "mail.smtp.auth";
    private final static String KEY_SOCKET = "mail.smtp.socketFactory.class";
    private final static String FILE_NAME = "mailSender";
    private Properties properties;

    public static SmtpMailSender getInstance() {
        if(instance == null){
            instance = new SmtpMailSender();
        }
        return instance;
    }

    private SmtpMailSender(){
        ResourceBundle bundle = ResourceBundle.getBundle(FILE_NAME);
        properties = System.getProperties();
        properties.put(KEY_HOST, bundle.getString(KEY_HOST));
        properties.put(KEY_STARTTLS, bundle.getString(KEY_STARTTLS));
        properties.put(KEY_PORT, bundle.getString(KEY_PORT));
        properties.put(KEY_AUTH, bundle.getString(KEY_AUTH));
        properties.put(KEY_SOCKET, bundle.getString(KEY_SOCKET));
    }

    public void sendMail(User user, String messageText) throws ServiceException {
        String to = user.getEmail();

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM, PASSWORD);
                    }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setText(messageText);
            Transport.send(message);
            log.info("Message has been send to " + to);

        } catch (MessagingException e) {
            log.error("Message hasn`t been send to " + to);
            throw new ServiceException("Exception during mail sending", e);
        }
    }
}
