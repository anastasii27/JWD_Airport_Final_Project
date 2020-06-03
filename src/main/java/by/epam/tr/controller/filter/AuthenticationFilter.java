package by.epam.tr.controller.filter;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.CommandName;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse resp = (HttpServletResponse) servletResponse;
//        String action =  req.getParameter("action");
//        User user = (User) req.getSession().getAttribute("user");
//        CommandName commandName;
//        String accessType;
//
//        if(action == null) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//
//        commandName =  CommandName.valueOf(action.toUpperCase());
//        accessType = commandName.getAccessType();
//
//        if(user == null && accessType.equals("authorized")){
//            resp.sendRedirect("mmm?action=show_sign_in_page");
//        }else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
