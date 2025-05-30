package com.example.fanfarehub.model;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class Evenement {
    private int idEvenement;
    private String nomEvenement;
    private Timestamp horodatage;
    private Duration duree;
    private String lieu;
    private String description;
    private int idTypeEvenement;
    private String nomFanfaronCreateur;

    // Getters et Setters

    public int getIdEvenement() {
        return idEvenement;
    }
    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }
    public String getNomEvenement() {
        return nomEvenement;
    }
    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }
    public Timestamp getHorodatage() {
        return horodatage;
    }
    public void setHorodatage(Timestamp horodatage) {
        this.horodatage = horodatage;
    }
    public Duration getDuree() {
        return duree;
    }
    public void setDuree(Duration duree) {
        this.duree = duree;
    }
    public String getLieu() {
        return lieu;
    }
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getIdTypeEvenement() {
        return idTypeEvenement;
    }
    public void setIdTypeEvenement(int idTypeEvenement) {
        this.idTypeEvenement = idTypeEvenement;
    }
    public String getNomFanfaronCreateur() {
        return nomFanfaronCreateur;
    }
    public void setNomFanfaronCreateur(String nomFanfaronCreateur) {
        this.nomFanfaronCreateur = nomFanfaronCreateur;
    }

    public long getHeures() {
        return duree.toHours();
    }

    public long getMinutes() {
        return duree.toMinutes() % 60;
    }
}
