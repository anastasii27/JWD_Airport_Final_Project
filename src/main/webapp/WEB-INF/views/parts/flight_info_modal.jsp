<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.label.flight" var="flight_label" />
        <fmt:message bundle="${loc}" key="local.label.plane" var="plane_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.date" var="date_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.time" var="time_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.country" var="country_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.city" var="city_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.airport" var="airport_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.arrivals" var="arrivals_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.departures" var="departures_label" />
        <fmt:message bundle="${loc}" key="local.label.title.flight_info" var="flight_info_label" />

        <title>Flight information modal</title>
    </head>
    <body>
        <div class="modal fade" id="flight_info_modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">

            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">${flight_info_label}</h5>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="row" id="info_flight_number">
                                <label>${flight_label}</label>

                            </div>
                            <div class="row" id="info_plane_model">
                                <label>${plane_label}</label>

                            </div>
                        </div>
                        <div class="row">
                            <div class = "info_departure col-md-6">
                                <label>${departures_label}</label>
                                <div class="row" id="info_dep_date">
                                    <label>${date_label}</label>
                                </div>
                                <div class="row" id="info_dep_time">
                                    <label>${time_label}</label>
                                </div>
                                <div class="row" id="info_dep_country">
                                    <label>${country_label}</label>
                                </div>
                                <div class="row" id="info_dep_city">
                                    <label>${city_label}</label>
                                </div>
                                <div class="row" id="info_dep_airport">
                                    <label>${airport_label}</label>
                                </div>
                            </div>
                            <div class = "info_arrival col-md-6">
                                <label>${arrivals_label}</label>
                                <div class="row" id="info_dest_date">
                                    <label>${date_label}</label>
                                </div>
                                <div class="row" id="info_dest_time">
                                    <label>${time_label}</label>
                                </div>
                                <div class="row" id="info_dest_country">
                                    <label>${country_label}</label>
                                </div>
                                <div class="row" id="info_dest_city">
                                    <label>${city_label}</label>
                                </div>
                                <div class="row" id="info_dest_airport">
                                    <label>${airport_label}</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
