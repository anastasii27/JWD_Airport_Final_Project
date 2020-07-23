<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.password_change.title" var="title_label"/>
        <fmt:message bundle="${loc}" key="local.label.new_password" var="new_pas_label"/>
        <fmt:message bundle="${loc}" key="local.label.old_password" var="old_pas_label"/>
        <fmt:message bundle="${loc}" key="local.label.password_conf" var="confirm_label"/>
        <fmt:message bundle="${loc}" key="local.button.change" var="change_btn"/>

        <title>Change password</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/change-password-modal.css"/>
    </head>
    <body>
        <div class="modal fade" id="password_change_modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">${title_label}</h5>
                    </div>
                    <div class="modal-body">
                        <form action="airport" method="post" id="edit_password">
                            <input type="hidden" name="action" value="change_password" />
                            <input type="hidden" name="login" value="${sessionScope.user.login}" />
                            <div class="form-group row">
                                <label for="old_pass" class="labels">${old_pas_label}</label>
                                <input type="password" id="old_pass" name="old_password"/>
                            </div>
                            <div class="form-group row">
                                <label for="new_pass" class="labels">${new_pas_label}</label>
                                <input type="password" id="new_pass" name="new_password"/>
                            </div>
                            <div class="form-group row">
                                <label for="confirm_pass" class="labels">${confirm_label}</label>
                                <input type="password" id="confirm_pass" name="confirm_password"/>
                            </div>
                            <div id="change_btn">
                                <input type="submit" class="btn btn-primary" value="${change_btn}"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
