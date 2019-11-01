package com.example.getin.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText emailField;
    private EditText mdpField;
    private Button btnConnect;
    private TextView btnInscrire;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"Firebase connected !",Toast.LENGTH_SHORT).show();
    mFirebaseAuth = FirebaseAuth.getInstance();
    emailField = findViewById(R.id.emailField);
    mdpField= findViewById(R.id.mdpField);
    btnConnect = findViewById(R.id.btnConnect);
    btnInscrire = findViewById(R.id.btnInscrire);
    btnConnect.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email=emailField.getText().toString();
            String pwd = mdpField.getText().toString();
            if(email.isEmpty()){
              emailField.setError("Please enter email id");
              emailField.requestFocus();
            }else if (pwd.isEmpty()){
                mdpField.setError("Please enter your password");
                mdpField.requestFocus();

            }else if(email.isEmpty() && pwd.isEmpty()){
                Toast.makeText(MainActivity.this,"Fields are empty !",Toast.LENGTH_SHORT).show();

            }else {
                mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"SignUp Unsuccessful, Please try again",Toast.LENGTH_SHORT).show();
                        }else {
                            startActivity(new Intent(MainActivity.this,ChooseAct.class));
                        }
                    }
                });
            }
        }
    });
        btnInscrire.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,InscriptionActivity.class);
                startActivity(i);
            }
        });
    }

    public void chooseAct(View v){
        startActivity(new Intent(this,ChooseAct.class));
    }
}
