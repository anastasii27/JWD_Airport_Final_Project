<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.crew.name" var="crew_name_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.no_date" var="data_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.pilot" var="pilot_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.steward" var="steward_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.create_btn" var="create_label" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_crew" var="crews_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.edit_crew" var="edit_label" />
        <fmt:message bundle="${loc}" key="local.button.add" var="add_btn"/>
        <fmt:message bundle="${loc}" key="local.button.save" var="save_btn"/>
        <fmt:message bundle="${loc}" key="local.js.lang" var="lang" />

        <title>${crews_label}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/crews.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/jquery-ui.min.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/design/js/ajax-request.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/full-js.js" charset="UTF-8"></script>

        <script>
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_crew_page"/>').hide();
                });
            });
        </script>

    </head>
    <body lang="${lang}">
        <jsp:include page="parts/header.jsp"/>
        <%--Crews list--%>
        <div class="row col-12">
            <div id="title">
                <h2>${crew_name_label}</h2>
            </div>
            <div class="col-md-4" id="crews">
                <c:forEach items="${requestScope.crew}" var="crew_item">
                    <ul class="list-group crews">
                        <li class="list-group-item" value="${crew_item}">${crew_item} <button type="button" class="close delete_crew_btn" >&times;</button></li>

                    </ul>
                </c:forEach>
            </div>
            <div id="crews_error" class="d-none">
                <h2>${data_label}</h2>
            </div>

            <%--Crew members list--%>
            <div class="container choose_crew_members col-md-6 d-none">
                <div class="row ml-auto  mr-auto">
                    <div class="col-md-5 p-0">
                        <h2>${pilot_label}</h2>
                        <div class="users_select" id="pilots_select">
                            <select size=10 name= "pilots" id="pilots" multiple>
                                <c:forEach var="pilot" items="${pilots_list}">
                                    <option>${pilot.name} ${pilot.surname} </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-5 p-0">
                        <div class="row">
                            <h2>${steward_label}</h2>
                            <div class="add_crew_btn" id="confirm_add">
                                <button type="button" class="btn btn-info ">
                                    <span>${save_btn}</span>
                                </button>
                            </div>
                        </div>
                        <div class="users_select" id="stewards_select" >
                            <select size=10 name="stewards" id="stewards" multiple>
                                <c:forEach var="steward" items="${stewards_list}">
                                    <option>${steward.name} ${steward.surname} </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <%--Members for adding--%>
            <div class="container crew_members col-md-6 d-none">
                <div class="row ml-auto  mr-auto">
                    <div class="col-md-5 p-0">
                            <h2>${pilot_label}</h2>
                            <ul class="list-group members_list" id="pilots_list">

                            </ul>
                    </div>
                    <div class="col-md-5 p-0">
                        <div class="row">
                            <h2>${steward_label}</h2>
                            <div class="add_crew_btn" id="add_user">
                                <button type="button" class="btn btn-info">
                                    <span>${add_btn}</span>
                                </button>
                            </div>
                        </div>
                        <ul class="list-group members_list" id="steward_list">

                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <%--Buttons--%>
        <div id="create_crew_btn">
            <form>
                <input type="button" class="btn btn-primary"  value="${create_label}" onClick='location.href="${pageContext.request.contextPath}/airport?action=show_create_crew_page"'>
            </form>
        </div>
        <div id="edit_crew_btn">
            <button type="button" class="btn btn-info">
                <span class="edit">${edit_label}</span>
            </button>
        </div>
        <jsp:include page="../../jsp/parts/footer.jsp"/>
    </body>
</html>



