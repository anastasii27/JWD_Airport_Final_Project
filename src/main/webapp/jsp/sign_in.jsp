<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>

<html>
    <head>

        <title>Sign in</title>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.enter_button" var="sign_in_label" />
        <fmt:message bundle="${loc}" key="local.label.login" var="login_label" />
        <fmt:message bundle="${loc}" key="local.label.password" var="password_label" />
        <fmt:message bundle="${loc}" key="local.enter_button" var="enter_button" />
        <fmt:message bundle="${loc}" key="local.label.create_account" var="create_account_label" />
        <fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="local.button.name.en" var="en_button" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/sign_in.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.1/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/validation.js"></script>

    </head>
    <body>
        <nav class="navbar navbar-expand-lg">
            <div class="collapse navbar-collapse lang">

                <form class="my-2 mr-1" action="mmm" method="get">
                    <input type="hidden" name="action" value="change_language" />
                    <input type="hidden" name="local" value="ru" />
                    <input type="hidden" name="url" value="${pageContext.request.contextPath}/mmm?action=show_sign_in_page" />
                    <input class="btn-md  my-2 my-sm-0 mr-2" type="submit" value="${ru_button}" /><br />
                </form>


                <form class="my-2 mr-1" action="mmm" method="get">
                    <input type="hidden" name="action" value="change_language"/>
                    <input type="hidden" name="local" value="en" />
                    <input type="hidden" name="url" value="${pageContext.request.contextPath}/mmm?action=show_sign_in_page" />
                    <input class="btn-md  my-2 my-sm-0 mr-2" type="submit" value="${en_button}" /><br />
                </form>

            </div>
        </nav>
<%--       <c:set var = "url" scope = "session" value = "${pageContext.request.contextPath}/mmm?action=show_sign_in_page"/>--%>

        <div class="form">
            <h4 class = "label">${sign_in_label}</h4>

            <form id="sign_in" action="mmm" method="post">
                <input type="hidden" name="action" value="sign_in"/>
                <div class="form-group log row">
                    <label for="inputLogin" class="col-sm-2 col-form-label">${login_label}</label>
                    <div class="col-sm-8 ml-3">
                        <input type="text" name="login" class="form-control" id="inputLogin"/>
                    </div>
                </div>

                <div class="form-group pas row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">${password_label}</label>
                    <div class="col-sm-8 ml-3">
                        <input type="password" name="user_password"  class="form-control" id="inputPassword"/>
                    </div>
                </div>
                <div class="form-group row last ">
                    <div class="col-sm-7 in create">
                        <a href="${pageContext.request.contextPath}/mmm?action=show_register_page">${create_account_label}</a>
                    </div>
                    <div class="col-sm-4 in button">
                        <button type="submit" class="btn btn-primary">${enter_button}</button>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
