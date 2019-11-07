package com.example.getin.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getin.R;
import com.example.getin.controller.covoiture.MesAnnoncesCovoiture;
import com.example.getin.controller.covoitureur.DetailAnnonceCovoiture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class ProfilActivity extends AppCompatActivity {
    private  FirebaseUser user;
    private FirebaseDatabase data;
    private DatabaseReference ref;
    private String uid,unvotes,unote,chked;
    private TextView nom_prenom,age,profession,telephone,cin,rate;
    private Button evaluer;
    private ImageView prf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        evaluer = findViewById(R.id.evaluer);
        user = FirebaseAuth.getInstance().getCurrentUser();
        data = FirebaseDatabase.getInstance();
        String id = this.getIntent().getStringExtra("uid");
        if (id != null){
            uid=id;
        }
        else{
            uid = user.getUid();
            evaluer.setVisibility(View.GONE);
        }

        ref = data.getReference("Utilisateur").child(uid);

        nom_prenom = findViewById(R.id.utilisateur_nom_prenom);
        age = findViewById(R.id.utilisateur_age);
        profession = findViewById(R.id.utilisateur_profession);
        telephone = findViewById(R.id.utilisateur_numtelep);
        cin = findViewById(R.id.utilisateur_cin);
        prf = findViewById(R.id.prf);
        rate = findViewById(R.id.utilisateur_rate);

        ref.addValueEventListener( new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nom = dataSnapshot.child("nom").getValue().toString();
                String prenom = dataSnapshot.child("prenom").getValue().toString();
                String nomprenom = nom +" "+prenom;
                String uage = dataSnapshot.child("age").getValue().toString();
                String uprofession = dataSnapshot.child("profession").getValue().toString();
                String utelephone = dataSnapshot.child("telephone").getValue().toString();
                String ucin = dataSnapshot.child("cin").getValue().toString();
                String usexe = dataSnapshot.child("sexe").getValue().toString();
                unvotes = dataSnapshot.child("nbrVotes").getValue().toString();
                unote = dataSnapshot.child("note").getValue().toString();

                DecimalFormat form = new DecimalFormat("0.0");
                if(Float.parseFloat(unvotes) != 0)
                rate.setText(form.format(Float.parseFloat(unote)/Float.parseFloat(unvotes)));
                else rate.setText("0");

                nom_prenom.setText(nomprenom);
                age.setText(uage);
                profession.setText(uprofession);
                telephone.setText(utelephone);
                cin.setText(ucin);

                if(usexe.equals("Femme")) prf.setImageResource(R.drawable.girl1);
                else prf.setImageResource(R.drawable.boy2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        evaluer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilActivity.this);
                builder.setTitle("Evaluer le covoitureur");
                String notes[] = {"1 (Nul)","2 (Pas mal)","3 (Assez bien)","4 (Bien)","5 (Excellent)"};
                final int checkedItem = 0;
                builder.setSingleChoiceItems(notes, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog)dialog).getListView();
                        Object chkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        chked = (String) chkedItem;
                        Toast.makeText(ProfilActivity.this,chked,Toast.LENGTH_LONG).show();
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int un = Integer.parseInt(unote);
                        int uv = Integer.parseInt(unvotes);
                        int c = Integer.parseInt(chked.charAt(0)+"");

                        un += c;
                        uv += 1;

                        ref.child("note").setValue(un);
                        ref.child("nbrvotes").setValue(uv);

                        Toast.makeText(ProfilActivity.this,"Evaluation effectuée !",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Annuler", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}