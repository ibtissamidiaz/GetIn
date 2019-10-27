package com.example.getin.controller.covoitureur;

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
import com.example.getin.model.AnnonceCovoitureur;
import com.example.getin.model.Voiture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoVoiture extends AppCompatActivity {

    EditText num_imm,nom_v,nbr_places2;
    Button ajout;

    DatabaseReference ref;
    AnnonceCovoitureur annonceCovoitureur;
    Voiture voiture;

    long maxId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_voiture);
        num_imm = findViewById(R.id.ed_num_immatriculation);
        nom_v = findViewById(R.id.ed_nom_voiture);
        nbr_places2 = findViewById(R.id.ed_nbr_places2);
        ajout = findViewById(R.id.ajouter_annonce);


        ref = FirebaseDatabase.getInstance().getReference().child("AnnonceCovoitureur");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String num_im = num_imm.getText().toString().trim();
                String n_voiture = nom_v.getText().toString().trim();
                String np2 = nbr_places2.getText().toString().trim();

                if (num_im.matches("") || n_voiture.matches("") || np2.matches(""))
                    Toast.makeText(InfoVoiture.this, "Veulliez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
                else{
                    if(! num_im.matches("^([0-9]{5})-(\\w)-([0-9]{1,2}$)"))
                        Toast.makeText(InfoVoiture.this, "Numero d'immatriculation incorrect", Toast.LENGTH_SHORT).show();
                    else{
                        if(! np2.matches("^(10|[0-9])$") )
                            Toast.makeText(InfoVoiture.this, "Nombre de places invalide", Toast.LENGTH_SHORT).show();
                        else{
                            annonceCovoitureur = (AnnonceCovoitureur) getIntent().getSerializableExtra("annonce");
                            int nb_pl2 = Integer.parseInt(np2);

                            if(nb_pl2 < annonceCovoitureur.getNbr_personnes())
                                Toast.makeText(InfoVoiture.this, "Nombre de places invalide\n Inferieur au nombre de places disponibles", Toast.LENGTH_SHORT).show();
                            else{
                                voiture = new Voiture(num_im,n_voiture,nb_pl2);

                                if(annonceCovoitureur != null) annonceCovoitureur.setVoiture(voiture);

                                ref.child(String.valueOf(maxId + 1)).setValue(annonceCovoitureur);
                                Toast.makeText(InfoVoiture.this, "Annonce ajouté !", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(InfoVoiture.this, MainActivity.class));
                            }
                        }
                    }
                }
            }
        });
    }
}