$(document).ready(function ($) {

    let lang = $('body').attr('lang');

    $('#submit').on('click',function (){
        $('p').hide();
    });

    $('#my_flights_form').validate({
        rules:{
            departure_date:{
                required: true,
                date_pattern_check: true,
                legal_date_check: true
            }
        },

        messages:{
            departure_date:{
                required:''
            }
        }
    });

    $('#arr_dep_form').validate({
        rules:{
            departure_date:{
                required: true,
                date_pattern_check: true,
                legal_date_check: true
            },
            city: {
                city_check: true
            }
        },

        messages:{
            departure_date:{
                required:''
            }
        },

        submitHandler: function(form) {

            let from = $('input[name="from"]').val();
            let city = $('select[name="city"]').val();
            flightType = $('input[name="type"]:checked').val();
            let departure_date = $('input[name="departure_date"]').val();

            $.ajax({
                type: "GET",
                url: "/JWD_Task3_war/ajax",
                dataType:'json',
                data: {command: 'show_flights',city: city, type: flightType, departure_date: departure_date, from:from},

                success: function (data) {

                    let tableLine = '';
                    let count =0;

                    $('.arr_dep').hide();
                    $('#no_flights').hide();
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
                        $('#no_flights').show();
                    }
                    else {
                        determineTableType(flightType, tableLine);
                    }
                },
                error: function (data) {
                    $('#no_flights').show();
                }
            });
            return false;
        }
    });

    $('#sign_up').validate({
        rules:{
            user_name: {
                required_value: true,
                user_name_surname_check: true
            },
            surname: {
                required_value: true,
                user_name_surname_check: true
            },
            email:{
                required_value: true,
                email_check: true
            },
            user_role: 'role_check',
            career_start_year:{
                required_value: true,
                number_check: true
            },
            login:{
                required_value: true,
                login_len_check: [4,15],
                no_sign_pattern_check: true
            },
            user_password:{
                required_value: true,
                password_len_check: [6,15],
                no_sign_pattern_check: true
            },
            confirm_password:{
                required_but_no_mes: true,
                equal_passwords_check: true
            }
        },

        errorPlacement: function(error, element){

            let id = element.attr("id");

            if(id === 'inputPassword1' || id === 'inputStart'|| id  === "inputRole"){
                $(element).after(error);
            }else{
                $('label[for="'+ id +'"]').append(error);
            }

            if(id==='inputPassword2'){
                $('input[id="inputPassword1"]').after(error);
            }
        }
    });

    $('#sign_in').validate({
        rules:{
            login: 'required',
            user_password: 'required'
        },
        messages:{
            login: null,
            user_password: null
        }
    });

    $('#create_crew').validate({
        rules:{
            crew_name: {
                required: true,
                crew_name_pattern: true,
                remote:{
                    url: '/JWD_Task3_war/ajax',
                    type: 'GET',
                    data:{command: 'check_crew_name', crew_name: function() {
                            return $( "#crew_name" ).val();
                        }
                    },
                    async:true
                }
            },
            first_pilot: 'required',
            pilot: {
                required: true,
                not_equal_pilots_check: true
            },
            steward: 'required'
        },
        messages:{
            crew_name: {
                 required:null,
                 remote: function () {
                     if(lang==="ru"){
                         return "\u0414\u0430\u043d\u043d\u043e\u0435\u0020\u0438\u043c\u044f\u0020\u0443\u0436\u0435\u0020\u0437\u0430\u043d\u044f\u0442\u043e";
                     }else{
                         return "This name is already taken";
                     }
                 }
            },
            first_pilot: null,
            pilot: null,
            steward: null
        }
    });

    $('#create_flight').validate({
        rules:{
            flight_number:{
                required: true,
                flight_number_pattern_check: true,
            },
            planes: 'required',
            departure_date:{
                required:true,
                date_pattern_check:true,
                remote:{
                    url: '/JWD_Task3_war/ajax',
                    type: 'GET',
                    data:{
                        command: 'check_flight_number',
                        flight_number: function() {
                            return $( "#flight_number" ).val();
                        },
                        date: function() {
                            return $( "#dep_flights_piker" ).val();
                        }
                    },
                    async:true
                }
            },
            departure_time:{
                required:true,
                same_date_time_check: true
            },
            departure_country: 'required',
            departure_airport:{
                required: true,
                not_equal_airports_check: true
            },

            destination_date:{
                required:true,
                date_pattern_check:true,
                remote:{
                    url: '/JWD_Task3_war/ajax',
                    type: 'GET',
                    data:{
                        command: 'check_flight_number',
                        flight_number: function() {
                            return $( "#flight_number" ).val();
                        },
                        date: function() {
                            return $( "#dest_flights_piker" ).val();
                        }
                    },
                    async:true
                }
            },
            destination_time:{
                required:true,
                same_date_time_check: true
            },
            destination_country: 'required',
            destination_airport:{
                required: true,
                not_equal_airports_check: true
            }
        },
        messages:{
            flight_number:{
                required: null,
            },
            planes: null,
            departure_date:{
                required: null,
                remote: function () {
                    if(lang==="ru"){
                        return "\u0422\u0430\u043a\u043e\u0439\u0020\u0440\u0435\u0439\u0441\u0020\u0443\u0436\u0435\u0020\u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442\u0021";
                    }else{
                        return "This flight name is already taken";
                    }
                }
            },
            departure_time:{
                required: null,
            },
            departure_country: null,
            departure_airport:{
                required: null,
            },

            destination_date:{
                required: null,
                remote: function () {
                    if(lang==="ru"){
                        return "\u0422\u0430\u043a\u043e\u0439\u0020\u0440\u0435\u0439\u0441\u0020\u0443\u0436\u0435\u0020\u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442\u0021";
                    }else{
                        return "This flight name is already taken";
                    }
                }
            },
            destination_time:{
                required: null,
            },
            destination_country: null,
            destination_airport:{
                required: null,
            }
        },

        errorPlacement: function(error, element){
            let id = element.attr("id");

            if(id === 'dest_flights_piker' || id === 'dep_flights_piker' ){
                $('#flight_number').after(error);
            }
        }

    });

    $('#edit_flight').validate({
        rules:{
            flight_number:{
                required: true,
                flight_number_pattern_check: true,
            },
            planes: 'required',
            departure_date:{
                required:true,
                date_pattern_check:true,//
                remote:{
                    url: '/JWD_Task3_war/ajax',
                    type: 'GET',
                    data:{
                        command: 'check_flight_number',
                        flight_number: function() {
                            return $( "#edit_flight_number" ).val();
                        },
                        date: function() {
                            return $( "#edit_dep_flights_piker" ).val();
                        }
                    },
                    async:true
                }
            },
            departure_time:{
                required:true,
                same_date_time_check_for_edit: true
            },
            departure_airport:{
                required: true,
                not_equal_airports_check: true
            },

            destination_date:{
                required:true,
                date_pattern_check:true,
                remote:{
                    url: '/JWD_Task3_war/ajax',
                    type: 'GET',
                    data:{
                        command: 'check_flight_number',
                        flight_number: function() {
                            return $( "#edit_flight_number" ).val();
                        },
                        date: function() {
                            return $( "#edit_dest_flights_piker" ).val();
                        }
                    },
                    async:true
                }
            },
            destination_time:{
                required:true,
                same_date_time_check_for_edit: true
            },
            destination_airport:{
                required: true,
                not_equal_airports_check: true
            }
        },
        messages:{
            flight_number:{
                required: null,
            },
            planes: null,
            departure_date:{
                required: null,
                remote: function () {
                    if(lang==="ru"){
                        return "\u0422\u0430\u043a\u043e\u0439\u0020\u0440\u0435\u0439\u0441\u0020\u0443\u0436\u0435\u0020\u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442\u0021";
                    }else{
                        return "This flight name is already taken";
                    }
                }
            },
            departure_time:{
                required: null,
            },
            departure_airport:{
                required: null,
            },
            destination_date:{
                required: null,
                remote: function () {
                    if(lang==="ru"){
                        return "\u0422\u0430\u043a\u043e\u0439\u0020\u0440\u0435\u0439\u0441\u0020\u0443\u0436\u0435\u0020\u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442\u0021";
                    }else{
                        return "This flight name is already taken";
                    }
                }
            },
            destination_time:{
                required: null,
            },
            destination_airport:{
                required: null,
            }
        },

        errorPlacement: function(error, element){
            let id = element.attr("id");

            if(id === 'edit_dest_flights_piker' || id === 'edit_dep_flights_piker' ){
                $('#edit_flight_number').after(error);
            }
        }

    });

    $.validator.addMethod('required_value', function(value) {
        return value.length !== 0;
    }, function ( ) {
        if(lang=='en'){
            return "Enter data";
        }else{
            return "\u0412\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u0434\u0430\u043d\u043d\u044b\u0435";
        }
    });

    $.validator.addMethod('user_name_surname_check', function(value) {
        return value.match(new RegExp("^" + "[A-Za-z]+" + "$"));
    }, function () {
        if(lang=='en'){
            return "Invalid input";
        }else{
            return "\u0414\u0430\u043d\u043d\u044b\u0435\u0020\u0432\u0432\u0435\u0434\u0435\u043d\u044b\u0020\u043d\u0435\u0432\u0435\u0440\u043d\u043e";
        }
    });

    $.validator.addMethod('email_check', function(value) {
        return value.match(new RegExp("^" + "[\\w.-]+@[a-z]+.[a-z]+" + "$"));
    }, function () {
        if(lang=='en'){
            return "Enter correct email";
        }else{
            return " \u0045\u006d\u0061\u0069\u006c\u0020\u0432\u0432\u0435\u0434\u0435\u043d\u0020\u043d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u043e";
        }
    });

    $.validator.addMethod('role_check', function(value) {
        return $('select').val() != '';
    }, function () {
        if(lang=='en'){
            return "Choose position";
        }else{
            return "\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435\u0020\u0434\u043e\u043b\u0436\u043d\u043e\u0441\u0442\u044c";
        }
    });

    $.validator.addMethod('number_check', function(value) {
        return value.match(new RegExp("^" + "[0-9]+" + "$"));
    }, function () {
        if(lang=='en'){
            return "Enter a number";
        }else{
            return "\u0412\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u0447\u0438\u0441\u043b\u043e";
        }
    });

    $.validator.addMethod('login_len_check', function(value) {
        return !(value.length < 4 || value.length > 15);
    }, function () {
        if(lang=='en'){
            return "Use 4-15 characters for your login";
        }else{
            return "\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435\u0020\u043e\u0442\u0020\u0034\u0020\u0434\u043e\u0020\u0031\u0035\u0020\u0441\u0438\u043c\u0432\u043e\u043b\u043e\u0432";
        }
    });

    $.validator.addMethod('password_len_check', function(value) {
        return !(value.length < 6 || value.length > 15);
    }, function () {
        if(lang=='en'){
            return "Use 6-15 characters for your password";
        }else{
            return "\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435\u0020\u043e\u0442\u0020\u0036\u0020\u0434\u043e\u0020\u0031\u0035\u0020\u0441\u0438\u043c\u0432\u043e\u043b\u043e\u0432";
        }
    });

    $.validator.addMethod('no_sign_pattern_check', function(value) {
        return value.match(new RegExp("^" + "[A-Za-z0-9]+" + "$"));
    }, function () {
        if(lang=='en'){
            return "Use only letters and  numbers";
        }else{
            return "\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435\u0020\u0442\u043e\u043b\u044c\u043a\u043e\u0020\u0431\u0443\u043a\u0432\u044b\u0020\u0438\u0020\u0446\u0438\u0444\u0440\u044b";
        }
    });

    $.validator.addMethod('required_but_no_mes', function(value) {
        return value.length != 0;
    }, null);

    $.validator.addMethod('equal_passwords_check', function(value) {
        return value== $('#inputPassword1').val();
    }, function () {
        if(lang=='en'){
            return "Those password don`t match.Try again";
        }else{
            return "\u041f\u0430\u0440\u043e\u043b\u0438\u0020\u043d\u0435\u0020\u0441\u043e\u0432\u043f\u0430\u0434\u0430\u044e\u0442\u002e\u0020\u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0439\u0442\u0435\u0020\u0441\u043d\u043e\u0432\u0430";
        }
    });

    $.validator.addMethod('date_pattern_check', function(value) {
        return value.match(new RegExp("^" + "\\d{4}-\\d{2}-\\d{2}" + "$"));
    }, function () {
        return '';
    });

    $.validator.addMethod('legal_date_check', function(value) {

        let enteredDate = new Date(value);

        return enteredDate >= getMinDate(11) ;
    }, function () {
        return '';
    });

    $.validator.addMethod('city_check', function(value) {
        return $('select').val() != '';
    }, function () {
        return'';
    });

    $.validator.addMethod('crew_name_pattern', function(value) {
        return value.match(new RegExp("^" + "[A-Z]{1}\\d{1,3}" + "$"));
    }, function () {
        return'';
    });

    $.validator.addMethod('not_equal_pilots_check', function(value) {//todo redo
        return value !== $('#first_pilot').val();
    }, function () {
        return'';
    });

    $.validator.addMethod('same_date_time_check', function(value) {
        let departure_date = $('#dep_flights_piker').val();
        let departure_time = $('#dep_time').val();
        let destination_date = $('#dest_flights_piker').val();
        let destination_time = $('#dest_time').val();

        if(departure_date.length!==0 && departure_time.length!==0
                && destination_date.length!==0 && destination_time.length!==0){
            let departure = new Date(departure_date + 'T' + departure_time);
            let arrival = new Date(destination_date + 'T' + destination_time);

            return departure<arrival;
        }else {
            return true;
        }
    }, function () {
        if(lang=='en'){
            return "Departure is before arrival!";
        }else{
            return "\u041f\u0440\u0438\u043b\u0435\u0442\u0020\u0440\u0430\u043d\u044c\u0448\u0435\u0020\u0432\u044b\u043b\u0435\u0442\u0430\u0021";
        }
    });

    $.validator.addMethod('same_date_time_check_for_edit', function(value) {
        let departure_date = $('#edit_dep_flights_piker').val();
        let departure_time = $('#edit_dep_time').val();
        let destination_date = $('#edit_dest_flights_piker').val();
        let destination_time = $('#edit_dest_time').val();

        if(departure_date.length!==0 && departure_time.length!==0
            && destination_date.length!==0 && destination_time.length!==0){
            let departure = new Date(departure_date + 'T' + departure_time);
            let arrival = new Date(destination_date + 'T' + destination_time);

            return departure<arrival;
        }else {
            return true;
        }
    }, function () {
        if(lang=='en'){
            return "Departure is before arrival!";
        }else{
            return "\u041f\u0440\u0438\u043b\u0435\u0442\u0020\u0440\u0430\u043d\u044c\u0448\u0435\u0020\u0432\u044b\u043b\u0435\u0442\u0430\u0021";
        }
    });

    $.validator.addMethod('not_equal_airports_check', function(value) {
        let departure_airport = $('select[name = "departure_airport"]').val();
        let destination_airport = $('select[name = "destination_airport"]').val();

        if(departure_airport.length!==0 && destination_airport.length!==0){
            return departure_airport !==destination_airport;
        }else {
            return true;
        }
    }, function () {
        if(lang=='en'){
            return "You entered same airports!";
        }else{
            return "\u0412\u044b\u0020\u0443\u043a\u0430\u0437\u0430\u043b\u0438\u0020\u043e\u0434\u0438\u043d\u0430\u043a\u043e\u0432\u044b\u0435\u0020\u0430\u044d\u0440\u043e\u043f\u043e\u0440\u0442\u044b\u0021";
        }
    });

    $.validator.addMethod('flight_number_pattern_check', function(value) {
        console.log(value)
        return value.match(new RegExp("^" + "[A-Z]{2} \\d{4}" + "$"));
    }, function () {
        if(lang=='en'){
            return "Illegal flight number format!";
        }else{
            return "\u041d\u043e\u043c\u0435\u0440\u0020\u0440\u0435\u0439\u0441\u0430\u0020\u0443\u043a\u0430\u0437\u0430\u043d\u0020\u0432\u0020\u043d\u0435\u0432\u0435\u0440\u043d\u043e\u043c\u0020\u0444\u043e\u0440\u043c\u0430\u0442\u0435\u0021";
        }
    });

});