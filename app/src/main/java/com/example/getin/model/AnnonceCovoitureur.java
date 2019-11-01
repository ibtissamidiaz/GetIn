package com.example.getin.model;

import java.io.Serializable;

public class AnnonceCovoitureur extends Annonce implements Serializable {

    private int nbr_personnes;
    private Voiture voiture;
    private double prix;

    public AnnonceCovoitureur(String heure_depart, String heure_arrivee, String point_depart, String point_arrivee, String description, long utilisateur_id, int nbr_personnes, double prix, Voiture voiture) {
        super(heure_depart, heure_arrivee, point_depart, point_arrivee, description, utilisateur_id);
        this.nbr_personnes = nbr_personnes;
        this.prix = prix;
        this.voiture = voiture;
    }

    public AnnonceCovoitureur(String heure_depart, String heure_arrivee, String point_depart, String point_arrivee, String description, long utilisateur_id, int nbr_personnes, double prix) {
        super(heure_depart, heure_arrivee, point_depart, point_arrivee, description, utilisateur_id);
        this.nbr_personnes = nbr_personnes;
        this.prix = prix;
    }

    public AnnonceCovoitureur() {
        super();
    }

    public int getNbr_personnes() {
        return nbr_personnes;
    }

    public double getPrix() {
        return prix;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setNbr_personnes(int nbr_personnes) {
        this.nbr_personnes = nbr_personnes;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }
}
