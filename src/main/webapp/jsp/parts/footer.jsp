<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.message.contact_us" var="contact_mes" />

        <title>Footer</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/footer.css"/>

    </head>
    <body>
        <div class="row footer">
            <div id="copyright">
                @2020.Blue Sky
            </div>
            <div class="row" id="socials">
                <div id="inst">
                    <img src="${pageContext.request.contextPath}/design/img/inst.jpg" alt="Inst">
                </div>
                <div id="fb">
                    <img src="${pageContext.request.contextPath}/design/img/fc.jpg" alt="FB">
                </div>
                <div id="vk">
                    <img src="${pageContext.request.contextPath}/design/img/vk.jpg" alt="VK">
                </div>
            </div>
            <div id="contact">
                ${contact_mes}<br/>
                blue.sky.airp@gmail.com
            </div>
        </div>
    </body>
</html>
