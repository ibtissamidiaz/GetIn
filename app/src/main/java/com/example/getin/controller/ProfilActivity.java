package com.example.getin.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.getin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilActivity extends AppCompatActivity {
    private  FirebaseUser user;
    private FirebaseDatabase data;
    private DatabaseReference ref;
    private String uid;
    private TextView nom_prenom,age,profession,telephone,cin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        user = FirebaseAuth.getInstance().getCurrentUser();
        data = FirebaseDatabase.getInstance();
        String id = this.getIntent().getStringExtra("uid");
        if (id != null){
            uid=id;
        }
        else{
            uid = user.getUid();
        }

        ref = data.getReference("Utilisateur").child(uid);

        nom_prenom = findViewById(R.id.utilisateur_nom_prenom);
        age = findViewById(R.id.utilisateur_age);
        profession = findViewById(R.id.utilisateur_profession);
        telephone = findViewById(R.id.utilisateur_numtelep);
        cin = findViewById(R.id.utilisateur_cin);

        ref.addValueEventListener( new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nom = dataSnapshot.child("nom").getValue().toString();
                String prenom = dataSnapshot.child("prenom").getValue().toString();
                String nomprenom = nom +" "+prenom;
                String uage = dataSnapshot.child("age").getValue().toString();
                String uprofession = dataSnapshot.child("profession").getValue().toString();
                String utelephone = dataSnapshot.child("telephone").getValue().toString();
                String ucin = dataSnapshot.child("cin").getValue().toString();

                nom_prenom.setText(nomprenom);
                age.setText(uage);
                profession.setText(uprofession);
                telephone.setText(utelephone);
                cin.setText(ucin);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}