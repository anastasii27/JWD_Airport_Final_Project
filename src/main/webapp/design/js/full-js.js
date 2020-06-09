$(document).ready(function ($) {

    $('.datepicker-here').datepicker({
        dateFormat: 'yyyy-mm-dd',
        firstDay: 1,
        minDate: getMinDate(10),
        maxDate: getMaxDate(31)
    });

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

    console.log(end);

    if(end==-1){
        console.log("fuv")
        return url.substring(start);
    }
    return url.substring(start, end);
}