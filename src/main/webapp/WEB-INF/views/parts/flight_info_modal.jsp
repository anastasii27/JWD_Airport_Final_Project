<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Flight information</title>
    </head>
    <body>
    <!-- Modal -->
        <div class="modal fade" id="flight_info_modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">

            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">ИНФОРМАЦИЯ О РЕЙСЕ</h5>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div id="info_flight_number">
                                <label> НОМЕР РЕЙСА</label>

                            </div>
                            <div id="info_plane_model">
                                <label>САМОЛЕТ</label>

                            </div>
                            <div class="row">
                                <div class = "info_departure">
                                    <div id="info_dep_date">
                                        <label> ДАТА ОТП</label>
                                    </div>
                                    <div id="info_dep_time">
                                        <label> ВРЕМЯ ОТПР</label>
                                    </div>
                                    <div id="info_dep_country">
                                        <label> СТРАНА ОТПР</label>
                                    </div>
                                    <div id="info_dep_city">
                                        <label> ГОРОД ОТПР</label>
                                    </div>
                                    <div id="info_dep_airport">
                                        <label> СТРАНА ОТПР</label>
                                    </div>
                                </div>
                                <div class = "info_arrival">
                                    <div id="info_dest_date">
                                        <label> ДАТА ПР</label>
                                    </div>
                                    <div id="info_dest_time">
                                        <label> ВРЕМЯ ПР</label>
                                    </div>
                                    <div id="info_dest_country">
                                        <label> СТРАНА ПР</label>
                                    </div>
                                    <div id="info_dest_city">
                                        <label> ГОРОД ПР</label>
                                    </div>
                                    <div id="info_dest_airport">
                                        <label> АЭРОПОРТ ПР</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
