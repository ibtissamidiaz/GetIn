package com.example.getin.controller.covoiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.getin.R;
import com.example.getin.controller.MainActivity;
import com.example.getin.model.AnnonceCovoiture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnnonceCovoitureForm extends AppCompatActivity {

    EditText point_depart,point_arrivee,heure_depart,heure_arrivee,nbr_personnes,description;
    Button ajouter;

    DatabaseReference ref;
    AnnonceCovoiture annonceCovoiture;

    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_covoiture_form);
        point_depart = findViewById(R.id.ed_point_depart);
        point_arrivee = findViewById(R.id.ed_point_arrivee);
        heure_depart = findViewById(R.id.ed_temps_depart);
        heure_arrivee = findViewById(R.id.ed_temps_arrivee);
        nbr_personnes = findViewById(R.id.ed_nbr_personnes);
        description = findViewById(R.id.ed_description);
        ajouter = findViewById(R.id.button_ajouter);


        ref = FirebaseDatabase.getInstance().getReference().child("AnnonceCovoiture");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid=(dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String point_dep = point_depart.getText().toString().trim();
                String point_arr = point_arrivee.getText().toString().trim();
                String heure_dep = heure_depart.getText().toString().trim();
                String heure_arr = heure_arrivee.getText().toString().trim();
                String desc = description.getText().toString();
                int nbr_pers = Integer.parseInt(nbr_personnes.getText().toString().trim());

                annonceCovoiture = new AnnonceCovoiture(heure_dep,heure_arr,point_dep,point_arr,desc,1,nbr_pers);

                ref.child(String.valueOf(maxid+1)).setValue(annonceCovoiture);
                Toast.makeText(AnnonceCovoitureForm.this,"Annonce ajouté !",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AnnonceCovoitureForm.this, MainActivity.class));

            }
        });
    }


}
