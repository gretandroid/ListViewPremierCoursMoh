package com.example.listviewpremiercours.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personne implements Serializable {

    private static int counter = 0;
    private static int idGenerator() {
        return ++counter;
    }
    private int id;
    private String nom, prenom;

    public Personne(String nom, String prenom) {
        this.id = idGenerator();
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @NonNull
    @Override
    public String toString() {
        //noinspection StringBufferReplaceableByString
        return new StringBuilder("Personne{")
                .append("id=").append(id)
                .append(", nom='").append(nom).append('\'')
                .append(", prenom='").append(prenom).append('\'')
                .append('}').toString();
    }
}
