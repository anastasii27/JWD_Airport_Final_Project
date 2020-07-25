<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.choose_crew.title" var="choose_label" />
        <fmt:message bundle="${loc}" key="local.label.crew.name" var="crews_label"/>
        <fmt:message bundle="${loc}" key="local.label.choose_crew.members" var="crew_memb_label"/>
        <fmt:message bundle="${loc}" key="local.label.create_flight.finish" var="finish_label"/>
        <fmt:message bundle="${loc}" key="local.label.create_flight.back" var="back_label"/>
        <fmt:message bundle="${loc}" key="local.label.create_flight.create" var="create_label"/>
        <fmt:message bundle="${loc}" key="local.label.crew.pilot" var="pilot_label"/>
        <fmt:message bundle="${loc}" key="local.label.crew.steward" var="steward_label"/>

        <title>${choose_label}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/validation-plug-in.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/choose-crew.css"/>
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
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=free_crews_for_flight"/>').hide();
                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="parts/header.jsp"/>
        <div id = "no_choose_mes" aria-hidden="true" style="background-color: red"> No choose</div>
        <c:set var = "result" value = "${requestScope.result}"/>
        <c:if test = "${result eq null}">
        <table class ="table" id="crew_table" border="2">
            <tr>
                <th>${crews_label}</th><th>${crew_memb_label}</th>
            </tr>
            <c:forEach items="${requestScope.crew_members}" var="entry">
                <tr class="values">
                    <td class="crew_name">${entry.key}</td>
                    <td>
                        <p><b>${pilot_label}:</b>
                            <c:forEach items="${entry.value}" var="user">
                                <c:if test = "${user.role eq 'pilot'}">
                                     ${user.name} ${user.surname}
                                </c:if>
                            </c:forEach>
                        </p>
                        <p><b>${steward_label}:</b>
                            <c:forEach items="${entry.value}" var="user">
                                <c:if test = "${user.role eq 'steward'}">
                                    ${user.name} ${user.surname}
                                </c:if>
                            </c:forEach>
                        </p>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </c:if>
        <c:if test = "${result ne null}">
            <div class="no_crews_mes">
                <p><c:out value="${result}"/></p>
            </div>
        </c:if>
        <div id="choose_crew_btn">
            <button type="button" class="btn btn-info">
                <span class="edit">${create_label}</span>
            </button>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="choose_crew_modal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                       ${finish_label}
                        <jsp:useBean id="now" class="java.util.Date"/>
                        <fmt:formatDate type="time" value="${now}" pattern="yyyy-MM-dd" var="depDate"/>
                        <button type="button" class="btn btn-info" onclick="document.location.href= '${pageContext.request.contextPath}/airport?action=show_flight_management_page&departure_date=${depDate}'">
                            <span class="back_to_flights">${back_label}</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
