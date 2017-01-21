package nadineastakhova.systemclient.Works;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nadineastakhova.systemclient.ConnectToServer;
import nadineastakhova.systemclient.MainActivity;

/**
 * Created by Nadine on 15.11.2016.
 * Work Class
 */

public class Work {

    private String id;
    private String fileName;
    private String status;
    private String mark;
    private Date comp_date;
    private String fk_Student;

    public static final String NEW = "new";
    public static final String ACCEPT = "accept";
    public static final String NO_ACCEPT = "no_accept";

    boolean flag;

    public Work(){
        super();
    }

    public Work(String id, String fileName, String status, String mark, String comp_date, String fk_Student){
        this.id = id;
        this.fileName = fileName;
        this.status = status;
        this.mark = mark;
        //Completion date of work
        DateFormat format = new SimpleDateFormat( "yyyy-MM-dd");
        try {
            this.comp_date = format.parse(comp_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.fk_Student = fk_Student;
    }

    public String getId(){
        return this.id;
    }

    public String getMark(){
        return this.mark;
    }

    public String getStatus(){
        return this.status;
    }

    //Get completion date
    public String getComp_date(){
        int d = this.comp_date.getDate() + 1;
        Date t = (Date)(this.comp_date.clone());
        t.setDate(d);
        return new SimpleDateFormat("dd-MM-yyyy").format(t);
    }

    @Override
    public String toString() {
        String resultStr = fileName.substring(fileName.indexOf('_') + 1, fileName.indexOf('.'));
        String str = resultStr.replaceAll("_", " ");
        return  str;
    }

    //edit info in database with server help
    public boolean editInfo(String id,  String status, String mark)
    {
        final String editWork = "work/edit/" +  id + "/" + status + "/" + mark;
        flag = false;
        Runnable runnable = new Runnable() {
            public void run() {
                //request to server
                ConnectToServer connect = new ConnectToServer(MainActivity.POST, editWork);
                flag = true;
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        while (!flag);
        return true;
    }
}
