package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Statut;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StatutDaoImpl implements StatutDao {

    private final Connection connection;

    public StatutDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Statut> findAll() {
        List<Statut> statuts = new ArrayList<>();
        String sql = "SELECT id_statut, libelle_statut, couleur FROM statut_participation";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                statuts.add(new Statut(
                        rs.getInt("id_statut"),
                        rs.getString("libelle_statut"),
                        rs.getString("couleur")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // ou logger
        }

        return statuts;
    }

    @Override
    public Optional<Statut> findById(int id) {
        String sql = "SELECT id_statut, libelle_statut, couleur FROM statut_participation WHERE id_statut = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Statut(
                            rs.getInt("id_statut"),
                            rs.getString("libelle_statut"),
                            rs.getString("couleur")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // ou logger
        }

        return Optional.empty();
    }
}
