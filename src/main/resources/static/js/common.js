const socket = new SockJS("/websocket");
const stompClient = Stomp.over(socket);

window.onload = function() {
    const receiver = $("#sessionId").val();
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/alarm/receiving/'+receiver, function (greeting) {
            showGreeting(JSON.parse(greeting.body).message, JSON.parse(greeting.body).url);
        });
    });
}

function sendNote() {
    let id =  document.getElementById('receiver-id').getAttribute('value');
    stompClient.send("/server/note/"+id, {}, JSON.stringify({
                                            'user_id' : $("#sender_id").val(),
                                            'receiver_id' : document.getElementById('receiver-id').getAttribute('value'),
                                            'title' : $("#note-title").val(),
                                            'content' : $("#note-content").val()
                                            }));
    $('#noteModal').modal('hide');
    alert("쪽지가 전송되었습니다.");
}

function showGreeting(message, url) {
    showNotification();
    disabledNotification();
    removeItem();
    $(".notification-container").append("<p>" + message + "</p>");
    $(".alarm-msg").append("<li><a id='alarm-msg' href="+url+" class='dropdown-item'>" + message + "</a></li>");
}

$(function () {
    $("form").on('submit', function (e) {
//        e.preventDefault();
    });
    $( "#send-note" ).click(function() { sendNote(); });
});


function reply(object) {
            let note_id = $(object).attr('value');
            let id =  $("#receiver_id"+note_id).val();
            stompClient.send("/server/note/"+id, {}, JSON.stringify({
                                                    'user_id' : $('#sender_id'+note_id).val(),
                                                    'receiver_id' : $('#receiver_id'+note_id).val(),
                                                    'title' : $('#note-title'+note_id).val(),
                                                    'content' : $('#note-content'+note_id).val()
                                                    }));
            $('#noteModal-reply'+note_id).modal('hide');
            alert("답장이 전송되었습니다.");
            window.location.reload();
}

const notification = document.getElementById('notification-container')

const showNotification = () => {
  notification.classList.add('show')
  setTimeout(() => {
    notification.classList.remove('show')
  }, 10000)
}

const disabledNotification = () => {
  setTimeout(() => {
    $(".notification-container *").remove();
  }, 10000)
}

function removeItem()  {
    $(".empty-dropdown").remove();
}

//알람 읽음 체크
function click_alarm(object) {
    const id = $(object).attr('value');

    const data = {
        alarm_id : id
    }
    $.ajax({
        type:"DELETE",
        url:"/alarm/" + data.alarm_id,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        cache : false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
    }).done(function() {
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}


const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");
const name = $("#userName").val();