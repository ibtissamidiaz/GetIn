package com.example.getin.controller.covoitureur;

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
import com.example.getin.model.AnnonceCovoitureur;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class AnnonceCovoitureurForm extends AppCompatActivity {

    EditText point_depart,point_arrivee,heure_depart,heure_arrivee,nbr_places,prix,description;
    AnnonceCovoitureur annonceCovoitureur;
    Button pick_dep,pick_arr,suivant;

    int year,month,day,hour,minute;
    String hr;

    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user= mFirebaseAuth.getCurrentUser();
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_covoitureur_form);
        point_depart = findViewById(R.id.ed_point_depart1);
        point_arrivee = findViewById(R.id.ed_point_arrivee1);
        heure_depart = findViewById(R.id.ed_temps_depart1);
         heure_depart.setEnabled(false);
        heure_arrivee = findViewById(R.id.ed_temps_arrivee1);
         heure_arrivee.setEnabled(false);
        nbr_places = findViewById(R.id.ed_nbr_places1);
        prix = findViewById(R.id.ed_prix1);
        description = findViewById(R.id.ed_description1);
        pick_dep = findViewById(R.id.bt_tps_dep1);
        pick_arr = findViewById(R.id.bt_tps_arr1);
        suivant = findViewById(R.id.button_suivant1);

        if(user != null)
            uid = user.getUid();

        View.OnClickListener showDateTimePicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View vv = v;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AnnonceCovoitureurForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                             hr = dayOfMonth+"/"+(monthOfYear+1)+"/"+year+" - ";

                             Calendar c = Calendar.getInstance();
                             hour = c.get(Calendar.HOUR_OF_DAY);
                             minute = c.get(Calendar.MINUTE);

                             TimePickerDialog timePickerDialog = new TimePickerDialog(AnnonceCovoitureurForm.this, new TimePickerDialog.OnTimeSetListener() {
                                 @Override
                                 public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                     if(minute < 10)
                                         hr += hourOfDay+"h0"+minute;
                                     else
                                         hr += hourOfDay+"h"+minute;

                                     if (vv.getId() == R.id.bt_tps_dep1)
                                         heure_depart.setText(hr);
                                     else
                                         heure_arrivee.setText(hr);
                                 }}, hour, minute, true);
                             timePickerDialog.show();
                    }}, year, month, day);
                datePickerDialog.show();
            }
        };

        pick_dep.setOnClickListener(showDateTimePicker);

        pick_arr.setOnClickListener(showDateTimePicker);

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String point_dep = point_depart.getText().toString().trim();
                String point_arr = point_arrivee.getText().toString().trim();
                String heure_dep = heure_depart.getText().toString().trim();
                String heure_arr = heure_arrivee.getText().toString().trim();
                String desc = description.getText().toString();
                String np = nbr_places.getText().toString().trim();
                String pr = prix.getText().toString().trim();

                if (point_dep.matches("") || point_arr.matches("") || heure_dep.matches("") || heure_arr.matches("") || np.matches("") || pr.matches(""))
                    Toast.makeText(AnnonceCovoitureurForm.this, "Veulliez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
                else{
                    if(! pr.matches("^(\\d*\\.?\\d*)$"))
                        Toast.makeText(AnnonceCovoitureurForm.this, "Prix invalide", Toast.LENGTH_SHORT).show();
                    else{
                        if(! np.matches("^(10|[0-9])$"))
                            Toast.makeText(AnnonceCovoitureurForm.this, "Nombre de places invalide", Toast.LENGTH_SHORT).show();
                        else{
                            int nbr_pl = Integer.parseInt(np);
                            double pri = Double.parseDouble(pr);

                            annonceCovoitureur = new AnnonceCovoitureur(heure_dep,heure_arr,point_dep,point_arr,desc,uid,nbr_pl,pri);


                            Intent intent = new Intent(AnnonceCovoitureurForm.this, InfoVoiture.class);
                            intent.putExtra("annonce",annonceCovoitureur);


                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });

    }

}
