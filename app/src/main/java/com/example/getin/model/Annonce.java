package com.example.getin.model;

import java.io.Serializable;

public class Annonce implements Serializable {

    private String id_annonce;
    private String heure_depart;
    private String heure_arrivee;
    private String point_depart;
    private String point_arrivee;
    private String description;
    private String utilisateur_id;


    public Annonce() {}

    public Annonce(String heure_depart, String heure_arrivee, String point_depart, String point_arrivee) {
        this.heure_depart = heure_depart;
        this.heure_arrivee = heure_arrivee;
        this.point_depart = point_depart;
        this.point_arrivee = point_arrivee;
    }

    public Annonce(String heure_depart, String heure_arrivee, String point_depart, String point_arrivee, String description, String utilisateur_id) {
        this.heure_depart = heure_depart;
        this.heure_arrivee = heure_arrivee;
        this.point_depart = point_depart;
        this.point_arrivee = point_arrivee;
        this.description = description;
        this.utilisateur_id = utilisateur_id;
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

    public String getUtilisateur_id() {
        return utilisateur_id;
    }

    public String getId_annonce() {
        return id_annonce;
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

    public void setUtilisateur_id(String utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public void setId_annonce(String id_annonce) {
        this.id_annonce = id_annonce;
    }
}
