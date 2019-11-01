package com.example.getin.controller.covoiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.getin.R;

public class MesDemmandesCovoiture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_demmandes_covoiture);
    }

    // le menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.drop_down_menu,menu);
        MenuItem mesdemandes=menu.findItem(R.id.mesdemandes);
        mesdemandes.setVisible(false);
        MenuItem searchItem =menu.findItem(R.id.app_bar_search);
        searchItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.mesannonces){
            startActivity(new Intent(this, MesAnnoncesCovoiture.class));
            return true;
        }
        if(id==R.id.monprofil){
            return true;
        }
        if (id==R.id.deconnecter){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
