$(document).ready(function ($) {

    //crews list
    $(document).on('click', '.crews li', function () {
        let value = getCrewName($(this).text());

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'show_crew_members',crew_name: value},

            success: function (data) {
                createMembersTable(data);
            },
            error: function (data) {
                $('.crew_members ').hide();
                $('#crews_error').show();
            }
        });
    });

    function createMembersTable(data) {

        let pilots = '';
        let stewardesses ='';
        let pilotCount = 0;
        let stewardCount = 0;

        $('#pilots_list li').remove();
        $('#steward_list li').remove();

        $.each(data, function (user, userInfo) {
            if(userInfo.role == "pilot"){
                pilots += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + '</li>';
                pilotCount++;
            }
            if(userInfo.role == "steward"){
                stewardesses += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + '</li>';
                stewardCount++;
            }
        });

        if(stewardCount===0 && pilotCount==0) {
            $('.crew_members').hide();
            $('#crews_error').show();
        }else {
            $('.crew_members').show();
            $('#crews_error').hide();
            $('#pilots_list').append(pilots);
            $('#steward_list').append(stewardesses)
        }
    }
});
