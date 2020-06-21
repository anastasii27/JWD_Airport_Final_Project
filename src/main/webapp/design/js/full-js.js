$(document).ready(function ($) {

    $('.datepicker-here').datepicker({
        dateFormat: 'yyyy-mm-dd',
        firstDay: 1,
        minDate: getMinDate(10),
        maxDate: getMaxDate(31)
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
    $('#add_crew_btn').hide();

    $('.crews li').on('click', function () {
        $('.crews li').css('border', '');
        $(this).css('border', '1px solid #0f1970');
    });

    $('#edit_crew_btn').on('click', function () {

        if(!$(this).hasClass('clicked')) {

            $(this).addClass('clicked');
            $('.close').show();
            $('#create_crew_btn').hide();
            $('#add_crew_btn').show();
            $("span", this).text("Save");
        } else {
            $(this).removeClass('clicked');
            $('.close').hide();
            $('#create_crew_btn').show();
            $('#add_crew_btn').hide();
            $("span", this).text("Edit");
        }
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