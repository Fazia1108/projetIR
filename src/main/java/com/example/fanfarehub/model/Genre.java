package com.example.fanfarehub.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Genre")
public class Genre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genre")
    private Integer idGenre;

    @Column(name = "libelle_genre", unique = true, nullable = false)
    private String libelleGenre;

    @OneToMany(mappedBy = "genre")
    private List<Fanfaron> fanfarons;

    // Constructeurs, getters et setters
    public Genre() {}

    public Integer getIdGenre() { return idGenre; }
    public void setIdGenre(Integer idGenre) { this.idGenre = idGenre; }
    public String getLibelleGenre() { return libelleGenre; }
    public void setLibelleGenre(String libelleGenre) { this.libelleGenre = libelleGenre; }
    public List<Fanfaron> getFanfarons() { return fanfarons; }
    public void setFanfarons(List<Fanfaron> fanfarons) { this.fanfarons = fanfarons; }
}