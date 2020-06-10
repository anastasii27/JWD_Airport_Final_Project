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
        $('#steward_list li').remove();

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
        $('#steward_list').append(stewardesses);
    }

    $(document).on('click', '#submit', function () {

        let from = $('input[name="from"]').val();
        let city = $('input[name="city"]').val();
        let type = $('input[name="type"]').val();
        let departure_date = $('input[name="departure_date"]').val();

        $.get('/JWD_Task3_war/aaa',
            {
                command: 'show_flights',
                city: city,
                departure_date: departure_date,
                type:type,
                from: from
            }
        );
    });
});
