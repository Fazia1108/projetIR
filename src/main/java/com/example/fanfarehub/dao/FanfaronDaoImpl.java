package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Fanfaron;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FanfaronDaoImpl implements FanfaronDao {

    private final Connection connection;

    public FanfaronDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Fanfaron> findByNomFanfaron(String nomFanfaron) {
        String sql = "SELECT * FROM fanfaron WHERE nom_fanfaron = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Fanfaron> findByEmail(String email) {
        String sql = "SELECT * FROM fanfaron WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
   }

    @Override
    public void create(Fanfaron fanfaron) {
        String sql = "INSERT INTO fanfaron (nom_fanfaron, email, mdp, prenom, nom, id_genre, id_contrainte_alimentaire, date_creation) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fanfaron.getNomFanfaron());
            stmt.setString(2, fanfaron.getEmail());
            stmt.setString(3, fanfaron.getMotDePasse());
            stmt.setString(4, fanfaron.getPrenom());
            stmt.setString(5, fanfaron.getNom());
            stmt.setObject(6, fanfaron.getGenre() != null ? fanfaron.getGenre().getId() : null);
            stmt.setObject(7, fanfaron.getContraintesAlimentaires() != null ? fanfaron.getContraintesAlimentaires().getIdContrainteAlimentaire() : null);
            stmt.setTimestamp(8, Timestamp.valueOf(fanfaron.getDateCreation()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Fanfaron fanfaron) {
        String sql = "UPDATE fanfaron SET email = ?, mdp = ?, prenom = ?, nom = ?, id_genre = ?, id_contrainte_alimentaire = ?, derniere_connexion = ?, role = ? " +
                "WHERE nom_fanfaron = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fanfaron.getEmail());
            stmt.setString(2, fanfaron.getMotDePasse());
            stmt.setString(3, fanfaron.getPrenom());
            stmt.setString(4, fanfaron.getNom());
            stmt.setObject(5, fanfaron.getGenre() != null ? fanfaron.getGenre().getId() : null);
            stmt.setObject(6, fanfaron.getContraintesAlimentaires() != null ? fanfaron.getContraintesAlimentaires().getIdContrainteAlimentaire() : null);
            stmt.setTimestamp(7, fanfaron.getDerniereConnexion() != null ? Timestamp.valueOf(fanfaron.getDerniereConnexion()) : null);
            stmt.setString(8, fanfaron.getRole());
            stmt.setString(9, fanfaron.getNomFanfaron());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Fanfaron> findById(String nomFanfaron) {
        return findByNomFanfaron(nomFanfaron);
    }

    @Override
    public List<Fanfaron> findAll() {
        List<Fanfaron> fanfarons = new ArrayList<>();
        String sql = "SELECT * FROM fanfaron";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                fanfarons.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fanfarons;
    }

    @Override
    public void delete(String nomFanfaron) {
        String sqlDeleteInscription = "DELETE FROM inscription_evenement WHERE nom_fanfaron = ?";
        String sqlDeleteAppartenance = "DELETE FROM appartenance WHERE nom_fanfaron = ?";
        String sqlDeletePupitre = "DELETE FROM fanfaron_pupitre WHERE nom_fanfaron = ?";
        String sqlDeleteFanfaron = "DELETE FROM fanfaron WHERE nom_fanfaron = ?";

        try {
            connection.setAutoCommit(false); // Début de la transaction

            try (PreparedStatement stmt1 = connection.prepareStatement(sqlDeleteInscription)) {
                stmt1.setString(1, nomFanfaron);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = connection.prepareStatement(sqlDeleteAppartenance)) {
                stmt2.setString(1, nomFanfaron);
                stmt2.executeUpdate();
            }

            try (PreparedStatement stmt3 = connection.prepareStatement(sqlDeletePupitre)) {
                stmt3.setString(1, nomFanfaron);
                stmt3.executeUpdate();
            }

            try (PreparedStatement stmt4 = connection.prepareStatement(sqlDeleteFanfaron)) {
                stmt4.setString(1, nomFanfaron);
                stmt4.executeUpdate();
            }

            connection.commit(); // Valider les suppressions
        } catch (SQLException e) {
            try {
                connection.rollback(); // Annuler en cas d'erreur
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("Erreur lors de la suppression du fanfaron et de ses relations", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private Fanfaron map(ResultSet rs) throws SQLException {
        Fanfaron f = new Fanfaron();
        f.setNomFanfaron(rs.getString("nom_fanfaron"));
        f.setEmail(rs.getString("email"));
        f.setMotDePasse(rs.getString("mdp"));
        f.setPrenom(rs.getString("prenom"));
        f.setNom(rs.getString("nom"));
        f.setRole(rs.getString("role"));

        Timestamp dateCreation = rs.getTimestamp("date_creation");
        if (dateCreation != null) {
            f.setDateCreation(dateCreation.toLocalDateTime());
        }

        Timestamp derniereConnexion = rs.getTimestamp("derniere_connexion");
        if (derniereConnexion != null) {
            f.setDerniereConnexion(derniereConnexion.toLocalDateTime());
        }

        // Si tu veux charger les objets Genre et ContrainteAlimentaire depuis leurs id, fais-le ici
        // f.setGenre(...);
        // f.setContraintesAlimentaires(...);

        return f;
    }
}
