package nadineastakhova.systemclient.Profile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import nadineastakhova.systemclient.ConnectToServer;
import nadineastakhova.systemclient.Groups.GroupsListActivity;
import nadineastakhova.systemclient.MainActivity;
import nadineastakhova.systemclient.R;
import nadineastakhova.systemclient.Subject.SubjectListActivity;
import nadineastakhova.systemclient.Works.WorksListActivity;

public class ProfileActivity extends AppCompatActivity {

    String idUsers = "";
    ProfileInfo user;
    ConnectToServer connect;
    boolean flag;
    boolean isSuc = false;
    private io.socket.client.Socket socket;
    String id;
    String message;
    String idDelMess;
    private static final int NOTIFY_ID = 101;
    private static String username;
    private static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");

        //String login = "Not found";
       final String login = "users/" + username + "/" + password;
       // final String login = "users/albus/12345678";

        ActionBar actionBar = getSupportActionBar();
        flag = false;

        Runnable runnable = new Runnable() {
            public void run() {
                connect = new ConnectToServer(handler, MainActivity.GET, login);
                flag = true;
                isSuc = connect.getIsSuc();

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        while (!flag);

        user = new ProfileInfo(connect.getResultJSON());
        connectSocket();
        configSocketEvents();
        socket.emit("getNewUser", user.getIdProf());
    }

    public void setEnterId(String id){
        idUsers += id;
    }

    public void onClick(View view) {
        Intent intent = new Intent(ProfileActivity.this, GroupsListActivity.class);
        intent.putExtra("idProf", user.getIdProf());
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String s = bundle.getString("Key");
            TextView nameUser = (TextView)findViewById(R.id.textUser);
            TextView emailUser = (TextView)findViewById(R.id.userEmail);
            TextView skypeUser = (TextView)findViewById(R.id.userSkype);

            if(s.length() <= 2) {
                nameUser.setText("User not found");
            }
            else {
                nameUser.setText("Hello, " + user.getName());
                emailUser.setText("Your Email: " + user.getEmail());
                //skypeUser.setText("Skype: " + user.getSkype());
            }
        }
    };

    public void onClickSubjectButton(View view) {
        Intent intent = new Intent(ProfileActivity.this, SubjectListActivity.class);
        intent.putExtra("idProf", user.getIdProf());
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickExitButton(View view) {
        socket.emit("disconnectUser", user.getIdProf());
        user = null;
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.putExtra("ifExit", true);
        startActivity(intent);
    }

    public void connectSocket(){
        try {
            socket = IO.socket("http://192.168.43.64:8000");
            socket.connect();

        } catch(Exception e){
            System.out.println(e);
        }
    }

    public void configSocketEvents(){
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("SocketIO Connected");

                socket.emit("idUser", user.getIdProf());

            }
        }).on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    id = data.getString("id");
                    //socket.emit("idSocket", id);
                    Log.d("SocketIO", "My ID: " + id);
                } catch (JSONException e) {
                    Log.d("SocketIO", "Error getting ID");
                }
            }
        })
        .on("newMess", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                   // id = data.getString("id");
                    message = data.getString("message");
                    idDelMess = data.getString("idDel");
                    Log.d("SocketIO", "New mess"  +" "+ message +" "+idDelMess);

                    Context context = getApplicationContext();
                    Resources res = context.getResources();
                    int notifyID = 1;

                   // Intent notificationIntent = new Intent(context, ProfileActivity.class);
                    Intent notificationIntent = new Intent();
                    PendingIntent contentIntent = PendingIntent.getActivity(context,
                            0, notificationIntent,
                            PendingIntent.FLAG_CANCEL_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

                    builder.setContentIntent(contentIntent)
                            .setSmallIcon(R.drawable.notific)
                            .setLargeIcon(BitmapFactory.decodeResource(res,R.drawable.notif))
                            .setColor(Color.WHITE)
                            .setContentTitle("New work")
                            .setContentText(message)// Текст уведомления
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
                    Notification notification = builder.build();

                    Uri ringURI =
                            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    notification.sound = ringURI;

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(notifyID, notification);


                } catch (JSONException e) {
                    Log.d("SocketIO", "Error getting New mess");
                }
            }
        });
    }

}
