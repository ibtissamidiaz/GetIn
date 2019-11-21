package com.example.getin.controller.covoitureur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.getin.R;
import com.example.getin.model.AnnonceCovoitureur;
import com.example.getin.model.Voiture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModifierVoiture extends AppCompatActivity {

    EditText num_imm,nom_v,nbr_places2;
    Button modif;

    DatabaseReference ref;
    AnnonceCovoitureur annonceCovoitureur;
    Voiture voiture;

    String annId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_voiture);

        num_imm = findViewById(R.id.ed_num_immatriculation_m);
        nom_v = findViewById(R.id.ed_nom_voiture_m);
        nbr_places2 = findViewById(R.id.ed_nbr_places2_m);
        modif = findViewById(R.id.modifier_annonce);


        Bundle b=this.getIntent().getExtras();
        if (b != null) annonceCovoitureur= (AnnonceCovoitureur) b.getSerializable("annonceM");

        if(annonceCovoitureur != null){
            annId = annonceCovoitureur.getId_annonce();
            num_imm.setText(annonceCovoitureur.getVoiture().getNum_immatriculation());
            nom_v.setText(annonceCovoitureur.getVoiture().getMarque());
            nbr_places2.setText(String.valueOf(annonceCovoitureur.getVoiture().getNbr_places()));
        }




        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String num_im = num_imm.getText().toString().trim();
                String n_voiture = nom_v.getText().toString().trim();
                String np2 = nbr_places2.getText().toString().trim();

                if (num_im.matches("") || n_voiture.matches("") || np2.matches(""))
                    Toast.makeText(ModifierVoiture.this, "Veulliez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
                else{
                    if(! num_im.matches("^([0-9]{4,6})-(\\w)-([0-9]{1,2}$)"))
                        Toast.makeText(ModifierVoiture.this, "Numero d'immatriculation incorrect", Toast.LENGTH_SHORT).show();
                    else{
                        if(! np2.matches("^(10|[0-9])$") )
                            Toast.makeText(ModifierVoiture.this, "Nombre de places invalide", Toast.LENGTH_SHORT).show();
                        else{
                            int nb_pl2 = Integer.parseInt(np2);

                            if(nb_pl2 < annonceCovoitureur.getNbr_personnes())
                                Toast.makeText(ModifierVoiture.this, "Nombre de places invalide\n Inferieur au nombre de places disponibles", Toast.LENGTH_SHORT).show();
                            else{
                                voiture = new Voiture(num_im,n_voiture,nb_pl2);

                                if(annonceCovoitureur != null) annonceCovoitureur.setVoiture(voiture);

                                ref = FirebaseDatabase.getInstance().getReference().child("AnnonceCovoitureur").child(annId);
                                ref.setValue(annonceCovoitureur);

                                Toast.makeText(ModifierVoiture.this, "Annonce modifiée !", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ModifierVoiture.this, MesAnnoncesCovoitureur.class));
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }
}
