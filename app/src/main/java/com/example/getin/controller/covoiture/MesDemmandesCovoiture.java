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
import com.example.getin.controller.ProfilActivity;
import com.example.getin.controller.covoitureur.MesAnnoncesCovoitureur;
import com.example.getin.model.Demande;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MesDemmandesCovoiture extends AppCompatActivity {

    ListView listDemandeCovoiture;
    FirebaseDatabase data;
    DatabaseReference ref,refannonce;
    List<Demande> demandes = new ArrayList<>();
    DemandeCovoitureurAdapter adapter;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_demmandes_covoiture);

        String s = this.getIntent().getStringExtra("espace");

        //la liste des demandes covoiturés
        listDemandeCovoiture = findViewById(R.id.mes_demandes);
        adapter = new DemandeCovoitureurAdapter(this, demandes);
        user= FirebaseAuth.getInstance().getCurrentUser();
        String uid= user.getUid();
        data=FirebaseDatabase.getInstance();
        ref=data.getReference("DemandeCovoiture");
        refannonce=data.getReference("AnnonceCovoitureur");
        if(s != null && s.equals("covoiture")){
            Query q=ref.orderByChild("utilisateur_id").equalTo(uid);
            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Demande demande;
                        demande = ds.getValue(Demande.class);
                        if(demande != null) demande.setId(ds.getKey());
                        demandes.add(demande);
                        listDemandeCovoiture.setAdapter(adapter);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }else {
            Query q=refannonce.orderByChild("utilisateur_id").equalTo(uid);
            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String id_annonce=ds.getKey();
                        Query q=ref.orderByChild("annonce_id").equalTo(id_annonce);
                        q.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Demande demande;
                                    demande = ds.getValue(Demande.class);
                                    if(demande != null) demande.setId(ds.getKey());
                                    demandes.add(demande);
                                    listDemandeCovoiture.setAdapter(adapter);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) { }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }

    }
    // le menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.drop_down_menu,menu);
        MenuItem mesannonces=menu.findItem(R.id.mesdemandes);
        mesannonces.setVisible(false);
        MenuItem searchItem =menu.findItem(R.id.app_bar_search);
        searchItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.mesannonces){
            startActivity(new Intent(this, MesAnnoncesCovoitureur.class));
            return true;
        }
        if(id==R.id.monprofil){
            startActivity(new Intent(this, ProfilActivity.class));
            return true;
        }
        if (id==R.id.deconnecter){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
