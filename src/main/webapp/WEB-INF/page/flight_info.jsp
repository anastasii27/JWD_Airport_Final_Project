<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>

    <head>
        <title>Flight information</title>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.pilot" var="pilot_label" />
        <fmt:message bundle="${loc}" key="local.label.steward" var="steward_label" />
        <fmt:message bundle="${loc}" key="local.label.flight" var="flight_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.date" var="date_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.time" var="time_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.country" var="country_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.city" var="city_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.airport" var="airport_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.arrivals" var="arrivals_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.departures" var="departures_label" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

        <script>
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input type="hidden" name="url" value="${pageContext.request.contextPath}/mmm?action=show_flight_info&group=${requestScope.group}&flight_number=${requestScope.flight_number}&departure_date=${requestScope.departure_date}"/>');
                });
            });
        </script>

    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <div class="row">
            <div class = 'col-md-4'>
                <h2>${pilot_label}</h2>
                    <c:if test="${requestScope.result ne null}">
                        <c:out value="${requestScope.result}"/>
                    </c:if>
                    <c:if test="${requestScope.result eq null}">
                        <c:forEach items="${requestScope.users}" var="user">
                            <c:set var = "role" scope = "request" value = "${user.role}"/>
                                <c:if test="${role eq 'pilot'}">
                                    <c:out value="${user.name} ${user.surname}"/>
                                </c:if>
                        </c:forEach>
                    </c:if>
            </div>
            <div class = 'col-md-4'>
                <h2>${steward_label}</h2>
                    <c:if test="${requestScope.result ne null}">
                        <c:out value="${requestScope.result}"/>
                    </c:if>
                    <c:if test="${requestScope.result eq null}">
                        <c:forEach items="${requestScope.users}" var="user">
                            <c:set var = "role" scope = "request" value = "${user.role}"/>
                                <c:if test="${role eq 'stewardess'}">
                                    <c:out value="${user.name} ${user.surname}"/><br/>
                                </c:if>
                        </c:forEach>
                    </c:if>
            </div>
            <div class = 'col-md-4 container'>
                <div class = "row">
                    <h2>${flight_label}</h2>
                    <c:out value=" ${requestScope.flight_number}"/>
                </div>
                <div>
                    <h2>${departures_label}</h2>
                    <c:if test="${requestScope.result1 ne null}">
                        <c:out value="${requestScope.result1}"/>
                    </c:if>
                    <c:if test="${requestScope.result1 eq null}">
                        <p>${time_label}: <c:out value="${requestScope.flight.departureTime}"/></p>
                        <p>${date_label}: <c:out value="${requestScope.flight.departureDate}"/></p>
                        <p>${country_label}: <c:out value="${requestScope.flight.departureCountry}"/></p>
                        <p>${city_label}: <c:out value="${requestScope.flight.departureCity}"/></p>
                        <p>${airport_label}: <c:out value="${requestScope.flight.departureAirport} (${requestScope.flight.departureAirportShortName})"/></p>
                    </c:if>
                </div>
                <div>
                    <h2>${arrivals_label}</h2>
                    <c:if test="${requestScope.result1 ne null}">
                        <c:out value="${requestScope.result1}"/>
                    </c:if>
                    <c:if test="${requestScope.result1 eq null}">
                        <p>${time_label}: <c:out value="${requestScope.flight.destinationTime}"/></p>
                        <p>${date_label}:  <c:out value="${requestScope.flight.destinationDate}"/></p>
                        <p>${country_label}: <c:out value="${requestScope.flight.destinationCountry}"/></p>
                        <p>${city_label}: <c:out value="${requestScope.flight.destinationCity}"/></p>
                        <p>${airport_label}: <c:out value="${requestScope.flight.destinationAirport} (${requestScope.flight.destinationAirportShortName})"/></p>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
