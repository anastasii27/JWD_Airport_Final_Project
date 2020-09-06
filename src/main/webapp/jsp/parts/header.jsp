<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="local.button.name.en" var="en_button" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_arr_and_dep" var="arr_dep_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_about" var="about_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_timetable" var="timetable_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_lang" var="lang_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main" var="main_label" />
        <fmt:message bundle="${loc}" key="local.enter_button" var="enter_label" />

        <title>Header</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/header.css"/>

    </head>
    <body>
        <nav class="navbar navbar-expand-lg ">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">
                <img src="${pageContext.request.contextPath}/design/img/label.jpg" alt="BS">
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0" id="menu">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}">${main_label}</a>
                    </li>
                    <li class="nav-item ">
                        <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_departures_arrivals">${arr_dep_label}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_flight_timetable_page">${timetable_label}</a>
                    </li>
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

                <c:if test="${sessionScope.user eq null}">
                    <form class="form-inline my-2 my-lg-0" action="airport" method="get">
                        <input type="hidden" name="action" value="show_sign_in_page"/>
                        <input class = "btn-md  my-2 my-sm-0 mr-2" type="submit" value="${enter_label}"/> <br/>
                    </form>
                </c:if>
                <c:if test="${sessionScope.user ne null}">
                    <c:set var = "role" scope = "session" value = "${user.role}"/>
                    <button type="button" class="btn-lg back" onclick="document.location.href= '${pageContext.request.contextPath}/airport?action=show_user_page'">
                            ${user.name} ${user.surname}
                    </button>
                </c:if>
            </div>
        </nav>
    </body>
</html>