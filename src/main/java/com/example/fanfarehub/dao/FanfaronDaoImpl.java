package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Fanfaron;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FanfaronDaoImpl implements FanfaronDao {

    @PersistenceContext(unitName = "fanfareHubPU")
    private EntityManager em;

    @Override
    public Optional<Fanfaron> findByNomFanfaron(String nomFanfaron) {
        return em.createQuery("SELECT f FROM Fanfaron f WHERE f.nomFanfaron = :nom", Fanfaron.class)
                .setParameter("nom", nomFanfaron)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Fanfaron> findByEmail(String email) {
        return em.createQuery("SELECT f FROM Fanfaron f WHERE f.email = :email", Fanfaron.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    @Transactional
    public void create(Fanfaron fanfaron) {
        em.persist(fanfaron);
    }

    @Override
    @Transactional
    public void update(Fanfaron fanfaron) {
        em.merge(fanfaron);
    }

    @Override
    public Optional<Fanfaron> findById(String id) {
        return Optional.ofNullable(em.find(Fanfaron.class, id));
    }

    @Override
    public List<Fanfaron> findAll() {
        return em.createQuery("SELECT f FROM Fanfaron f", Fanfaron.class).getResultList();
    }

    @Override
    @Transactional
    public void delete(Fanfaron fanfaron) {
        em.remove(em.merge(fanfaron));
    }
}