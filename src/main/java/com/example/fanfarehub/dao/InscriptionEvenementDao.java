package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Evenement;
import com.example.fanfarehub.model.dto.InscriptionDTO;

import java.util.List;

public interface InscriptionEvenementDao {
    public void upsert(String nomFanfaron, int idEvenement, int idInstrument, int idStatut);
    public List<InscriptionDTO> findByEvenementGrouped(int idEvenement);
    public void deleteByEvenement(int idEvenement);
    public boolean existeInscription(int idEvenement, String nomFanfaron);


}
