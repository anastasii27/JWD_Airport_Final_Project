<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <title>Sign in</title>

        <fmt:message bundle="${loc}" key="local.label.login" var="login_label" />
        <fmt:message bundle="${loc}" key="local.label.password" var="password_label" />
        <fmt:message bundle="${loc}" key="local.enter_button" var="enter_button" />
        <fmt:message bundle="${loc}" key="local.label.create_account" var="create_account_label" />

    </head>
    <body>

        <form action="../mmm" method="post">
            <input type="hidden" name="action" value="sign_in"/>
            <p>${login_label}<input type="text" name="login" value="" required/></p>
            <p>${password_label}<input type="text" name="user_password" value="" required/></p>
            <input type="submit" value="${enter_button}"/> <br/>
        </form>

        <a href="${pageContext.request.contextPath}/mmm?action=show_register_page">${create_account_label}</a>

    </body>
</html>
