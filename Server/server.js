//Server nodejs
var http = require('http'),
    fs = require('fs'),
    index = fs.readFileSync(__dirname + '/index.html');

var app = http.createServer(function(req, res) {
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end(index);
});

//Game Engine socket
var gameEngineSocketID;

//Device socket
var clientSocketID;

//Dashboard socket
var dashboardSocketID;

var io = require('socket.io').listen(app);

io.on('connection', function (socket) {
    socket.emit('welcome', { message: 'Welcome!', id: socket.id });

    socket.on('init_engine', function(data, callback) {
        console.log('Engine is connected. Id is ' + data.id);
        console.log(data);
        gameEngineSocketID = data.id;
        callback();
    });

    socket.on('init_dashboard', function(data) {
        console.log('dashboard is connected');
        clientSocketID = data.id;
    });

    socket.on('init_client', function(data) {
        console.log('client is connected');
        clientSocketID = data.id;
    });

    socket.on('request', function(data, callback) {
        io.sockets.connected[gameEngineSocketID].emit('request', data, function(response) {
          callback(response);
       });
    });

    socket.on('getState', function(data, callback) {
        console.log(data);
        var reqData = data;
        //Add the request name to the JSON request data
        reqData.requestName = 'getState';
        //Send the new request object to the game engine
        io.sockets.connected[gameEngineSocketID].emit('request', reqData, function(response) {
            callback(response);
        });
    });

    socket.on('nextTurn', function(data, callback) {
        console.log(data);
        var reqData = data;
        //Add the request name to the JSON request data
        reqData.requestName = 'nextTurn';
        //Send the new request object to the game engine
        io.sockets.connected[gameEngineSocketID].emit('request', reqData, function(response) {
            callback(response);
        });
    });

    socket.on('solveEmergency', function(data, callback) {
        console.log(data);
        var reqData = data;
        //Add the request name to the JSON request data
        reqData.requestName = 'nextTurn';
        //Send the new request object to the game engine
        io.sockets.connected[gameEngineSocketID].emit('request', reqData, function(response) {
            callback(response);
        });
    });
});

app.listen(6882);
console.log('Listening');