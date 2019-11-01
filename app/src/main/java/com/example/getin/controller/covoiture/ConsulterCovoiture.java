package com.example.getin.controller.covoiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.getin.R;
import com.example.getin.controller.covoitureur.AnnonceCovoitureurForm;
import com.example.getin.model.AnnonceCovoiture;
import com.example.getin.model.AnnonceCovoitureur;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConsulterCovoiture extends AppCompatActivity {
    ListView listAnnoncesCovoiture;
    FirebaseDatabase data;
    DatabaseReference ref;
    ArrayList<AnnonceCovoitureur> annonces=new ArrayList<>();
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
        //annonces = new ArrayList<>();
        adapter = new AnnonceCovoitureurAdapter(this,annonces);
        ref.addValueEventListener(new ValueEventListener() {
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
        SearchView search = (SearchView) searchItem.getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
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
