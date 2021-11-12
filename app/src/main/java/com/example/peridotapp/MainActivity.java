package com.example.peridotapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;


public class MainActivity extends AppCompatActivity{

    FirebaseUser user;
    TextView txt_name;
    DatabaseReference database;
    FirebaseMessaging firebaseMessaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button help_btn = findViewById(R.id.btn_help);
        Button logout_btn = findViewById(R.id.btn_logout);
        txt_name = findViewById(R.id.txt_userName);
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("notification_jhb");


       database = FirebaseDatabase.getInstance().getReference("User");
        database.addValueEventListener(new ValueEventListener() {  FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    //String name = (snap.child("name").getValue().toString());
                    txt_name.setText(snap.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*if(user != null){
            for(UserInfo profile : user.getProviderData()){
                String uid = profile.getUid();
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Toast.makeText(getApplicationContext(), "Your email : " + email + "\nYour Uid = " + user.getUid(), Toast.LENGTH_LONG).show();
                txt_name.setText(new StringBuilder().append(uid).append("\n").append(email).toString());

            }
        }*/


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

    }

}