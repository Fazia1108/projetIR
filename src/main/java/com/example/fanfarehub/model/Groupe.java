package com.example.fanfarehub.model;

public class Groupe {
    private int idGroupe;
    private String nomGroupe;

    public Groupe() {}

    //Getters et Setters
    public Groupe(int idGroupe, String nomGroupe) {
        this.idGroupe = idGroupe;
        this.nomGroupe = nomGroupe;
    }

    public int getIdGroupe() { return idGroupe; }
    public String getNomGroupe() { return nomGroupe; }

    public void setIdGroupe(int idGroupe) { this.idGroupe = idGroupe; }
    public void setNomGroupe(String nomGroupe) { this.nomGroupe = nomGroupe; }
}
