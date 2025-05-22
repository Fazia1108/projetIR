package com.example.fanfarehub.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "fanfaron")
public class Fanfaron implements Serializable {
    @Id
    @Column(name = "nom_fanfaron")
    private String nomFanfaron;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "mdp", nullable = false)
    private String motDePasse;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "nom", nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_genre")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "id_contrainte_alimentaire")
    private ContrainteAlimentaire contraintesAlimentaires;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "derniere_connexion")
    private LocalDateTime derniereConnexion;

    // Constructeurs (avec et sans arguments)
    public Fanfaron() {
        this.dateCreation = LocalDateTime.now();
    }

    public Fanfaron(String nomFanfaron, String email, String motDePasse, String prenom, String nom, Genre genre, ContrainteAlimentaire contraintesAlimentaires) {
        this.nomFanfaron = nomFanfaron;
        this.email = email;
        this.motDePasse = motDePasse;
        this.prenom = prenom;
        this.nom = nom;
        this.genre = genre;
        this.contraintesAlimentaires = contraintesAlimentaires;
        this.dateCreation = LocalDateTime.now();
    }

    // Getters et setters pour tous les champs
    public String getNomFanfaron() { return nomFanfaron; }
    public void setNomFanfaron(String nomFanfaron) { this.nomFanfaron = nomFanfaron; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }
    public ContrainteAlimentaire getContraintesAlimentaires() { return contraintesAlimentaires; }
    public void setContraintesAlimentaires(ContrainteAlimentaire contraintesAlimentaires) { this.contraintesAlimentaires = contraintesAlimentaires; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public LocalDateTime getDerniereConnexion() { return derniereConnexion; }
    public void setDerniereConnexion(LocalDateTime derniereConnexion) { this.derniereConnexion = derniereConnexion; }
}