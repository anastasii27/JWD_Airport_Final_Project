let departureAirport;
let destinationAirport;
let date;
let time;
let departureDate;
let destinationDate;
let destinationTime;
let departureTime;

$(document).ready(function ($) {
    const HOME_AIRPORT = 'Minsk(MSQ)';
    let crewName;

    //crews list
    $('.crews li').on('click', function () {
        $('.choose_crew_members ').hide();
        crewName = getCrewName($(this).text());

        showCrewAjax(crewName);
    });

    //crew editing
    $('.delete_crew_btn').on('click', function () {
        let resultConfirm = confirm("Are you really want to delete?");

        if(resultConfirm===true){
            let liToDelete =  $(this).parent();
            $.ajax({
                type: "POST",
                url: "/JWD_Task3_war/ajax",
                dataType:'json',
                data: {command: 'delete_crew',crew_name: crewName},

                success: function (data) {
                    if(data===true){
                        $(liToDelete).remove();
                        $('.crew_members ').hide();
                    }else {
                        //todo mes
                    }
                },
                error: function (data) {
                    $('.crew_members ').hide();
                    $('#crews_error').show();
                }
            });
        }
    });

    $(document).on('click','.delete_user_btn', function () {
        let crewMember = getCrewMemberName($(this).parent().text());
        let resultConfirm = confirm("Are you really want to delete?");

        if(resultConfirm===true){
            $.ajax({
                type: "POST",
                url: "/JWD_Task3_war/ajax",
                dataType:'json',
                data: {command: 'delete_crew_member',crew_name: crewName, user: crewMember},

                success: function (data) {
                    if(data===true){
                        showCrewAjax(crewName);
                    }
                },
                error: function (data) {
                    $('.crew_members ').hide();
                    $('#crews_error').show();
                }
            });
        }
    });

    $(document).on('click','#add_member_btn', function () {
        let crewMember = getCrewMemberName($(this).parent().text());

        $.ajax({
            type: "POST",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'add_crew_member',crew_name: crewName, user: crewMember},

            success: function (data) {
                if(data===true){
                    showCrewAjax(crewName);
                }
            },
            error: function (data) {
                $('.crew_members ').hide();
                $('#crews_error').show();
            }
        });
    });

    $('#add_user').on('click', function () {
        let pilots = $('#pilots_select').text();
        let stewards = $('#stewards_select').text();

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'show_crew_members',crew_name: crewName},

            success: function (data) {
                $.each(data, function (user, userInfo) {
                    user = userInfo.name+ ' '+ userInfo.surname;
                    if(pilots.search(user)!==-1){
                        $("#pilots_select option:contains(\""+user+"\")").attr('disabled', 'disabled');
                    }
                    if(stewards.search(user)!==-1){
                        $("#stewards_select option:contains(\""+user+"\")").attr('disabled', 'disabled');;
                    }
                });
            },
            error: function (data) {
                $('.choose_crew_members ').hide();
                $('#crews_error').show();
            }
        });
    });

    $('#confirm_add').on('click', function () {
        let pilots = $('#pilots_select option:selected').text();
        let stewards = $('#stewards_select option:selected').text();

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'add_crew_member',crew_name: crewName, user: pilots+stewards},

            success: function (data) {
                if(data===true){
                    showCrewAjax(crewName);
                }
            },
            error: function (data) {
                $('.choose_crew_members ').hide();
                $('#crews_error').show();
            }
        });
    });

    //flight info
    $('#flights_table .flights .info').click( function () {
        let flightNumber = $(this).parent().find('td').eq(0).text();
        let depDate = $('.flights').attr('depDate');

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'flight_info', flight_number: flightNumber, departure_date:depDate},

            success: function (data) {//todo на пустоту проверка
                departureAirport =  data.departureAirport + '(' + data.departureAirportShortName + ')';
                departureDate = data.departureDate.year + '-' + addZeroBeforeValue(data.departureDate.month) + '-' + addZeroBeforeValue(data.departureDate.day);
                departureTime = addZeroBeforeValue(data.departureTime.hour) + ":" + addZeroBeforeValue(data.departureTime.minute);

                destinationAirport = data.destinationAirport + '(' + data.destinationAirportShortName + ')';
                destinationDate = addZeroBeforeValue(data.destinationDate.year) + '-' + addZeroBeforeValue(data.destinationDate.month) + '-' + addZeroBeforeValue(data.destinationDate.day);
                destinationTime = addZeroBeforeValue(data.destinationTime.hour) + ":" + addZeroBeforeValue(data.destinationTime.minute)

                $('#info_flight_number').append('<p>' + data.flightNumber + '</p>');
                $('#info_plane_model').append('<p>' + data.plane.model + '<p>');
                $('#info_dep_date').append('<p>' + departureDate + '<p>');
                $('#info_dep_time').append('<p>' + departureTime + '<p>');
                $('#info_dep_country').append('<p>' + data.departureCountry + '<p>');
                $('#info_dep_city').append('<p>' + data.departureCity + '<p>');
                $('#info_dep_airport').append('<p>' + departureAirport + '<p>');
                $('#info_dest_date').append('<p>' + destinationDate + '<p>');
                $('#info_dest_time').append('<p>' + destinationTime + '<p>');
                $('#info_dest_country').append('<p>' + data.destinationCountry + '<p>');
                $('#info_dest_city').append('<p>' + data.destinationCity + '<p>');
                $('#info_dest_airport').append('<p>' + destinationAirport + '<p>');

                $('#flight_info_modal').modal('show');
            }
        });
    });

    //flight creation
    $('#dep_country').on('change', function () {
        createAirportSelectAjax($(this).val(), '#dep_airport');
    });

    $('#dest_country').on('change', function () {
        createAirportSelectAjax($(this).val(), '#dest_airport');
    });

    $('#dep_airport').on('change', function () {
        departureAirport = $(this).val();

        if(departureAirport === HOME_AIRPORT){
            freeDispatchersAjax(departureAirport);
        }

        freePlanesAjax('#planes');
    });

    $('#dest_time').on('change', function () {
        destinationTime = $(this).val();

        freePlanesAjax('#planes');
    });

    $('#dep_time, #dest_time').on('change', function () {
        time = $(this).val();

        if(departureAirport === HOME_AIRPORT || destinationAirport === HOME_AIRPORT){
            freeDispatchersAjax(HOME_AIRPORT);
        }
    });

    $('#dest_airport').on('change', function () {
        destinationAirport = $(this).val();

        if(destinationAirport === HOME_AIRPORT){
            freeDispatchersAjax(destinationAirport);
        }
        freePlanesAjax("#planes");
    });

    $('#dep_flights_piker').datepicker({
        onSelect: function (){
            date = $('#dep_flights_piker').val();
            departureDate = date;

            if(departureAirport === HOME_AIRPORT || destinationAirport=== HOME_AIRPORT){
                freeDispatchersAjax(HOME_AIRPORT);
            }
            freePlanesAjax('#planes');
        }
    });

    $('#dest_flights_piker').datepicker({
        onSelect: function (){
            date = $('#dest_flights_piker').val();
            destinationDate = date;

            if(departureAirport === HOME_AIRPORT|| destinationAirport=== HOME_AIRPORT){
                freeDispatchersAjax(HOME_AIRPORT);
            }
            freePlanesAjax('#planes');
        }
    });

    $('#choose_crew_btn').on('click', function () {
        let crewName = $('.clicked td').eq(0).text();

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'set_crew_for_flight',crew_name: crewName},

            success: function (data) {
                if(data === true){
                    $('#choose_crew_modal').modal('show')
                }else {
                    $('#no_choose_mes').fadeIn();
                }
            },
            error: function (data) {
                $('#no_choose_mes').fadeIn();
            }
        });
    });

    $(document).on('click', '.delete_flight_btn', function () {
        let flightNumber = $(this).parent().find('td').eq(0).text();
        let rowToDelete = $(this).parent();
        let departureDate = $('#btn').attr('dep_date');

        $.ajax({
            type: "POST",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'delete_flight', flight_number: flightNumber, departure_date:departureDate},

            success: function (data) {
                if(data === true){
                    $(rowToDelete).remove();
                }else {
                    $('#no_delete_mes').fadeIn();
                }
            },
            error: function (data) {
                $('#no_delete_mes').fadeIn();
            }
        });
    });

    //flight editing
    $('#edit_dep_airport').on('change', function (){
        departureAirport = $(this).val();

        freePlanesAjax('#edit_planes');
        freeCrewsAjax();
    });

    $('#edit_dest_airport').on('change', function (){
        destinationAirport = $(this).val();

        freePlanesAjax('#edit_planes');
        freeCrewsAjax();
    });

    $('#edit_dest_time').on('change', function (){
        destinationTime = $(this).val();

        freePlanesAjax('#edit_planes');
        freeCrewsAjax();
    });

    $('#edit_dep_time').on('change', function (){
        departureTime = $(this).val();

        freeCrewsAjax();
    });

    $('#edit_dest_flights_piker').datepicker({
        onSelect: function (){
            $('#edit_planes option, #edit_crew option').remove();
            destinationDate = $('#edit_dest_flights_piker').val();

            freePlanesAjax('#edit_planes');
            freeCrewsAjax();
        }
    });

    $('#edit_dep_flights_piker').datepicker({
        onSelect: function (){
            $('#edit_planes option, #edit_crew option').remove();
            departureDate = $('#edit_dep_flights_piker').val();

            freePlanesAjax('#edit_planes');
            freeCrewsAjax();
        }
    });

    $(document).on('click', '.edit_flight_btn',  function () {
        let flightNumber = $(this).parent().find('td').eq(0).text();
        let depDate = $('#btn').attr('dep_date');

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'flight_info', flight_number: flightNumber, departure_date:depDate},

            success: function (data) {//todo на пустоту проверка
                    let flightId = data.id;
                    let plane = data.plane.model + '  ' + data.plane.number;
                    departureAirport =  data.departureCity + '(' + data.departureAirportShortName + ')';
                    departureDate = data.departureDate.year + '-' + addZeroBeforeValue(data.departureDate.month) + '-' + addZeroBeforeValue(data.departureDate.day);
                    departureTime = addZeroBeforeValue(data.departureTime.hour) + ":" + addZeroBeforeValue(data.departureTime.minute);
                    destinationAirport = data.destinationCity + '(' + data.destinationAirportShortName + ')';
                    destinationDate = addZeroBeforeValue(data.destinationDate.year) + '-' + addZeroBeforeValue(data.destinationDate.month) + '-' + addZeroBeforeValue(data.destinationDate.day);
                    destinationTime = addZeroBeforeValue(data.destinationTime.hour) + ":" + addZeroBeforeValue(data.destinationTime.minute)

                    $('#flight_id').val(flightId);
                    $('#edit_flight_number').append('<p>' + data.flightNumber + '</p>');
                    $('#edit_planes').append('<option selected>' + plane + '</option>');

                    if(typeof data.crew=== 'undefined'){
                        $('#edit_crew').append('<option selected>' +  ' ' + '</option>');
                    }else {
                        $('#edit_crew').append('<option selected>' + data.crew + '</option>');
                    }
                    setFlightStatus(data.status);

                    $('#edit_dep_flights_piker').val(departureDate);
                    $('#edit_dep_time').val(departureTime);
                    $('#edit_dep_airport').append('<option selected>' +  departureAirport +'</option>');
                    $('#edit_dep_country').append('<p>' + data.departureCountry + '</p>');
                    $('#edit_dest_flights_piker').val(destinationDate);
                    $('#edit_dest_time').val(destinationTime);
                    $('#edit_dest_airport').append('<option selected>' +  destinationAirport + '</option>');
                    $('#edit_dest_country').append('<p>' + data.destinationCountry + '</p>');

                    addAirportsToSelectAjax(data.departureCountry, data.departureAirportShortName, '#edit_dep_airport');
                    addAirportsToSelectAjax(data.destinationCountry, data.destinationAirportShortName, '#edit_dest_airport');
                    addPlanesToSelectAjax(data);
                    addFreeCrewsToSelectAjax(data);
            }
        });
    });
});

