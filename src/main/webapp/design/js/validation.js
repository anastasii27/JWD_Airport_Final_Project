$(document).ready(function ($) {

    let lang = $('body').attr('lang');

    $('#submit').on('click',function (){
        $('p').hide();
    });

    $('#my_flights_form').validate({
        rules:{
            departure_date:{
                required: true,
                pattern_date: true,
                legal_date: true
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
                pattern_date: true,
                legal_date: true
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

                error:alert('ERROR')
            });
            return false;
        }
    });

    $('#sign_up').validate({
        rules:{
            user_name: {
                required_value: true,
                user_name_surname: true
            },
            surname: {
                required_value: true,
                user_name_surname: true
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
                login_len: [4,15],
                pattern_check: true
            },
            user_password:{
                required_value: true,
                password_len: [6,15],
                pattern_check: true
            },
            confirm_password:{
                required_but_no_mes: true,
                equal_pas: true
            }
        },

        errorPlacement: function(error, element){

            let id= element.attr("id");

            if(id=='inputPassword1' ||id=='inputStart'|| id =="inputRole"){
                $(element).after(error);
            }else{
                $('label[for="'+ id +'"]').append(error);
            }

            if(id=='inputPassword2'){
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

    $.validator.addMethod('required_value', function(value) {
        return value.length !== 0;
    }, function ( ) {
        if(lang=='en'){
            return "Enter data";
        }else{
            return "\u0412\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u0434\u0430\u043d\u043d\u044b\u0435";
        }
    });

    $.validator.addMethod('user_name_surname', function(value) {
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

    $.validator.addMethod('login_len', function(value) {
        return !(value.length < 4 || value.length > 15);
    }, function () {
        if(lang=='en'){
            return "Use 4-15 characters for your login";
        }else{
            return "\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435\u0020\u043e\u0442\u0020\u0034\u0020\u0434\u043e\u0020\u0031\u0035\u0020\u0441\u0438\u043c\u0432\u043e\u043b\u043e\u0432";
        }
    });

    $.validator.addMethod('password_len', function(value) {
        return !(value.length < 6 || value.length > 15);
    }, function () {
        if(lang=='en'){
            return "Use 6-15 characters for your password";
        }else{
            return "\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435\u0020\u043e\u0442\u0020\u0036\u0020\u0434\u043e\u0020\u0031\u0035\u0020\u0441\u0438\u043c\u0432\u043e\u043b\u043e\u0432";
        }
    });

    $.validator.addMethod('pattern_check', function(value) {
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

    $.validator.addMethod('equal_pas', function(value) {
        return value== $('#inputPassword1').val();
    }, function () {
        if(lang=='en'){
            return "Those password don`t match.Try again";
        }else{
            return "\u041f\u0430\u0440\u043e\u043b\u0438\u0020\u043d\u0435\u0020\u0441\u043e\u0432\u043f\u0430\u0434\u0430\u044e\u0442\u002e\u0020\u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0439\u0442\u0435\u0020\u0441\u043d\u043e\u0432\u0430";
        }
    });

    $.validator.addMethod('pattern_date', function(value) {
        return value.match(new RegExp("^" + "\\d{4}-\\d{2}-\\d{2}" + "$"));
    }, function () {
        return '';
    });

    $.validator.addMethod('legal_date', function(value) {

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
});