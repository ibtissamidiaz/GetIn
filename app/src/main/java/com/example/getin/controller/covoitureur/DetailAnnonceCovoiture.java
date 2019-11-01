package com.example.getin.controller.covoitureur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.getin.R;
import com.example.getin.controller.covoiture.MesAnnoncesCovoiture;
import com.example.getin.controller.covoiture.MesDemmandesCovoiture;
import com.example.getin.model.AnnonceCovoiture;
import com.example.getin.model.AnnonceCovoitureur;

public class DetailAnnonceCovoiture extends AppCompatActivity {

    TextView hrDepart,hrArrive,ptDepart,ptArrive,placeDispo,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_annonce_covoiture);
        Bundle b=this.getIntent().getExtras();
        AnnonceCovoiture an= (AnnonceCovoiture) b.getSerializable("annonce");

        // recuperation des textView
        hrDepart=findViewById(R.id.detail_hrDep_covoiture);
        hrArrive=findViewById(R.id.detail_hrArr_covoiture);
        ptDepart=findViewById(R.id.detail_ptDep_covoiture);
        ptArrive=findViewById(R.id.detail_ptArr_covoiture);
        placeDispo=findViewById(R.id.detail_place_covoiture);
        description=findViewById(R.id.detail_desc_covoiture);

        //remplir les champs
        hrDepart.setText( an.getHeure_depart());
        hrArrive.setText(an.getHeure_arrivee());
        ptDepart.setText( an.getPoint_depart());
        ptArrive.setText(an.getPoint_arrivee());
        placeDispo.setText(""+an.getNbr_personnes());
        description.setText( an.getDescription());
    }

    // le menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.drop_down_menu,menu);
        MenuItem searchItem =menu.findItem(R.id.app_bar_search);
        searchItem.setVisible(false);
        MenuItem mesdemandes=menu.findItem(R.id.mesdemandes);
        mesdemandes.setVisible(false);
        MenuItem profil=menu.findItem(R.id.monprofil);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.mesannonces){
            startActivity(new Intent(this, MesAnnoncesCovoitureur.class));
            return true;
        }
        if (id==R.id.deconnecter){
            return true;
        }
        if(id==R.id.monprofil){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
