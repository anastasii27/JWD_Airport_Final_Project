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
        <fmt:message bundle="${loc}" key="local.label.login" var="login_label" />
        <fmt:message bundle="${loc}" key="local.label.edit_user" var="edit_label" />
        <fmt:message bundle="${loc}" key="local.back_button" var="back_btn"/>
        <fmt:message bundle="${loc}" key="local.message.role.pilot" var="pilot_label"/>
        <fmt:message bundle="${loc}" key="local.message.role.steward" var="steward_label"/>
        <fmt:message bundle="${loc}" key="local.message.role.dispatcher" var="disp_label"/>
        <fmt:message bundle="${loc}" key="local.message.role.admin" var="admin_label"/>

        <title>${sessionScope.user.name} ${sessionScope.user.surname}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/validation-plug-in.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/edit-user.css"/>
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
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_user_editing_page"/>').hide();
                });
            });
        </script>

    </head>
    <body>
        <jsp:include page="parts/header.jsp"/>
        <div id="back_btn">
            <button type="button" class="btn-lg btn-info" onclick="document.location.href= '${pageContext.request.contextPath}/airport?action=show_user_page'">
                ${back_btn}
            </button>
        </div>
        <div id="title">
            <h2>${edit_label}</h2>
        </div>
        <div id="edit_form">
            <form action="airport" id="edit_user" method="post">
                <input type="hidden" name="action" value="edit_user"/>
                <input type="hidden" name="id" value="${sessionScope.user.id}"/>
                <input type="hidden" name="login" value="${sessionScope.user.login}"/>
                <input type="hidden" name="user_password" value="notrealpas123"/>

                <div class="form-group row">
                    <label for="inputName">${name_label}</label>
                    <input type="text" name="user_name" class="form-control" id="inputName" value="${sessionScope.user.name}"/>
                </div>
                <div class="form-group row">
                    <label for="inputSurname">${surname_label}</label>
                    <input type="text" name="surname" class="form-control" id="inputSurname" value="${sessionScope.user.surname}"/>
                </div>
                <div class="form-group row">
                    <label for="inputEmail">Email</label>
                    <input type="text" name="email" class="form-control" id="inputEmail" value="${sessionScope.user.email}"/>
                </div>
                <div class="form-group row">
                    <label for="inputRole">${role_label}</label>
                    <c:if test = "${sessionScope.user.role eq 'steward'}">
                        <c:set var="role" value="${steward_label}" />
                    </c:if>
                    <c:if test = "${sessionScope.user.role eq 'pilot'}">
                        <c:set var="role" value="${pilot_label}" />
                    </c:if>
                    <c:if test = "${sessionScope.user.role eq 'dispatcher'}">
                        <c:set var="role" value="${disp_label}" />
                    </c:if>
                    <c:if test = "${sessionScope.user.role eq 'admin'}">
                        <c:set var="role" value="${admin_label}" />
                    </c:if>
                    <input type="text" name="user_role" class="form-control" id="inputRole" value="${role}" readonly/>
                </div>
                <div class="form-group row">
                    <label for="inputCareerStartYear">${start_label}</label>
                    <input type="text" name="career_start_year" class="form-control" id="inputCareerStartYear" value="${sessionScope.user.careerStartYear}"/>
                </div>
                <div id="change_btn">
                    <input type="submit" class="btn btn-primary" id="submit" value="${edit_btn}"/>
                </div>
            </form>
        </div>
        <jsp:include page="../../jsp/parts/footer.jsp"/>
    </body>
</html>
