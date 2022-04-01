package com.example.listviewpremiercours.controller;

import com.example.listviewpremiercours.model.Personne;

import java.util.ArrayList;
import java.util.List;

public class DaoPersonne {
    //On définit un ArrayList pour stocker les personnes
    static List<Personne> list = new ArrayList<>();

    public static void addPersonne(Personne personne) {
        list.add(personne);
    }

    public static void delPersonne(int index) {
        list.remove(index);
    }

    public static List<Personne> getAllPersonnes() {
        return list;
    }

    public static void modfify(int index,Personne p){
        list.set(index, p);
    }
}
