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
            stmt.setString(3, toPostgresInterval(evenement.getDuree()));
            stmt.setString(4, evenement.getLieu());
            stmt.setString(5, evenement.getDescription());
            stmt.setInt(6, evenement.getIdTypeEvenement());
            stmt.setString(7, evenement.getNomFanfaronCreateur());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'événement : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'insertion de l'événement", e);
        }
    }

    private String toPostgresInterval(java.time.Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        long hours = absSeconds / 3600;
        long minutes = (absSeconds % 3600) / 60;
        long secs = absSeconds % 60;
        return String.format("%d:%02d:%02d", hours, minutes, secs);
    }

    @Override
    public Optional<Evenement> findById(int id) {
        // La requête SQL inclut la jointure avec la table type_evenement et sélectionne libelle_type
        String sql = "SELECT e.*, te.libelle_type FROM evenement e " +
                "JOIN type_evenement te ON e.id_type_evenement = te.id_type_evenement " +
                "WHERE e.id_evenement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'événement par ID : " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Evenement> findAll() {
        List<Evenement> evenements = new ArrayList<>();
        // La requête SQL inclut la jointure avec la table type_evenement et sélectionne libelle_type
        String sql = "SELECT e.*, te.libelle_type FROM evenement e " +
                "JOIN type_evenement te ON e.id_type_evenement = te.id_type_evenement " +
                "ORDER BY e.horodatage ASC";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                evenements.add(map(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les événements : " + e.getMessage());
            e.printStackTrace();
        }
        return evenements;
    }

    private Evenement map(ResultSet rs) throws SQLException {
        Evenement e = new Evenement();
        e.setIdEvenement(rs.getInt("id_evenement"));
        e.setNomEvenement(rs.getString("nom_evenement"));
        e.setHorodatage(rs.getTimestamp("horodatage"));
        e.setDuree(parsePostgresInterval(rs.getString("duree")));
        e.setLieu(rs.getString("lieu"));
        e.setDescription(rs.getString("description"));
        e.setIdTypeEvenement(rs.getInt("id_type_evenement"));
        e.setNomFanfaronCreateur(rs.getString("nom_fanfaron_createur"));
        e.setNomTypeEvenement(rs.getString("libelle_type")); // <<< UTILISE MAINTENANT "libelle_type"

        return e;
    }

    private Duration parsePostgresInterval(String interval) {
        String[] parts = interval.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);

        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }

    @Override
    public void update(Evenement evenement) {
        String sql = "UPDATE evenement SET nom_evenement = ?, horodatage = ?, duree = CAST(? AS interval), lieu = ?, description = ?, id_type_evenement = ?, nom_fanfaron_createur = ? WHERE id_evenement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, evenement.getNomEvenement());
            stmt.setTimestamp(2, evenement.getHorodatage());
            stmt.setString(3, toPostgresInterval(evenement.getDuree()));
            stmt.setString(4, evenement.getLieu());
            stmt.setString(5, evenement.getDescription());
            stmt.setInt(6, evenement.getIdTypeEvenement());
            stmt.setString(7, evenement.getNomFanfaronCreateur());
            stmt.setInt(8, evenement.getIdEvenement());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'événement : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de l'événement", e);
        }
    }

    @Override
    public void delete(int idEvenement) {
        String sql = "DELETE FROM evenement WHERE id_evenement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEvenement);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'événement : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression de l'événement", e);
        }
    }
}