package nadineastakhova.systemclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import nadineastakhova.systemclient.Subject.Subject;

/**
 * Created by Nadine on 13.10.2016.
 */

public class ConnectToServer {

    final String ipserver = "192.168.43.64";

    String resultJSON = "";
    boolean isSuc = false;

    public ConnectToServer(Handler handler, String request, String param){

                URL url;
                HttpURLConnection conn;
                BufferedReader rd;
                String line;
                String result = "";
                this.isSuc = false;

                try {
                    url = new URL("http://"+ipserver+":8000/"+param);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod(request);
                    try {
                        int status = conn.getResponseCode();

                        InputStreamReader input;
                        if(status >= HttpStatus.SC_BAD_REQUEST) {
                            input = new InputStreamReader(conn.getErrorStream());
                            Log.d("666","что то не так");
                        }
                        else
                            input = new InputStreamReader(conn.getInputStream());

                        rd = new BufferedReader(input);
                        while ((line = rd.readLine()) != null) {
                            Log.d("666","read");
                            result += line;
                        }
                        this.isSuc = true;
                        rd.close();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } finally {
                        conn.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();

                bundle.putString("Key", result);
                msg.setData(bundle);
                handler.sendMessage(msg);
                resultJSON += result;
            }

    public ConnectToServer(String request, String param){
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL("http://"+ipserver+":8000/"+param);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(request);
            try {
                int status = conn.getResponseCode();

                InputStreamReader input;
                if(status >= HttpStatus.SC_BAD_REQUEST) {
                    input = new InputStreamReader(conn.getErrorStream());
                    Log.d("666","что то не так");
                }
                else
                    input = new InputStreamReader(conn.getInputStream());

                rd = new BufferedReader(input);
                while ((line = rd.readLine()) != null) {
                    Log.d("666","read");
                    result += line;
                }
                rd.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultJSON += result;
    }

    public boolean getIsSuc(){return  this.isSuc;}

    public String getResultJSON(){
        return resultJSON;
    }

}
