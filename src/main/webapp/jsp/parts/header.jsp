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
        <fmt:message bundle="${loc}" key="local.label.menu_main_arr_and_dep" var="arr_dep_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_about" var="about_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_park" var="park_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main_lang" var="lang_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_main" var="main_label" />
        <fmt:message bundle="${loc}" key="local.enter_button" var="enter_label" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/header.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/jquery-ui.min.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/design/js/full-js.js" charset="UTF-8"></script>

    </head>
    <body>
        <nav class="navbar navbar-expand-lg ">
            <a class="navbar-brand" href="#">Navbar</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0" id="menu">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">${main_label}</a>
                    </li>
                    <li class="nav-item ">
                        <a class="nav-link" href="${pageContext.request.contextPath}/airport?action=show_departures_arrivals&from=main">${arr_dep_label}</a>
                    </li>
                    <li class="nav-item ">
                        <a class="nav-link" href="#">${about_label}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">${park_label}</a>
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

                <form class="form-inline my-2 my-lg-0" action="airport" method="get">
                    <input type="hidden" name="action" value="show_sign_in_page"/>
                    <input class = "btn-md  my-2 my-sm-0 mr-2" type="submit" value="${enter_label}"/> <br/>
                </form>
            </div>
        </nav>
    </body>
</html>