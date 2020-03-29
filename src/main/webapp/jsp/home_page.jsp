<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <title>Home page</title>
    </head>
    <body>

    <jsp:useBean id="user" class="by.epam.tr.bean.User" />
    <jsp:setProperty property="*" name="user"/>
    <jsp:getProperty property="name" name="user"/>

    <c:out value="${pageScope.user.name}" />

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

        <table class ="table" border="2">
            <tr><th>Название бригады </th><th>Дата создания</th></tr>
            <c:forEach items="${group}" var="group_item">
                <tr>
                    <td>${group_item.name}</td>
                    <td>${group_item.dateOfCreating}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
