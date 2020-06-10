$(document).ready(function ($) {

    $(document).on('click', '.crews li', function () {
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

        $('#pilots_list li').remove();
        $('#stewardess_list li').remove();

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
        $('#stewardess_list').append(stewardesses);
    }
});
