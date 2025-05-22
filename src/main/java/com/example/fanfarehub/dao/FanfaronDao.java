package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Fanfaron;
import java.util.Optional;
import java.util.List;

public interface FanfaronDao {
    Optional<Fanfaron> findByNomFanfaron(String nomFanfaron);

    Optional<Fanfaron> findByEmail(String email);

    void create(Fanfaron fanfaron);

    void update(Fanfaron fanfaron);

    Optional<Fanfaron> findById(String id);

    List<Fanfaron> findAll();

    void delete(Fanfaron fanfaron);
}