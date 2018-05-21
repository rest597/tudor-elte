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
        url: "/tudor/getAllAnswersForQuestion/" + questionId,
        dataType: "json",
        success: function(response){
            //Important code starts here to populate table
            jQuery.each(response, function(i,data) {
                var buttons = '<button onclick="archiveAnswer(' + data.answerId + ')" class="btn btn-info btn-sm">';
                buttons += '<span class="fa fa-archive"></span></button>';
                buttons += '<button onclick="unarchiveAnswer(' + data.answerId + ')" class="btn btn-secondary btn-sm">';
                buttons += '<span class="fa fa-archive"></span></button>';


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

function newAnswerModal(questionId){
    //Set question id
    $('#newAnswerForm').attr("onsubmit", "newAnswer(" + questionId + ")");
}


function newAnswer(questionId){
    var newAnswerData = {
        "title": $('#newAnswerTitle').val(),
        "body": $('#newAnswerBody').val(),
    };

    //Save answer
    $.ajax({
        type: "POST",
        url: "/tudor/newAnswer/" + questionId,
        data: JSON.stringify(newAnswerData),
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



function archiveAnswer(answerId){
    if(confirm('Archive answer: #' + answerId + '?')){
        $.ajax({
            type: "POST",
            url: "/tudor/archiveAnswer/" + answerId,
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

function unarchiveAnswer(answerId){
    if(confirm('Unarchive answer: #' + answerId + '?')){
        $.ajax({
            type: "POST",
            url: "/tudor/unarchiveAnswer/" + answerId,
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

function getMySpec(){
    $.ajax({
        type: "GET",
        url: "/tudor/getMySpec",
        success: function(response){
            $('#mySpec').text(response);
        },
        error: function(a, b, c){
            console.log(a);
            console.log(b);
            console.log(c);
        }
    });
}


$(function() { 
    // Load my spec
    getMySpec();

    //Load questions  
    $.ajax({
        type: "GET",
        url: "/tudor/getAllQuestions",
        dataType: "json",
        success: function(response){
            //Important code starts here to populate table
            jQuery.each(response, function(i,data) {
                var buttons = '<button onclick="loadAnswers(' + data.questionId + ')" data-toggle="modal" data-target="#questionModal" class="btn btn-info btn-sm">';
                buttons += '<span class="fa fa-exclamation"></span></button> ';
                
                if(!data.archived){
                    buttons += '<button onclick="newAnswerModal(' + data.questionId + ')" data-toggle="modal" data-target="#newAnswerModal" class="btn btn-secondary btn-sm">';
                    buttons += '<span class="fa fa-plus"></span></button> ';
                }

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