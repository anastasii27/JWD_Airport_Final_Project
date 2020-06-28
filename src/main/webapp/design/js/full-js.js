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
        firstDay: 1,
        minDate: getMinDate(0)
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
    $('.arr_dep').hide();
    $('#no_flights').hide();

    //crews
    $('.crew_members').hide();
    $('#crews_error').hide();
    $('.close').hide();
    $('.add_crew_btn').hide();
    $('.choose_crew_members').hide();

    $('.crews li').on('click', function () {
        $('.crews li').css('border', '');
        $(this).css('border', '1px solid #0f1970');
    });

    $('#edit_crew_btn').on('click', function () {

        if(!$(this).hasClass('clicked')) {

            $(this).addClass('clicked');
            $('.close').show();
            $('#create_crew_btn').hide();
            $('.add_crew_btn').show();
            $("span", this).text("Save");
        } else {
            $(this).removeClass('clicked');
            $('.close').hide();
            $('#create_crew_btn').show();
            $('.add_crew_btn').hide();
            $('.choose_crew_members').hide();
            $("span", this).text("Edit");//todo локализация кнопок
        }
    });

   $(document).on('click','#add_user', function () {
       $('.choose_crew_members').show();
       $('.crew_members').hide();
       $('#pilots_select option:disabled').removeAttr('disabled');
       $('#stewards_select option:disabled').removeAttr('disabled');
       $('#pilots_select option:selected').removeAttr('selected');
       $('#stewards_select option:selected').removeAttr('selected');
    });

    $(document).on('click','#confirm_add', function () {
        $('.choose_crew_members').hide();
        $('.crew_members').show();

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