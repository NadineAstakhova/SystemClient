package nadineastakhova.systemclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nadineastakhova.systemclient.Profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    public static final String GET = "GET";
    public static final String POST = "POST";
    boolean isSuc = false;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_LOGIN = "Login";
    public static final String APP_PREFERENCES_PASS = "Pass";
    SharedPreferences mSettings;
    private boolean isExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Bundle getInt = getIntent().getExtras();
        if (getInt != null) {
            isExit = getIntent().getExtras().getBoolean("ifExit");
        }
        else
            isExit = false;
        System.out.println(isExit);


        Runnable runnable = new Runnable() {
            //request to server
                public void run() {
                    ConnectToServer connect = new ConnectToServer(handler, GET, "api");
                    isSuc = connect.getIsSuc();
                    //smt wrong
                    //for second enter
                   /*if (isSuc && !isExit && mSettings.contains(APP_PREFERENCES_LOGIN) && mSettings.contains(APP_PREFERENCES_PASS)) {
                        System.out.println(mSettings.getString(APP_PREFERENCES_LOGIN, ""));
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.putExtra("username", mSettings.getString(APP_PREFERENCES_LOGIN, ""));
                        intent.putExtra("password", mSettings.getString(APP_PREFERENCES_PASS, ""));
                        startActivity(intent);
                    }*/
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();
    }


    public void onClick(View view) {
        //if server works
        System.out.println(isSuc);
        if (isSuc) {
            EditText loginText = (EditText) findViewById(R.id.enterText);
            EditText passText = (EditText) findViewById(R.id.passText);
            //if fields are empty
            if (loginText.getText().toString().length() < 1 || passText.getText().toString().length() < 1) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Write your login and password, please", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_LOGIN, loginText.getText().toString());
                editor.putString(APP_PREFERENCES_PASS, passText.getText().toString());
                editor.apply();
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("username", loginText.getText().toString());
                intent.putExtra("password", passText.getText().toString());
                startActivity(intent);
            }
        }
        //if server doesn't work
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Sorry, server doesn't work. Try later, please.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String s = bundle.getString("Key");
            TextView textStatus = (TextView)findViewById(R.id.textStatus);
            System.out.println(bundle.getString("Key"));
            textStatus.setText(s);
            textStatus.setTextColor(Color.rgb(0, 139, 69));
        }
    };
}


