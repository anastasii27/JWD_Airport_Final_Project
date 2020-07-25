<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.js.lang" var="lang" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_my_flights" var="my_flights_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.date" var="date_label" />
        <fmt:message bundle="${loc}" key="local.button.search.show" var="show_button" />
        <fmt:message bundle="${loc}" key="local.button.create_flight.create" var="create_button" />
        <fmt:message bundle="${loc}" key="local.label.admin_menu" var="title_label" />
        <fmt:message bundle="${loc}" key="local.message.error.flight_delete" var="delete_info" />
        <jsp:useBean id="now" class="java.util.Date"/>
        <fmt:formatDate type="time" value="${now}" pattern="yyyy-MM-dd" var="today"/>

        <title>${title_label}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/flights-table.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/validation-plug-in.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/design/css/datepicker.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/admin-flights.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/jquery-ui.min.js" charset="UTF-8"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/design/js/datepicker.min.js" charset="UTF-8"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/i18n/datepicker.en.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/i18n/datepicker.ru.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/ajax-request.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/full-js.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.2/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/validation.js" charset="UTF-8"></script>

        <script>
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_flight_management_page&departure_date=${today}"/>').hide();
                });
            });
        </script>

    </head>
    <body lang="${lang}">
        <jsp:include page="parts/header.jsp"/>
        <div id = "no_delete_mes" aria-hidden="true" style="background: #007bff26">${delete_info}</div>
        <div class="col-md-5 form">
            <form action="airport" method="get" id="my_flights_form">
                <input type="hidden" name="action" value="show_flight_management_page"/>
                <div class="form-group row">
                    <div class="for_label">
                        <label for="my_flights_piker">${date_label}</label>
                    </div>
                    <input type='text' name="departure_date" id= "my_flights_piker" class="datepicker-here"
                           data-language="${lang}"/>
                    <div id="btn" dep_date = "${requestScope.departure_date}">
                        <button type="submit" class="btn btn-primary">${show_button}</button>
                    </div>
                </div>
            </form>
        </div>
        <div id="admin_flights">
            <jsp:include page="parts/flights_table.jsp"/>
        </div>
        <jsp:include page="parts/edit_flight_modal.jsp"/>
        <jsp:include page="parts/flight_info_modal.jsp"/>
    </body>
</html>
