function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

function editUser(userId){
    alert('jaj ' + userId);
}



function loadAnswers(questionId){
    $("#answerTable tbody").empty();

    //Load answers
    $.ajax({
        type: "GET",
        url: "/admin/getAllAnswersForQuestion/" + questionId,
        dataType: "json",
        success: function(response){
            //Important code starts here to populate table
            jQuery.each(response, function(i,data) {
                var buttons = '<button onclick="deleteAnswer(' + data.answerId + ')" class="btn btn-danger btn-sm">';
                buttons += '<span class="fa fa-trash"></span></button>';

                var ratingStr = 'No rating';
                if(parseInt(data.rating) > 0){
                    ratingStr = '<span class="fa fa-star"></span>';
                    for (let index = 1; index < parseInt(data.rating); index++) {
                        ratingStr += '<span class="fa fa-star"></span>';
                    }
                }
                
                var dataStr = "<tr><td>" + (i+1) + "</td>";
                dataStr += "<td>" + data.answerId + "</td>";
                dataStr += "<td>" + data.title + "</td>";
                dataStr += "<td>" + formatDate(data.date) + "</td>"
                dataStr += "<td>" + data.body + "</td>"
                dataStr += "<td>" + ratingStr + "</td>"
                dataStr += "<td>" + data.archived + "</td>"
                dataStr += "<td>" + buttons + "</td></tr>"
                $("#answerTable tbody").append(dataStr);
            });
        }
    });
}

function deleteUser(userType, userId){
    //Delete user
    if(confirm('Delete ' + userType + ': #' + userId + '?')){
        $.ajax({
            type: "DELETE",
            url: "/admin/deleteUser/" + userType + "/" + userId,
            success: function(response){
                location.reload();
            },
            error: function(a, b, c){
                console.log(a);
                console.log(b);
                console.log(c);
            }
        });
    }
}

function deleteQuestion(questionId){
    alert('juj ' + questionId);
}

function deleteAnswer(questionId){
    alert('juj ' + questionId);
}

function newCustomer(){
    var newCustomerData = {
        "type": "Customer",
        "username": $('#customerUsername').val(),
        "password": $('#customerPassword').val(),
        "name": $('#customerName').val(),
        "age": $('#customerAge').val(),
        "bio": $('#customerBio').val()
    };

    //Save customer
    $.ajax({
        type: "POST",
        url: "/admin/newUser",
        data: JSON.stringify(newCustomerData),
        dataType: "json",
        contentType: "application/json",
        success: function(response, xhr){
            location.reload();
        },
        error: function(a, b, c){
            console.log(a);
            console.log(b);
            console.log(c);
        }
    });

    return false;
}

function newTudor(){
    var newTudorData = {
        "type": "Tudor",
        "username": $('#tudorUsername').val(),
        "password": $('#tudorPassword').val(),
        "name": $('#tudorName').val(),
        "age": $('#tudorAge').val(),
        "specialization": $('#tudorSpec').val(),
        "experience": $('#tudorExperience').val()
    };

    //Save tudor
    $.ajax({
        type: "POST",
        url: "/admin/newUser",
        data: JSON.stringify(newTudorData),
        dataType: "json",
        contentType: "application/json",
        success: function(response, xhr){
            location.reload();
        },
        error: function(a, b, c){
            console.log(a);
            console.log(b);
            console.log(c);
        }
    });

    return false;
}

function newAdmin(){
    var newAdminData = {
        "type": "Admin",
        "username": $('#adminUsername').val(),
        "password": $('#adminPassword').val(),
    };

    //Save admin
    $.ajax({
        type: "POST",
        url: "/admin/newUser",
        data: JSON.stringify(newAdminData),
        dataType: "json",
        contentType: "application/json",
        success: function(response, xhr){
            location.reload();
        },
        error: function(a, b, c){
            console.log(a);
            console.log(b);
            console.log(c);
        }
    });

    return false;
}

$(function() {
    //Load users
    $.ajax({
        type: "GET",
        url: "/admin/getAllUser",
        dataType: "json",
        success: function(response){
            //Important code starts here to populate table
            jQuery.each(response, function(i,data) {
                var buttons = '<button onclick="editUser(' + data.userId + ')" class="btn btn-info btn-sm">';
                buttons += '<span class="fa fa-bars"></span></button> '
                buttons += '<button onclick="deleteUser(\'' + data.userType + '\', ' + data.userId + ')" class="btn btn-danger btn-sm">';
                buttons += '<span class="fa fa-trash"></span></button>';

                var dataStr = "<tr><td>" + (i+1) + "</td>";
                dataStr += "<td>" + data.userId + "</td>";
                dataStr += "<td>" + data.username + "</td>";
                dataStr += "<td>" + data.userType + "</td>"
                dataStr += "<td>" + buttons + "</td></tr>"
                $("#userDataTable tbody").append(dataStr);
            });
        }
    });
 
    //Load questions  
    $.ajax({
        type: "GET",
        url: "/admin/getAllQuestions",
        dataType: "json",
        success: function(response){
            //Important code starts here to populate table
            jQuery.each(response, function(i,data) {
                var buttons = '<button onclick="loadAnswers(' + data.questionId + ')" data-toggle="modal" data-target="#questionModal" class="btn btn-info btn-sm">';
                buttons += '<span class="fa fa-exclamation"></span></button> '
                buttons += '<button onclick="deleteQuestion(' + data.questionId + ')" class="btn btn-danger btn-sm">';
                buttons += '<span class="fa fa-trash"></span></button>';

                var dataStr = "<tr><td>" + (i+1) + "</td>";
                dataStr += "<td>" + data.questionId + "</td>";
                dataStr += "<td>" + data.title + "</td>";
                dataStr += "<td>" + formatDate(data.date) + "</td>"
                dataStr += "<td>" + data.body + "</td>"
                dataStr += "<td>" + data.specialization + "</td>"
                dataStr += "<td>" + data.archived + "</td>"
                dataStr += "<td>" + buttons + "</td></tr>"
                $("#userQuestionTable tbody").append(dataStr);
            });
        }
    });

});