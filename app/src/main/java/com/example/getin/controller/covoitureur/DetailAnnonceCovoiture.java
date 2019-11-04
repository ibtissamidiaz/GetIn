package com.example.getin.controller.covoitureur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getin.R;
import com.example.getin.controller.covoiture.DetailAnnonceCovoitureur;
import com.example.getin.controller.covoiture.MesAnnoncesCovoiture;
import com.example.getin.controller.covoiture.MesDemmandesCovoiture;
import com.example.getin.controller.covoiture.ModifierAnnonceCovoiture;
import com.example.getin.model.AnnonceCovoiture;
import com.example.getin.model.AnnonceCovoitureur;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailAnnonceCovoiture extends AppCompatActivity {

    TextView hrDepart,hrArrive,ptDepart,ptArrive,placeDispo,description;
    Button modifier,supprimer;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_annonce_covoiture);
        Bundle b=this.getIntent().getExtras();
        final AnnonceCovoiture an= (AnnonceCovoiture) b.getSerializable("annonce");
        id = an.getId_annonce();

        // recuperation des textView
        hrDepart=findViewById(R.id.detail_hrDep_covoiture);
        hrArrive=findViewById(R.id.detail_hrArr_covoiture);
        ptDepart=findViewById(R.id.detail_ptDep_covoiture);
        ptArrive=findViewById(R.id.detail_ptArr_covoiture);
        placeDispo=findViewById(R.id.detail_place_covoiture);
        description=findViewById(R.id.detail_desc_covoiture);
        modifier=findViewById(R.id.modifier);
        supprimer=findViewById(R.id.supprimer);

        //remplir les champs
        hrDepart.setText( an.getHeure_depart());
        hrArrive.setText(an.getHeure_arrivee());
        ptDepart.setText( an.getPoint_depart());
        ptArrive.setText(an.getPoint_arrivee());
        placeDispo.setText(""+an.getNbr_personnes());
        description.setText( an.getDescription());
        String action=this.getIntent().getStringExtra("action");
        if (action != null) {
            if (action.equals("consultation")) {
                modifier.setVisibility(View.INVISIBLE);
                supprimer.setVisibility(View.INVISIBLE);
            }
        }

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailAnnonceCovoiture.this, ModifierAnnonceCovoiture.class);
                intent.putExtra("annonceM",an);
                startActivity(intent);
            }
        });

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder buildDialog = new AlertDialog.Builder(DetailAnnonceCovoiture.this);
                buildDialog.setTitle("Confirmation");
                buildDialog.setMessage("Etes-vous sûr de vouloir supprimer l'annonce ?");
                buildDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("AnnonceCovoiture").child(id);
                        dR.removeValue();
                        Toast.makeText(DetailAnnonceCovoiture.this,"Annonce supprimée !",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DetailAnnonceCovoiture.this, MesAnnoncesCovoiture.class));
                    }
                });
                buildDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                buildDialog.show();
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
