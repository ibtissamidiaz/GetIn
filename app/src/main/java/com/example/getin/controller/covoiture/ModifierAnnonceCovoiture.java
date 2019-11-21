package com.example.getin.controller.covoiture;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.getin.R;
import com.example.getin.model.AnnonceCovoiture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ModifierAnnonceCovoiture extends AppCompatActivity {

    EditText point_depart,point_arrivee,heure_depart,heure_arrivee,nbr_personnes,description;
    Button ch1,ch2,modifier;

    int year,month,day,hour,minute;
    String hr,annId;

    DatabaseReference ref;
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user= mFirebaseAuth.getCurrentUser();
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_annonce_covoiture);
        point_depart = findViewById(R.id.ed_point_depart_mc);
        point_arrivee = findViewById(R.id.ed_point_arrivee_mc);
        heure_depart = findViewById(R.id.ed_temps_depart_mc);
        heure_depart.setEnabled(false);
        heure_arrivee = findViewById(R.id.ed_temps_arrivee_mc);
        heure_arrivee.setEnabled(false);
        nbr_personnes = findViewById(R.id.ed_nbr_personnes_mc);
        description = findViewById(R.id.ed_description_mc);
        ch1 = findViewById(R.id.bt_tps_dep1_mc);
        ch2 = findViewById(R.id.bt_tps_arr1_mc);
        modifier = findViewById(R.id.button_modifier);


        if(user != null)
            uid = user.getUid();

        final AnnonceCovoiture annonceCovoiture = (AnnonceCovoiture) this.getIntent().getExtras().getSerializable("annonceM");
        if(annonceCovoiture != null){
            annId = annonceCovoiture.getId_annonce();

            point_depart.setText(annonceCovoiture.getPoint_depart());
            point_arrivee.setText(annonceCovoiture.getPoint_arrivee());
            heure_depart.setText(annonceCovoiture.getHeure_depart());
            heure_arrivee.setText(annonceCovoiture.getHeure_arrivee());
            nbr_personnes.setText(String.valueOf(annonceCovoiture.getNbr_personnes()));
            description.setText(annonceCovoiture.getDescription());
        }


        View.OnClickListener showDateTimePicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View vv = v;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ModifierAnnonceCovoiture.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        hr = dayOfMonth+"/"+(monthOfYear+1)+"/"+year+" - ";

                        Calendar c = Calendar.getInstance();
                        hour = c.get(Calendar.HOUR_OF_DAY);
                        minute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(ModifierAnnonceCovoiture.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if(minute < 10)
                                    hr += hourOfDay+"h0"+minute;
                                else
                                    hr += hourOfDay+"h"+minute;

                                if (vv.getId() == R.id.bt_tps_dep1 )
                                    heure_depart.setText(hr);
                                else
                                    heure_arrivee.setText(hr);
                            }}, hour, minute, true);
                        timePickerDialog.show();
                    }}, year, month, day);
                datePickerDialog.show();
            }
        };

        ch1.setOnClickListener(showDateTimePicker);

        ch2.setOnClickListener(showDateTimePicker);

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String point_dep = point_depart.getText().toString().trim();
                String point_arr = point_arrivee.getText().toString().trim();
                String heure_dep = heure_depart.getText().toString().trim();
                String heure_arr = heure_arrivee.getText().toString().trim();
                String desc = description.getText().toString();
                String nper = nbr_personnes.getText().toString().trim();

                if (point_dep.matches("") || point_arr.matches("") || heure_dep.matches("") || heure_arr.matches("") || nper.matches(""))
                    Toast.makeText(ModifierAnnonceCovoiture.this, "Veulliez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
                else {
                    if (!nper.matches("^(10|[0-9])$")) {
                        Toast.makeText(ModifierAnnonceCovoiture.this, "Nombre de personnes invalide", Toast.LENGTH_SHORT).show();
                    } else {
                        int nbr_pers = Integer.parseInt(nper);

                        ref = FirebaseDatabase.getInstance().getReference().child("AnnonceCovoiture").child(annId);

                        annonceCovoiture.setPoint_depart(point_dep);
                        annonceCovoiture.setPoint_arrivee(point_arr);
                        annonceCovoiture.setHeure_depart(heure_dep);
                        annonceCovoiture.setHeure_arrivee(heure_arr);
                        annonceCovoiture.setNbr_personnes(nbr_pers);
                        annonceCovoiture.setDescription(desc);
                        annonceCovoiture.setUtilisateur_id(uid);

                        ref.setValue(annonceCovoiture);

                        Toast.makeText(ModifierAnnonceCovoiture.this, "Annonce modifiée !", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ModifierAnnonceCovoiture.this, MesAnnoncesCovoiture.class));
                        finish();
                    }
                }
            }
        });
    }
}
