package by.epam.airport_system.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

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

    public class WrappedRequest extends HttpServletRequestWrapper{
        private static final String DATE_PATTERN ="^(202[0-9])[-]([0]?[1-9]|[1][0-2])[-]([0]?[1-9]|[1|2][0-9]|[3][0|1])$";
        private static final String TIME_PATTERN ="^([2][0-3]|[0-1][0-9]|[1-9]):([0-5][0-9])$";
        private static final String DATE_PARAM ="date";
        private static final String TIME_PARAM ="time";
        private static final String DEFAULT_DATE ="0000-00-00";
        private static final String DEFAULT_TIME ="00:00";

        WrappedRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);

            if (name.contains(DATE_PARAM) && !isDateValid(value)) {
                value = DEFAULT_DATE;
            }

            if (name.contains(TIME_PARAM) && !isTimeValid(value)) {
                value = DEFAULT_TIME;
            }

            return value;
        }

        private boolean isDateValid(String value){
            return value.trim().matches(DATE_PATTERN);
        }

        private boolean isTimeValid(String value){
            return value.trim().matches(TIME_PATTERN);
        }
    }
}
