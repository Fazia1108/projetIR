package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.ContrainteAlimentaire;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ContrainteAlimentaireDaoImpl implements ContrainteAlimentaireDao {

    @PersistenceContext(unitName = "fanfareHubPU")
    private EntityManager em;

    @Override
    public Optional<ContrainteAlimentaire> findById(Integer id) {
        return Optional.ofNullable(em.find(ContrainteAlimentaire.class, id));
    }

    @Override
    public List<ContrainteAlimentaire> findAll() {
        return em.createQuery("SELECT c FROM ContrainteAlimentaire c", ContrainteAlimentaire.class).getResultList();
    }
}