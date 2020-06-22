<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.js.lang" var="lang" />
        <fmt:message bundle="${loc}" key="local.label.crew.create" var="create_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.commander" var="commander_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.second_pilot" var="second_pilot_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.stewards" var="stewards_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.crt" var="create_btn" />
        <fmt:message bundle="${loc}" key="local.label.crew.creation" var="creation_label" />

        <title>${creation_label}</title>

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

        <script>
            $('#steward').selectpicker();
        </script>

        <script>
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_create_crew_page"/>').hide();
                });
            });
        </script>

    </head>
    <body  lang="${lang}" >
        <jsp:include page="parts/header.jsp"/>
        <form action="airport" method="post" id="create_crew">
            <div class="form-group">
                <input type="hidden" name="action" value="create_crew" />
                <label for="crew_name">${create_label}</label>
                <input type="text" id="crew_name" name="crew_name"/>
            </div>
            <div class="form-group">
                <label for="first_pilot">${commander_label}</label>
                <select name= "first_pilot" id="first_pilot">
                <option selected></option>
                <c:forEach var="pilot" items="${pilots_list}">
                    <option>${pilot.name} ${pilot.surname} </option>
                </c:forEach>
                 </select>
            </div>
            <div class="form-group">
                <label for="second_pilot">${second_pilot_label}</label>
                <select name= "second_pilot" id="second_pilot">
                    <option selected></option>
                    <c:forEach var="pilot" items="${pilots_list}">
                        <option>${pilot.name} ${pilot.surname}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="steward">${stewards_label}</label>
                <select name= "steward" id="steward" class="selectpicker" multiple>
                    <c:forEach var="steward" items="${stewards_list}">
                        <option>${steward.name} ${steward.surname} </option>
                    </c:forEach>
                </select>
            </div>
                <input type="submit" class="btn btn-primary" value="${create_btn}"/>
        </form>
    </body>
</html>

