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

public class ModiferAnnonceCovoitureur extends AppCompatActivity {

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
        setContentView(R.layout.activity_modifer_annonce_covoitureur);
        point_depart = findViewById(R.id.ed_point_depart_m);
        point_arrivee = findViewById(R.id.ed_point_arrivee_m);
        heure_depart = findViewById(R.id.ed_temps_depart_m);
        heure_depart.setEnabled(false);
        heure_arrivee = findViewById(R.id.ed_temps_arrivee_m);
        heure_arrivee.setEnabled(false);
        nbr_places = findViewById(R.id.ed_nbr_places_m);
        prix = findViewById(R.id.ed_prix_m);
        description = findViewById(R.id.ed_description_m);
        pick_dep = findViewById(R.id.bt_tps_dep_m);
        pick_arr = findViewById(R.id.bt_tps_arr_m);
        suivant = findViewById(R.id.button_suivant_m);

        if(user != null)
            uid = user.getUid();

        Bundle b=this.getIntent().getExtras();
        annonceCovoitureur= (AnnonceCovoitureur) b.getSerializable("annonce");

        point_depart.setText(annonceCovoitureur.getPoint_depart());
        point_arrivee.setText(annonceCovoitureur.getPoint_arrivee());
        heure_depart.setText(annonceCovoitureur.getHeure_depart());
        heure_arrivee.setText(annonceCovoitureur.getHeure_arrivee());
        nbr_places.setText(String.valueOf(annonceCovoitureur.getNbr_personnes()));
        prix.setText(String.valueOf(annonceCovoitureur.getPrix()));
        description.setText(annonceCovoitureur.getDescription());

        View.OnClickListener showDateTimePicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View vv = v;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ModiferAnnonceCovoitureur.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        hr = dayOfMonth+"/"+(monthOfYear+1)+"/"+year+" - ";

                        Calendar c = Calendar.getInstance();
                        hour = c.get(Calendar.HOUR_OF_DAY);
                        minute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(ModiferAnnonceCovoitureur.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if(minute < 10)
                                    hr += hourOfDay+"h0"+minute;
                                else
                                    hr += hourOfDay+"h"+minute;

                                if (vv.getId() == R.id.bt_tps_dep_m)
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
                    Toast.makeText(ModiferAnnonceCovoitureur.this, "Veulliez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
                else {
                    if (!pr.matches("^(\\d*\\.?\\d*)$"))
                        Toast.makeText(ModiferAnnonceCovoitureur.this, "Prix invalide", Toast.LENGTH_SHORT).show();
                    else {
                        if (!np.matches("^(10|[0-9])$"))
                            Toast.makeText(ModiferAnnonceCovoitureur.this, "Nombre de places invalide", Toast.LENGTH_SHORT).show();
                        else {
                            int nbr_pl = Integer.parseInt(np);
                            double pri = Double.parseDouble(pr);

                            annonceCovoitureur.setPoint_depart(point_dep);
                            annonceCovoitureur.setPoint_arrivee(point_arr);
                            annonceCovoitureur.setHeure_depart(heure_dep);
                            annonceCovoitureur.setHeure_arrivee(heure_arr);
                            annonceCovoitureur.setNbr_personnes(nbr_pl);
                            annonceCovoitureur.setPrix(pri);
                            annonceCovoitureur.setDescription(desc);
                            annonceCovoitureur.setUtilisateur_id(uid);

                            Intent intent = new Intent(ModiferAnnonceCovoitureur.this, ModifierVoiture.class);
                            intent.putExtra("annonceM", annonceCovoitureur);

                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}
