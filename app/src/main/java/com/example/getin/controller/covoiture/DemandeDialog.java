package com.example.getin.controller.covoiture;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.getin.R;
import com.example.getin.model.AnnonceCovoitureur;
import com.example.getin.model.Demande;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.getin.controller.covoiture.AnnonceCovoitureForm.getAlphaNumericString;

public class DemandeDialog extends AppCompatDialogFragment {
    private String id;
    private  String uid;

    private EditText editTextdescription;
    private DemandeDialogListener listener;
    private Demande demande;
    private  DatabaseReference ref;


    public DemandeDialog(String id, String uid) {
        this.id = id;
        this.uid = uid;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view)
                .setTitle("Description")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"Demande annulée !",Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ref = FirebaseDatabase.getInstance().getReference("DemandeCovoiture");

                                String description = editTextdescription.getText().toString();
                                demande = new Demande();
                                demande.setAnnonce_id(id);
                                demande.setUtilisateur_id(uid);
                                demande.setDescription(description);
                                demande.setEtat("En attente");
                                String genId = getAlphaNumericString(5);
                                ref.child(genId).setValue(demande);
                                Toast.makeText(getContext(),"Demande envoyée !",Toast.LENGTH_SHORT).show();
                    }
                });
        editTextdescription = view.findViewById(R.id.description);
         return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DemandeDialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + "il faut implementer DemanedDialogListener");
        }
    }

    public interface DemandeDialogListener{
        /*void applyTexts(String description, int flag);*/
    }
}
