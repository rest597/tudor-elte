function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}


function loadAnswers(questionId){
    $("#answerTable tbody").empty();

    //Load answers
    $.ajax({
        type: "GET",
        url: "/customer/getAllAnswersForQuestion/" + questionId,
        dataType: "json",
        success: function(response){
            //Important code starts here to populate table
            jQuery.each(response, function(i,data) {
                var buttons = '';
                
                if(!data.archived){
                    buttons += '<button onclick="rateAnswer(' + data.answerId + ')" class="btn btn-info btn-sm">';
                    buttons += '<span class="fa fa-star"></span></button>';
                }

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

function deleteMyProfile(){
    //Delete user
    if(confirm('Delete your profile?')){
        $.ajax({
            type: "DELETE",
            url: "/user/deleteMyprofile",
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
    //Delete question
    if(confirm('Delete question: #' + questionId + '?')){
        $.ajax({
            type: "DELETE",
            url: "/customer/deleteQuestion/" + questionId,
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


function newQuestion(){
    var newCustomerData = {
        "title": $('#newQuestionTitle').val(),
        "body": $('#newQuestionBody').val(),
        "specialization": $('#newQuestionSpec').val()
    };

    //Save question
    $.ajax({
        type: "POST",
        url: "/customer/newQuestion",
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

function archiveQuestion(questionId){
    //Delete user
    if(confirm('Archive question: #' + questionId + '?')){
        $.ajax({
            type: "POST",
            url: "/customer/archiveQuestion/" + questionId,
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

function unarchiveQuestion(questionId){
    //Delete user
    if(confirm('Unarchive question: #' + questionId + '?')){
        $.ajax({
            type: "POST",
            url: "/customer/unarchiveQuestion/" + questionId,
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

function rateAnswer(answerId){
    //Delete user
    var promptRes = prompt("Please enter your rating (1-5):", "");

    if(promptRes != null && promptRes != "" && parseInt(promptRes) > 0 && parseInt(promptRes) <= 5 ){
        $.ajax({
            type: "POST",
            url: "/customer/rateAnswer/" + answerId + "/" + parseInt(promptRes),
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

$(function() { 
    //Load questions  
    $.ajax({
        type: "GET",
        url: "/customer/getMyQuestions",
        dataType: "json",
        success: function(response){
            //Important code starts here to populate table
            jQuery.each(response, function(i,data) {
                var buttons = '<button onclick="loadAnswers(' + data.questionId + ')" data-toggle="modal" data-target="#questionModal" class="btn btn-info btn-sm">';
                buttons += '<span class="fa fa-exclamation"></span></button> '
                buttons += '<button onclick="archiveQuestion(' + data.questionId + ')" class="btn btn-info btn-sm">';
                buttons += '<span class="fa fa-archive"></span></button>';
                buttons += '<button onclick="unarchiveQuestion(' + data.questionId + ')" class="btn btn-secondary btn-sm">';
                buttons += '<span class="fa fa-archive"></span></button>';
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