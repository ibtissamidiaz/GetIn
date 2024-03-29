package com.example.getin.controller.covoitureur;

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
import com.example.getin.model.AnnonceCovoiture;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class AnnonceCovoitureAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
   private List<AnnonceCovoiture> annonceCovoitures;
   private ArrayList<AnnonceCovoiture> arrayAnnonces;

    public AnnonceCovoitureAdapter(Context mContext,List<AnnonceCovoiture> list){
        this.mContext=mContext;
        this.annonceCovoitures =list;
        inflater = LayoutInflater.from(mContext);
        this.arrayAnnonces = new ArrayList<>();
    }

    public void setArrayAnnonces(ArrayList<AnnonceCovoiture> arrayAnnonces) {
        this.arrayAnnonces = arrayAnnonces;
    }

    public class ViewHolderCovoiture{
        TextView hrdepart,hrarrive,ptdepart,ptarrive;
        FloatingActionButton annonceur;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return annonceCovoitures.size();
    }

    @Override
    public Object getItem(int position) {
        return annonceCovoitures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderCovoiture holder;
        if(convertView==null){
            holder = new ViewHolderCovoiture();
            convertView = inflater.inflate(R.layout.annonce_covoiture_item,null);
            holder.ptdepart=convertView.findViewById(R.id.ptdepartcovoitureur);
            holder.ptarrive=convertView.findViewById(R.id.ptarrivecovoitureur);
            holder.hrdepart=convertView.findViewById(R.id.heuredepartcovoitureur);
            holder.hrarrive=convertView.findViewById(R.id.heurearrivecovoitureur);
           // holder.annonceur=convertView.findViewById(R.id.id_covoitureur);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolderCovoiture) convertView.getTag();
        }
        holder.ptdepart.setText("De : "+ annonceCovoitures.get(position).getPoint_depart());
        holder.ptarrive.setText("A : "+ annonceCovoitures.get(position).getPoint_arrivee());
        holder.hrdepart.setText(annonceCovoitures.get(position).getHeure_depart());
        holder.hrarrive.setText(annonceCovoitures.get(position).getHeure_arrivee());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pour voir plus de details
                Intent i=new Intent(mContext, DetailAnnonceCovoiture.class);
                Intent intent=((Activity) mContext).getIntent();
                String s=intent.getStringExtra("action");
                i.putExtra("action",s);
                Bundle b = new Bundle();
                b.putSerializable("annonce", annonceCovoitures.get(position));
                i.putExtras(b);
                mContext.startActivity(i);
            }
        });

        FloatingActionButton annonceur=convertView.findViewById(R.id.id_covoitureur);
        annonceur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code pour voir le profil de l'annonceur
                Intent i = new Intent(mContext, ProfilActivity.class);
                i.putExtra("uid",annonceCovoitures.get(position).getUtilisateur_id());
                mContext.startActivity(i);
            }
        });

        return convertView;
    }

    public void filter(String motCle){
        motCle = motCle.toLowerCase(Locale.getDefault());
        annonceCovoitures.clear();
        if(motCle.length()==0){
            annonceCovoitures.addAll(arrayAnnonces);
        }
        else{
            for (AnnonceCovoiture an : this.arrayAnnonces){
                if (an.getPoint_depart().toLowerCase(Locale.getDefault()).contains(motCle) ||
                        an.getPoint_arrivee().toLowerCase(Locale.getDefault()).contains(motCle) ||
                        an.getDescription().toLowerCase(Locale.getDefault()).contains(motCle)){
                    this.annonceCovoitures.add(an);
                }
            }
        }
        notifyDataSetChanged();
    }
}
