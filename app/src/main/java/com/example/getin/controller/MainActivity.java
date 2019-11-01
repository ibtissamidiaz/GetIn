package com.example.getin.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getin.R;
import com.example.getin.model.Utilisateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText mdpField;
    private Button btnConnect;
    private TextView btnInscrire;
    private TextView btnmdp;
    private ProgressBar progressBar;
    FirebaseAuth mFirebaseAuth;
    Utilisateur utilisateur;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.emailField);
        mdpField= findViewById(R.id.mdpField);
        btnmdp = findViewById(R.id.btnReset);
        progressBar = findViewById(R.id.progressBar);
        btnConnect = findViewById(R.id.btnConnect);
        btnInscrire = findViewById(R.id.btnInscrire);
       /* mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(MainActivity.this,"You are logged in ",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,ChooseAct.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };*/
        btnConnect.setOnClickListener(new View.OnClickListener(){
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

                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Login Error , Please Login Again !",Toast.LENGTH_SHORT).show();

                            }

                            else {
                                Intent intohome = new Intent(MainActivity.this,ChooseAct.class);
                                startActivity(intohome);
                            }
                            progressBar.setVisibility(View.GONE);

                        }
                    });
                }

            }
        });
        btnInscrire.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InscriptionActivity.class);
                startActivity(i);
            }
        });
        btnmdp.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                btnmdp.setTextColor(R.color.colorPrimaryDark);
                Intent i = new Intent(MainActivity.this,ResetPasswordActivity.class);
                startActivity(i);
            }
        });

    }


   /* @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }*/


}
