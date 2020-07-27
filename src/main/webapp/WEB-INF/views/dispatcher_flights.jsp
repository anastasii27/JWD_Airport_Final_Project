<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/custom_tags/customTags" prefix="ct"%>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.plane" var="plane_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.time" var="time_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.arrivals" var="arrivals_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.departures" var="departures_label" />
        <fmt:message bundle="${loc}" key="local.label.flight" var="flight_label" />
        <fmt:message bundle="${loc}" key="local.label.status" var="status_label" />
        <fmt:message bundle="${loc}" key="local.js.lang" var="lang" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_my_flights" var="my_flights_label" />

        <title>${my_flights_label}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/dispatcher-flights.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/jquery-ui.min.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/design/js/ajax-request.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/full-js.js" charset="UTF-8"></script>

        <script>
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_dispatcher_flights"/>').hide();
                });
            });
        </script>

    </head>
    <body>
        <jsp:include page="parts/header.jsp"/>

        <jsp:useBean id="now" class="java.util.Date" />
        <fmt:formatDate var="today" value="${now}" pattern="yyyy-MM-dd"/>
        <div id="date_range">
            <ct:date-range startDate="${today}" rangeLength="1"/>
        </div>

        <c:set var = "result" value = "${requestScope.result}"/>
        <c:if test = "${result eq null}">
            <div id="disp_table">
                <table class ="table" border="2">
                    <tr>
                        <th>${flight_label}</th><th>${time_label}</th>
                        <th colspan="2">${arrivals_label}/${departures_label}</th>
                        <th>${plane_label}</th><th>${status_label}</th>
                    </tr>
                    <c:forEach items="${requestScope.flight}" var="flight_item">
                        <tr>
                            <td>${flight_item.flightNumber}</td>
                            <td>${flight_item.departureTime}</td>
                            <td>${flight_item.departureCity}(${flight_item.departureAirportShortName})</td>
                            <td>${flight_item.destinationCity}(${flight_item.destinationAirportShortName})</td>
                            <td>${flight_item.planeModel}</td>
                            <td>${flight_item.status}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <c:if test = "${result ne null}">
            <div id="info">
                <c:out value="${result}"/>
            </div>
        </c:if>
        <jsp:include page="../../jsp/parts/footer.jsp"/>
    </body>
</html>
