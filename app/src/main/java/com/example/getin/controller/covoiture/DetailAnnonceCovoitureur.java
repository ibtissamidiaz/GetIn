package com.example.getin.controller.covoiture;

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
import com.example.getin.controller.ProfilActivity;
import com.example.getin.controller.covoitureur.DetailAnnonceCovoiture;
import com.example.getin.controller.covoitureur.MesAnnoncesCovoitureur;
import com.example.getin.controller.covoitureur.ModiferAnnonceCovoitureur;
import com.example.getin.model.AnnonceCovoiture;
import com.example.getin.model.AnnonceCovoitureur;
import com.example.getin.model.Demande;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.SQLOutput;

import static com.example.getin.controller.covoiture.AnnonceCovoitureForm.getAlphaNumericString;

public class DetailAnnonceCovoitureur extends AppCompatActivity implements DemandeDialog.DemandeDialogListener {
    TextView hrDepart,hrArrive,ptDepart,ptArrive,placeDispo,prix,description,nomVoiture,immatricule,nbPlaces;
    Button demmander;
    Button modifier,supprimer;
    String id,uid;
    Demande demande;
    String demandeDescription;
    FirebaseUser user;
    FirebaseDatabase data;
    DatabaseReference ref;
    int flagOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_annonce_covoitureur);
        Bundle b=this.getIntent().getExtras();
        final AnnonceCovoitureur an= (AnnonceCovoitureur) b.getSerializable("annonce");
        id = an.getId_annonce();
        user= FirebaseAuth.getInstance().getCurrentUser();
        data=FirebaseDatabase.getInstance();
        ref=data.getReference("DemandeCovoiture");
        uid= user.getUid();

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
        modifier=findViewById(R.id.modifierAnnonceCovoitureur);
        supprimer=findViewById(R.id.SupprimerAnnonceCovoitureur);

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

        String action=this.getIntent().getStringExtra("action");
        if (action != null) {
            if (action.equals("consultation")) {
                modifier.setVisibility(View.INVISIBLE);
                supprimer.setVisibility(View.INVISIBLE);
            }
            else{
                modifier.setVisibility(View.INVISIBLE);
                supprimer.setVisibility(View.INVISIBLE);
                demmander.setVisibility(View.INVISIBLE);
            }
        }
        else{
            demmander.setVisibility(View.INVISIBLE);

        }


        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailAnnonceCovoitureur.this, ModiferAnnonceCovoitureur.class);
                intent.putExtra("annonce",an);
                startActivity(intent);
            }
        });

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder buildDialog = new AlertDialog.Builder(DetailAnnonceCovoitureur.this);
                buildDialog.setTitle("Confirmation");
                buildDialog.setMessage("Etes-vous sûr de vouloir supprimer l'annonce ?");
                buildDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("AnnonceCovoitureur").child(id);
                        dR.removeValue();
                        Toast.makeText(DetailAnnonceCovoitureur.this,"Annonce supprimée",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DetailAnnonceCovoitureur.this, MesAnnoncesCovoitureur.class));
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
        //Demander
        demmander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });
    }
    public void openDialog(){
        DemandeDialog demandeDialog = new DemandeDialog(id,uid);
        demandeDialog.show(getSupportFragmentManager(),"demande dialog");
    }
    /*@Override
    public void applyTexts(String description,int flag) {
        demandeDescription = description;
        flagOk = flag;
    }*/



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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
