<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <title>CHOOSE CREW</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/validation-plug-in.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/jquery-ui.min.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/design/js/ajax-request.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/full-js.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.2/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/validation.js" charset="UTF-8"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.css" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>

    </head>
    <body>
        <jsp:include page="parts/header.jsp"/>

        <table class ="table" id="crew_table" border="2">
            <tr>
                <th>НАЗВАНИЕ ЭКИПАЖА</th><th>СОСТАВ ЭКИПАЖА</th>
            </tr>
            <c:forEach items="${requestScope.crew_members}" var="entry">
                <tr>
                    <td class="crew_name">${entry.key}</td>
                    <td>
                        <p><b>Pilots:</b>
                            <c:forEach items="${entry.value}" var="user">
                                <c:if test = "${user.role eq 'pilot'}">
                                     ${user.name} ${user.surname}
                                </c:if>
                            </c:forEach>
                        </p>
                        <p><b>Stewards:</b>
                            <c:forEach items="${entry.value}" var="user">
                                <c:if test = "${user.role eq 'steward'}">
                                    ${user.name} ${user.surname}
                                </c:if>
                            </c:forEach>
                        </p>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div id="choose_crew_btn">
            <button type="button" class="btn btn-info">
                <span class="edit">СОЗДАТЬ РЕЙС</span>
            </button>
        </div>
    </body>
</html>
