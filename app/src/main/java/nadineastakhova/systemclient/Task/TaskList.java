package nadineastakhova.systemclient.Task;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadine on 07.11.2016.
 */

public class TaskList {

    private List<Task> list = new ArrayList<Task>();
    private String strFromJSON;

    public TaskList(String resJSON, TextView textView){
        this.strFromJSON = resJSON;
        if (resJSON != null) {
            try {
                textView.setVisibility(View.GONE);
                JSONArray ja = new JSONArray(resJSON);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    list.add(new Task(jo.getString("idList_of_task"),jo.getString("Name_of_task"),jo.getString("FK_Subject"), jo.getString("Date")));
                }
            }
            catch (final JSONException e) {
                Log.e("TAG", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("TAG", "Couldn't get json from server.");
        }
    }

    public List<Task> getList(){
        return this.list;
    }
}
