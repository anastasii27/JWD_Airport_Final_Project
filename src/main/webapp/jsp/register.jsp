<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="local.button.name.en" var="en_button" />
        <fmt:message bundle="${loc}" key="local.label.login" var="login_label" />
        <fmt:message bundle="${loc}" key="local.label.password" var="password_label" />
        <fmt:message bundle="${loc}" key="local.label.name" var="name_label" />
        <fmt:message bundle="${loc}" key="local.label.surname" var="surname_label" />
        <fmt:message bundle="${loc}" key="local.label.start_year" var="start_label" />
        <fmt:message bundle="${loc}" key="local.label.role" var="role_label" />
        <fmt:message bundle="${loc}" key="local.register_button" var="register_button" />

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Registration</title>
    </head>
    <body>

        <form action="../mmm" method="get">
            <input type="hidden" name="action" value="change_language" />
            <input type="hidden" name="local" value="ru" />
            <input type="submit" value="${ru_button}" /><br />
        </form>

        <form action="../mmm" method="get">
            <input type="hidden" name="action" value="change_language"/>
            <input type="hidden" name="local" value="en" />
            <input type="submit" value="${en_button}" /><br />
        </form>

        <form action="../mmm" method="post">
            <input type="hidden" name="action" value="register"/>
            <h3>Регистрация</h3>
            <p>${login_label}
                <input type="text" name="login" value="" required/>
            </p>
            <p>${password_label}
                <input type="text" name="user_password" value="" required/>
            </p>
            <p>${name_label}
                <input type="text" name="user_name" value="" required/>
            </p>
            <p>${surname_label}
                <input type="text" name="surname" value="" required/>
            </p>
            <p>Email
                <input type="text" name="email" value="" required/>
            </p>
            <p>${start_label}
                <input type="text" name="career_start_year" value="" required/>
            </p>
            <p>${role_label}
            <input type="text" name="user_role" value="pilot" required/>
            </p>
            <input type="submit" value="${register_button}"/> <br/>
        </form>

        <c:set var = "url" scope = "session" value = "${pageContext.request.requestURL}"/>

    </body>
</html>
