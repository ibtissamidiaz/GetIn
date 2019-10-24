package com.example.getin.model;

import java.io.Serializable;

public class Voiture implements Serializable {

    private String num_immatriculation;
    private int nbr_places;
    private String nom_voiture;

    public Voiture(String num_immatriculation,  String nom_voiture, int nbr_places) {
        this.num_immatriculation = num_immatriculation;
        this.nbr_places = nbr_places;
        this.nom_voiture = nom_voiture;
    }

    public Voiture() {
    }

    public String getNum_immatriculation() {
        return num_immatriculation;
    }

    public int getNbr_places() {
        return nbr_places;
    }

    public String getMarque() {
        return nom_voiture;
    }

    public void setNum_immatriculation(String num_immatriculation) {
        this.num_immatriculation = num_immatriculation;
    }

    public void setNbr_places(int nbr_places) {
        this.nbr_places = nbr_places;
    }

    public void setMarque(String nom_voiture) {
        this.nom_voiture = nom_voiture;
    }

}
