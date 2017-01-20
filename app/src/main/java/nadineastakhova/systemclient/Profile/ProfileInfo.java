package nadineastakhova.systemclient.Profile;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Nadine on 17.11.2016.
 */

public class ProfileInfo {

    private String idProf;
    private String idUser;
    private String name;
    private String email;
    private String skype;
    private io.socket.client.Socket socket;
    String id;

    public ProfileInfo(String resJSON){


        if (resJSON != null) {
            try {
                JSONArray info = new JSONArray(resJSON);
                for (int i = 0; i < info.length(); i++) {
                    JSONObject c = info.getJSONObject(i);
                    idProf   = c.getString("id");
                    idUser = c.getString("idUsers");
                    name = c.getString("name") + ' ' + c.getString("surname");
                    email = c.getString("email");
                    skype = c.getString("skype");
                }
            } catch (final JSONException e) {
                Log.e("TAG", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("TAG", "Couldn't get json from server.");
        }
    }

    public String getIdProf(){
        return this.idProf;
    }
    public String getIdUser(){
        return this.idUser;
    }
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getSkype(){
        if(this.skype.equals("null"))
        {
            return "don't set";
        }
        else
            return this.skype;
    }

}
