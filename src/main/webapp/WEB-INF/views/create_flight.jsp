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
        <fmt:message bundle="${loc}" key="local.button.create_flight.dispatcher" var="dispatcher_label"/>
        <fmt:message bundle="${loc}" key="local.label.flight_info.date" var="date_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.time" var="time_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.country" var="country_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.city" var="city_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.airport" var="airport_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.arrivals" var="arrivals_label" />
        <fmt:message bundle="${loc}" key="local.label.flight_info.departures" var="departures_label" />
        <fmt:message bundle="${loc}" key="local.label.title.flight_info" var="flight_info_label" />
        <fmt:message bundle="${loc}" key="local.label.create_flight.continue" var="cont_label"/>
        <fmt:message bundle="${loc}" key="local.label.create_flight.create" var="title"/>
        <fmt:message bundle="${loc}" key="local.label.create_flight" var="create_title"/>
        <fmt:message bundle="${loc}" key="local.js.lang" var="lang" />

        <title>${title}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/create-flight.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/design/css/validation-plug-in.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/design/css/datepicker.min.css" rel="stylesheet" type="text/css">
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
        <script>
            $(document).ready(function () {
                $('.lang').on('click', function (){
                    $('.lang').append('<input impl="hidden" name="url" value="${pageContext.request.contextPath}/airport?action=show_create_flight_page"/>').hide();
                });
            });
        </script>
    </head>
    <body lang="${lang}">
        <jsp:include page="parts/header.jsp"/>
        <div id="title">
            <h2>${create_title}</h2>
        </div>
        <div class="col-md-12" id="form">
            <form action="airport" method="post" id="create_flight">
                <div id="common_info" class="row">
                    <div class="form-group">
                        <input type="hidden" name="action" value="create_flight" />
                        <label for="flight_number">${flight_label}</label>
                        <input type="text" id="flight_number" name="flight_number"/>
                    </div>
                    <div class="form-group">
                        <label for="dispatcher">${dispatcher_label}</label>
                        <select name= "user" id="dispatcher">
                            <option selected></option>

                        </select>
                    </div>
                    <div class="form-group">
                        <label for="planes">${plane_label}</label>
                        <select name= "planes" id="planes">

                        </select>
                    </div>
                </div>
                <div id="for_error"></div>
                <div class="row">
                    <div id="departure" class="col-md-6">
                        <h3>${departures_label}</h3>
                        <div class="form-group">
                            <label for="dep_flights_piker">${date_label}</label>
                            <input type='text' name="departure_date" id= "dep_flights_piker" class="datepicker-here admin_picker"
                                   data-language="${lang}" value = "${requestScope.departure_date}"/>
                        </div>
                        <div class="form-group">
                            <label for="dep_time">${time_label}</label>
                            <input type='time' name="departure_time" id= "dep_time"/>
                        </div>
                        <div class="form-group">
                            <label for="dep_country">${country_label}</label>
                            <select name= "departure_country" id="dep_country">
                                <option selected></option>
                                <c:forEach var="country" items="${country_list}">
                                    <option>${country}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="dep_airport">${airport_label}</label>
                            <select name= "departure_airport" id="dep_airport">
                                <option selected></option>

                            </select>
                        </div>
                    </div>
                    <div id="arrival" class="col-md-6">
                        <h3>${arrivals_label}</h3>
                        <div class="form-group">
                            <label for="dest_flights_piker">${date_label}</label>
                            <input type='text' name="destination_date" id= "dest_flights_piker" class="datepicker-here admin_picker"
                                   data-language="${lang}" value = "${requestScope.departure_date}"/>
                        </div>
                        <div class="form-group">
                            <label for="dest_time">${time_label}</label>
                            <input type='time' name="destination_time" id= "dest_time"/>
                        </div>
                        <div class="form-group">
                            <label for="dest_country">${country_label}</label>
                            <select name= "destination_country" id="dest_country">
                                <option selected></option>
                                <c:forEach var="country" items="${country_list}">
                                    <option>${country}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="dest_airport">${airport_label}</label>
                            <select name= "destination_airport" id="dest_airport">
                                <option selected></option>

                            </select>
                        </div>
                    </div>
                </div>
                <div id="for_error2"></div>
                <div id="cont_btn">
                    <input type="submit" class="btn btn-primary" value="${cont_label}"/>
                </div>
            </form>
        </div>
    </body>
</html>
