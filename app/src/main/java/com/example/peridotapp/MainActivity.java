package com.example.peridotapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity{
    FirebaseUser user;
    TextView txt_name;
    Button help_btn, logout_btn, respond_btn;
    FirebaseMessaging firebaseMessaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        help_btn = findViewById(R.id.btn_help);
        logout_btn = findViewById(R.id.btn_logout);
        respond_btn = findViewById(R.id.btn_respondToRequest);
        txt_name = findViewById(R.id.txt_userName);
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("notification_jhb");

        txt_name.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finishAffinity();
            }
        });

        respond_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
    }

}