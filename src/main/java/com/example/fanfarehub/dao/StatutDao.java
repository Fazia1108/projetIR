package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Statut;
import java.util.List;
import java.util.Optional;

public interface StatutDao {
    List<Statut> findAll();
    Optional<Statut> findById(int id);
}
