package com.example.fanfarehub.model.dto;

public class InscriptionDTO {
    private String nomFanfaron;
    private String nomInstrument;
    private String libelleStatut;
    private String couleur;

    // Getter et Setter pour nomFanfaron
    public String getNomFanfaron() {
        return nomFanfaron;
    }

    public void setNomFanfaron(String nomFanfaron) {
        this.nomFanfaron = nomFanfaron;
    }

    // Getter et Setter pour nomInstrument
    public String getNomInstrument() {
        return nomInstrument;
    }

    public void setNomInstrument(String nomInstrument) {
        this.nomInstrument = nomInstrument;
    }

    // Getter et Setter pour libelleStatut
    public String getLibelleStatut() {
        return libelleStatut;
    }

    public void setLibelleStatut(String libelleStatut) {
        this.libelleStatut = libelleStatut;
    }

    // Getter et Setter pour couleur
    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
