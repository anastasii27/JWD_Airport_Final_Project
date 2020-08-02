<%@ page  contentType="text/html; charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="local.button.name.en" var="en_button" />
        <fmt:message bundle="${loc}" key="local.label.login" var="login_label" />
        <fmt:message bundle="${loc}" key="local.label.password" var="password_label" />
        <fmt:message bundle="${loc}" key="local.label.name" var="name_label" />
        <fmt:message bundle="${loc}" key="local.label.surname" var="surname_label" />
        <fmt:message bundle="${loc}" key="local.label.start_year" var="start_label" />
        <fmt:message bundle="${loc}" key="local.label.role" var="role_label" />
        <fmt:message bundle="${loc}" key="local.label.register" var="register_label" />
        <fmt:message bundle="${loc}" key="local.label.password_conf" var="conf_label" />
        <fmt:message bundle="${loc}" key="local.form.info_1" var="login_info" />
        <fmt:message bundle="${loc}" key="local.form.info_2" var="password_info" />
        <fmt:message bundle="${loc}" key="local.register_button" var="register_button" />
        <fmt:message bundle="${loc}" key="local.js.lang" var="lang" />
        <fmt:message bundle="${loc}" key="local.label.title.registration" var="register_label" />
        <fmt:message bundle="${loc}" key="local.back_button" var="back_btn"/>

        <title>${register_label}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/register.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.1/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/validation.js" charset="UTF-8"></script>

    </head>
    <body lang="${lang}">
        <div class="row buttons">
            <div id="back_btn">
                <button type="button" class="btn-lg btn-info" onclick="document.location.href= '${pageContext.request.contextPath}/airport?action=show_sign_in_page'">
                    ${back_btn}
                </button>
            </div>
            <div id="forms" class="row">
                <div id="form1">
                    <form class="my-2 mr-1" action="airport" method="get">
                        <input type="hidden" name="action" value="change_language" />
                        <input type="hidden" name="local" value="ru" />
                        <input type="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_register_page"/>
                        <input class="btn-md  my-2 my-sm-0 mr-2" type="submit" value="${ru_button}" /><br />
                    </form>
                </div>
                <div id="form2">
                    <form class="my-2 mr-1" action="airport" method="get">
                        <input type="hidden" name="action" value="change_language"/>
                        <input type="hidden" name="local" value="en" />
                        <input type="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_register_page"/>
                        <input class="btn-md  my-2 my-sm-0 mr-2" type="submit" value="${en_button}" /><br />
                    </form>
                </div>
            </div>
        </div>
        <div class="form col-md-5">
            <form action="airport" method="post" id ="sign_up">
                <input type="hidden" name="action" value="register"/>
                <div class="reg">
                    <h3>${register_label}</h3>
                </div>
                <div class="form-row mt-2">
                    <div class="form-group col-md-5">
                        <label for="inputName">${name_label} </label>
                        <input type="text" name="user_name" class="form-control" id="inputName" autocomplete="off"/>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="inputSurname" >${surname_label} </label>
                        <input type="text" name="surname" class="form-control" id="inputSurname" autocomplete="off"/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-10">
                        <label for="inputEmail">Email </label>
                        <input type="text" name="email" class="form-control" id="inputEmail" autocomplete="off"/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="inputRole">${role_label} </label>
                        <select  name= "user_role" id="inputRole" class="form-control">
                                <option selected></option>
                                <option>admin</option>
                        </select>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="inputStart">${start_label} </label>
                        <input type="text" name="career_start_year" class="form-control" id="inputStart" autocomplete="off"/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-10">
                        <label for="inputLogin">${login_label} </label>
                        <input type="text" name="login" class="form-control" id="inputLogin" autocomplete="off"/>
                        <p class="info">${login_info}</p>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="inputPassword1">${password_label} </label>
                        <input type="password" name="user_password" class="form-control" id="inputPassword1" autocomplete="off"/>
                        <p class="info">${password_info}</p>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="inputPassword2">${conf_label} </label>
                        <input type="password" name="confirm_password" class="form-control" id="inputPassword2" autocomplete="off"/>
                        <div class="button">
                            <input type="submit" class="btn btn-primary" id="submit" value="${register_button}"/>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
