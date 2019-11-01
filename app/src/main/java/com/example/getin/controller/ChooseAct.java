package com.example.getin.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.drop_down_menu, menu);
        return true;
    }
    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.deconnecter:

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToAnnonceCovoitureur(View v){
        startActivity(new Intent(this, ConsulterCovoitureur.class));
    }


    public void goTOAnnonceCovoiture(View view) {
        startActivity(new Intent(this, ConsulterCovoiture.class));

    }
}
