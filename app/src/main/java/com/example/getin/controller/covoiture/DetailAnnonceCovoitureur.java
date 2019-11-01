package com.example.getin.controller.covoiture;

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
import com.example.getin.model.AnnonceCovoiture;
import com.example.getin.model.AnnonceCovoitureur;

public class DetailAnnonceCovoitureur extends AppCompatActivity {
    TextView hrDepart,hrArrive,ptDepart,ptArrive,placeDispo,prix,description,nomVoiture,immatricule,nbPlaces;
    Button demmander;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_annonce_covoitureur);
        Bundle b=this.getIntent().getExtras();
        AnnonceCovoitureur an= (AnnonceCovoitureur) b.getSerializable("annonce");

        // recuperation des textView
        hrDepart=findViewById(R.id.detail_hrDep_covoitureur);
        hrArrive=findViewById(R.id.detail_hrArr_covoitureur);
        ptDepart=findViewById(R.id.detail_ptDep_covoitureur);
        ptArrive=findViewById(R.id.detail_ptArr_covoitureur);
        placeDispo=findViewById(R.id.detail_placeDisp);
        prix=findViewById(R.id.detail_prix_covoitureur);
        description=findViewById(R.id.detail_desc_covoitureur);
        nomVoiture=findViewById(R.id.detail_nomV_covoitureur);
        immatricule=findViewById(R.id.detail_matV_covoitureur);
        nbPlaces=findViewById(R.id.detail_nbPlace_covoitureur);
        demmander=findViewById(R.id.demandeCovoiture);

        //remplir les champs

        //annonce
        hrDepart.setText( an.getHeure_depart());
        hrArrive.setText(an.getHeure_arrivee());
        ptDepart.setText( an.getPoint_depart());
        ptArrive.setText(an.getPoint_arrivee());
        prix.setText( an.getPrix() + " DH");
        placeDispo.setText(""+an.getNbr_personnes());
        description.setText( an.getDescription());

        //voiture
        nomVoiture.setText(an.getVoiture().getMarque());
        immatricule.setText(an.getVoiture().getNum_immatriculation());
        nbPlaces.setText(an.getVoiture().getNbr_places() +" places");


        //Action sur la demande
        demmander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ici le code du demande de covoiturage
            }
        });
    }

    // le menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.drop_down_menu,menu);
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
        if (id==R.id.mesdemandes){
            startActivity(new Intent(this, MesDemmandesCovoiture.class));

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