function findMainPilot(crewName) {
    let lang = $('body').attr('lang');
    let commander;

    if(lang === 'ru'){
        commander  = '\u0020\u0428\u0442\u0443\u0440\u043c\u0430\u043d';
    }else {
        commander = ' Commander';
    }

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType: 'json',
            data: {command: 'find_main_pilot',crew_name: crewName},

            success: function (data) {
                $('#pilots_list li').each(function(){
                    if($(this).text().includes(data.name + ' ' + data.surname)){
                        $(this).replaceWith('<li class="list-group-item">'+ data.name + ' ' + data.surname +  commander  +
                            ' <button type="button" class="close delete_user_btn">&times;</button></li>');
                    }
                });
            }
        });

}

function freePlanesAjax(selector) {
    let emptyOption = '<option selected>' + ' ' + '</option>';

    if(departureDate !== undefined && departureAirport !== undefined && destinationDate !== undefined
        && destinationTime !== undefined && destinationAirport !==undefined) {
        $(selector + ' option').remove();

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType: 'json',
            data: {
                command: 'find_free_plane',
                departure_airport: departureAirport,
                departure_date: departureDate,
                destination_airport: destinationAirport,
                destination_date: destinationDate,
                destination_time: destinationTime
            },

            success: function (data) {
                let option = '';

                $.each(data, function (airport, airportInfo) {
                    option += '<option>' + airportInfo.model + ' ' + airportInfo.number + '</option>';
                });

                $(selector).append(emptyOption);
                $(selector).append(option);
            }
        });
    }
}

