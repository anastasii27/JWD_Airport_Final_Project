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
    </head>
    <body>
        <c:if test="${requestScope.result ne null}">
            <c:out value="${requestScope.result}"/>
        </c:if>

        <c:if test="${requestScope.result eq null}">
            <table class ="table" border="2">
                <tr>
                    <th>${flight_label}</th><th>${dest_city_label}</th><th>${plane_label}</th>
                    <th>${dep_time_label}</th><th>${status_label}</th>
                </tr>
                <c:forEach items="${requestScope.flight}" var="flight_item">
                    <tr onclick="document.location.href= '${pageContext.request.contextPath}/mmm?action=show_flight_info&flight_number=${flight_item.flightNumber}&departure_date=${flight_item.departureDate}'">
                        <td>${flight_item.flightNumber}</td>
                        <td>${flight_item.destinationCity}(${flight_item.destinationAirportShortName})</td>
                        <td>${flight_item.planeModel}</td>
                        <td>${flight_item.departureTime}</td>
                        <td>${flight_item.status}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>