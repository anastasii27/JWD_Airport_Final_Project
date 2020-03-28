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
            <p>Логин
                <input type="text" name="login" value="" />
            </p>
            <p>Пароль
                <input type="text" name="user_password" value="" />
            </p>
            <p>Имя
                <input type="text" name="user_name" value="" />
            </p>
            <p>Фамилия
                <input type="text" name="surname" value="" />
            </p>
            <p>Email
                <input type="text" name="email" value="" />
            </p>
            <p>Год старта карьеры
                <input type="text" name="career_start_year" value="" />
            </p>
            <p>Роль
            <input type="text" name="user_role" value="pilot" />
            </p>
            <input type="submit" value="Зарегистрироваться"/> <br/>
        </form>
    </body>
</html>
