<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Data Input</title>
    </head>
    <body>
        <form action="mmm" method="post">
            <input type="hidden" name="action" value="sing_in"/>
            <p>Логин
                <input type="text" name="login" value="" />
            </p>
            <p>Пароль
                <input type="text" name="user_password" value="" />
            </p>
            <input type="submit" value="Войти"/> <br/>
            <a href="register.jsp">Создать аккаунт</a>
        </form>
    </body>
</html>