package com.example.getin.controller.covoiture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.getin.R;

public class AnnonceCovoitureForm extends AppCompatActivity {

    EditText point_depart,point_arrivee,heure_depart,heure_arrivee,nbr_places,prix,description;
    Button ajouter;

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
    }


}
