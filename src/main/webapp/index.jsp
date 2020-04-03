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
        <fmt:message bundle="${loc}" key="local.enter_button" var="enter_button" />
        <fmt:message bundle="${loc}" key="local.label.create_account" var="create_account_label" />

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Data Input</title>
    </head>
    <body>

        <form action="mmm" method="post">
            <input type="hidden" name="action" value="change_language" />
            <input type="hidden" name="local" value="ru" />
            <input type="submit" value="${ru_button}" /><br />
        </form>

        <form action="mmm" method="post">
            <input type="hidden" name="action" value="change_language"/>
            <input type="hidden" name="local" value="en" />
            <input type="submit" value="${en_button}" /><br />
        </form>

        <form action="mmm" method="post">
            <input type="hidden" name="action" value="sign_in"/>
            <p>${login_label}<input type="text" name="login" value="" required/></p>
            <p>${password_label}<input type="text" name="user_password" value="" required/></p>
            <input type="submit" value="${enter_button}"/> <br/>
            <a href="jsp/register.jsp">${create_account_label}</a>
        </form>

        <c:set var = "url" scope = "session" value = "${pageContext.request.requestURL}"/>

    </body>
</html>