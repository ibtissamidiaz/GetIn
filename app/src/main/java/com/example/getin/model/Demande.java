package com.example.getin.model;

import java.io.Serializable;

public class Demande implements Serializable {
    private String id;
    private String utilisateur_id;
    private String annonce_id;
    private String etat;
    private String description;

    public Demande(String utilisateur_id, String annonce_id, String etat, String description) {
        this.utilisateur_id = utilisateur_id;
        this.annonce_id = annonce_id;
        this.etat = etat;
        this.description = description;
    }

    public Demande() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtilisateur_id() {
        return utilisateur_id;
    }

    public String getAnnonce_id() {
        return annonce_id;
    }

    public String getEtat() {
        return etat;
    }

    public String getDescription() {
        return description;
    }

    public void setUtilisateur_id(String utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public void setAnnonce_id(String annonce_id) {
        this.annonce_id = annonce_id;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
