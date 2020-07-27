<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <fmt:message bundle="${loc}" key="local.label.flight" var="flight_label" />
        <fmt:message bundle="${loc}" key="local.label.dest_city" var="dest_label" />
        <fmt:message bundle="${loc}" key="local.label.dep_time" var="dep_time_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.date" var="date_label" />
        <fmt:message bundle="${loc}" key="local.label.nearest_flight" var="near_flight_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.edit_btn" var="edit_btn" />
        <fmt:message bundle="${loc}" key="local.label.password" var="password_label" />
        <fmt:message bundle="${loc}" key="local.label.login" var="login_label" />
        <fmt:message bundle="${loc}" key="local.js.lang" var="lang" />
        <fmt:message bundle="${loc}" key="local.label.change_login" var="change_login_label" />
        <fmt:message bundle="${loc}" key="local.label.change_password" var="change_pas_label" />

        <title>${sessionScope.user.name} ${sessionScope.user.surname}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/validation-plug-in.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/user-page.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/jquery-ui.min.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/design/js/full-js.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/ajax-request.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.2/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/validation.js" charset="UTF-8"></script>

        <script>
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_user_page"/>').hide();
                });
            });
        </script>
    </head>
    <body lang="${lang}">
        <jsp:include page="parts/header.jsp"/>

        <div id="edit_user_btn">
            <button type="button" class="btn btn-info"
                    onclick="document.location.href= '${pageContext.request.contextPath}/airport?action=show_user_editing_page'">
                ${edit_btn}
            </button>
        </div>
        <div id="personal_info">
            <p>${name_label}: <c:out value= "${sessionScope.user.name}" /></p>
            <p>${surname_label}: <c:out value= "${sessionScope.user.surname}" /></p>
            <p>Email: <c:out value= "${sessionScope.user.email}" /></p>
            <p>${role_label}: <c:out value= "${sessionScope.user.role}" /></p>
            <p>${start_label}: <c:out value= "${sessionScope.user.careerStartYear}" /></p>
            <p>${password_label}: <a href="#" data-toggle="modal" data-target="#password_change_modal">${change_pas_label}</a></p>
            <p>${login_label}: <a href="#" data-toggle="modal" data-target="#login_change_modal">${change_login_label}</a></p>
        </div>
        <jsp:include page="parts/change_password_modal.jsp"/>
        <jsp:include page="parts/change_login_modal.jsp"/>
        <jsp:include page="../../jsp/parts/footer.jsp"/>
    </body>
</html>
