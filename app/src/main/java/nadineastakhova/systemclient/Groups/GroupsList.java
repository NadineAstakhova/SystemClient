package nadineastakhova.systemclient.Groups;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadine on 20.11.2016.
 */

public class GroupsList {
    private List<Group> list = new ArrayList<Group>();

    public GroupsList(String resJSON, TextView textView){
        if (resJSON != null) {
            try {
                textView.setVisibility(View.GONE);
                JSONArray ja = new JSONArray(resJSON);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    list.add(new Group(jo.getString("idgroup"),jo.getString("name")));
                }
            }
            catch (final JSONException e) {
                Log.e("TAG", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("TAG", "Couldn't get json from server.");
        }
    }

    public List<Group> getList(){
        return this.list;
    }
}
