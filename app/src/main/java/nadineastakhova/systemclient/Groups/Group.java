package nadineastakhova.systemclient.Groups;

import android.util.Log;

import nadineastakhova.systemclient.ConnectToServer;
import nadineastakhova.systemclient.MainActivity;

/**
 * Created by Nadine on 20.11.2016.
 */

public class Group {

    private String idGroup;
    private String name;
    boolean flag;

    public Group(){
        super();
    }

    public Group(String id, String name){
        this.idGroup = id;
        this.name = name;
    }

    public String getIdGroup(){
        return this.idGroup;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public boolean addGroup(String name){
        final String newGroup = "group/add/" +  name ;
        flag = false;
        Runnable runnable = new Runnable() {
            public void run() {
                ConnectToServer connect = new ConnectToServer(MainActivity.POST, newGroup);
                Log.d("888", "OK");
                flag = true;

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        while (!flag);
        return true;
    }

    public boolean deleteGroup(String id){
        final String deleteGroup = "group/delete/" +  id;
        flag = false;
        Runnable runnable = new Runnable() {
            public void run() {
                ConnectToServer connect = new ConnectToServer(MainActivity.GET, deleteGroup);
                Log.d("99", "OK");
                flag = true;

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        while (!flag);
        return true;
    }

    public boolean editGroup(String id, String newName){
        final String editGroup = "group/edit/" +  id + "/" + newName;
        flag = false;
        Runnable runnable = new Runnable() {
            public void run() {
                ConnectToServer connect = new ConnectToServer(MainActivity.POST, editGroup);
                Log.d("999", "OK");
                flag = true;

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        while (!flag);
        return true;
    }

}
