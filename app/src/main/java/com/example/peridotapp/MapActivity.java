package com.example.peridotapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;

public class MapActivity extends AppCompatActivity {
    EditText editText_message;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Button openL_btn = findViewById(R.id.btn_open_location);
        Button sendMessage_btn = findViewById(R.id.btn_send_message);
        editText_message = findViewById(R.id.eT_Message);
        firebaseFirestore = FirebaseFirestore.getInstance();

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
                HelpRequestNotification helpRequestNotification = new HelpRequestNotification();
                helpRequestNotification.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                helpRequestNotification.setSenderUID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                helpRequestNotification.setMessage(editText_message.getText().toString());
                helpRequestNotification.setDate(Calendar.getInstance().getTime());
                helpRequestNotification.setStatus("Unfulfilled");
                helpRequestNotification.setLocation("-26.240420, 28.059280");

                firebaseFirestore.collection("notifications")
                        .add(helpRequestNotification)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MapActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MapActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        });

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}