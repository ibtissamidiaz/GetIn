package com.example.getin.controller.covoiture;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.getin.R;
import com.example.getin.controller.ProfilActivity;
import com.example.getin.controller.covoitureur.MesAnnoncesCovoitureur;
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
    DatabaseReference ref,ref1;

    public DemandeCovoitureurAdapter(Context mContext,List<Demande> list){
        this.mContext=mContext;
        this.demandeCovoitureurs=list;
        inflater = LayoutInflater.from(mContext);
    }

    public class ViewHolderDemande{
        TextView description,etat;
        FloatingActionButton profil,annonce;
        ImageButton modifier_accepter,supprimer_refuser;
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
        ref1=data.getReference("DemandeCovoiture");
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
            holder.modifier_accepter.setImageResource(R.drawable.ic_edit);
            holder.supprimer_refuser.setImageResource(R.drawable.ic_delete);

           }
        }else {
            holder.modifier_accepter.setImageResource(R.drawable.ic_done_black_24dp);
            holder.supprimer_refuser.setImageResource(R.drawable.ic_close_black_24dp);
        }
        holder.modifier_accepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(espace!= null && espace.equals("covoiture")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Modifier la demande");
                    final EditText input = new EditText(mContext);
                    builder.setView(input);
                    input.setText(demandeCovoitureurs.get(position).getDescription());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                       ref1.child(demandeCovoitureurs.get(position).getId()).child("description").setValue(input.getText().toString().trim());
                            Toast.makeText(mContext,"Demande modifiée !",Toast.LENGTH_LONG).show();
                            ((Activity) mContext).finish();
                            mContext.startActivity(((Activity) mContext).getIntent());
                        }
                    });
                    builder.setNegativeButton("Annuler", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    //acceptation
                    ref1.child(demandeCovoitureurs.get(position).getId()).child("etat").setValue("Acceptée");
                    Toast.makeText(mContext,"Demande acceptée",Toast.LENGTH_LONG).show();
                    ((Activity) mContext).finish();
                    mContext.startActivity(((Activity) mContext).getIntent());



                }
            }
        });
        holder.supprimer_refuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(espace!= null && espace.equals("covoiture")){
                    //suppression
                    AlertDialog.Builder buildDialog = new AlertDialog.Builder(mContext);
                    buildDialog.setTitle("Confirmation");
                    buildDialog.setMessage("Etes-vous sûr de vouloir supprimer la demande ?");
                    buildDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String id = demandeCovoitureurs.get(position).getId();
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("DemandeCovoiture").child(id);
                            dR.removeValue();
                            Toast.makeText(mContext,"Demande supprimée",Toast.LENGTH_LONG).show();
                            ((Activity) mContext).finish();
                            mContext.startActivity(((Activity) mContext).getIntent());
                        }
                    });
                    buildDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    buildDialog.show();
                }
                else{
                    AlertDialog.Builder buildDialog = new AlertDialog.Builder(mContext);
                    buildDialog.setTitle("Confirmation");
                    buildDialog.setMessage("Etes-vous sûr de vouloir refuser l'annonce ?");
                    buildDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //refus
                            ref1.child(demandeCovoitureurs.get(position).getId()).child("etat").setValue("Refusée");
                            Toast.makeText(mContext, "Demande refusée", Toast.LENGTH_LONG).show();
                            ((Activity) mContext).finish();
                            mContext.startActivity(((Activity) mContext).getIntent());
                        }  });

                    buildDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    buildDialog.show();}
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



}
