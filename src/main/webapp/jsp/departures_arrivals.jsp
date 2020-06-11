<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>

<html>
    <head>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_arr_and_dep" var="arr_dep_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.arrivals" var="arr_button" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.departures" var="dep_button" />
        <fmt:message bundle="${loc}" key="local.send_button" var="send_button" />
        <fmt:message bundle="${loc}" key="local.message.no_flights" var="no_flights_mes" />

        <title>${arr_dep_label}</title>

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
        <script src="${pageContext.request.contextPath}/design/js/ajax-request.js" charset="UTF-8"></script>

        <script>
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_departures_arrivals&from=${requestScope.from}"/>').hide();
                });
            });
        </script>

    </head>
    <body>

        <c:if test="${requestScope.from eq 'main'}">
            <jsp:include page="parts/header.jsp"/>
        </c:if>

        <c:if test="${requestScope.from eq 'user'}">
            <jsp:include page="../WEB-INF/views/parts/header.jsp"/>
        </c:if>

        <form action="" method="post" id="calendar">
            <input type="hidden" name="command" value="show_flights"/>
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
            <input type="submit" id="submit" value="${send_button}"/>
        </form>
        <div class="arr_dep" id="arr_table">
            <jsp:include page="parts/arrivals_table.jsp"/>
        </div>
        <div class="arr_dep" id="dep_table">
            <jsp:include page="parts/departures_table.jsp"/>
        </div>
        <div id="noFlights">
            <h3>${no_flights_mes}</h3>
        </div>
    </body>
</html>
