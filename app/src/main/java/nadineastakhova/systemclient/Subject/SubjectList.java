package nadineastakhova.systemclient.Subject;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadine on 01.11.2016.
 */

public class SubjectList {

    private  List<Subject> list = new ArrayList<Subject>();
    private String strFromJSON;

    public SubjectList(String resJSON, TextView textView){
        this.strFromJSON = resJSON;
        if (resJSON != null) {
            try {
                textView.setVisibility(View.GONE);
                JSONArray ja = new JSONArray(resJSON);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    list.add(new Subject(jo.getString("idSubject"),jo.getString("Name")));
                }
            }
            catch (final JSONException e) {
                Log.e("TAG", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("TAG", "Couldn't get json from server.");
        }
    }

    public List<Subject> getList(){
        return this.list;
    }


}
