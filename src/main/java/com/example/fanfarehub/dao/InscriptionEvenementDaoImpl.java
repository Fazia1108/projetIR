package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.dto.InscriptionDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionEvenementDaoImpl implements InscriptionEvenementDao {
    private final Connection connection;

    public InscriptionEvenementDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void upsert(String nomFanfaron, int idEvenement, int idPupitre, int idStatut) {
        String deleteSql = "DELETE FROM inscription_evenement WHERE nom_fanfaron = ? AND id_evenement = ?";
        String insertSql = "INSERT INTO inscription_evenement (nom_fanfaron, id_evenement, id_pupitre, id_statut) VALUES (?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
                deleteStmt.setString(1, nomFanfaron);
                deleteStmt.setInt(2, idEvenement);
                deleteStmt.executeUpdate();
            }

            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                insertStmt.setString(1, nomFanfaron);
                insertStmt.setInt(2, idEvenement);
                insertStmt.setInt(3, idPupitre); // remplacé idInstrument
                insertStmt.setInt(4, idStatut);
                insertStmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<InscriptionDTO> findByEvenementGrouped(int idEvenement) {
        List<InscriptionDTO> result = new ArrayList<>();
        String sql = "SELECT i.nom_fanfaron, p.nom_pupitre, s.libelle_statut, s.couleur " +
                "FROM inscription_evenement i " +
                "JOIN pupitre p ON i.id_pupitre = p.id_pupitre " +
                "JOIN statut_participation s ON i.id_statut = s.id_statut " +
                "WHERE i.id_evenement = ? " +
                "ORDER BY p.nom_pupitre, s.id_statut";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEvenement);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InscriptionDTO dto = new InscriptionDTO();
                    dto.setNomFanfaron(rs.getString("nom_fanfaron"));
                    dto.setNomInstrument(rs.getString("nom_pupitre")); // Adapter le nom si tu veux
                    dto.setLibelleStatut(rs.getString("libelle_statut"));
                    dto.setCouleur(rs.getString("couleur"));
                    result.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
