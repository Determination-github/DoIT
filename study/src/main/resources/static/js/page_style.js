var stompClient = null;
var sender = null;
var receiver = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

window.onload = function() {
    var socket = new SockJS("/websocket");
    stompClient = Stomp.over(socket);
    receiver = $("#sessionId").val();
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/note/receiving/'+receiver, function (greeting) {
            $('#noteModal').modal('hide');
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    let id =  document.getElementById('receiver-id').getAttribute('value');
    stompClient.send("/note/send/"+id, {}, JSON.stringify({
                                            'user_id' : $("#sender_id").val(),
                                            'receiver_id' : document.getElementById('receiver-id').getAttribute('value'),
                                            'title' : $("#note-title").val(),
                                            'content' : $("#note-content").val()
                                            }));
}

function showGreeting(message) {
    $(".notification-container").append("<p>"+message+"</p");
    showNotification();
    disabledNotification();
//    alert(message);
//    $(".note-bar").append("<li><a class='dropdown-item'>" + message + "</a></li>");
}

$(function () {
    $("form").on('submit', function (e) {
//        e.preventDefault();
    });
//    $( "#note" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send-note" ).click(function() { sendName(); });
});

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