function freeCrewsAjax() {
    if(departureDate !== undefined && departureAirport !== undefined  && departureTime !== undefined
        && destinationDate !== undefined && destinationTime !== undefined && destinationAirport !==undefined){

        $('#edit_crew option').remove();

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType: 'json',
            data: {
                command: 'find_free_crew',
                departure_date: departureDate,
                departure_time: departureTime,
                departure_airport: departureAirport,
                destination_date: destinationDate,
                destination_time: destinationTime,
                destination_airport: destinationAirport
            },

            success: function (data) {
                let option = '';

                $.each(data, function (crew, crewInfo) {
                    option += '<option>' + crewInfo + '</option>';
                });
                $('#edit_crew').append(option);
            }
        });
    }
}

function freeDispatchersAjax(airport) {
    let emptyOption = '<option selected>' + ' ' + '</option>';

    if(date !== undefined && time !== undefined && airport.length !== 0){
        $('#dispatcher option').remove();

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {
                command: 'find_free_dispatcher', date: date, time: time, city_with_airport: airport
            },

            success: function (data) {
                let option = '';

                $.each(data, function (dispatcher, dispatcherInfo) {
                    option += '<option>' + dispatcherInfo.name + ' ' + dispatcherInfo.surname + '</option>';
                });

                $('#dispatcher').append(emptyOption);
                $('#dispatcher').append(option);
            }
        });
    }
}

