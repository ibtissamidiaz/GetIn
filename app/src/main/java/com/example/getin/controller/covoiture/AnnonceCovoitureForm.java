package com.example.getin.controller.covoiture;

import androidx.annotation.NonNull;
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
import com.example.getin.controller.MainActivity;
import com.example.getin.model.AnnonceCovoiture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AnnonceCovoitureForm extends AppCompatActivity {

    EditText point_depart,point_arrivee,heure_depart,heure_arrivee,nbr_personnes,description;
    Button ch1,ch2,ajouter;

    DatabaseReference ref;
    AnnonceCovoiture annonceCovoiture;

    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user= mFirebaseAuth.getCurrentUser();
    String uid;


    int year,month,day,hour,minute;
    String hr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_covoiture_form);
        point_depart = findViewById(R.id.ed_point_depart);
        point_arrivee = findViewById(R.id.ed_point_arrivee);
        heure_depart = findViewById(R.id.ed_temps_depart);
        heure_depart.setEnabled(false);
        heure_arrivee = findViewById(R.id.ed_temps_arrivee);
        heure_arrivee.setEnabled(false);
        nbr_personnes = findViewById(R.id.ed_nbr_personnes);
        description = findViewById(R.id.ed_description);
        ch1 = findViewById(R.id.bt_tps_dep1);
        ch2 = findViewById(R.id.bt_tps_arr1);
        ajouter = findViewById(R.id.button_ajouter);

        if(user != null)
           uid = user.getUid();
        else
            uid = "1";

        ref = FirebaseDatabase.getInstance().getReference().child("AnnonceCovoiture");

        View.OnClickListener showDateTimePicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View vv = v;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AnnonceCovoitureForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        hr = dayOfMonth+"/"+(monthOfYear+1)+"/"+year+" - ";

                        Calendar c = Calendar.getInstance();
                        hour = c.get(Calendar.HOUR_OF_DAY);
                        minute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AnnonceCovoitureForm.this, new TimePickerDialog.OnTimeSetListener() {
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

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String point_dep = point_depart.getText().toString().trim();
                String point_arr = point_arrivee.getText().toString().trim();
                String heure_dep = heure_depart.getText().toString().trim();
                String heure_arr = heure_arrivee.getText().toString().trim();
                String desc = description.getText().toString();
                String nper = nbr_personnes.getText().toString().trim();

                if (point_dep.matches("") || point_arr.matches("") || heure_dep.matches("") || heure_arr.matches("") || nper.matches(""))
                    Toast.makeText(AnnonceCovoitureForm.this, "Veulliez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
                else{
                    if(!nper.matches("^(10|[0-9])$")){
                        Toast.makeText(AnnonceCovoitureForm.this, "Nombre de personnes invalide", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int nbr_pers = Integer.parseInt(nper);

                        annonceCovoiture = new AnnonceCovoiture(heure_dep,heure_arr,point_dep,point_arr,desc,uid,nbr_pers);

                        String genId = getAlphaNumericString(5);

                        ref.child(genId).setValue(annonceCovoiture);
                        Toast.makeText(AnnonceCovoitureForm.this,"Annonce ajoutée !",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AnnonceCovoitureForm.this, MesAnnoncesCovoiture.class));
                        finish();
                    }
                }
            }
        });
    }

    static String getAlphaNumericString(int n)
    {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
