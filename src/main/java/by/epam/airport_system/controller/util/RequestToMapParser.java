package by.epam.airport_system.controller.util;

import by.epam.airport_system.controller.constant_parameter.ParameterName;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestToMapParser {

    public static Map<String, String> toRequestParamsMap(ServletRequest request){
        String key;
        Map<String, String> params =  new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();

        while (keys.hasMoreElements()){
            key = keys.nextElement();
            params.put(key, request.getParameter(key));
        }

        addLanguageValueToMap(request, params);

        return params;
    }

    public static void addLanguageValueToMap(ServletRequest request, Map<String, String> params) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String lang = (String) httpServletRequest.getSession().getAttribute(ParameterName.LOCAL);

        params.put(ParameterName.LOCAL, lang);
    }
}
