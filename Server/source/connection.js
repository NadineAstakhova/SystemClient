var mysql = require('mysql');
//connect ti database. for now to local database
function Connection() {
  this.pool = null;
 
  this.init = function() {
    this.pool = mysql.createPool({
      connectionLimit: 100,
      host: 'localhost',
      port : '3305',
      user: 'root',
      password: 'root',
      database: 'system_of_individual_works'
    });
  };
 
 
  this.acquire = function(callback) {
    this.pool.getConnection(function(err, connection) { 
      if(err){
        console.log('Database connection error');
        console.error(err);
    }else{
        console.log('Database connection successful');
    }
      callback(err, connection);        
    });
  };
}
 
module.exports = new Connection();
