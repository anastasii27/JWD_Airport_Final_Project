<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Registration</title>
    </head>
    <body>
        <form action="mmm" method="post">
            <input type="hidden" name="action" value="register"/>
            <h3>Регистрация</h3>
            <p>Имя
                <input type="text" name="user_name" value="" />
            </p>
            <p>Логин
                <input type="text" name="login" value="" />
            </p>
            <p>Пароль
                <input type="text" name="password" value="" />
            </p>
            <input type="submit" value="Зарегистрироваться"/> <br/>
        </form>
    </body>
</html>
