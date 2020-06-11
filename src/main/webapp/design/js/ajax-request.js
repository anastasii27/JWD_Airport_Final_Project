$(document).ready(function ($) {

    $(document).on('click', '.crews li', function () {
        let value = $(this).text();

        $.get('/JWD_Task3_war/ajax',
            {
                command: 'crew_members',
                crew_name: value
            },
            createMembersTable
        );
    });

    function createMembersTable(data) {

        $('#pilots_list li').remove();
        $('#steward_list li').remove();

        let pilots = '';
        let stewardesses ='';

        $.each(data, function (user, userInfo) {
            if(userInfo.role == "pilot"){
                pilots += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + '</li>';
            }
            if(userInfo.role == "stewardess"){
                stewardesses += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + '</li>';
            }
        });

        $('#pilots_list').append(pilots);
        $('#steward_list').append(stewardesses);
    }

    let flightType='';

    $(document).on('click', '#submit', function (e) {

        e.preventDefault();

        let from = $('input[name="from"]').val();
        let city = $('select[name="city"]').val();
        flightType = $('input[name="type"]:checked').val();
        let departure_date = $('input[name="departure_date"]').val();

        $.get('/JWD_Task3_war/ajax',
            {
                command: 'show_flights',
                city: city,
                departure_date: departure_date,
                type: flightType,
                from: from
            },
            createFlightsTable
        );
    });

    function createFlightsTable(data) {

        let tableLine = '';
        let count =0;

        $('.arr_dep').hide();
        $('#noFlights').hide();
        $('#arrivals tr td').remove();
        $('#departures tr td').remove();

        $.each(data, function (flight, flightInfo) {

            tableLine += '<tr><td>' + flightInfo.flightNumber + '</td>'+
                       '<td>' +flightInfo.destinationCity +'('+flightInfo.destinationAirportShortName +')' + '</td>'+
                       '<td>' +flightInfo.planeModel + '</td>'+
                       '<td>' +flightInfo.departureTime.hour +':' + flightInfo.departureTime.minute + '</td>'+
                       '<td>' +flightInfo.status + '</td></tr>';
            count++;
        });

        if(count===0){
            $('#noFlights').show();
        }
        else {
            determineTableType(flightType, tableLine);
        }

    }
});
