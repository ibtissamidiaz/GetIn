package com.example.getin.model;

public class AnnonceCovoiture extends Annonce {
    private int nbr_personnes;

    public AnnonceCovoiture(long annonce_id, String heure_depart, String heure_arrivee, String point_depart, String point_arrivee, String description, long utilisateur_id, int nbr_personnes) {
        super(annonce_id, heure_depart, heure_arrivee, point_depart, point_arrivee, description, utilisateur_id);
        this.nbr_personnes = nbr_personnes;
    }

    public AnnonceCovoiture() {
        super();
    }

    public int getNbr_personnes() {
        return nbr_personnes;
    }

    public void setNbr_personnes(int nbr_personnes) {
        this.nbr_personnes = nbr_personnes;
    }
}
