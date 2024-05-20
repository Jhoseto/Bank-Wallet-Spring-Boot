
var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/notifications', function (notification) {

        console.log('Received notification: ' + notification.body);

        if(notification.body !== 0){
            updateNotificationCount(notification.body);

        }
    });
});

function updateNotificationCount(count) {
    var notificationCountElement = document.getElementById('notificationCount');
    notificationCountElement.innerText = count;
    var countElement = document.querySelector('.div-block-8');


    if (count > 0) {
        countElement.style.display = "block";
    } else {
        countElement.style.display = "none";
    }

}