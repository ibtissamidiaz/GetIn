package com.example.getin.model;

public class Annonce {
    private long annonce_id;
    private String heure_depart;
    private String heure_arrivee;
    private String point_depart;
    private String point_arrivee;
    private String description;
    private long utilisateur_id;


    public Annonce() {}

    public Annonce(long annonce_id, String heure_depart, String heure_arrivee, String point_depart, String point_arrivee, String description, long utilisateur_id) {
        this.annonce_id = annonce_id;
        this.heure_depart = heure_depart;
        this.heure_arrivee = heure_arrivee;
        this.point_depart = point_depart;
        this.point_arrivee = point_arrivee;
        this.description = description;
        this.utilisateur_id = utilisateur_id;
    }

    public long getAnnonce_id() {
        return annonce_id;
    }

    public String getHeure_depart() {
        return heure_depart;
    }

    public String getHeure_arrivee() {
        return heure_arrivee;
    }

    public String getPoint_depart() {
        return point_depart;
    }

    public String getPoint_arrivee() {
        return point_arrivee;
    }

    public String getDescription() {
        return description;
    }

    public long getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setAnnonce_id(long annonce_id) {
        this.annonce_id = annonce_id;
    }

    public void setHeure_depart(String heure_depart) {
        this.heure_depart = heure_depart;
    }

    public void setHeure_arrivee(String heure_arrivee) {
        this.heure_arrivee = heure_arrivee;
    }

    public void setPoint_depart(String point_depart) {
        this.point_depart = point_depart;
    }

    public void setPoint_arrivee(String point_arrivee) {
        this.point_arrivee = point_arrivee;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUtilisateur_id(long utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }
}