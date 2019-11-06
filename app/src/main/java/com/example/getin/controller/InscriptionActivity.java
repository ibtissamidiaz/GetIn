package com.example.getin.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.getin.R;
import com.example.getin.model.Utilisateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InscriptionActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText mdpField;
    private EditText confField;
    private EditText nomField;
    private EditText prenomfField;
    private EditText ageField;
    private EditText professionField;
    private EditText telephoneField;
    private EditText CINField;
    private Button btnInscription;
    Utilisateur utilisateur = new Utilisateur();
    FirebaseAuth mFirebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.emailField);
        mdpField= findViewById(R.id.mdpField);
        confField = findViewById(R.id.confField);
        nomField = findViewById(R.id.nomField);
        prenomfField = findViewById(R.id.prenomField);
        ageField = findViewById(R.id.ageField);
        professionField = findViewById(R.id.professionField);
        telephoneField = findViewById(R.id.telephoneField);
        CINField = findViewById(R.id.CINField);
        btnInscription = findViewById(R.id.btnInscription);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Utilisateur");

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailField.getText().toString();
                String pwd = mdpField.getText().toString();
                String confpwd = confField.getText().toString();
                String nom = nomField.getText().toString();
                String prenom = prenomfField.getText().toString();
                int age = Integer.parseInt(ageField.getText().toString().trim());
                String profession = professionField.getText().toString();
                String telephone = telephoneField.getText().toString();
                String CIN = CINField.getText().toString();
                if(email.isEmpty()){
                    emailField.setError("Entrer votre adresse mail ");
                    emailField.requestFocus();
                }else if (pwd.isEmpty()){
                    mdpField.setError("Entrer votre mot de passe");
                    mdpField.requestFocus();

                }else if(confpwd.isEmpty()){
                    confField.setError("Entrer votre mot de passe");
                    confField.requestFocus();
                }else if(!pwd.equals(confpwd)){
                    confField.setError("Les mots de passe ne sont pas identiques");
                    confField.requestFocus();
                }else if(nom.isEmpty()){
                    confField.setError("Entrer votre nom");
                    confField.requestFocus();
                }else if(prenom.isEmpty()){
                    confField.setError("Entrer votre prenom");
                    confField.requestFocus();
                }/*else if(age == null){
                    confField.setError("Please enter your password");
                    confField.requestFocus();
                }*/else if(profession.isEmpty()){
                    confField.setError("Entrer votre Profession");
                    confField.requestFocus();
                }else if(telephone.isEmpty()){
                    confField.setError("Entrer votre numéro de téléphone");
                    confField.requestFocus();
                }else if(CIN.isEmpty()){
                    confField.setError("Entrer votre CIN");
                    confField.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty() && confpwd.isEmpty()){
                    Toast.makeText(InscriptionActivity.this,"Les champs sont vides !",Toast.LENGTH_SHORT).show();

                }else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(InscriptionActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(InscriptionActivity.this,"Inscription échouée, Essayez encore ",Toast.LENGTH_SHORT).show();
                            }else {
                                FirebaseUser user= mFirebaseAuth.getCurrentUser();
                               String uid=user.getUid();
                               int age = Integer.parseInt(ageField.getText().toString().trim());
                              utilisateur.setNom(nomField.getText().toString());
                              utilisateur.setPrenom(nomField.getText().toString().trim());
                              utilisateur.setAge(age);
                              utilisateur.setProfession(nomField.getText().toString().trim());
                              utilisateur.setTelephone(nomField.getText().toString().trim());
                              utilisateur.setCIN(nomField.getText().toString().trim());
                              databaseReference.child(uid).setValue(utilisateur);

                                startActivity(new Intent(InscriptionActivity.this,MainActivity.class));
                            }
                        }
                    });
                }
            }
        });

    }

    public void chooseAct(View v){
        startActivity(new Intent(this,ChooseAct.class));
    }
}
