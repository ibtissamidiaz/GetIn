package com.example.getin.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.getin.R;
import com.example.getin.controller.covoiture.AnnonceCovoitureForm;
import com.example.getin.controller.covoitureur.AnnonceCovoitureurForm;

public class ChooseAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
    }

    public void goToAjouterAnnonceCovoitureur(View v){
        startActivity(new Intent(this, AnnonceCovoitureurForm.class));
    }

    public void goToAjouterAnnonceCovoiture(View v){
        startActivity(new Intent(this, AnnonceCovoitureForm.class));
    }


}
