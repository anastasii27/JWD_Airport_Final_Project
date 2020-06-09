$(document).ready(function ($) {

    $(document).on('click', 'td', function () {
        let value = $(this).text();

        $.get('/JWD_Task3_war/aaa',
            {
                command: 'crew_members',
                crew_name: value
            },
            createMembersTable
        );
    });

    function createMembersTable(data) {

        let pilots = '';
        let stewardesses ='';

        $.each(data, function (user, userInfo) {
            if(userInfo.role == "pilot"){
                pilots += ' '+ userInfo.name + ' ' + userInfo.surname;
            }
            if(userInfo.role == "stewardess"){
                stewardesses += ' ' + userInfo.name + ' ' + userInfo.surname;
            }
        });

        $('#members').html('<p>'+ pilots+'<br/>'+ stewardesses+'</p>');
    }


});
