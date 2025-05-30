package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Pupitre;

import java.util.List;

public interface PupitreDao {
    List<Pupitre> findAll();
    void insertFanfaronPupitre(String nomFanfaron, int idPupitre);
    void deleteFanfaronPupitres(String nomFanfaron); // pour réinitialiser les choix
    List<Integer> findPupitreIdsByFanfaron(String nomFanfaron);

    List<Pupitre> findByFanfaron(String nomFanfaron);
}
