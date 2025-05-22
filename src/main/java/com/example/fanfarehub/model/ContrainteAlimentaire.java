package com.example.fanfarehub.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "contrainte_alimentaire")
public class ContrainteAlimentaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrainte_alimentaire")
    private Integer idContrainteAlimentaire;

    @Column(name = "libelle_contrainte", unique = true, nullable = false)
    private String libelleContrainte;

    @OneToMany(mappedBy = "contraintesAlimentaires")
    private List<Fanfaron> fanfarons;

    // Constructeurs, getters et setters
    public ContrainteAlimentaire() {}

    public Integer getIdContrainteAlimentaire() { return idContrainteAlimentaire; }
    public void setIdContrainteAlimentaire(Integer idContrainteAlimentaire) { this.idContrainteAlimentaire = idContrainteAlimentaire; }
    public String getLibelleContrainte() { return libelleContrainte; }
    public void setLibelleContrainte(String libelleContrainte) { this.libelleContrainte = libelleContrainte; }
    public List<Fanfaron> getFanfarons() { return fanfarons; }
    public void setFanfarons(List<Fanfaron> fanfarons) { this.fanfarons = fanfarons; }
}