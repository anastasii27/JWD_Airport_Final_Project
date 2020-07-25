<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.plane" var="plane_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_time" var="dep_time_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_time" var="dest_time_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_city" var="dest_city_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_city" var="dep_city_label" />
        <fmt:message bundle="${loc}" key="local.label.flight" var="flight_label" />
        <fmt:message bundle="${loc}" key="local.label.status" var="status_label" />
        <fmt:message bundle="${loc}" key="local.js.lang" var="lang" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_my_flights" var="my_flights_label" />

        <title>Flights table</title>

    </head>
    <body>
        <c:set var = "result" value = "${requestScope.result}"/>
        <div id="table">
            <c:if test = "${result eq null}">
                <table class ="table" border="2" id ="flights_table">
                    <tr>
                        <th>${flight_label}</th><th>${dep_time_label}</th>
                        <th>${dep_city_label}</th><th>${dest_city_label}</th>
                        <th>${plane_label}</th><th>${status_label}</th>
                    </tr>
                    <c:forEach items="${requestScope.flight}" var="flight_item">
                        <tr class ="flights" depDate="${flight_item.departureDate}">
                            <td class="info">${flight_item.flightNumber}</td>
                            <td class="info">${flight_item.departureTime}</td>
                            <td class="info">${flight_item.departureCity}(${flight_item.departureAirportShortName})</td>
                            <td class="info">${flight_item.destinationCity}(${flight_item.destinationAirportShortName})</td>
                            <td class="info">${flight_item.planeModel}</td>
                            <td class="info">${flight_item.status}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
        <c:if test = "${result ne null}">
            <div style="width: 26vw; margin-top:3vh; margin-left: auto; margin-right: auto;">
                <h3>${result}</h3>
            </div>
        </c:if>
    </body>
</html>