$(document).ready(function ($) {

    let crewName;

    //crews list
    $('.crews li').on('click', function () {
        $('.choose_crew_members ').hide();
        crewName = getCrewName($(this).text());
        showCrewAjax(crewName);
    });

    //crew edition
    $('.delete_crew_btn').on('click', function () {//todo переделать success
        let resultConfirm = confirm("Are you really want to delete?");

        if(resultConfirm===true){
            $(this).parent().remove();

            $.ajax({
                type: "POST",
                url: "/JWD_Task3_war/ajax",
                dataType:'json',
                data: {command: 'delete_crew',crew_name: crewName},

                success: function (data) {
                   // showCrewAjax(crewName);
                },
                error: function (data) {
                    $('.crew_members ').hide();
                    $('#crews_error').show();
                }
            });
        }
    });

    $(document).on('click','.delete_user_btn', function () {
        let crewMember = getCrewMemberName($(this).parent().text());
        let resultConfirm = confirm("Are you really want to delete?");

        if(resultConfirm===true){
            $.ajax({
                type: "POST",
                url: "/JWD_Task3_war/ajax",
                dataType:'json',
                data: {command: 'delete_crew_member',crew_name: crewName, user: crewMember},

                success: function (data) {
                    if(data===true){
                        showCrewAjax(crewName);
                    }
                },
                error: function (data) {
                    $('.crew_members ').hide();
                    $('#crews_error').show();
                }
            });
        }
    });

    $(document).on('click','#add_member_btn', function () {
        let crewMember = getCrewMemberName($(this).parent().text());

        $.ajax({
            type: "POST",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'add_crew_member',crew_name: crewName, user: crewMember},

            success: function (data) {
                if(data===true){
                    showCrewAjax(crewName);
                }
            },
            error: function (data) {
                $('.crew_members ').hide();
                $('#crews_error').show();
            }
        });
    });

    $('#add_user').on('click', function () {

        // $('#pilots_select option:disabled').removeAttr('disabled');
        // $('#stewards_select option:disabled').removeAttr('disabled');
        // $('#pilots_select option:selected').removeAttr('selected');
        // $('#stewards_select option:selected').removeAttr('selected');

        let pilots = $('#pilots_select').text();
        let stewards = $('#stewards_select').text();

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'show_crew_members',crew_name: crewName},

            success: function (data) {
                $.each(data, function (user, userInfo) {
                    user = userInfo.name+ ' '+ userInfo.surname;
                    if(pilots.search(user)!==-1){
                        $("#pilots_select option:contains(\""+user+"\")").attr('disabled', 'disabled');
                    }
                    if(stewards.search(user)!==-1){
                        $("#stewards_select option:contains(\""+user+"\")").attr('disabled', 'disabled');;
                    }
                });
            },
            error: function (data) {
                $('.choose_crew_members ').hide();
                $('#crews_error').show();
            }
        });
    });

    $('#confirm_add').on('click', function () {

        let pilots = $('#pilots_select option:selected').text();
        let stewards = $('#stewards_select option:selected').text();

        $.ajax({
            type: "GET",
            url: "/JWD_Task3_war/ajax",
            dataType:'json',
            data: {command: 'add_crew_member',crew_name: crewName, user: pilots+stewards},

            success: function (data) {
                if(data===true){
                    showCrewAjax(crewName);
                }
            },
            error: function (data) {
                $('.choose_crew_members ').hide();
                $('#crews_error').show();
            }
        });
    });


});

function showCrewAjax(crewName) {

    $.ajax({
        type: "GET",
        url: "/JWD_Task3_war/ajax",
        dataType:'json',
        data: {command: 'show_crew_members',crew_name: crewName},

        success: function (data) {
            createMembersTable(data);
        },
        error: function (data) {
            $('.crew_members ').hide();
            $('#crews_error').show();
        }
    });
}

function createMembersTable(data) {

    let pilots = '';
    let stewardesses ='';
    let pilotCount = 0;
    let stewardCount = 0;

    $.each(data, function (user, userInfo) {
        if(userInfo.role === "pilot"){
            pilots += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + ' <button type="button" class="close delete_user_btn">&times;</li>';
            pilotCount++;
        }
        if(userInfo.role === "steward"){
            stewardesses += '<li class="list-group-item">'+ userInfo.name + ' ' + userInfo.surname + ' <button type="button" class="close delete_user_btn">&times;</li>';
            stewardCount++;
        }
    });

    // if(stewardCount===0 && pilotCount==0) {
    //     $('.crew_members').hide();
    //     $('#crews_error').show();
    // }else {
        $('.crew_members').show();
        $('#crews_error').hide();
        $('#pilots_list').empty().append(pilots);
        $('#steward_list').empty().append(stewardesses);
        if($('#edit_crew_btn').hasClass('clicked')){
            $('.close').show();
            $('#add_crew_btn').show();
        }else {
            $('.close').hide();
            $('#add_crew_btn').hide();
        }
    //}
}