package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Evenement;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Evenement> findById(int id) {
        String sql = "SELECT * FROM evenement WHERE id_evenement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public List<Evenement> findAll() {
        List<Evenement> evenements = new ArrayList<>();
        String sql = "SELECT * FROM evenement";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                evenements.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenements;
    }

    private Evenement map(ResultSet rs) throws SQLException {
        Evenement f = new Evenement();
        f.setIdEvenement(rs.getInt("id_evenement"));
        f.setNomEvenement(rs.getString("nom_evenement"));
        f.setHorodatage(rs.getTimestamp("horodatage"));
        f.setDuree(parsePostgresInterval(rs.getString("duree"))); // ✅ nouvelle méthode ici
        f.setLieu(rs.getString("lieu"));
        f.setDescription(rs.getString("description"));
        f.setIdTypeEvenement(rs.getInt("id_type_evenement"));
        f.setNomFanfaronCreateur(rs.getString("nom_fanfaron_createur"));

        return f;
    }

    private Duration parsePostgresInterval(String interval) {
        // Format attendu : "HH:mm:ss"
        String[] parts = interval.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);

        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }

    @Override
    public void update(Evenement evenement) {
        String sql = "UPDATE Evenement SET nom_evenement = ?, horodatage = ?, duree = CAST(? AS interval), lieu = ?, description = ?, id_type_evenement = ? WHERE id_evenement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, evenement.getNomEvenement());
            stmt.setTimestamp(2, evenement.getHorodatage());
            stmt.setString(3, toPostgresInterval(evenement.getDuree()));
            stmt.setString(4, evenement.getLieu());
            stmt.setString(5, evenement.getDescription());
            stmt.setInt(6, evenement.getIdTypeEvenement());
            stmt.setInt(7, evenement.getIdEvenement());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de l'événement", e);
        }
    }

}
