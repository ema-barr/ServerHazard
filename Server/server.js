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
        handleRequest('getState', data, callback);
    });

    socket.on('nextTurn', function(data, callback) {
        handleRequest('nextTurn', data, callback);
    });

    socket.on('moveActionPawn', function(data, callback) {
        handleRequest('moveActionPawn', data, callback);
    });

    socket.on('solveEmergency', function(data, callback) {
        handleRequest('solveEmergency', data, callback);
    });

    socket.on('takeResources', function(data, callback) {
        handleRequest('takeResources', data, callback);
    });

    socket.on('useBonusCard', function(data, callback) {
        handleRequest('useBonusCard', data, callback);
    });

    socket.on('buildStronghold', function(data, callback) {
        handleRequest('buildStronghold', data, callback);
    });

    socket.on('getCurrentTurn', function(data, callback) {
        handleRequest('getCurrentTurn', data, callback);
    });

    socket.on('getAdjacentLocations', function(data, callback) {
        handleRequest('getAdjacentLocations', data, callback);
    });

    socket.on('getEmergencies', function(data, callback) {
        handleRequest('getEmergencies', data, callback);
    });

    socket.on('getTransports', function(data, callback) {
        handleRequest('getTransports', data, callback);
    });

    socket.on('getStrongholdInfo', function(data, callback) {
        handleRequest('getStrongholdInfo', data, callback);
    });

    socket.on('moveTransportPawn', function(data, callback) {
        handleRequest('moveTransportPawn', data, callback);
    });

    socket.on('chooseProductionCard', function(data, callback) {
        handleRequest('chooseProductionCard', data, callback);
    });

    function handleRequest(requestName, data, callback) {
        console.log("Request received. Name: " + requestName + ", \nData:");
        console.log(data);
        var reqData = data;
        //Add the request name to the JSON request data
        reqData.requestName = requestName;
        //Send the new request object to the game engine
        io.sockets.connected[gameEngineSocketID].emit('request', reqData, function(response) {
            callback(response);
        });
    }
});

app.listen(6882);
console.log('Listening');