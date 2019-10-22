package com.example.getin.model;

public class Voiture {

    private long num_immatriculation;
    private int nbr_places;
    private String marque;
    private String type;
    private String assurance;

    public Voiture(long num_immatriculation, int nbr_places, String marque, String type, String assurance) {
        this.num_immatriculation = num_immatriculation;
        this.nbr_places = nbr_places;
        this.marque = marque;
        this.type = type;
        this.assurance = assurance;
    }

    public Voiture() {
    }

    public long getNum_immatriculation() {
        return num_immatriculation;
    }

    public int getNbr_places() {
        return nbr_places;
    }

    public String getMarque() {
        return marque;
    }

    public String getType() {
        return type;
    }

    public String getAssurance() {
        return assurance;
    }

    public void setNum_immatriculation(long num_immatriculation) {
        this.num_immatriculation = num_immatriculation;
    }

    public void setNbr_places(int nbr_places) {
        this.nbr_places = nbr_places;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAssurance(String assurance) {
        this.assurance = assurance;
    }

    //TEEEEES
    //AYA TEST
    //Hayat
}
