package by.epam.airport_system.controller.filter;

import by.epam.airport_system.bean.Role;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.front.CommandName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private final static String PATH_TO_REDIRECT = "/jsp/sign_in.jsp";
    private final static String NO_ROLE = "no_role";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String action =  req.getParameter(ParameterName.ACTION);
        User user = (User) req.getSession().getAttribute(ParameterName.USER);

        if(action!=null) {
            Role [] roles = getActionPermittedRoles(action);

            if(!doRolesApproachForAction(roles, user)){
               req.getRequestDispatcher(PATH_TO_REDIRECT).forward(req, resp);
            }
        }

        filterChain.doFilter(req, resp);
    }

    private boolean doRolesApproachForAction(Role [] roles, User user) {
        for (Role role: roles) {
            String roleName = role.name().toLowerCase();

            if(roleName.equals(NO_ROLE)) {
                return true;
            }
            if (user != null && roleName.equals(user.getRole())) {
               return true;
            }
        }

        return false;
    }

    private Role[] getActionPermittedRoles(String action){
        CommandName commandName;

        try {
            commandName =  CommandName.valueOf(action.toUpperCase());
        }catch (IllegalArgumentException e){
            commandName = CommandName.NO_SUCH_COMMAND;
        }

        return commandName.getRoles();
    }

    @Override
    public void destroy() {

    }
}
