package com.example.peridotapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPhoneNumber, mPassword;
    Button mRegisterBtn;
    TextView mLoginText;
    FirebaseAuth fAuth;
    ProgressBar proBar;
    DatabaseReference dbRef;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName = findViewById(R.id.txt_name);
        mEmail = findViewById(R.id.email_ed_txt);
        mPhoneNumber = findViewById(R.id.phoneNumber_ed_txt);
        mPassword = findViewById(R.id.password_ed_txt);
        mRegisterBtn = findViewById(R.id.btn_register);
        mLoginText = findViewById(R.id.login_txt);
        proBar = findViewById(R.id.register_pBar);
        fAuth = FirebaseAuth.getInstance();
        user = new Users();
        dbRef = FirebaseDatabase.getInstance().getReference().child("User");

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                Long phoneNum = Long.parseLong(mPhoneNumber.getText().toString().trim());

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }

                user.setName(mFullName.getText().toString().trim());
                user.setEmail(email);
                user.setPhoneNumber(phoneNum);
                dbRef.push().setValue(user);

                Toast.makeText(Register.this, "User Data stored successfully", Toast.LENGTH_SHORT).show();

                proBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            proBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}