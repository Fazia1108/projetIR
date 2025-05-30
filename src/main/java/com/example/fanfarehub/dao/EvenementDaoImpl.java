package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Evenement;

import java.sql.*;

public class EvenementDaoImpl implements EvenementDao {
    private final Connection connection;

    public EvenementDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Evenement evenement) {
        String sql = "INSERT INTO evenement (nom_evenement, horodatage, duree, lieu, description, id_type_evenement, nom_fanfaron_createur) " +
                "VALUES (?, ?, CAST(? AS interval), ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, evenement.getNomEvenement());
            stmt.setTimestamp(2, evenement.getHorodatage());
            stmt.setString(3, toPostgresInterval(evenement.getDuree())); // <-- ici
            stmt.setString(4, evenement.getLieu());
            stmt.setString(5, evenement.getDescription());
            stmt.setInt(6, evenement.getIdTypeEvenement());
            stmt.setString(7, evenement.getNomFanfaronCreateur());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Convertit une Duration en format HH:mm:ss
    private String toPostgresInterval(java.time.Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        long hours = absSeconds / 3600;
        long minutes = (absSeconds % 3600) / 60;
        long secs = absSeconds % 60;
        return String.format("%d:%02d:%02d", hours, minutes, secs); // Exemple : 1:30:00
    }
}
