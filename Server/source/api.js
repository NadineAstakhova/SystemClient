var mysql  = require('mysql');
var dbconn = mysql.createConnection({
  host     : 'localhost',
  port : '3305',
  user     : 'root',
  password : 'root',
  database : 'system_of_individual_works'
});

dbconn.connect(function(err){
  if(err){
    console.log('Database connection error');
    console.error(err);
  }else{
    console.log('Database connection successful');
  }
});

dbconn.query('SELECT * FROM users',function(err, records){
  if(err) throw err;

  console.log('Data received from Db:n');
  console.log(records);
});

dbconn.end(function(err) {
  // Function to close database connection
});