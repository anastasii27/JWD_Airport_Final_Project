package by.epam.airport_system.controller.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final String ATTRIBUTE_NAME = "local";
    private static final String DEFAULT_LANGUAGE = "ru";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute(ATTRIBUTE_NAME, DEFAULT_LANGUAGE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}