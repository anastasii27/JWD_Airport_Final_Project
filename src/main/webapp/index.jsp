<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Data Input</title>
    </head>
    <body>
    <form action="Controller" method="post">
        <p>Enter login:</p>
        <input type="text" name="login" value="" />
        <p>Enter password:</p>
        <input type="text" name="password" value="" /> <p></p>
        <input type="submit" name = "log" value="Sing_In"/> <p></p>
        <input type="submit" name  = "log" value="Register"/>
    </form>
    </body>
</html>