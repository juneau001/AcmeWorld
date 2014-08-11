function chatRelay() {
    alert("calling function");
    if ("WebSocket" in window) {
        
     
        var ws = new WebSocket("ws://localhost:8080/AcmeWorld/chatEndpoint");
        
        ws.onopen = function() {
            // WebSocket is connected, send data
            ws.send("Sending message...");
        };
        ws.onmessage = function(evt) {
            var received_message = evt.data;
            alert("Received Message: " + received_message);
        };
        ws.onclose = function() {
            // WebSocket is closed
        };
    } else {
        alert("Browser does not support WebSockets...upgrade!");

    }
}
function closeConnection() {
    if (ws !== null) {
        ws.close();
        ws = null;
    }
}

