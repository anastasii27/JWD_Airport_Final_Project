<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <title>${sessionScope.user.name} ${sessionScope.user.surname}</title>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="local.button.name.en" var="en_button" />
        <fmt:message bundle="${loc}" key="local.label.name" var="name_label" />
        <fmt:message bundle="${loc}" key="local.label.surname" var="surname_label" />
        <fmt:message bundle="${loc}" key="local.label.start_year" var="start_label" />
        <fmt:message bundle="${loc}" key="local.label.role" var="role_label" />
        <fmt:message bundle="${loc}" key="local.label.plane" var="plane_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_time" var="dep_time_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_time" var="dest_time_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_city" var="dest_city_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_city" var="dep_city_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_tab_header" var="f_tab_label" />
        <fmt:message bundle="${loc}" key="local.label.groups_tab_header" var="g_tab_label" />
        <fmt:message bundle="${loc}" key="local.label.creating_date" var="data_label" />
        <fmt:message bundle="${loc}" key="local.label.group_name" var="group_label" />
        <fmt:message bundle="${loc}" key="local.label.flight" var="flight_label" />


        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

        <script>
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input type="hidden" name="url" value="${pageContext.request.contextPath}/mmm?action=show_user_page"/>');
                });
            });
        </script>

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
                <th>${flight_label}</th><th>${dep_time_label}</th> <th>${dep_city_label}</th>
                <th>${dest_time_label}</th><th>${dest_city_label}</th><th>${plane_label}</th>
            </tr>
            <c:forEach items="${sessionScope.flight}" var="flight_item">
                <tr onclick="document.location.href= '${pageContext.request.contextPath}/mmm?action=show_flight_info&group=${flight_item.groupName}&flightNumber=${flight_item.flightNumber}'">
                    <td>${flight_item.flightNumber}</td>
                    <td>${flight_item.departureTime}</td>
                    <td>${flight_item.departureCity}(${flight_item.departureAirportShortName})</td>
                    <td>${flight_item.destinationTime}</td>
                    <td>${flight_item.destinationCity}(${flight_item.destinationAirportShortName})</td>
                    <td>${flight_item.planeModel}</td>
                </tr>
            </c:forEach>
        </table>

        <c:set var = "role" scope = "session" value = "${user.role}"/>
        <c:if test = "${role eq 'pilot' || role eq 'stewardess'}">
            <h3>${g_tab_label}: </h3>
            <table class ="table" border="2">
                <tr><th>${group_label} </th><th>${data_label}</th></tr>
                <c:forEach items="${group}" var="group_item">
                    <tr>
                        <td>${group_item.name}</td>
                        <td>${group_item.dateOfCreating}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

<%--        <c:set var = "url" scope = "session" value = "${pageContext.request.contextPath}/mmm?action=show_user_page"/>--%>

    </body>
</html>
