package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.ContrainteAlimentaire;
import java.util.Optional;
import java.util.List;

public interface ContrainteAlimentaireDao {
    Optional<ContrainteAlimentaire> findById(Integer id);

    List<ContrainteAlimentaire> findAll();
    boolean create(ContrainteAlimentaire contrainte);
    boolean update(ContrainteAlimentaire contrainte);
    boolean delete(int id);
}