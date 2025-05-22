package com.example.fanfarehub.controller;

import com.example.fanfarehub.dao.ContrainteAlimentaireDao;
import com.example.fanfarehub.model.ContrainteAlimentaire;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class ContrainteAlimentaireBean {

    @Inject
    private ContrainteAlimentaireDao contrainteAlimentaireDao;

    private List<ContrainteAlimentaire> contraintes;

    public List<ContrainteAlimentaire> getContraintes() {
        return contraintes;
    }

    @PostConstruct
    public void init() {
        this.contraintes = contrainteAlimentaireDao.findAll();
    }
}