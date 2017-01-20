package nadineastakhova.systemclient.Task;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nadineastakhova.systemclient.ConnectToServer;
import nadineastakhova.systemclient.MainActivity;

/**
 * Created by Nadine on 07.11.2016.
 */

public class Task {

    private String id;
    private String name;
    private Date date;
    private String idSubject;

    public Task(){super();}

    public Task(String id, String name, String idSubject){
        super();
        this.id = id;
        this.name = name;
        this.idSubject = idSubject;
    }

    public Task(String id, String name, String idSubject, String date){
        super();
        this.id = id;
        this.name = name;
        this.idSubject = idSubject;
        DateFormat format = new SimpleDateFormat( "yyyy-MM-dd");
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getTaskId(){
        return this.id;
    }

    public String getTaskName(){
        return this.name;
    }

    public void setTaskName(String name){
        this.name = name;
    }

    public String getDate(){
        int d = this.date.getDate() + 1;
        Date t = (Date)(this.date.clone());
        t.setDate(d);

        return new SimpleDateFormat("dd-MM-yyyy").format(t);
    }

    public void setDate(String date){
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return  this.name;
    }

    boolean flag;

    public boolean add(String name, String date, String idSubject){
        final String newTask = "task/add/" +  name + "/" + date + "/" + idSubject;
        flag = false;
        Runnable runnable = new Runnable() {
            public void run() {
                ConnectToServer connect = new ConnectToServer(MainActivity.POST, newTask);
                flag = true;

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        while (!flag);
        return true;
    }

    public boolean remove(String idTask){
        final String deleteTask = "task/delete/" +  idTask;
        flag = false;
        Runnable runnable = new Runnable() {
            public void run() {
                ConnectToServer connect = new ConnectToServer(MainActivity.GET, deleteTask);
                flag = true;

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        while (!flag);
        return true;
    }

    public boolean editTask(String idTask, String newDate){
        final String editTask = "task/edit/" +  idTask + "/" + newDate;
        flag = false;
        Runnable runnable = new Runnable() {
            public void run() {
                ConnectToServer connect = new ConnectToServer(MainActivity.POST, editTask);
                flag = true;
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        while (!flag);
        return true;

    }
}
