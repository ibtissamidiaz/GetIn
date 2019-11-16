package com.example.getin.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText mdpField;
    private Button btnConnect;
    private TextView btnInscrire;
    private TextView btnmdp;
    private ProgressBar progressBar;
    private FirebaseAuth mFirebaseAuth;
    private Utilisateur utilisateur;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private long backPresedTime;



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
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(MainActivity.this,"Vous êtes connectés ",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,ChooseAct.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this,"Veuillez vous connectez",Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnConnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String email=emailField.getText().toString().trim();
                String pwd = mdpField.getText().toString();
                if(email.isEmpty()){
                    emailField.setError("Entrer votre adresse mail");
                    emailField.requestFocus();
                }else if (pwd.isEmpty()){
                    mdpField.setError("Entrer votre mot de passe");
                    mdpField.requestFocus();

                }else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"Les champs sont vides  !",Toast.LENGTH_SHORT).show();

                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if(!task.isSuccessful()) {
                                try {
                                    throw task.getException();
                                } catch(FirebaseAuthInvalidCredentialsException e) {
                                    mdpField.setError("Mot de passe invalid !");
                                    mdpField.requestFocus();
                                }catch(FirebaseAuthException e) {
                                    emailField.setError("Email ou mot de passe invalid !");
                                    emailField.requestFocus();
                                }  catch (Exception e){
                                    Toast.makeText(MainActivity.this,"Connexion échouée , Essayer encore !",Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
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
    @Override
    public void onBackPressed() {
        if(backPresedTime +2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(getBaseContext(),"Cliquez une autre fois pour quitter",Toast.LENGTH_SHORT).show();
        }
        backPresedTime = System.currentTimeMillis();

    }



}
