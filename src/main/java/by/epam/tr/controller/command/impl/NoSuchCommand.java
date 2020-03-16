package by.epam.tr.controller.command.impl;

import by.epam.tr.controller.JSPPageName;
import by.epam.tr.controller.command.Command;
import javax.servlet.http.HttpServletRequest;

public class NoSuchCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return JSPPageName.ERROR_PAGE;
    }
}
