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

        <title>${my_flights_label}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/flight_search_form.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/design/css/datepicker.min.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/jquery-ui.min.js" charset="UTF-8"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/design/js/datepicker.min.js" charset="UTF-8"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/i18n/datepicker.en.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/i18n/datepicker.ru.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/full-js.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.2/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/validation.js" charset="UTF-8"></script>

        <script>
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/mmm?action=show_my_flights&departure_date=${requestScope.departure_date}"/>').hide();
                });
            });
        </script>

    </head>
    <body>

        <jsp:include page="header.jsp"/>

        <form action="mmm" method="get" id="calendar">
            <input type="hidden" name="action" value="show_my_flights"/>
            <label for="piker"></label>
            <input type='text' name="departure_date" id= "piker" class="datepicker-here"
                   data-language="${lang}" value = "${requestScope.departure_date}"/>
            <input type="submit" value="Send"/>
        </form>

        <c:set var = "result" value = "${requestScope.result}"/>
        <c:if test = "${result eq null}">

            <table class ="table" border="2">
                <tr>
                    <th>${flight_label}</th><th>${dep_time_label}</th> <th>${dep_city_label}</th>
                    <th>${dest_time_label}</th><th>${dest_city_label}</th><th>${plane_label}</th>
                    <th>${status_label}</th>
                </tr>
                <c:forEach items="${requestScope.flight}" var="flight_item">
                    <tr onclick="document.location.href= '${pageContext.request.contextPath}/mmm?action=show_flight_info&flight_number=${flight_item.flightNumber}&departure_date=${flight_item.departureDate}'">
                        <td>${flight_item.flightNumber}</td>
                        <td>${flight_item.departureTime}</td>
                        <td>${flight_item.departureCity}(${flight_item.departureAirportShortName})</td>
                        <td>${flight_item.destinationTime}</td>
                        <td>${flight_item.destinationCity}(${flight_item.destinationAirportShortName})</td>
                        <td>${flight_item.planeModel}</td>
                        <td>${flight_item.status}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:if test = "${result ne null}">
            <c:out value="${result}"/>
        </c:if>
    </body>
</html>
