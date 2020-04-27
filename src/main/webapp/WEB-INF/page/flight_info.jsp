<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>

    <head>
        <title>Flight information</title>
    </head>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <body>
        <jsp:include page="header.jsp"/>
        <div class="row">
            <div class = 'col-md-4'>
                <h2>Pilots</h2>
                    <c:if test="${requestScope.result ne null}">
                        <c:out value="${requestScope.result}"/>
                    </c:if>

                    <c:if test="${requestScope.result eq null}">
                        <c:forEach items="${requestScope.group}" var="user">
                            <c:set var = "role" scope = "request" value = "${user.role}"/>
                                <c:if test="${role eq 'pilot'}">
                                    <c:out value="${user.name} ${user.surname}"/>
                                </c:if>
                        </c:forEach>
                    </c:if>
            </div>
            <div class = 'col-md-4'>
                <h2>Stewardesses</h2>
                    <c:if test="${requestScope.result ne null}">
                        <c:out value="${requestScope.result}"/>
                    </c:if>
                    <c:if test="${requestScope.result eq null}">
                        <c:forEach items="${requestScope.group}" var="user">
                            <c:set var = "role" scope = "request" value = "${user.role}"/>
                                <c:if test="${role eq 'stewardess'}">
                                    <c:out value="${user.name} ${user.surname}"/><br/>
                                </c:if>
                        </c:forEach>
                    </c:if>
            </div>
            <div class = 'col-md-4'>
                3
            </div>
        </div>
    </body>
</html>
