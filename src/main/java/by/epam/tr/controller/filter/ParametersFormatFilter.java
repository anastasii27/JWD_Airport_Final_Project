package by.epam.tr.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

@WebFilter("/*")
public class ParametersFormatFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        filterChain.doFilter(new WrappedRequest(httpServletRequest),servletResponse);
    }

    @Override
    public void destroy() {

    }

    private class WrappedRequest extends HttpServletRequestWrapper{
        private static final String DATE_PATTERN ="^(202[0-9])[-]([0]?[1-9]|[1][0-2])[-]([0]?[1-9]|[1|2][0-9]|[3][0|1])$";
        private static final String PARAM_NAME ="date";
        private static final String DEFAULT_DATE ="0000-00-00";

        WrappedRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);

            if (name.contains(PARAM_NAME) && !checkWithDatePattern(value)) {
                value = DEFAULT_DATE;
            }
            return value;
        }

        private boolean checkWithDatePattern(String date) {
            return date.trim().matches(DATE_PATTERN);
        }
    }
}
