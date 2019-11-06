package com.example.getin.model;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private String nom;
    private String prenom;
    private int age;
    private String profession;
    private String telephone;
    private String CIN;
    private String sexe;
    private int nbrVotes;
    private int note;

    public Utilisateur(String nom, String prenom, int age, String profession, String telephone, String CIN) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.profession = profession;
        this.telephone = telephone;
        this.CIN = CIN;
        this.nbrVotes = 0;
        this.note = 0;
    }

    public Utilisateur() {}

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

    public int getnbrVotes() {
        return nbrVotes;
    }

    public int getnote() {
        return note;
    }

    public String getSexe() {
        return sexe;
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

    public void setnbrVotes(int nbrVotes) {
        nbrVotes = nbrVotes;
    }

    public void setnote(int note) {
        note = note;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
}
