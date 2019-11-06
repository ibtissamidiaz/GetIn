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

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.getin.R;
import com.example.getin.model.Demande;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.getin.controller.covoiture.AnnonceCovoitureForm.getAlphaNumericString;

public class DemandeDialog extends AppCompatDialogFragment {
    private String id;
    private  String uid;

    private EditText editTextdescription;
    private DemandeDialogListener listener;
    private Demande demande;
    private FirebaseDatabase data;
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

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data=FirebaseDatabase.getInstance();
                        ref=data.getReference("DemandeCovoiture");
                        String description = editTextdescription.getText().toString();
                        demande = new Demande();
                        demande.setAnnonce_id(id);
                        demande.setUtilisateur_id(uid);
                        demande.setDescription(description);
                        demande.setEtat("En attente");
                        String genId = getAlphaNumericString(5);
                        ref.child(genId).setValue(demande);


                        /*listener.applyTexts(description,1);*/
                        //Ajouter toast
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
