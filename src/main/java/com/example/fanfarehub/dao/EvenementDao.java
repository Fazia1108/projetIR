package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Evenement;

import java.util.List;
import java.util.Optional;

public interface EvenementDao {
    public void insert (Evenement evenement);
    public List<Evenement> findAll();

    Optional<Evenement> findById(int idEvenement);
}
