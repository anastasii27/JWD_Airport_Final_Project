package by.epam.airport_system.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private final static String ENCODING_PARAM = "encoding";
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(ENCODING_PARAM);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
