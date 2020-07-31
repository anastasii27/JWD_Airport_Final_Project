package by.epam.airport_system.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class AjaxContentTypeFilter implements Filter {
    private final static String CONTENT_TYPE_PARAM = "content-type";
    private String contentType;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        contentType = filterConfig.getInitParameter(CONTENT_TYPE_PARAM);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType(contentType);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
