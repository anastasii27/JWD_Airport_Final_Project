<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <title>Result</title>
    </head>
    <body>
        <c:out value= "${result}" />
        <button type="button" class="btn btn-info" onclick="document.location.href= '${sessionScope.previous_page_url}'">
            Назад
        </button>
    </body>
</html>
