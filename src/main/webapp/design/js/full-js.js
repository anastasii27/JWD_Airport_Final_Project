$(document).ready(function ($) {

    $('#my_flights_piker').datepicker({
        dateFormat: 'yyyy-mm-dd',
        firstDay: 1,
        minDate: getMinDate(10),
        maxDate: getMaxDate(31)
    });

    $('#dep_arr_piker').datepicker({
        dateFormat: 'yyyy-mm-dd',
        firstDay: 1,
        minDate: getMinDate(1),
        maxDate: getMaxDate(1)
    });

    $('#dep_flights_piker').datepicker({
        dateFormat: 'yyyy-mm-dd',
        firstDay: 1
    });

    $('#dest_flights_piker').datepicker({
        dateFormat: 'yyyy-mm-dd',
        firstDay: 1
    });

    //menu
    $(function () {
        let url = window.location.href;
        let action = getActionFromURL(url);

        $('#menu li').each(function () {
            let menu_url = $(this).find('a').attr('href');
            let menu_action = getActionFromURL(menu_url);

            if (action ==menu_action) {
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

        if(!$(this).hasClass('clicked')) {

            $(this).addClass('clicked');
            $('.close, .add_crew_btn ').show();
            $('#create_crew_btn').hide();
            $("span", this).text("Save");
        } else {
            $(this).removeClass('clicked');
            $('.close, .add_crew_btn, .choose_crew_members').hide();
            $('#create_crew_btn').show();
            $("span", this).text("Edit");//todo локализация кнопок
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

    //create_flight
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

    //choose crew
    $('#crew_table').on('click','tr', function () {
        $('tr').css('background', '');
        $('tr').removeClass('clicked');
        $(this).css('background', 'red');
        $(this).addClass('clicked');
    });
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