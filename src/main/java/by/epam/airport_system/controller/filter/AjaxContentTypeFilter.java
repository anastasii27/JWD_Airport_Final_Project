package by.epam.airport_system.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class AjaxContentTypeFilter implements Filter {
    private final static String RESPONSE_CONTENT_TYPE = "application/json";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType(RESPONSE_CONTENT_TYPE);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
