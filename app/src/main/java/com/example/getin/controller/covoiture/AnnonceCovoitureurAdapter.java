package com.example.getin.controller.covoiture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.getin.R;
import com.example.getin.controller.ProfilActivity;
import com.example.getin.model.AnnonceCovoitureur;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AnnonceCovoitureurAdapter extends BaseAdapter implements DemandeDialog.DemandeDialogListener {
    private Context mContext;
    private LayoutInflater inflater;
    private List<AnnonceCovoitureur> annonceCovoitureurs;
    private ArrayList<AnnonceCovoitureur> arrayAnnonces;

   public AnnonceCovoitureurAdapter(Context mContext,List<AnnonceCovoitureur> list){
             this.mContext=mContext;
             this.annonceCovoitureurs=list;
             inflater = LayoutInflater.from(mContext);
             this.arrayAnnonces = new ArrayList<>();
   }

    public void setArrayAnnonces(ArrayList<AnnonceCovoitureur> arrayAnnonces) {
        this.arrayAnnonces = arrayAnnonces;
    }

    public static class ViewHolder{
       TextView hrdepart,hrarrive,ptdepart,ptarrive,prix;
       FloatingActionButton annonceur;
   }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return annonceCovoitureurs.size();
    }

    @Override
    public Object getItem(int position) {
        return annonceCovoitureurs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       final ViewHolder holder;
       if(convertView==null){
           holder = new ViewHolder();
           convertView = inflater.inflate(R.layout.annonce_covoitureur_item,null);
            holder.ptdepart=convertView.findViewById(R.id.ptdepartcovoiture);
           holder.ptarrive=convertView.findViewById(R.id.ptarrivecovoiture);
           holder.hrdepart=convertView.findViewById(R.id.heuredepartcovoiture);
           holder.hrarrive=convertView.findViewById(R.id.heurearrivecovoiture);
           holder.annonceur=convertView.findViewById(R.id.id_covoiture);
           holder.prix=convertView.findViewById(R.id.prixcovoiture);

           convertView.setTag(holder);
       }
       else {
          holder = (ViewHolder) convertView.getTag();
       }
       holder.ptdepart.setText("De : "+annonceCovoitureurs.get(position).getPoint_depart());
       holder.ptarrive.setText("A : "+annonceCovoitureurs.get(position).getPoint_arrivee());
       holder.hrdepart.setText(annonceCovoitureurs.get(position).getHeure_depart());
       holder.hrarrive.setText(annonceCovoitureurs.get(position).getHeure_arrivee());
       holder.prix.setText(annonceCovoitureurs.get(position).getPrix() + " DH ");


       convertView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                   //pour voir plus de details
               Intent i=new Intent(mContext,DetailAnnonceCovoitureur.class);
               Intent intent=((Activity) mContext).getIntent();
               String s=intent.getStringExtra("action");
               i.putExtra("action",s);
               Bundle b = new Bundle();
               b.putSerializable("annonce",annonceCovoitureurs.get(position));
               i.putExtras(b);
               mContext.startActivity(i);
           }
       });

       FloatingActionButton annonceur=convertView.findViewById(R.id.id_covoiture);
       annonceur.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //code pour voir le profil de l'annonceur
               Intent i = new Intent(mContext, ProfilActivity.class);
               i.putExtra("uid",annonceCovoitureurs.get(position).getUtilisateur_id());
               mContext.startActivity(i);
           }
       });


        return convertView;


    }

    public void filter(String motCle){
       motCle = motCle.toLowerCase(Locale.getDefault());
       annonceCovoitureurs.clear();
       if(motCle.length()==0){
           annonceCovoitureurs.addAll(arrayAnnonces);
       }
       else{
           for (AnnonceCovoitureur an : this.arrayAnnonces){
               if (an.getPoint_depart().toLowerCase(Locale.getDefault()).contains(motCle) ||
                       an.getPoint_arrivee().toLowerCase(Locale.getDefault()).contains(motCle) ||
                       an.getDescription().toLowerCase(Locale.getDefault()).contains(motCle)){
                   this.annonceCovoitureurs.add(an);
               }
           }
       }
       notifyDataSetChanged();
    }

}
