var todo = require('./models/indworks');
var url = require('url');
var path            = require('path'); // модуль для парсинга пути
var qs = require("querystring");


module.exports = {
  configure: function(app) {
    app.get('/users', function(req, res) {
      todo.get(res, req.url);
    });  
    
    app.get('/subject', function(req, res) {      
      todo.get(res, req.url);
    });
       
    app.get('/student', function(req, res) {      
      todo.get(res, req.url);
    });
    
    app.get('/users/:id', function(req, res) {      
      todo.getUserByID(req.params.id, res);
    });
    
    //Проверяет совпадение введённых логина и пароля с имеющимися в БД
     app.get('/users/:login/:pass', function(req, res) {   
      todo.getProfessorByID(req.params.login, req.params.pass, res);
    });
    
    //Выполняет запрос к БД и возвращает JSON-строку предметов для пользователя
    app.get('/subject/:idProf', function(req, res) {      
      todo.getSubjectByProfessor(req.params.idProf, res);
    });
    
    //Вставляет запись с новым предметом в БД для пользователя
    app.post('/subject/add/:newsubject/:idprof', function(req, res) {
      todo.addSub(req.params.newsubject,req.params.idprof, res);
    });
    
    //Выполняет запрос к БД для удаления предмета по id
    app.get('/subject/delete/:idsubject', function(req, res) {
      todo.deleteSub(req.params.idsubject, res);
    });
    
    //Выполняет запрос на изменение имени предмета по id
    app.post('/subject/edit/:idsubject/:newname', function(req, res) {
      todo.editSub(req.params.idsubject, req.params.newname, res);
    });
        
    app.get('/student/:id', function(req, res) {      
      todo.getStudentByID(req.params.id, res);
    });
        
    app.get('/group/:id', function(req, res) {      
      todo.getGroupByID(req.params.id, res);
    });
    
    //Выполняет запрос к БД и возвращает JSON-строку заданий для предмета по заданному id
    app.get('/tasks/:idSubject', function(req, res) {     
      todo.getTaskForSubject(req.params.idSubject, res);
    });
    
    //Вставляет запись с новым заданием в БД для предмета
    app.post('/task/add/:newtask/:date/:idsub', function(req, res) {
      todo.addTask(req.params.newtask, req.params.date, req.params.idsub, res);
    });
    
    //Выполняет запрос к БД для удаления задания по id
    app.get('/task/delete/:idtask', function(req, res) {
      todo.deleteTask(req.params.idtask, res);
    });
    
    //Выполняет запрос на изменение даты сдачи задания по его id
    app.post('/task/edit/:idtask/:newdate', function(req, res) {
      todo.editTask(req.params.idtask, req.params.newdate, res);
    });
    
    //Выполняет запрос к БД и возвращает JSON-строку работ студентов по id задания
    app.get('/works/:idTask', function(req, res) {     
      todo.getWorksForTask(req.params.idTask, res);
    });
    
    //Выполняет запрос на изменение статуса и оценки работы по её id
    app.post('/work/edit/:idwork/:newstatus/:newmark', function(req, res) {
      todo.editWork(req.params.idwork, req.params.newstatus, req.params.newmark, res);
      if(req.params.newstatus !== 'new')
        todo.deleteOldMes(req.params.idwork);
    });
    
    //Возвращает JSON-строку групп из БД
    app.get('/groups', function(req, res) {      
      todo.getGroups(res, req.url);
    });
    
    //Вставляет запись с новой группой в БД 
    app.post('/group/add/:newgroup', function(req, res) {
      todo.addGroup(req.params.newgroup, res);
    });
    
    //Выполняет запрос к БД для удаления группы по id
    app.get('/group/delete/:idgroup', function(req, res) {
      todo.deleteGroup(req.params.idgroup, res);
    });
    
    //Выполняет запрос на изменение имени группы по её id
    app.post('/group/edit/:idgroup/:newname', function(req, res) {
      todo.editGroup(req.params.idgroup, req.params.newname, res);
    });
    
    app.post('/setgroup/:idsub/:idgroup', function(req, res) {
      todo.setGroupForSub(req.params.idsub, req.params.idgroup, res);
    });
    
    
    
   
         
  }
};