function showCrewAjax(crewName) {
    $.ajax({
        type: "GET",
        url: "/JWD_Task3_war/ajax",
        dataType:'json',
        data: {command: 'show_crew_members',crew_name: crewName},

        success: function (data) {
            createMembersTable(data);
            findMainPilot(crewName);
        },
        error: function (data) {
            $('.crew_members ').hide();
            $('#crews_error').show();
        }
    });
}

function createMembersTable(data) {
    let pilots = '';
    let stewardesses ='';
    let pilotCount = 0;
    let stewardCount = 0;

    $.each(data, function (user, userInfo) {
        if(userInfo.role === "pilot"){
            pilots += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + ' <button type="button" class="close delete_user_btn">&times;</button></li>';
            pilotCount++;
        }
        if(userInfo.role === "steward"){
            stewardesses += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + ' <button type="button" class="close delete_user_btn">&times;</button></li>';
            stewardCount++;
        }
    });

    // if(stewardCount===0 && pilotCount==0) {
    //     $('.crew_members').hide();
    //     $('#crews_error').show();
    // }else {
        $('.crew_members').show();
        $('#crews_error').hide();
        $('#pilots_list').empty().append(pilots);
        $('#steward_list').empty().append(stewardesses);
        if($('#edit_crew_btn').hasClass('clicked')){
            $('.close').show();
            $('#add_crew_btn').show();
        }else {
            $('.close').hide();
            $('#add_crew_btn').hide();
        }
    //}
}

