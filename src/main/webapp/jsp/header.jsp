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
        <fmt:message bundle="${loc}" key="local.label.menu_user_my_page" var="my_page" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_flights" var="flights" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_empl_" var="empl" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_crew" var="crew" />
        <fmt:message bundle="${loc}" key="local.label.menu_admin_park" var="park" />
        <fmt:message bundle="${loc}" key="local.exit_button" var="exit_table" />

        <link rel="stylesheet" href="../css/header.css"/>
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
                    <c:choose>
                        <c:when test = "${role.equals('pilot') || role.equals('stewardess')}">
                            <li class="nav-item active">
                                <a class="nav-link" href="#">${my_page}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="#">${flights}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">${empl}</a>
                            </li>
                        </c:when>
                        <c:when test = "${role.equals('dispatcher')}">
                            <li class="nav-item active">
                                <a class="nav-link" href="#">${my_page}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="#">${flights}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">${empl}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">${crew}</a>
                            </li>
                        </c:when>
                        <c:when test = "${role.equals('admin')}">
                            <li class="nav-item active">
                                <a class="nav-link" href="#">${flights}</a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link" href="#">${empl}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">${park}</a>
                            </li>
                        </c:when>
                    </c:choose>

                </ul>

                <ul class="navbar-nav mr-3">
                    <li class="nav-item dropdown ">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Язык
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/mmm?action=change_language&local=ru">${ru_button}</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/mmm?action=change_language&local=en">${en_button}</a>
                        </div>
                    </li>
                </ul>

                <form class="form-inline my-2 my-lg-0" action="../mmm" method="post">
                    <input type="hidden" name="action" value="sign_out"/>
                    <input class = "btn-md  my-2 my-sm-0 mr-2" type="submit" value="${exit_table}"/> <br/>
                </form>

            </div>
        </nav>
    </body>
</html>
