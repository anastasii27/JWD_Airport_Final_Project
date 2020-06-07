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
<%--        <fmt:message bundle="${loc}" key="local.label.menu_user_empl" var="empl_label" />--%>
        <fmt:message bundle="${loc}" key="local.label.menu_user_crew" var="crew_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_park" var="park_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_lang" var="lang_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_my_flights" var="my_fl_label" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/header.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    </head>
    <body>
        <nav class="navbar navbar-expand-lg ">
            <a class="navbar-brand" href="#">Navbar</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">

                    <c:set var = "role" scope = "session" value = "${user.role}"/>
                    <jsp:useBean id="now" class="java.util.Date"/>
                    <fmt:formatDate type="time" value="${now}" pattern="yyyy-MM-dd" var="depDate"/>
                    <c:choose>
                        <c:when test = "${role eq 'pilot' || role eq 'stewardess'}">
                            <li class="nav-item active">
                                <a class="nav-link" href="${pageContext.request.contextPath}/mmm?action=show_user_page">${my_page}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="${pageContext.request.contextPath}/mmm?action=show_my_flights&departure_date=${depDate}">${my_fl_label}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="${pageContext.request.contextPath}/mmm?action=show_flights_page">${flights_label}</a>
                            </li>
                        </c:when>
                        <c:when test = "${role eq 'dispatcher'}">
                            <li class="nav-item active">
                                <a class="nav-link" href="#">${my_page}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="#">${flights_label}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">${empl_label}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">${crew_label}</a>
                            </li>
                        </c:when>
                        <c:when test = "${role eq 'admin'}">
                            <li class="nav-item active">
                                <a class="nav-link" href="#">${flights_label}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="#">${empl_label}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">${park_label}</a>
                            </li>
                        </c:when>
                    </c:choose>

                </ul>

                <form class = "lang my-2 mr-1" action="mmm" method="get">
                    <input type="hidden" name="action" value="change_language" />
                    <input type="hidden" name="local" value="ru" />
                    <input class = "btn-md  my-2 my-sm-0 mr-2"  type="submit" value="${ru_button}" /><br />
                </form>

                <form class = "lang my-2 mr-4" action="mmm" method="get">
                    <input type="hidden" name="action" value="change_language"/>
                    <input type="hidden" name="local" value="en" />
                    <input class = "btn-md  my-2 my-sm-0 mr-2"  type="submit" value="${en_button}" /><br />
                </form>

                <form class="form-inline my-2 my-lg-0" action="mmm" method="get">
                    <input type="hidden" name="action" value="sign_out"/>
                    <input class = "btn-md  my-2 my-sm-0 mr-2" type="submit" value="${exit_button}"/> <br/>
                </form>
            </div>
        </nav>
    </body>
</html>
