var socket;
var ws;



function connect() {
    socket = new SockJS("http://localhost:8080/echo");
    ws = Stomp.over(socket);

    ws.connect({}, function(frame) {
        console.log("connected")
        ws.subscribe("/user/queue/errors", function(message) {
            alert("Error " + message.body);
        });

        console.log('session id :' + socket.id);

        ws.subscribe("/user/queue/server_messages", function(message) {
            showGreeting(message.body);
        });

        ws.subscribe("/user/queue/reply", function(message) {
            showGreeting(message.body);
        });
    }, function(error) {
        console.log("STOMP error " + error);
    });
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    let message = '{"name": ' + $("#name").val() + '}';
    console.log('Sending message to server: ' + message);
    ws.send("/app/message", {priority: 9}, message);
}

function showGreeting(message) {
    $("#greetings").append(" " + message + "");
}


