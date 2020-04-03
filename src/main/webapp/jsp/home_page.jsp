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
        <fmt:message bundle="${loc}" key="local.label.name" var="name_label" />
        <fmt:message bundle="${loc}" key="local.label.surname" var="surname_label" />
        <fmt:message bundle="${loc}" key="local.label.start_year" var="start_label" />
        <fmt:message bundle="${loc}" key="local.label.role" var="role_label" />
        <fmt:message bundle="${loc}" key="local.table.creating_date" var="data_table" />
        <fmt:message bundle="${loc}" key="local.table.group" var="group_table" />
        <fmt:message bundle="${loc}" key="local.exit_button" var="exit_table" />


        <title>Home page</title>
    </head>
    <body>

        <form action="../mmm" method="post">
            <input type="hidden" name="action" value="change_language" />
            <input type="hidden" name="local" value="ru" />
            <input type="submit" value="${ru_button}" /><br />
        </form>

        <form action="../mmm" method="post">
            <input type="hidden" name="action" value="change_language"/>
            <input type="hidden" name="local" value="en" />
            <input type="submit" value="${en_button}" /><br />
        </form>


        <jsp:useBean id="user" scope="session" class="by.epam.tr.bean.User"/>
        <p>${name_label}: <jsp:getProperty property="name" name="user"/></p>
        <p>${surname_label}: <jsp:getProperty property="surname" name="user"/></p>
        <p>Email: <jsp:getProperty property="email" name="user"/></p>
        <p>${role_label}: <jsp:getProperty property="role" name="user"/></p>
        <p>${start_label}: <jsp:getProperty property="career_start_year" name="user"/></p>


<%--    <h3>Вылеты:</h3>--%>
<%--        <table class ="table" border="2">--%>
<%--            <tr><th>Город отправления</th><th>Город прилета</th><th>Название рейса</th><th>Время вылета</th></tr>--%>
<%--            <c:forEach items="${flight}" var="flight_item">--%>
<%--                <tr>--%>
<%--                    <td>${flight_item.departure_city}</td>--%>
<%--                    <td>${flight_item.arrival_city}</td>--%>
<%--                    <td>${flight_item.flight_name}</td>--%>
<%--                    <td>${flight_item.departure_time}</td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--        </table>--%>

        <table class ="table" border="2">
            <tr><th>${group_table} </th><th>${data_table}</th></tr>
            <c:forEach items="${group}" var="group_item">
                <tr>
                    <td>${group_item.name}</td>
                    <td>${group_item.dateOfCreating}</td>
                </tr>
            </c:forEach>
        </table>

        <form action="../mmm" method="post">
            <input type="hidden" name="action" value="sign_out"/>
            <input type="submit" value="${exit_table}"/> <br/>
        </form>

        <c:set var = "url" scope = "session" value = "${pageContext.request.requestURL}"/>
    </body>
</html>
