$(document).ready(function ($) {

    //crews list
    $(document).on('click', '.crews li', function () {
        let value = $(this).text();

        $.get('/JWD_Task3_war/ajax',
            {
                command: 'show_crew_members',
                crew_name: value
            },
            createMembersTable
        ).fail(function () {
           alert("error")
        });
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
            if(userInfo.role == "steward"){
                stewardesses += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + '</li>';
            }
        });

        $('#pilots_list').append(pilots);
        $('#steward_list').append(stewardesses);
    }

    // //crew creation
    // $('#create_crew').focusout(function () {
    //
    //     let crewName = $('input[name="crew_name"]').val();
    //
    //     $.post('/JWD_Task3_war/ajax',
    //         {
    //             command: 'create_crew',
    //             crew_name: crewName
    //         },
    //         creationResult
    //     );
    // });
    //
    // function creationResult(data) {
    //
    //    if(data=='true'){
    //        $('#create_crew').css({"background": "#a6c5ff", "border": "1px solid #0f1970"});
    //    }
    // }
});
