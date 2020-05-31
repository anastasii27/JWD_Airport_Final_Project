$(document).ready(function ($) {

    $('.datepicker-here').datepicker({
        dateFormat: 'yyyy-mm-dd',
        firstDay: 1,
        minDate: getMinDate(10),
        maxDate: getMaxDate(30)
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

});