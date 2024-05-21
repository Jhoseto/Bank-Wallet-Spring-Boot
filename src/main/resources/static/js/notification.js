document.addEventListener('DOMContentLoaded', function() {
    var notificationCountElement = document.getElementById('notificationCount');

    // Настройваме началното състояние на елемента да е скрито
    if (notificationCountElement) {
        notificationCountElement.style.display = 'none';
    }

    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/notifications', function(notification) {
            var count = JSON.parse(notification.body);
            if (notificationCountElement) {
                notificationCountElement.innerText = count;
                if (count > 0) {
                    notificationCountElement.style.display = 'block';
                } else {
                    notificationCountElement.style.display = 'none';
                }
            }
        });
    });

    var element = document.getElementById('notification');
    var notificationCountElementBlock = document.querySelector('.div-block-8');

    if (element && notificationCountElementBlock) {
        element.addEventListener('click', function() {
            fetch('/notifications/markAsRead', { method: 'GET' })
                .then(response => {
                    if (response.ok) {
                        notificationCountElementBlock.style.display = 'none';
                        if (notificationCountElementBlock.innerText > 0){
                            location.reload()
                        }

                    } else {
                        console.error('Error marking notifications as read');
                    }
                })
                .catch(error => {
                    console.error('Fetch error:', error);
                });
        });
    } else {
        console.error('Element with id "notification" or ".div-block-8" not found');
    }
});

