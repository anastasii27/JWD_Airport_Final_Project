<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <title>Form</title>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.arrivals" var="arr_button" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.departures" var="dep_button" />
        <fmt:message bundle="${loc}" key="local.send_button" var="send_button" />
    </head>
    <body>
        <form action="mmm" method="get" id="calendar">
            <input type="hidden" name="action" value="show_flights"/>
            <input type="hidden" name="from" value="${requestScope.from}"/>
            <label for="piker"></label>
            <select  name= "city" id="input_city">
                <option selected></option>
                <c:forEach var="city" items="${city_with_airport}">
                    <option>${city}</option>
                </c:forEach>
            </select>
            <input type='text' name="departure_date" id= "piker" class="datepicker-here"
                   data-language="${lang}" value = "${requestScope.departure_date}"/>
            <label for="arrival_type">${dep_button}</label>
            <input type="radio" id="arrival_type" name="type" value="departure" checked>
            <label for="departure_type">${arr_button}</label>
            <input type="radio" id="departure_type" name="type" value="arrival">
            <input type="submit" value="${send_button}"/>
        </form>
    </body>
</html>