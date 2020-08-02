package by.epam.airport_system.service.mailing;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.service.ServiceException;

public interface MailSender {
    void sendMail(User to, String messageText) throws ServiceException;
}
