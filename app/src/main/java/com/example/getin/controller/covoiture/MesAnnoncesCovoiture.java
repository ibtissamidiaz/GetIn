package com.example.getin.controller.covoiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.getin.R;
import com.example.getin.controller.covoitureur.AnnonceCovoitureAdapter;
import com.example.getin.model.AnnonceCovoiture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MesAnnoncesCovoiture extends AppCompatActivity {

    ListView listAnnoncesCovoiture;
    FirebaseDatabase data;
    DatabaseReference ref;
    ArrayList<AnnonceCovoiture> annonces = new ArrayList<>();
    AnnonceCovoitureAdapter adapter;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces_covoiture);

        //la liste des annonce covoiturés
        listAnnoncesCovoiture = findViewById(R.id.mes_annonce_covoiture);
        adapter = new AnnonceCovoitureAdapter(this, annonces);
        user=FirebaseAuth.getInstance().getCurrentUser();
        String uid= user.getUid();
        data=FirebaseDatabase.getInstance();
        ref=data.getReference("AnnonceCovoiture");
        Query q=ref.orderByChild("utilisateur_id").equalTo(uid);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    AnnonceCovoiture annonce;
                    annonce = ds.getValue(AnnonceCovoiture.class);
                    if(annonce != null) annonce.setId_annonce(ds.getKey());
                    annonces.add(annonce);
                    listAnnoncesCovoiture.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    // le menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.drop_down_menu,menu);
        MenuItem mesannonces=menu.findItem(R.id.mesannonces);
        mesannonces.setVisible(false);
        MenuItem searchItem =menu.findItem(R.id.app_bar_search);
        searchItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
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
