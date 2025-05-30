package com.example.fanfarehub.model;

public class Statut {
    private int id_Statut;
    private String libelle_statut;
    private String couleur;

    public Statut(int idStatut, String libelleStatut, String couleur) {
        this.id_Statut = idStatut;
        this.libelle_statut = libelleStatut;
        this.couleur = couleur;
    }

    public int getIdStatut() { return id_Statut; }
    public void setIdStatut(int idStatut) { this.id_Statut = idStatut; }
    public String getLibelleStatut() { return libelle_statut; }
    public void setLibelleStatut(String libelle_statut) { this.libelle_statut = libelle_statut; }
    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
}
