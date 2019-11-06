package com.example.getin.controller.covoiture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.getin.R;
import com.example.getin.controller.ProfilActivity;
import com.example.getin.model.AnnonceCovoitureur;
import com.example.getin.model.Demande;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DemandeCovoitureurAdapter extends BaseAdapter implements DemandeDialog.DemandeDialogListener {
    private Context mContext;
    private LayoutInflater inflater;
    private List<Demande> demandeCovoitureurs;
    private String uid,id;
    FirebaseDatabase data;
    DatabaseReference ref;

    public DemandeCovoitureurAdapter(Context mContext,List<Demande> list){
        this.mContext=mContext;
        this.demandeCovoitureurs=list;
        inflater = LayoutInflater.from(mContext);
    }

    public class ViewHolderDemande{
        TextView description,etat;
        FloatingActionButton profil,annonce;
        Button modifier_accepter,supprimer_refuser;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return demandeCovoitureurs.size();
    }

    @Override
    public Object getItem(int position) {
        return demandeCovoitureurs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        data=FirebaseDatabase.getInstance();
        ref=data.getReference("AnnonceCovoitureur");
        DemandeCovoitureurAdapter.ViewHolderDemande holder;
        Intent intent = ((Activity) mContext).getIntent();
        final String espace=intent.getStringExtra("espace");
        if(convertView==null){
            holder = new DemandeCovoitureurAdapter.ViewHolderDemande();
            convertView = inflater.inflate(R.layout.demande_covoitureur_item,null);
            holder.description=convertView.findViewById(R.id.description_demande);
            holder.etat=convertView.findViewById(R.id.etatDemande);
            holder.modifier_accepter=convertView.findViewById(R.id.btn_modifier);
            holder.supprimer_refuser=convertView.findViewById(R.id.btn_supprimer);
            holder.profil = convertView.findViewById(R.id.id_profil);
            holder.annonce = convertView.findViewById(R.id.id_annonce);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolderDemande) convertView.getTag();
        }
        holder.description.setText(demandeCovoitureurs.get(position).getDescription());
        holder.etat.setText(demandeCovoitureurs.get(position).getEtat());
        if (espace != null){
           if(espace.equals("covoiture")) {
            holder.modifier_accepter.setText("Modifier");
            holder.supprimer_refuser.setText("Supprimer");

           }
        }else {
            holder.modifier_accepter.setText("Accepter");
            holder.supprimer_refuser.setText("Refuser");
        }
        holder.modifier_accepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(espace!= null && espace.equals("covoiture")){
                    /*openDialog();*/
                }
                else{
                    //acceptation
                }
            }
        });
        holder.supprimer_refuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(espace!= null && espace.equals("covoiture")){
                    //suppression
                }
                else{
                    //refus
                }
            }
        });
        holder.profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(espace!= null && espace.equals("covoiture")){

                    ref.child(demandeCovoitureurs.get(position).getAnnonce_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Intent i = new Intent(mContext,ProfilActivity.class);

                            i.putExtra("uid",dataSnapshot.getValue(AnnonceCovoitureur.class).getUtilisateur_id());

                            mContext.startActivity(i);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Intent i= new Intent(mContext, ProfilActivity.class);
                    i.putExtra("uid",demandeCovoitureurs.get(position).getUtilisateur_id());
                    mContext.startActivity(i);
                }
            }
        });
        holder.annonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.child(demandeCovoitureurs.get(position).getAnnonce_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Intent i = new Intent(mContext,DetailAnnonceCovoitureur.class);
                        Bundle b = new Bundle();
                        b.putSerializable("annonce",dataSnapshot.getValue(AnnonceCovoitureur.class));
                        i.putExtras(b);
                        i.putExtra("action","demande");
                        mContext.startActivity(i);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        return convertView;


    }

    /*public void openDialog(){
        DemandeDialog demandeDialog = new DemandeDialog(id,uid);
        demandeDialog.show(getSupportFragmentManager(),"demande dialog");
    }*/

}
