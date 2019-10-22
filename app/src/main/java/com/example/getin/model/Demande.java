package com.example.getin.model;

public class Demande {
    private long utilisateur_id;
    private long annonce_id;
    private String etat;
    private String description;

    public Demande(long utilisateur_id, long annonce_id, String etat, String description) {
        this.utilisateur_id = utilisateur_id;
        this.annonce_id = annonce_id;
        this.etat = etat;
        this.description = description;
    }

    public Demande() {}

    public long getUtilisateur_id() {
        return utilisateur_id;
    }

    public long getAnnonce_id() {
        return annonce_id;
    }

    public String getEtat() {
        return etat;
    }

    public String getDescription() {
        return description;
    }

    public void setUtilisateur_id(long utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public void setAnnonce_id(long annonce_id) {
        this.annonce_id = annonce_id;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
