$(document).ready(function ($) {
    let crewName;
    let departureAirport;
    let destinationAirport;
    let date;
    let time;

    //crews list
    $('.crews li').on('click', function () {
        $('.choose_crew_members ').hide();
        crewName = getCrewName($(this).text());
        showCrewAjax(crewName);
    });

    //crew edition
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

    $('.form-group').on('change','#dep_country', function () {
        createAirportSelectAjax('#dep_country');
    });

    $('.form-group').on('change','#dest_country', function () {
        createAirportSelectAjax('#dest_country');
    });

    $('.form-group').on('change','#dep_airport', function () {
        departureAirport = $(this).val();
        let emptyOption = '<option selected>' + ' ' + '</option>';

        $('#planes option').remove();

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'find_free_plane', departure_airport: departureAirport},

            success: function (data) {
                let option = '';

                $.each(data, function (airport, airportInfo) {
                    option += '<option>' + airportInfo.model + ' '+ airportInfo.number + '</option>';
                });

                $('#planes').append(emptyOption);
                $('#planes').append(option);
            },
            error: function (data) {
                $('#planes').append(emptyOption);
            }
        });
    });

    $('#dep_time, #dest_time').on('change', function () {
        time = $(this).val();

        if(departureAirport === 'Minsk(MSQ)'|| destinationAirport=== 'Minsk(MSQ)'){
            createFreeDispatchersSelect(date, time, departureAirport);
        }
    });

    $('#dest_airport').on('change', function () {
        destinationAirport = $(this).val();

        if(destinationAirport === 'Minsk(MSQ)'){
            createFreeDispatchersSelect(date, time, destinationAirport);
        }
    });

    $('#dep_flights_piker').datepicker({
        onSelect: function (){
            date = $('#dep_flights_piker').val();

            if(departureAirport === 'Minsk(MSQ)'|| destinationAirport=== 'Minsk(MSQ)'){
                createFreeDispatchersSelect(date, time, departureAirport);
            }
        }
    });

    $('#dest_flights_piker').datepicker({
        onSelect: function (){
            date = $('#dest_flights_piker').val();

            if(departureAirport === 'Minsk(MSQ)'|| destinationAirport=== 'Minsk(MSQ)'){
                createFreeDispatchersSelect(date, time, departureAirport);
            }
        }
    });

    $('#choose_crew_btn').on('click', function () {
        let crewName = $('.clicked td').eq(0).text();

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'set_crew_for_flight',crew_name: crewName},

            success: function (data) {//todo modal
                if(data === true){
                    //
                }else {

                }
            },
            error: function (data) {
               alert('not created')
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
                    alert("fuck")// todo message
                }
            },
            error: function (data) {
                alert('fuck') // todo message
            }
        });
    });

    $(document).on('click', '.edit_flight_btn',  function () {
        let flightNumber = $(this).parent().find('td').eq(0).text();
        let departureDate = $('#btn').attr('dep_date');

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'flight_info', flight_number: flightNumber, departure_date:departureDate},

            success: function (data) {
                if(data.length!==0){
                    $('#edit_flight_number').val(data.flightNumber);
                    $('#edit_planes').append('<option selected>' + data.plane.model + '  ' + data.plane.number + '</option>');

                    $('#edit_dep_flights_piker').val(data.departureDate.year + '-' + data.departureDate.month + '-' + data.departureDate.day);
                    $('#edit_dep_time').val(data.departureTime.hour + ":" + data.departureTime.minute );
                    $('#edit_dep_airport').append('<option selected>' + data.departureAirportShortName + '</option>');
                    $('#edit_dep_country_edit').append('<p>' + data.departureCountry + '</p>');

                    $('#edit_dest_flights_piker').val(data.destinationDate.year + '-' + data.destinationDate.month + '-' + data.destinationDate.day);
                    $('#edit_dest_time').val(data.destinationTime.hour + ":" + data.destinationTime.minute);
                    $('#edit_dest_airport').append('<option selected>' + data.destinationAirportShortName + '</option>');
                    $('#edit_dest_country_edit').append('<p>' + data.destinationCountry + '</p>');

                }
            },
            error: function (data) {
                alert('fuck') // todo message
            }
        });
    })
});

function showCrewAjax(crewName) {

    $.ajax({
        type: "GET",
        url: "/JWD_Task3_war/ajax",
        dataType:'json',
        data: {command: 'show_crew_members',crew_name: crewName},

        success: function (data) {
            createMembersTable(data);
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
            pilots += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + ' <button type="button" class="close delete_user_btn">&times;</li>';
            pilotCount++;
        }
        if(userInfo.role === "steward"){
            stewardesses += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + ' <button type="button" class="close delete_user_btn">&times;</li>';
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

function createAirportSelectAjax(selector) {
    let country = $(selector).val();
    let emptyOption = '<option selected>' + ' ' + '</option>';
    let airportSelect = getAirportSelector(selector);

    $(airportSelect+' option').remove();

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
        },
        error: function (data) {
            $(airportSelect).append(emptyOption);
        }
    });
}

function getAirportSelector(selector) {
    if(selector === '#dep_country'){
        return '#dep_airport';
    }
    if(selector === '#dest_country'){
        return '#dest_airport';
    }
}

function createFreeDispatchersSelect(date, time, airport) {

    let emptyOption = '<option selected>' + ' ' + '</option>';
    $('#dispatcher option').remove();

    console.log(date+time+airport);
    if(date !== null && time != null && airport != null){
        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'find_free_dispatcher',date: date, time:time, city_with_airport:airport},

            success: function (data) {
                let option = '';

                $.each(data, function (dispatcher, dispatcherInfo) {
                    option += '<option>' + dispatcherInfo.name + ' ' + dispatcherInfo.surname + '</option>';
                });

                $('#dispatcher').append(emptyOption);
                $('#dispatcher').append(option);
            },
            error: function (data) {
                $('#dispatcher').append(emptyOption);
            }
        });
    }
}
