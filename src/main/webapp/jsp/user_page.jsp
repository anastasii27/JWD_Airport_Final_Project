<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <title>Flights</title>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="local.button.name.en" var="en_button" />
        <fmt:message bundle="${loc}" key="local.label.name" var="name_label" />
        <fmt:message bundle="${loc}" key="local.label.surname" var="surname_label" />
        <fmt:message bundle="${loc}" key="local.label.start_year" var="start_label" />
        <fmt:message bundle="${loc}" key="local.label.role" var="role_label" />
        <fmt:message bundle="${loc}" key="local.label.plane" var="plane_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_date" var="dep_date_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_time" var="dep_time_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_date" var="dest_date_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_time" var="dest_time_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_airport" var="dest_airport_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_city" var="dest_city_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_country" var="dest_country_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_airport" var="dep_airport_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_city" var="dep_city_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_country" var="dep_country_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_tab_header" var="f_tab_label" />
        <fmt:message bundle="${loc}" key="local.label.groups_tab_header" var="g_tab_label" />
        <fmt:message bundle="${loc}" key="local.table.creating_date" var="data_table" />
        <fmt:message bundle="${loc}" key="local.table.group" var="group_table" />
        <fmt:message bundle="${loc}" key="local.exit_button" var="exit_table" />

    </head>
    <body>

        <jsp:include page="header.jsp"/>

        <p>${name_label}: <c:out value= "${sessionScope.user.name}" /></p>
        <p>${surname_label}: <c:out value= "${sessionScope.user.surname}" /></p>
        <p>Email: <c:out value= "${sessionScope.user.email}" /></p>
        <p>${role_label}: <c:out value= "${sessionScope.user.role}" /></p>
        <p>${start_label}: <c:out value= "${sessionScope.user.careerStartYear}" /></p>

        <h3>${f_tab_label}: </h3>
        <table class ="table" border="2">
            <tr>
                <th>${plane_label}</th><th>${dep_date_label}</th><th>${dep_time_label}</th><th>${dest_date_label}</th>
                <th>${dest_time_label}</th><th>${dest_airport_label}</th><th>${dest_city_label}</th><th>${dest_country_label}</th>
                <th>${dep_airport_label}</th><th>${dep_city_label}</th><th>${dep_country_label}</th>
            </tr>
            <c:forEach items="${flight}" var="flight_item">
                <tr>
                    <td>${flight_item.planeModel}</td>
                    <td>${flight_item.departureDate}</td>
                    <td>${flight_item.departureTime}</td>
                    <td>${flight_item.destinationDate}</td>
                    <td>${flight_item.destinationTime}</td>
                    <td>${flight_item.destinationAirport}</td>
                    <td>${flight_item.destinationCity}</td>
                    <td>${flight_item.destinationCountry}</td>
                    <td>${flight_item.departureAirport}</td>
                    <td>${flight_item.departureCity}</td>
                    <td>${flight_item.departureCountry}</td>
                </tr>
            </c:forEach>
        </table>

        <c:set var = "role" scope = "session" value = "${user.role}"/>
        <c:if test = "${role.equals('pilot') || role.equals('stewardess')}">
            <h3>${g_tab_label}: </h3>
            <table class ="table" border="2">
                <tr><th>${group_table} </th><th>${data_table}</th></tr>
                <c:forEach items="${group}" var="group_item">
                    <tr>
                        <td>${group_item.name}</td>
                        <td>${group_item.dateOfCreating}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:set var = "url" scope = "session" value = "${pageContext.request.requestURL}"/>

    </body>
</html>
