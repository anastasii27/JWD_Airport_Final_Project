$(document).ready(function ($) {

    let lang = $('body').attr('lang');

    $('#my_flights_piker').datepicker({
        dateFormat: 'yyyy-mm-dd',
        firstDay: 1,
        todayButton: new Date(),
        minDate: new Date(),
    });

    $('#dep_arr_piker').datepicker({
        dateFormat: 'yyyy-mm-dd',
        firstDay: 1,
        todayButton: new Date(),
        minDate: getMinDate(1),
        maxDate: getMaxDate(1)
    });

    $('#dep_flights_piker, #dest_flights_piker, #edit_dep_flights_piker, #edit_dest_flights_piker').datepicker({
        dateFormat: 'yyyy-mm-dd',
        firstDay: 1,
        minDate: new Date(),
    });

    //menu
    $(function () {
        let url = window.location.href;
        let action = getActionFromURL(url);

        $('#menu li').each(function () {
            let menu_url = $(this).find('a').attr('href');
            let menu_action = getActionFromURL(menu_url);

            if (action.includes(menu_action)) {
                $(this).addClass('active');
            }
        });
    });

     //arrivals departures
    $('.arr_dep, #no_flights').hide();

    //crews
    $('.crew_members, #crews_error, .close, .add_crew_btn, .choose_crew_members').hide();

    $('.crews li').on('click', function () {
        $('.crews li').css('border', '');
        $(this).css('border', '1px solid #0f1970');
    });

    $('#edit_crew_btn').on('click', function () {

        $('.choose_crew_members, #crews_error, .crew_members').removeClass("d-none");
        if(!$(this).hasClass('clicked')) {

            $(this).addClass('clicked');
            $('.close, .add_crew_btn ').show();
            $('#create_crew_btn').hide();
            if(lang === 'ru'){
                $("span", this).text("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c");
            }else {
                $("span", this).text("Save");
            }

        } else {
            $(this).removeClass('clicked');
            $('.close, .add_crew_btn, .choose_crew_members').hide();
            $('#create_crew_btn').show();
            if(lang === 'ru'){
                $("span", this).text("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0020\u044d\u043a\u0438\u043f\u0430\u0436");
            }else {
                $("span", this).text("Edit crew");
            }
        }
    });

   $(document).on('click','#add_user', function () {
       $('.choose_crew_members').show();
       $('.crew_members').hide();
       $('#pilots_select option:disabled, #stewards_select option:disabled').removeAttr('disabled');
       $('#pilots_select option:selected, #stewards_select option:selected').removeAttr('selected');
    });

    $(document).on('click','#confirm_add', function () {
        $('.choose_crew_members').hide();
        $('.crew_members').show();
    });

    //flight info
    $("#flight_info_modal").on("hidden.bs.modal", function () {
        $('p').remove()
    });

    //create_flight
    $("#create_flight #planes, #create_flight #dispatcher").prop("disabled", true);

    $('#dep_country').change(function () {
        let destinationCountry = $('#dest_country').val();

        if(destinationCountry !== 'Belarus'){
            $('#dispatcher option').remove();
        }
    });

    $('#dest_country').change(function () {
        let departureCountry = $('#dep_country').val();

        if(departureCountry !== 'Belarus'){
            $('#dispatcher option').remove();
        }
    });



    $('#dep_airport').on('change', function () {
        $('#planes option').remove();
    });

    //choose crew
    $('#crew_table').on('click','tr', function () {
        if($(this).hasClass("values")) {
            $('tr').css('background', '');
            $('tr').removeClass('clicked');
            $(this).css('background', '#007bff26');
            $(this).addClass('clicked');
        }
    });

    //admin flights
    $('#admin_flights #flights_table .flights').append('<button type="button" class="delete_flight_btn" >&times;</button>' +
        '<button type="button" class="edit_flight_btn" data-toggle="modal" data-target="#flight_edit_modal">&#9998;</button>')

    $("#flight_edit_modal").on("hidden.bs.modal", function () {
        $('#edit_dest_country p, #edit_dep_country p, #edit_planes option, #edit_dest_airport option,' +
            ' #edit_dep_airport option, #edit_crew option, #edit_flight_number p').remove()
    });

    $('#no_delete_mes, #no_choose_mes').hide();

    $(document).mouseup(function (e){
        $('#no_delete_mes, #no_choose_mes').fadeOut();
    });

    //flight editing
    $(document).on('change', '#edit_dest_airport ,#edit_dep_airport', function () {
        $('#edit_planes option, #edit_crew option').remove()
    });

    //flight timetable
    $('#search_table, #no_flights').hide();
});

function getMaxDate(days) {
    let date = new Date();

    date.setDate(date.getDate() + days);
    return date;
}

function getMinDate(days) {
    let date = new Date();

    date.setDate(date.getDate() - days);
    return date;
}

function getActionFromURL(url) {
    let start = url.indexOf("=");
    let end = url.indexOf("&");

    if(end===-1){
        return url.substring(start);
    }
    return url.substring(start, end);
}

function determineTableType(flightType, tableLine) {
    if(flightType === 'departure'){
        $('#dep_table').show();
        $('#departures').append(tableLine);
    }
    if(flightType === 'arrival'){
        $('#arr_table').show();
        $('#arrivals').append(tableLine);
    }
}

function getCrewName(value){
    let end = value.indexOf(" ");
    return value.substring(0, end);
}

function getCrewMemberName(value){
    let end = value.lastIndexOf(" ");
    return value.substring(0, end);
}

function addZeroBeforeValue(value){
    if(value.toString().length === 1){
       return "0".concat(value);
    }
    return value;
}

function setFlightStatus(status) {
    $('#edit_status option').each(function (i, elem) {
        if($(this).text()=== status){
            $(this).prop('selected', true);
        }
    });
}
