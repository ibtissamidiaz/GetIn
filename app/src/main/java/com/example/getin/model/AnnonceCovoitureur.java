package com.example.getin.model;

public class AnnonceCovoitureur extends Annonce {

    private int nbr_personnes;
    private double prix;

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

    public void setNbr_personnes(int nbr_personnes) {
        this.nbr_personnes = nbr_personnes;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
