package com.example.getin.controller.covoitureur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.getin.R;
import com.example.getin.controller.covoiture.MesAnnoncesCovoiture;

public class MesAnnoncesCovoitureur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces_covoitureur);
    }

    // le menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.drop_down_menu,menu);
        MenuItem searchItem =menu.findItem(R.id.app_bar_search);
        MenuItem mesdemandes=menu.findItem(R.id.mesdemandes);
        MenuItem mesannonces=menu.findItem(R.id.mesannonces);
        searchItem.setVisible(false);
        mesdemandes.setVisible(false);
        mesannonces.setVisible(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.deconnecter){
            return true;
        }
        if(id==R.id.monprofil){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
