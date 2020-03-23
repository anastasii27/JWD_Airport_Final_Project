<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <title>Home page</title>
    </head>
    <body>
        <h3>Вылеты:</h3>
        <table class ="table" border="2">
            <tr><th>Город отправления</th><th>Город прилета</th><th>Название рейса</th><th>Время вылета</th></tr>
            <c:forEach items="${flight}" var="flight_item">
                <tr>
                    <td>${flight_item.departure_city}</td>
                    <td>${flight_item.arrival_city}</td>
                    <td>${flight_item.flight_name}</td>
                    <td>${flight_item.departure_time}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
