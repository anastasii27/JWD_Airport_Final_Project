<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <title>Departures</title>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.plane" var="plane_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_time" var="dep_time_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_city" var="dest_city_label" />
        <fmt:message bundle="${loc}" key="local.label.flight" var="flight_label" />
        <fmt:message bundle="${loc}" key="local.label.status" var="status_label" />

        <script src="${pageContext.request.contextPath}/design/js/ajax-request.js" charset="UTF-8"></script>

    </head>
    <body>
            <table class ="table" id="departures" border="2">
                <tr>
                    <th>${flight_label}</th><th>${dest_city_label}</th><th>${plane_label}</th>
                    <th>${dep_time_label}</th><th>${status_label}</th>
                </tr>

            </table>
    </body>
</html>