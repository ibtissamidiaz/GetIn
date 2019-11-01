package com.example.getin.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.getin.R;
import com.example.getin.controller.covoiture.ConsulterCovoiture;
import com.example.getin.controller.covoitureur.AnnonceCovoitureurForm;
import com.example.getin.controller.covoitureur.ConsulterCovoitureur;
import com.example.getin.model.AnnonceCovoitureur;

public class ChooseAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
    }

    public void goToAnnonceCovoitureur(View v){
        startActivity(new Intent(this, ConsulterCovoitureur.class));
    }


    public void goTOAnnonceCovoiture(View view) {
        startActivity(new Intent(this, ConsulterCovoiture.class));

    }
}
