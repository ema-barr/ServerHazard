var express = require("express");
//Server nodejs
var http = require('http'),
    fs = require('fs'),
    index = fs.readFileSync(__dirname + '/index.html');
    dashboard = fs.readFileSync(__dirname + '/mockdash.html');



//Server port
var port = 6882;

//Web server
var app = express();
var server = http.createServer(app);

//Game Engine socket
var gameEngineSocketID;

//Device socket
var clientSocketID;

//Dashboard socket
var dashboardSocketID;

var io = require('socket.io').listen(server);

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
        dashboardSocketID = data.id;
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
        handleStandardRequest('getState', data, callback);
    });

    socket.on('nextTurn', function(data, callback) {
        handleRequestAndNotifyDashboard('nextTurn', data, callback);
    });

    socket.on('moveActionPawn', function(data, callback) {
        handleRequestAndNotifyDashboard('moveActionPawn', data, callback);
    });

    socket.on('solveEmergency', function(data, callback) {
        handleRequestAndNotifyDashboard('solveEmergency', data, callback);
    });

    socket.on('takeResources', function(data, callback) {
        handleRequestAndNotifyDashboard('takeResources', data, callback);
    });

    socket.on('useBonusCard', function(data, callback) {
        handleRequestAndNotifyDashboard('useBonusCard', data, callback);
    });

    socket.on('buildStronghold', function(data, callback) {
        handleRequestAndNotifyDashboard('buildStronghold', data, callback);
    });

    socket.on('getCurrentTurn', function(data, callback) {
        handleStandardRequest('getCurrentTurn', data, callback);
    });

    socket.on('getAdjacentLocations', function(data, callback) {
        handleStandardRequest('getAdjacentLocations', data, callback);
    });

    socket.on('getEmergencies', function(data, callback) {
        handleStandardRequest('getEmergencies', data, callback);
    });

    socket.on('getTransports', function(data, callback) {
        handleStandardRequest('getTransports', data, callback);
    });

    socket.on('getStrongholdInfo', function(data, callback) {
        handleStandardRequest('getStrongholdInfo', data, callback);
    });

    socket.on('moveTransportPawn', function(data, callback) {
        handleRequestAndNotifyDashboard('moveTransportPawn', data, callback);
    });

    socket.on('chooseProductionCard', function(data, callback) {
        handleChooseProductionCard('chooseProductionCard', data, callback);
    });
    
    function handleRequestAndNotifyDashboard(requestName, data, callback) {
        console.log("Request received. Name: " + requestName + ", \nData:");
        console.log(data);
        //Route the request to the appropriate method
        request(requestName, data, function(response) {
            responseJ = JSON.parse(response);
            logString = responseJ.logString;
            success = responseJ.success;
            stateRequest = {"requestName": "getState"};
            //Request the state to send to the dashboard
            sendUpdateToDashboard(success, logString, function() {
                callback(response);
            });
            /*
            request("getState", {}, function(getStateResponse) {
                //Send the state to the dashboard
                newResponse = {"success": success, "logString": logString};
                newResponse.state = JSON.parse(getStateResponse)
                io.sockets.connected[dashboardSocketID].emit('update', newResponse);
                callback(response)
            })*/
        });
    }

    function sendMessage(socketID, messageName, data) {
        if (io.sockets.connected[socketID] != undefined) {
            io.sockets.connected[socketID].emit(messageName, data);
        }
    }

    function sendUpdateToDashboard(success, logString, callback) {
        //Request the state to send to the dashboard
        request("getState", {}, function(getStateResponse) {
            //Send the state to the dashboard
            newResponse = {"success": success, "logString": logString};
            newResponse.state = JSON.parse(getStateResponse);
            //io.sockets.connected[dashboardSocketID].emit('update', newResponse);
            sendMessage(dashboardSocketID, 'update', newResponse);
            callback();
        })
    }

    function handleChooseProductionCard(requestName, data, callback) {
        dataJ = data;
        cardIndex = dataJ.cardIndex;
        handleStandardRequest("chooseProductionCard", data, function(response) {
            responseJ = JSON.parse(response);
            success = responseJ.success;
            logString = responseJ.logString;
            request("getState", {}, function(getStateResponse) {
                stateResponseJ = JSON.parse(getStateResponse);
                currentTurnJ = stateResponseJ.currentTurn;
                //If the production cards have been selected, notify the device so it can show the production group
                //interface.
                if (currentTurnJ.state === "MOVE_TRANSPORT_PAWN") {
                    sendMessage(clientSocketID, 'productionStateChanged', {});
                }
                newResponse = {"success": success, "logString": logString, "cardIndex":cardIndex};
                newResponse.state = stateResponseJ;
                sendMessage(dashboardSocketID, 'chooseProductionCard', newResponse);
            });
            callback(response);
        });
    }
    /*
    function handleNextTurn(requestName, data, callback) {
        //Close all pending popup messages
        closePendingPopups();
        console.log("Request received. Name: " + requestName + ", \nData:");
        console.log(data);
        //Route the next turn request to the appropriate method
        request(requestName, data, function(response) {
            responseJ = JSON.parse(response);
            logString = responseJ.logString;
            success = responseJ.success;
            stateRequest = {"requestName": "getState"};
            //Request the new game state that will be sent to the dashboard
            request("getState", {}, function(getStateResponse) {
                currentTurn = JSON.parse(getStateResponse).currentTurn.type;
                newResponse = {"success": success, "logString": logString, "state": getStateResponse};
                onNewTurn(currentTurn, newResponse)
            });
            callback(response);
        });
    }


    function onNewTurn(currentTurn, dashboardResponse) {
        if (currentTurn === "EventTurn") {
            //If it is an event turn, send a popup message to the dashboard
            io.sockets.connected[dashboardSocketID].emit('popupMessage', {"logString": logString}, function() {
                //Send the new state to the dashboard
                io.sockets.connected[dashboardSocketID].emit('update', dashboardResponse)
            })
        } else if (currentTurn === "ProductionTurn") {
            //Get the state of the Production Turn
            request("getCurrentTurn", {}, function(turnResponse) {
                turnResponseJ = JSON.parse(turnResponse);
                if (turnResponseJ.state === "CHOOSE_PRODUCTION_CARDS") {
                    //If the production group must choose the cards, the dashboard must show them in a popup
                    io.sockets.connected[dashboardSocketID].emit('productionCards', turnResponseJ.cards, function() {
                        //Send the new state to the dashboard
                        io.sockets.connected[dashboardSocketID].emit('update', dashboardResponse)
                    })
                }
            });
        } else {
            //Send the new state to the dashboard
            io.sockets.connected[dashboardSocketID].emit('update', dashboardResponse)
        }
    }
    */
    function handleStandardRequest(requestName, data, callback) {
        console.log("Request received. Name: " + requestName + ", \nData:");
        console.log(data);
        request(requestName, data, callback);
    }

    function request(requestName, data, callback) {
        var reqData = data;
        //Add the request name to the JSON request data
        reqData.requestName = requestName;
        //Send the new request object to the game engine
        io.sockets.connected[gameEngineSocketID].emit('request', reqData, function(response) {
            callback(response);
        });
    }
});

app.get('/', function (req, res) {
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end(index);
});

app.get('/dashboard', function (req, res) {
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end(dashboard);
});

server.listen(port);

console.log('Server is listening on port ' + port);