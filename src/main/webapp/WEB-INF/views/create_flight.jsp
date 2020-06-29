<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<html>
    <head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.js.lang" var="lang" />

        <title>CREATE FLIGHT</title>

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

    </head>
    <body>
        <jsp:include page="parts/header.jsp"/>
        <form action="airport" method="post" id="create_flight">
            <div class="row">
                <div class="form-group">
                    <input type="hidden" name="action" value="create_flight" />
                    <label for="flight_number"> НОМЕР РЕЙСА</label>
                    <input type="text" id="flight_number" name="flight_number"/>
                </div>
                <div class="form-group">
                    <label for="dispatcher">ДИСПЕТЧЕР</label>
                    <select name= "user" id="dispatcher">
                        <option selected></option>

                    </select>
                </div>
                <div class="form-group">
                    <label for="planes">САМОЛЕТ</label>
                    <select name= "planes" id="planes">

                    </select>
                </div>
            </div>
            <div class="row">
                <div id="departure" class="col-md-6">
                    <h3>ОТПРАВЛЕНИЕ</h3>
                    <div class="form-group">
                        <label for="dep_flights_piker">ДАТА ОТПРАВЛЕНИЯ</label>
                        <input type='text' name="departure_date" id= "dep_flights_piker" class="datepicker-here admin_picker"
                               data-language="${lang}" value = "${requestScope.departure_date}"/>
                    </div>
                    <div class="form-group">
                        <label for="dep_time">ВРЕМЯ ОТПРАВЛЕНИЯ</label>
                        <input type='time' name="departure_time" id= "dep_time"/>
                    </div>
                    <div class="form-group">
                        <label for="dep_country">СТРАНА ОТПРАВЛЕНИЯ</label>
                        <select name= "departure_country" id="dep_country">
                            <option selected></option>
                            <c:forEach var="country" items="${country_list}">
                                <option>${country}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="dep_airport">АЭРОПОРТ ОТПРАВЛЕНИЯ</label>
                        <select name= "departure_airport" id="dep_airport">
                            <option selected></option>

                        </select>
                    </div>
                </div>
                <div id="arrival" class="col-md-6">
                    <h3>ПРИБЫТИЕ</h3>
                    <div class="form-group">
                        <label for="dest_flights_piker">ДАТА ПРИБЫТИЯ</label>
                        <input type='text' name="destination_date" id= "dest_flights_piker" class="datepicker-here admin_picker"
                               data-language="${lang}" value = "${requestScope.departure_date}"/>
                    </div>
                    <div class="form-group">
                        <label for="dest_time">ВРЕМЯ ПРИБЫТИЯ</label>
                        <input type='time' name="destination_time" id= "dest_time"/>
                    </div>
                    <div class="form-group">
                        <label for="dest_country">СТРАНА ПРИБЫТИЯ</label>
                        <select name= "destination_country" id="dest_country">
                            <option selected></option>
                            <c:forEach var="country" items="${country_list}">
                                <option>${country}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="dest_airport">АЭРОПОРТ ПРИБЫТИЯ</label>
                        <select name= "destination_airport" id="dest_airport">
                            <option selected></option>

                        </select>
                    </div>
                </div>
            </div>
            <input type="submit" class="btn btn-primary" value="СОЗДАТЬ"/>
        </form>
    </body>
</html>
