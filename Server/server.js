//Server nodejs
var http = require('http'),
    fs = require('fs'),
    // NEVER use a Sync function except at start-up!
    index = fs.readFileSync(__dirname + '/index.html');

var app = http.createServer(function(req, res) {
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end(index);
});

//socket del motore di gioco
var gameEngineSocketID;

//socket del client;
var clientSocketID;

var io = require('socket.io').listen(app);

io.on('connection', function (socket) {
    socket.emit('welcome', { message: 'Welcome!', id: socket.id });

    socket.on('init_engine', function(data, callback) {
        console.log('Engine is connected. Id is ' + data.id);
        console.log(data);
        gameEngineSocketID = data.id;
        callback();
    });

    socket.on('init_client', function(data) {
        console.log('client is connected');
        clientSocketID = data.id;
    });

    socket.on('action', function doAction(data, callback) {
        console.log('action received');
        console.log(gameEngineSocketID);
        if (gameEngineSocketID != null) {
            io.sockets.connected[gameEngineSocketID].emit('action', data, function onActionReceived() {
                console.log("action response received");
                io.sockets.connected[gameEngineSocketID].emit('getState', data, function onStateReceived(response) {
                    if (clientSocketID != null) {
                        //io.sockets.connected[clientSocketID].emit('getStateResponse', response);
                        callback(response);
                    } else {
                        console.log('Cannot connect to client');
                    }
                });
            } );
        } else {
            console.log('Cannot connect to game engine');
        }
    });
});

app.listen(6882);
console.log('Listening');