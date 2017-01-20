package nadineastakhova.systemclient.Subject;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nadineastakhova.systemclient.ConnectToServer;
import nadineastakhova.systemclient.MainActivity;

/**
 * Created by Nadine on 30.10.2016.
 */

public class Subject {

    private String name;
    private String id;
    private String jsonRes="";


    public Subject(){
        super();
    }

    public Subject(String id, String name){
        super();
        this.id = id;
        this.name = name;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setJsonRes(String js){
        this.jsonRes = js;
    }
    public String getJsonRes(){
        return this.jsonRes;
    }



    @Override
    public String toString() {
        return  this.name;
    }



    boolean flag;

    public boolean add(String name, String idProf){
        final String newSubject = "subject/add/" +  name + "/" + idProf;
        flag = false;
        Runnable runnable = new Runnable() {
            public void run() {
                ConnectToServer connect = new ConnectToServer(MainActivity.POST, newSubject);
                Log.d("FOURTH", "OK");
                flag = true;

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        while (!flag);
        return true;
    }

    public boolean remove(String id){
        final String deleteSubject = "subject/delete/" +  id;
        flag = false;
        Runnable runnable = new Runnable() {
            public void run() {
                ConnectToServer connect = new ConnectToServer(MainActivity.GET, deleteSubject);
                Log.d("SIX", "OK");
                flag = true;

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        while (!flag);
        return true;

    }

    public boolean editName(String id, String newName)
    {
        final String editSubject = "subject/edit/" +  id + "/" + newName;
        flag = false;
        Runnable runnable = new Runnable() {
            public void run() {
                ConnectToServer connect = new ConnectToServer(MainActivity.POST, editSubject);
                Log.d("SIX", "OK");
                flag = true;

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        while (!flag);
        return true;
    }
}
