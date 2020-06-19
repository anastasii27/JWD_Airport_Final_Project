<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
<%--        <fmt:message bundle="${loc}" key="local.label.plane" var="plane_label" /> --%>

        <title>ВОЗДУШНЫЕ ЭКИПАЖИ</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/crews.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/jquery-ui.min.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/design/js/ajax-request.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/full-js.js" charset="UTF-8"></script>

    </head>
    <body>
        <jsp:include page="parts/header.jsp"/>
        <div class="row col-12">
            <div class="col-md-4">
                <h2>НАЗВАНИЕ ЭКИПАЖА</h2>
                <c:forEach items="${requestScope.crew}" var="crew_item">
                    <ul class="list-group crews">
                        <li class="list-group-item" value="${crew_item}">${crew_item} <button type="button" class="close delete_crew_btn" >&times;</button></li>
                    </ul>
                </c:forEach>
            </div>
            <div id="crews_error">
                ДАННЫЕ НЕ НАЙДЕНЫ
            </div>
            <div class="container crew_members col-md-6">
                <div class="row ml-auto  mr-auto">
                    <div class="col-md-5 p-0">
                        <h2>ПИЛОТЫ</h2>
                        <ul class="list-group members_list" id="pilots_list">

                        </ul>
                    </div>
                    <div class="col-md-5 p-0">
                        <h2>СТЮАРДЫ</h2>
                        <ul class="list-group members_list" id="steward_list">

                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <div id="create_crew_btn">
            <form>
                <input type="button" class="btn btn-primary"  value="СОЗДАТЬ" onClick='location.href="${pageContext.request.contextPath}/airport?action=show_create_crew_page"'>
            </form>
        </div>
        <div id="edit_crew_btn">
            <button type="button" class="btn btn-info">
                <span class="fa-pencil edit">Редактировать</span>
            </button>
        </div>
    </body>
</html>
