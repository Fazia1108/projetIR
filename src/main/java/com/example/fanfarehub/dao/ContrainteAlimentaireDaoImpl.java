package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.ContrainteAlimentaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContrainteAlimentaireDaoImpl implements ContrainteAlimentaireDao {
    private Connection connection;

    public ContrainteAlimentaireDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<ContrainteAlimentaire> findById(Integer id) {
        String sql = "SELECT * FROM contrainte_alimentaire WHERE id_contrainte_alimentaire = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
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
    public List<ContrainteAlimentaire> findAll() {
        List<ContrainteAlimentaire> contraintes = new ArrayList<>();
        String sql = "SELECT * FROM contrainte_alimentaire";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                contraintes.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contraintes;
    }

    public boolean create(ContrainteAlimentaire contrainte) {
        String sql = "INSERT INTO contrainte_alimentaire (libelle_contrainte) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, contrainte.getLibelleContrainte());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    contrainte.setIdContrainteAlimentaire(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(ContrainteAlimentaire contrainte) {
        String sql = "UPDATE contrainte_alimentaire SET libelle_contrainte = ? WHERE id_contrainte_alimentaire = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contrainte.getLibelleContrainte());
            stmt.setInt(2, contrainte.getIdContrainteAlimentaire());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM contrainte_alimentaire WHERE id_contrainte_alimentaire = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ContrainteAlimentaire map(ResultSet rs) throws SQLException {
        ContrainteAlimentaire c = new ContrainteAlimentaire();
        c.setIdContrainteAlimentaire(rs.getInt("id_contrainte_alimentaire"));
        c.setLibelleContrainte(rs.getString("libelle_contrainte"));
        return c;
    }
}
