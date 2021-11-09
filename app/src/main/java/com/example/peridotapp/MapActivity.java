package com.example.peridotapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class MapActivity extends AppCompatActivity {

    EditText editText_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Button openL_btn = findViewById(R.id.btn_open_location);
        Button sendMessage_btn = findViewById(R.id.btn_send_message);
        editText_message = findViewById(R.id.eT_Message);

        openL_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
                startActivity(intent);
            }
        });

        sendMessage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String message = editText_message.getText().toString().trim();
                if(message == " "){
                    Toast.makeText(getApplicationContext(), "Please enter a message before sending.", Toast.LENGTH_LONG).show();
                }*/
                addNotification();
            }
        });
    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_foreground)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}