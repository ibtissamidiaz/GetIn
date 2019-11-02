package com.example.getin.controller.covoitureur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.getin.R;
import com.example.getin.controller.covoiture.AnnonceCovoitureForm;
import com.example.getin.controller.covoiture.AnnonceCovoitureurAdapter;
import com.example.getin.controller.covoiture.ConsulterCovoiture;
import com.example.getin.controller.covoiture.MesAnnoncesCovoiture;
import com.example.getin.model.AnnonceCovoitureur;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MesAnnoncesCovoitureur extends AppCompatActivity {

    ListView listAnnoncesCovoiture;
    FirebaseDatabase data;
    DatabaseReference ref;
    ArrayList<AnnonceCovoitureur> annonces=new ArrayList<>();
    AnnonceCovoitureurAdapter adapter;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces_covoitureur);

        //la liste des annonce covoitureurs
        listAnnoncesCovoiture = findViewById(R.id.liste_annonce_covoiture);
        adapter = new AnnonceCovoitureurAdapter(this,annonces);
        data=FirebaseDatabase.getInstance();

        user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        ref=data.getReference("AnnonceCovoitureur");
        Query q=ref.orderByChild("utilisateur_id").equalTo(uid);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    AnnonceCovoitureur annonce;
                    annonce = ds.getValue(AnnonceCovoitureur.class);
                    annonces.add(annonce);
                    listAnnoncesCovoiture.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
