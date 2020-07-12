<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.flight" var="flight_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_time" var="dep_time_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_time" var="dest_time_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.date" var="date_label" />
        <fmt:message bundle="${loc}" key="local.label.plane" var="plane_label" />
        <fmt:message bundle="${loc}" key="local.message.no_flights" var="no_flights_mes" />
        <fmt:message bundle="${loc}" key="local.label.search.from" var="from_label" />
        <fmt:message bundle="${loc}" key="local.label.search.to" var="to_label" />
        <fmt:message bundle="${loc}" key="local.button.search.show" var="show_btn" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_timetable" var="timetable_label" />

        <title>${timetable_label}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/validation-plug-in.css"/>
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
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_flight_timetable_page"/>').hide();
                });
            });
        </script>

    </head>
    <body>
        <jsp:include page="parts/header.jsp"/>
        <form action="" method="get" id="flight_search_form">
            <div class="form-group row">
                <label for="dep_city">${from_label}</label>
                <select name= "dep_city" id="dep_city" >
                    <option selected></option>
                    <c:forEach var="city" items="${city_with_airport}">
                        <option>${city}</option>
                    </c:forEach>
                </select>
                <label for="dest_city" class="label">${to_label}</label>
                <select name= "dest_city" id="dest_city">
                    <option selected></option>
                    <c:forEach var="city" items="${city_with_airport}">
                        <option>${city}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <button type="submit" id="search_submit" class="btn btn-primary">${show_btn}</button>
            </div>
        </form>
        <div id="search_table">
            <table class ="table" id="timetable" border="2">
                <tr>
                    <th>${flight_label}</th><th>${dep_time_label}</th><th>${dest_time_label}</th>
                    <th>${date_label}</th><th>${plane_label}</th>
                </tr>

            </table>
        </div>
        <div id="no_flights">
            <h3>${no_flights_mes}</h3>
        </div>
    </body>
</html>
