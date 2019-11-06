
package com.example.getin.controller.covoitureur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.getin.R;
import com.example.getin.controller.ProfilActivity;
import com.example.getin.controller.covoiture.MesDemmandesCovoiture;
import com.example.getin.model.AnnonceCovoiture;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConsulterCovoitureur extends AppCompatActivity {

    ListView listAnnoncesCovoiture;
    FirebaseDatabase data;
    DatabaseReference ref;
    ArrayList<AnnonceCovoiture> annonces=new ArrayList<>();
    ArrayList<AnnonceCovoiture> FilterAnnonces=new ArrayList<>();
    AnnonceCovoitureAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_covoitureur);

        //le boutton d'ajout de l'annonce
        FloatingActionButton ajoutAnnonceCovoiture = findViewById(R.id.add_annonce_covoitureur);
        ajoutAnnonceCovoiture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsulterCovoitureur.this, AnnonceCovoitureurForm.class);
                startActivity(intent);
            }
        });

        //la liste des annonce covoiturés
        listAnnoncesCovoiture = findViewById(R.id.liste_annonce_covoitureur);
        data=FirebaseDatabase.getInstance();
        ref=data.getReference("AnnonceCovoiture");
        adapter = new AnnonceCovoitureAdapter(this,annonces);
        adapter.setArrayAnnonces(FilterAnnonces);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    AnnonceCovoiture annonce;
                    annonce = ds.getValue(AnnonceCovoiture.class);
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
        MenuItem mesdemandes=menu.findItem(R.id.mesdemandes);
        mesdemandes.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.mesannonces){
            startActivity(new Intent(this, MesAnnoncesCovoitureur.class));
            return true;
        }
        if(id==R.id.mesdemandes){
            startActivity(new Intent(this, MesDemmandesCovoiture.class));
            return true;
        }
        if (id==R.id.deconnecter){
            return true;
        }
        if(id==R.id.monprofil){
            startActivity(new Intent(this, ProfilActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
