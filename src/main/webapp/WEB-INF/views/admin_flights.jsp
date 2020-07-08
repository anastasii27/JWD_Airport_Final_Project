<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.js.lang" var="lang" />
        <fmt:message bundle="${loc}" key="local.label.menu_user_my_flights" var="my_flights_label" />
        <fmt:message bundle="${loc}" key="local.send_button" var="send_button" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.date" var="date_label" />

        <title>ADMIN FLIGHT</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/flights-table.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/validation-plug-in.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/design/css/datepicker.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/admin-flights.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/jquery-ui.min.js" charset="UTF-8"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/design/js/datepicker.min.js" charset="UTF-8"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/i18n/datepicker.en.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/i18n/datepicker.ru.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/ajax-request.js" charset="UTF-8"></script>
        <script src="${pageContext.request.contextPath}/design/js/full-js.js" charset="UTF-8"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.2/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/design/js/validation.js" charset="UTF-8"></script>

    </head>
    <body>
        <jsp:include page="parts/header.jsp"/>
        <div id = "no_delete_mes" aria-hidden="true" style="background-color: red"> No delete</div>
        <div class="col-md-5 form">
            <form action="airport" method="get" id="my_flights_form">
                <input type="hidden" name="action" value="show_flight_management_page"/>
                <div class="form-group row">
                    <div class="for_label">
                        <label for="my_flights_piker">${date_label}</label>
                    </div>
                    <input type='text' name="departure_date" id= "my_flights_piker" class="datepicker-here"
                           data-language="${lang}"/>
                    <div id="btn" dep_date = "${requestScope.departure_date}">
                        <button type="submit" class="btn btn-primary">${send_button}</button>
                    </div>
                </div>
            </form>
        </div>
        <jsp:include page="parts/flights_table.jsp"/>
        <div id="create_flight_btn">
            <form>
                <input type="button" class="btn btn-primary"  value="СОЗДАТЬ" onClick='location.href="${pageContext.request.contextPath}/airport?action=show_create_flight_page"'>
            </form>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="flight_edit_modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">

            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Изменение рейса</h5>
                    </div>
                    <div class="modal-body">
                        <form action="airport" method="post" id="edit_flight">
                            <div class="row">
                                <div class="form-group">
                                    <input type="hidden" name="action" value="edit_flight" />
                                    <label for="edit_flight_number"> НОМЕР РЕЙСА</label>
                                    <input type="text" id="edit_flight_number" name="flight_number"/>
                                </div>
                                <div class="form-group">
                                    <label for="edit_planes">САМОЛЕТ</label>
                                    <select name= "planes" id="edit_planes">

                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="edit_crew">ЭКИПАЖ</label>
                                    <select name= "crews" id="edit_crew">

                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="edit_status">СТАТУС</label>
                                    <select name= "status" id="edit_status">
                                        <option>Scheduled</option>
                                        <option>Canceled</option>
                                        <option>Arrived</option>
                                        <option>Delayed</option>
                                        <option>Departed</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div id="departure" class="col-md-5">
                                    <h3>ОТПРАВЛЕНИЕ</h3>
                                    <div class="form-group">
                                        <label for="edit_dep_flights_piker">ДАТА ОТПРАВЛЕНИЯ</label>
                                        <input type="text" name="departure_date" id= "edit_dep_flights_piker" class="datepicker-here admin_picker"
                                               data-language="${lang}"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="edit_dep_time">ВРЕМЯ ОТПРАВЛЕНИЯ</label>
                                        <input type='time' name="departure_time" id= "edit_dep_time"/>
                                    </div>
                                    <div class="form-group row" id="edit_dep_country">
                                        <label>СТРАНА ОТПРАВЛЕНИЯ</label>

                                    </div>
                                    <div class="form-group">
                                        <label for="edit_dep_airport">АЭРОПОРТ ОТПРАВЛЕНИЯ</label>
                                        <select name= "departure_airport" id="edit_dep_airport">

                                        </select>
                                    </div>
                                </div>
                                <div id="arrival" class="col-md-5">
                                    <h3>ПРИБЫТИЕ</h3>
                                    <div class="form-group">
                                        <label for="edit_dest_flights_piker">ДАТА ПРИБЫТИЯ</label>
                                        <input type='text' name="destination_date" id= "edit_dest_flights_piker" class="datepicker-here admin_picker"
                                               data-language="${lang}"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="edit_dest_time">ВРЕМЯ ПРИБЫТИЯ</label>
                                        <input type='time' name="destination_time" id= "edit_dest_time"/>
                                    </div>
                                    <div class="form-group row" id="edit_dest_country">
                                        <label>СТРАНА ПРИБЫТИЯ</label>

                                    </div>
                                    <div class="form-group">
                                        <label for="edit_dest_airport">АЭРОПОРТ ПРИБЫТИЯ</label>
                                        <select name= "destination_airport" id="edit_dest_airport">

                                        </select>
                                    </div>
                                </div>
                            </div>
                            <input type="submit" class="btn btn-primary" value="ИЗМЕНИТЬ"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
