<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.edit_flight" var="edit_label"/>
        <fmt:message bundle="${loc}" key="local.label.flight" var="flight_label" />
        <fmt:message bundle="${loc}" key="local.label.plane" var="plane_label" />
        <fmt:message bundle="${loc}" key="local.label.edit_flight.crew" var="crew_label" />
        <fmt:message bundle="${loc}" key="local.label.status" var="status_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.date" var="date_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.time" var="time_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.airport" var="airport_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.arrivals" var="arrivals_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.departures" var="departures_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.country" var="country_label" />
        <fmt:message bundle="${loc}" key="local.button.edit_flight" var="edit_btn" />

        <title>Edit flight modal</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/edit-flight-modal.css"/>

    </head>
    <body>
        <div class="modal fade" id="flight_edit_modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">${edit_label}</h5>
                    </div>
                    <div class="modal-body">
                        <form action="airport" method="post" id="edit_flight">
                            <input type="hidden" name="action" value="edit_flight" />
                            <input type="hidden" name="id" id="flight_id"/>
                            <div class="row">
                                <div class="form-group row" id="edit_flight_number">
                                    <label>${flight_label}</label>

                                </div>
                                <div class="form-group" id="edit_plane_div">
                                    <label for="edit_planes">${plane_label}</label>
                                    <select name= "planes" id="edit_planes">

                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group" id="edit_crew_div">
                                    <label for="edit_crew">${crew_label}</label>
                                    <select name= "crews" id="edit_crew">

                                    </select>
                                </div>
                                <div class="form-group" id="edit_status_div">
                                    <label for="edit_status">${status_label}</label>
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
                                    <div class="main_label_edit">
                                        <h3>${departures_label}</h3>
                                    </div>
                                    <div class="form-group">
                                        <label for="edit_dep_flights_piker">${date_label}</label>
                                        <input type="text" name="departure_date" id= "edit_dep_flights_piker" class="datepicker-here admin_picker"
                                               data-language="${lang}"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="edit_dep_time">${time_label}</label>
                                        <input type='time' name="departure_time" id= "edit_dep_time"/>
                                    </div>
                                    <div class="form-group row" id="edit_dep_country">
                                        <label>${country_label}</label>

                                    </div>
                                    <div class="form-group">
                                        <label for="edit_dep_airport">${airport_label}</label>
                                        <select name= "departure_airport" id="edit_dep_airport">

                                        </select>
                                    </div>
                                </div>
                                <div id="arrival" class="col-md-5">
                                    <div class="main_label_edit">
                                        <h3>${arrivals_label}</h3>
                                    </div>
                                    <div class="form-group">
                                        <label for="edit_dest_flights_piker">${date_label}</label>
                                        <input type='text' name="destination_date" id= "edit_dest_flights_piker" class="datepicker-here admin_picker"
                                               data-language="${lang}"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="edit_dest_time">${time_label}</label>
                                        <input type='time' name="destination_time" id= "edit_dest_time"/>
                                    </div>
                                    <div class="form-group row" id="edit_dest_country">
                                        <label>${country_label}</label>

                                    </div>
                                    <div class="form-group">
                                        <label for="edit_dest_airport">${airport_label}</label>
                                        <select name= "destination_airport" id="edit_dest_airport">

                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row" id="edit_flight_btn">
                                <input type="submit" class="btn btn-primary" value="${edit_btn}"/>
                                <div id="for_error_edit"></div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
