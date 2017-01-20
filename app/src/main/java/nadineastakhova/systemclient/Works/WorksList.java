package nadineastakhova.systemclient.Works;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadine on 15.11.2016.
 * Class creates list of Works from JSON-string
 */

public class WorksList {

    private List<Work> list = new ArrayList<Work>();
    private String strFromJSON;

    public WorksList(String resJSON, TextView textView){
        this.strFromJSON = resJSON;
        if (resJSON != null) {
            try {
                textView.setVisibility(View.GONE);
                JSONArray ja = new JSONArray(resJSON);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    list.add(new Work(jo.getString("idInd_work"),jo.getString("File"),jo.getString("Status"), jo.getString("Mark"),jo.getString("Completion_date"),jo.getString("FK_Student")));
                }
            }
            catch (final JSONException e) {
                Log.e("TAG", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("TAG", "Couldn't get json from server.");
        }
    }

    public List<Work> getList(){
        return this.list;
    }

}
