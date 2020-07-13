<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <title>Header</title>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="local.button.name.en" var="en_button" />
        <fmt:message bundle="${loc}" key="local.exit_button" var="exit_button" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_my_page" var="my_page" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_arr_and_dep" var="flights_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_crew" var="crew_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_lang" var="lang_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_my_flights" var="my_fl_label" />
        <fmt:message bundle="${loc}" key="local.label.admin_menu" var="fl_manage_label" />
        <fmt:message bundle="${loc}" key="local.label.create_flight.create" var="fl_create_label"/>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/header.css"/>

    </head>
    <body>
        <nav class="navbar navbar-expand-lg ">
            <a class="navbar-brand" href="#">Navbar</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0" id="menu">

                    <c:set var = "role" scope = "session" value = "${user.role}"/>
                    <jsp:useBean id="now" class="java.util.Date"/>
                    <fmt:formatDate type="time" value="${now}" pattern="yyyy-MM-dd" var="depDate"/>
                    <c:choose>
                        <c:when test = "${role eq 'pilot' || role eq 'steward'}">
                            <li class="nav-item ">
                                <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_user_page">${my_page}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_my_flights&departure_date=${depDate}&surname=${sessionScope.user.surname}&email=${sessionScope.user.email}">${my_fl_label}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_departures_arrivals&from=user">${flights_label}</a>
                            </li>
                        </c:when>
                        <c:when test = "${role eq 'dispatcher'}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_user_page">${my_page}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_dispatcher_flights">${my_fl_label}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_departures_arrivals&from=user">${flights_label}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_crew_page">${crew_label}</a>
                            </li>
                        </c:when>
                        <c:when test = "${role eq 'admin'}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_flight_management_page&departure_date=${depDate}">${fl_manage_label}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_create_flight_page">${fl_create_label}</a>
                            </li>
                        </c:when>
                    </c:choose>
                </ul>

                <form class = "lang my-2 mr-1" action="airport" method="get">
                    <input type="hidden" name="action" value="change_language" />
                    <input type="hidden" name="local" value="ru" />
                    <input class = "btn-md  my-2 my-sm-0 mr-2"  type="submit" value="${ru_button}" /><br />
                </form>

                <form class = "lang my-2 mr-4" action="airport" method="get">
                    <input type="hidden" name="action" value="change_language"/>
                    <input type="hidden" name="local" value="en" />
                    <input class = "btn-md  my-2 my-sm-0 mr-2"  type="submit" value="${en_button}" /><br />
                </form>

                <form class="form-inline my-2 my-lg-0" action="airport" method="get">
                    <input type="hidden" name="action" value="sign_out"/>
                    <input class = "btn-md  my-2 my-sm-0 mr-2" type="submit" value="${exit_button}"/> <br/>
                </form>
            </div>
        </nav>
    </body>
</html>
