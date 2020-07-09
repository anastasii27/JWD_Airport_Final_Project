package by.epam.tr.controller.filter;

import by.epam.tr.controller.util.RequestToMapParser;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@WebFilter("/*")
public class  DateFormatFilter implements Filter {
    private static final String DATE_PATTERN ="^(202[0-9])[-]([0]?[1-9]|[1][0-2])[-]([0]?[1-9]|[1|2][0-9]|[3][0|1])$";
    private static final String KEY_VALUE ="date";
    private static final String DEFAULT_DATE ="0000-00-00";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpServletRequest);
        Map<String, String> requestParams = RequestToMapParser.toRequestParamsMap(requestWrapper);

        for (String key: requestParams.keySet()) {
            if(key.contains(KEY_VALUE)){
                checkDate(requestWrapper, requestParams.get(key), key);
            }
        }
        filterChain.doFilter(requestWrapper,servletResponse);
    }

    private void checkDate(HttpServletRequestWrapper requestWrapper, String date, String key){
        if(checkWithDatePattern(date)){//todo
            System.out.println("fuck");
            requestWrapper.removeAttribute(key);
            Map<String, String> requestParams = RequestToMapParser.toRequestParamsMap(requestWrapper);
            System.out.println(requestParams);
            requestWrapper.setAttribute(key,DEFAULT_DATE);
            System.out.println(requestParams);
        }
    }

    private boolean checkWithDatePattern(String date){
        return date.trim().matches(DATE_PATTERN);
    }
    @Override
    public void destroy() {

    }
}
