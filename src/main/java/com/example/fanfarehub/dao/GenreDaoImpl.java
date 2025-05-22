package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Genre;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext(unitName = "fanfareHubPU")
    private EntityManager em;

    @Override
    public Optional<Genre> findById(Integer id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
    }
}