function createAirportSelectAjax(country, airportSelect) {
    let emptyOption = '<option selected>' + ' ' + '</option>';
    $(airportSelect + ' option').remove();

    $.ajax({
        type: "GET",
        url: "/JWD_Task3_war/ajax",
        dataType:'json',
        data: {command: 'find_country_airport',country: country},

        success: function (data) {
            let option = '';

            $.each(data, function (airport, airportInfo) {
                option += '<option>' + airportInfo + '</option>';
            });

            $(airportSelect).append(emptyOption);
            $(airportSelect).append(option);
        }
    });
}

function addAirportsToSelectAjax(country, airportShortName, selector){
    $.ajax({
        type: "GET",
        url: "/JWD_Task3_war/ajax",
        dataType:'json',
        data: {command: 'find_country_airport',country: country},

        success: function (data) {
            let option = '';

            $.each(data, function (airport, airportInfo) {
                if(!airportInfo.includes(airportShortName)){
                    option += '<option>' + airportInfo + '</option>';
                }
            });

            $(selector).append(option);
        }
    });
}

function addPlanesToSelectAjax(data) {
    let planeNumber = data.plane.number;

    if(departureDate !== undefined  && departureAirport !== undefined
        && destinationDate !== undefined && destinationAirport !== undefined && destinationTime !== undefined) {
        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType: 'json',
            data: {
                command: 'find_free_plane',
                departure_airport: departureAirport,
                departure_date: departureDate,
                destination_airport: destinationAirport,
                destination_date: destinationDate,
                destination_time: destinationTime
            },

            success: function (data) {
                let option = '';

                $.each(data, function (plane, planeInfo) {
                    if (planeInfo.number !== planeNumber) {
                        option += '<option>' + planeInfo.model + ' ' + planeInfo.number + '</option>';
                    }
                });

                $('#edit_planes').append(option);
            }
        });
    }
}

function addFreeCrewsToSelectAjax(data) {
    if(departureDate !== undefined && departureAirport !== undefined  && departureTime !== undefined
        && destinationDate !== undefined && destinationTime !== undefined && destinationAirport !==undefined){
        let currentCrew = data.crew;

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType: 'json',
            data: {
                command: 'find_free_crew',
                departure_date: departureDate,
                departure_time: departureTime,
                departure_airport: departureAirport,
                destination_date: destinationDate,
                destination_time: destinationTime,
                destination_airport: destinationAirport
            },

            success: function (data) {
                let option = '';

                $.each(data, function (crew, crewInfo) {
                    if (crewInfo !== currentCrew) {
                        option += '<option>' + crewInfo + '</option>';
                    }
                });

                $('#edit_crew').append(option);
            }
        });
    }
}

