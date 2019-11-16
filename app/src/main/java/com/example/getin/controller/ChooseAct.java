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
import com.google.firebase.auth.FirebaseAuth;

public class ChooseAct extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_choose);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.drop_down_menu, menu);
        MenuItem mesannonces=menu.findItem(R.id.mesannonces);
        mesannonces.setVisible(false);
        MenuItem mesdemandes=menu.findItem(R.id.mesdemandes);
        mesdemandes.setVisible(false);
        MenuItem searchItem =menu.findItem(R.id.app_bar_search);
        searchItem.setVisible(false);

        return true;
    }
    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.deconnecter:
                firebaseAuth.signOut();
                Intent i = new Intent(this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                /*finish();*/
                return true;
            case R.id.monprofil:
                startActivity(new Intent(this, ProfilActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToAnnonceCovoitureur(View v){
        Intent i=new Intent(this,ConsulterCovoitureur.class);
        i.putExtra("action","consultation");
        startActivity(i);
    }


    public void goTOAnnonceCovoiture(View view) {
        Intent i=new Intent(this,ConsulterCovoiture.class);
        i.putExtra("action","consultation");
        i.putExtra("espace","covoiture");
        startActivity(i);

    }
}
