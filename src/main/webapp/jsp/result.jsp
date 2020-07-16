<%@ page  contentType="text/html; charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.back_button" var="back_btn"/>

        <title>Result</title>

    </head>
    <body>
        <c:out value= "${result}" />
        <button type="button" class="btn btn-info" onclick="document.location.href= '${sessionScope.previous_page_url}'">
            ${back_btn}
        </button>
    </body>
</html>
