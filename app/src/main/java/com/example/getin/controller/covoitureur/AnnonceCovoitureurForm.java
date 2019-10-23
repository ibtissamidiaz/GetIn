package com.example.getin.controller.covoitureur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.getin.R;
import com.example.getin.model.AnnonceCovoitureur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnnonceCovoitureurForm extends AppCompatActivity {

    EditText point_depart,point_arrivee,heure_depart,heure_arrivee,nbr_places,prix,description;
    Button ajouter;

    DatabaseReference ref;
    AnnonceCovoitureur annonceCovoitureur;

    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_covoiture_form);
        point_depart = findViewById(R.id.ed_point_depart);
        point_arrivee = findViewById(R.id.ed_point_arrivee);
        heure_depart = findViewById(R.id.ed_temps_depart);
        heure_arrivee = findViewById(R.id.ed_temps_arrivee);
        nbr_places = findViewById(R.id.ed_nbr_places);
        prix = findViewById(R.id.ed_prix);
        description = findViewById(R.id.ed_description);
        ajouter = findViewById(R.id.button_ajouter);


//        ref = FirebaseDatabase.getInstance().getReference().child("AnnonceCovoitureur");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    maxid=(dataSnapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String point_dep = point_depart.getText().toString().trim();
                String point_arr = point_depart.getText().toString().trim();
                String heure_dep = heure_depart.getText().toString().trim();
                String heure_arr = heure_arrivee.getText().toString().trim();
                String desc = description.getText().toString();
                int nbr_pl = Integer.parseInt(nbr_places.getText().toString().trim());
                double pri = Double.parseDouble(prix.getText().toString().trim());

                annonceCovoitureur = new AnnonceCovoitureur(heure_dep,heure_arr,point_dep,point_arr,desc,1,nbr_pl,pri);

//                ref.child(String.valueOf(maxid+1)).setValue(annonceCovoitureur);
                Toast.makeText(AnnonceCovoitureurForm.this,"Annonce ajouté !",Toast.LENGTH_LONG).show();

            }
        });
    }


}
