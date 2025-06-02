package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Groupe;

import java.util.List;

public interface GroupeDao {

    List<Groupe> findAll();
    void insertFanfaronGroupe(String nomFanfaron, int idGroupe);
    void deleteFanfaronGroupes(String nomFanfaron);
    List<Integer> findGroupeIdsByFanfaron(String nomFanfaron);
    void insertGroupe(Groupe groupe);
    void updateGroupe(int idGroupe, Groupe groupe);
    void deleteGroupe(int idGroupe);
    void deleteAppartenanceByGroupeId(int idGroupe);

}
