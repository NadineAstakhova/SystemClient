var express = require('express');
var bodyparser = require('body-parser');
var connection = require('./connection');
var routes = require('./routes');
var todo = require('./models/indworks');
var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var todo = require('./models/indworks');

var users = [];

app.use(bodyparser.urlencoded({extended: true}));
app.use(bodyparser.json());

connection.init();
routes.configure(app);
//standard function for server  
server.listen(8000, function() {
  console.log('Server listening on port ' + server.address().port);
});

app.get('/api',function(req,res){	
	res.send('Server is found');
});

app.post('/',function(req,res){	
	console.log(req.body.foo);
    res.send('ok');
});

//next  functions describe how server send info for push message
io.on('connection', function(socket){
	console.log("User Connected!");
	var idMess;
	var idUs;
        //server monitors the connection of a new user
	socket.emit('socketID', { id: socket.id });
    socket.on('getNewUser', function (message){
	//push new user to users with unique id
        users.push(new user(socket.id, message));
        idUs = message;
	//check function is repeated every 10 seconds
        maybe(idUs, wait10sec);
        console.log('New user with id: ' + message);
    });
    //this function monitors disable user and delete him from users
    socket.on('disconnectUser', function(message){
	    for(var i = 0; i < users.length; i++){
		    if(users[i].idUser === message){
			    console.log("User Disconnected " + users[i].idUser);
			    users.splice(i, 1);
			    idUs = null;
		    }
	    }
	}); 
    socket.on('idUser', function (message) {}); 
   //callback function send request to database aboit new works for users 
   function maybe(idUs,  callback){
        console.log('A client is speaking to me! They’re saying: ' + idUs);
        for(var i = 0; i < users.length; i++){
            if(users[i].idUser !== undefined && idUs !== null)
                if(users[i].idUser === idUs){
                    console.log(users[i].idUser);
                    todo.getNewMess(idUs).then(t).catch((err) => setImmediate(() => { throw err; })); // Throw async to escape the promise chain
                }
        }
        callback();
   }
   //this function send new message to user if it exists
   function t(rows) {
       if(rows.length  !== 0) {
           var res = ""+rows[rows.length-1].new_task+" for "+rows[rows.length-1].subject+" by "+rows[rows.length-1].author;
           var idDelMess = rows[rows.length-1].id_Messages;
           var id;
           console.log('message here ' + res);
          
           socket.emit('newMess', {
               message:  res,
               idDel: idDelMess
           });
	   //delete new message from database when it was sent
           todo.deleteOldMes(rows[rows.length-1].id_work);    
        }
        else
          console.log("no message");  
   }
 //repeat function with timeout
  function wait10sec(){
        setTimeout(function(){
            maybe(idUs, wait10sec);
        }, 10000);
  }
});

function user(idSocket, idUser){
	this.idSocket = idSocket;
	this.idUser = idUser;
}









