var connection = require('../connection');
var url = require('url');
var LocalStrategy   = require('passport-local').Strategy;
var passport = require('passport');
 
function Indworks() {
   
    this.get = function(res, ur) {
    connection.acquire(function(err, con) {
        var reqStr;
        if (ur !== '/group'){ 
            reqStr = ur.substr(1); 
        }
        else { 
            reqStr = 'system_of_individual_works.' + ur.substr(1); 
        }        
        console.log(reqStr);
        con.query('select * from ' + reqStr + ' ', function(err, result) {
        if (err) {
            console.error(err); 
            callback(err, connection); 
        }
        con.release();
        res.send(result);
      });
    });
  };
  
  this.getUserByID = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM users WHERE idUsers = ?',[id], function(err, result) {
        con.release();
        res.send(result);
      });
    });
  };
  
  this.getUserByLogin = function(login, pass, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM users WHERE username = ? AND password = ?',[login, pass], function(err, result) {
         if (result.length == 0){
                console.log("Uncorrect login or password");
         }   
        con.release();     
        res.send(result);
      });
    });
  };
    
  this.getStudentByID = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM student WHERE id = ?',[id], function(err, result) {
        con.release();
        res.send(result);
      });
    });
  };
  
  this.getProfessorByUserId = function(idUser, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM professor WHERE type_user = ?',[idUser], function(err, result) {
          console.log(err);
        con.release();
        res.send(result);
      });
    });
  };
  
 /* this.getProfessorByID = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM professor WHERE id = ?',[id], function(err, result) {
        con.release();
        res.send(result);
      });
    });
  };*/
  
  this.getProfessorByID = function(login, pass, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM professor INNER JOIN users ON professor.type_user =  users.idUsers WHERE users.username = ? AND users.password = ?',[login, pass], function(err, result) {
        con.release();
        
        res.send(result);
      });
    });
  };
  
  this.getSubjectByID = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM subject WHERE idSubject = ?',[id], function(err, result) { 
        con.release();
        res.send(result);
      });
    });
  };
  
  this.getSubjectByProfessor = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM subject WHERE FK_Professor = ?',[id], function(err, result) { 
        con.release();
        res.send(result);
      });
    });
  };
  
  this.getGroupByID = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM system_of_individual_works.group WHERE idgroup = ?',[id], function(err, result) {
        con.release();
        res.send(result);
      });
    });
  };
  
  this.getTaskForSubject = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM list_of_task INNER JOIN subject ON list_of_task.FK_Subject =  subject.idSubject WHERE subject.idSubject =  ?',[id], function(err, result) {
        con.release();
        res.send(result);
      });
    });
  };
  
  this.addSub = function(newSub, id, res) {
    connection.acquire(function(err, con) {
      con.query('insert into subject set Name = ?, FK_Professor = ?',[newSub, id],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'TODO creation failed'});
        } else {
          res.send({status: 0, message: 'TODO created successfully'});
        }
      });
    });
  };
  
  this.deleteSub = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('DELETE FROM subject WHERE idSubject = ?',[id],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'TODO deleted failed'});
        } else {
          res.send({status: 0, message: 'TODO deleted successfully'});
        }
      });
    });
  };
  
  
  this.editSub = function(id, newName, res) {
    connection.acquire(function(err, con) {
      con.query('UPDATE subject SET Name = ? WHERE idSubject = ?',[newName, id],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'TODO changed failed'});
        } else {
          res.send({status: 0, message: 'TODO changed successfully'});
        }
      });
    });
  };
  
  this.addTask = function(newTask, date, id, res) {
    connection.acquire(function(err, con) {
      con.query('insert into list_of_task set Name_of_task = ?, Date = ?, FK_Subject = ?',[newTask, date, id],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'Task creation failed'});
        } else {
          res.send({status: 0, message: 'Task created successfully'});
        }
      });
    });
  };
  
  
  this.deleteTask = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('DELETE FROM list_of_task WHERE idList_of_task = ?',[id],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'Task deleted failed'});
        } else {
          res.send({status: 0, message: 'Task deleted successfully'});
        }
      });
    });
  };
  
   this.editTask = function(id, newDate, res) {
    connection.acquire(function(err, con) {
      con.query('UPDATE list_of_task SET Date = ? WHERE idList_of_task = ?',[newDate, id],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'TODO changed failed'});
        } else {
          res.send({status: 0, message: 'TODO changed successfully'});
        }
      });
    });
  };
  
  this.getWorksForTask = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM individual_works WHERE FK_Task =  ?',[id], function(err, result) {
        con.release();
        res.send(result);
      });
    });
  };
  
   this.editWork = function(id, newStatus, newMark, res) {
    connection.acquire(function(err, con) {
        var status;
        if(newStatus.indexOf('_'))
        {
            status = newStatus.replace('_', ' ');
            console.log(status);
        }
        else 
        status = newStatus;
      con.query('UPDATE individual_works SET Status = ?, Mark = ? WHERE idInd_work = ?',[status, newMark, id],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'TODO changed failed', err});
          console.log(err);
        } else {
          res.send({status: 0, message: 'TODO changed successfully'});
        }
      });
    });
  };
  
  this.getGroups = function(res, ur) {
    connection.acquire(function(err, con) {
      con.query('SELECT * FROM system_of_individual_works.group where system_of_individual_works.`group`.idgroup <> 1', function(err, result) {
          
        con.release();
        res.send(result);
      });
    });
  };
  
  this.addGroup = function(newGroup, res) {
    connection.acquire(function(err, con) {
      con.query('insert into system_of_individual_works.group set name = ?, status_group = ?', [newGroup, 'unlock'],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'group creation failed'});
        } else {
          res.send({status: 0, message: 'group created successfully'});
        }
      });
    });
  };
  
  this.deleteGroup = function(id, res) {
    connection.acquire(function(err, con) {
      con.query('DELETE FROM system_of_individual_works.group WHERE idgroup = ?',[id],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'group deleted failed'});
        } else {
          res.send({status: 0, message: 'group deleted successfully'});
        }
      });
    });
  };
  
  this.editGroup = function(id, newName, res) {
    connection.acquire(function(err, con) {
      con.query('UPDATE system_of_individual_works.group SET name = ? WHERE idgroup = ?',[newName, id],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'group changed failed'});
        } else {
          res.send({status: 0, message: 'group changed successfully'});
        }
      });
    });
  };
  
  this.setGroupForSub = function(idSub, idGroup, res) {
    connection.acquire(function(err, con) {
      con.query('insert into group_subject set FK_Subject = ?, FK_Group = ?', [idSub, idGroup],  function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'setgroup creation failed'});
        } else {
          res.send({status: 0, message: 'setgroup created successfully'});
        }
      });
    });
  };
  
 
 
 
  this.getNewMess = function(idProf, callback) {
      return new Promise(function(resolve, reject) {
       connection.acquire(function(err, con) {
         con.query('SELECT * FROM messages WHERE FK_Prof = ?',  [idProf],  function(err, json) {
            con.release();
            if (err) {
                return reject(err);
            }
           var str = JSON.stringify(json);
           var rows = JSON.parse(str);        
           resolve(rows);
        });
    });
  });
};

this.deleteOldMes = function(id) {
    connection.acquire(function(err, con) {
     con.query('SELECT * FROM messages WHERE id_work = ?',[id],  function(err, result) {
         if (err) {
             console.log(err);
         }
         else 
         {
             if(result.length  !== 0){
                con.query('DELETE FROM messages WHERE id_work = ?',[id],  function(err, result) {
                    con.release();
                    if (err) {
                        console.log(err);
                    }
                    else {
                        console.log("Message deleted successfully");       
                    }         
                });
            }
            else 
               console.log("no message");
        };
    });
});
};

  
 
}
module.exports = new Indworks();