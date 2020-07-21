<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.change_login.title" var="title_label"/>
        <fmt:message bundle="${loc}" key="local.label.new_login" var="login_label"/>
        <fmt:message bundle="${loc}" key="local.button.change" var="change_btn"/>

        <title>Change login</title>
    </head>
    <body>
        <div class="modal fade" id="login_change_modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">${title_label}</h5>
                    </div>
                    <div class="modal-body">
                        <form action="airport" method="post" id="edit_flight">
                            <input type="hidden" name="action" value="change_login" />
                            <input type="hidden" name="old_login" value="${sessionScope.user.login}" />
                            <label for="new_login">${login_label}</label>
                            <input type="text" id="new_login" name="new_login"/>
                            <input type="submit" class="btn btn-primary" value="${change_btn}"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
