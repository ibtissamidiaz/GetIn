package com.example.getin.controller.covoiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.getin.R;
import com.example.getin.controller.MainActivity;
import com.example.getin.controller.ProfilActivity;
import com.example.getin.model.AnnonceCovoitureur;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConsulterCovoiture extends AppCompatActivity {
    ListView listAnnoncesCovoiture;
    FirebaseDatabase data;
    FirebaseAuth firebaseAuth;
    DatabaseReference ref;
    ArrayList<AnnonceCovoitureur> annonces=new ArrayList<>();
    ArrayList<AnnonceCovoitureur> FilterAnnonces=new ArrayList<>();
    AnnonceCovoitureurAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_covoiture);

        //le boutton d'ajout de l'annonce
        FloatingActionButton ajoutAnnonceCovoiture = findViewById(R.id.add_annonce_covoiture);
        ajoutAnnonceCovoiture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsulterCovoiture.this, AnnonceCovoitureForm.class);
                startActivity(intent);
            }
        });

        //la liste des annonce covoitureurs
        listAnnoncesCovoiture = findViewById(R.id.liste_annonce_covoiture);
        data=FirebaseDatabase.getInstance();
        ref=data.getReference("AnnonceCovoitureur");
        //demandes = new ArrayList<>();
        adapter = new AnnonceCovoitureurAdapter(this,annonces);
        adapter.setArrayAnnonces(FilterAnnonces);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    AnnonceCovoitureur annonce;
                    annonce = ds.getValue(AnnonceCovoitureur.class);
                    if(annonce != null) annonce.setId_annonce(ds.getKey());
                    annonces.add(annonce);
                    FilterAnnonces.add(annonce);
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
        SearchView search = (SearchView) searchItem.getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
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
            Intent i = new Intent(this,MesDemmandesCovoiture.class);
            i.putExtra("espace","covoiture");
            startActivity(i);
            return true;
        }
        if(id==R.id.monprofil){
            startActivity(new Intent(this, ProfilActivity.class));
            return true;
        }
        if (id==R.id.deconnecter){
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            /*finish();*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
