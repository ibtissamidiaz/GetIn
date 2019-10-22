package com.example.getin.model;

public class Utilisateur {
    private long utilisateur_id;
    private String nom;
    private String prenom;
    private int age;
    private String profession;
    private String telephone;
    private String CIN;
    private Voiture voiture;

    public Utilisateur(long utilisateur_id, String nom, String prenom, int age, String profession, String telephone, String CIN, Voiture voiture) {
        this.utilisateur_id = utilisateur_id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.profession = profession;
        this.telephone = telephone;
        this.CIN = CIN;
        this.voiture = voiture;
    }

    public Utilisateur() {}

    public long getUtilisateur_id() {
        return utilisateur_id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public String getProfession() {
        return profession;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCIN() {
        return CIN;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setUtilisateur_id(long utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }
}
