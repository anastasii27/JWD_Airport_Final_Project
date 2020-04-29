<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
<head>
    <title>Error</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />
    <fmt:message bundle="${loc}" key="local.label.error" var="error_label" />

</head>
<body>
    <h1><b>${error_label}</b></h1>
</body>
</html>
