package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Pupitre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PupitreDaoImpl implements PupitreDao {

    private final Connection connection;

    public PupitreDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Pupitre> findAll() {
        List<Pupitre> liste = new ArrayList<>();
        String sql = "SELECT id_pupitre, nom_pupitre FROM pupitre";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pupitre p = new Pupitre();
                p.setIdPupitre(rs.getInt("id_pupitre"));
                p.setNomPupitre(rs.getString("nom_pupitre"));
                liste.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }


    @Override
    public void insertFanfaronPupitre(String nomFanfaron, int idPupitre) {
        String sql = "INSERT INTO fanfaron_pupitre (nom_fanfaron, id_pupitre) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            stmt.setInt(2, idPupitre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFanfaronPupitres(String nomFanfaron) {
        String sql = "DELETE FROM fanfaron_pupitre WHERE nom_fanfaron = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> findPupitreIdsByFanfaron(String nomFanfaron) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_pupitre FROM fanfaron_pupitre WHERE nom_fanfaron = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("id_pupitre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    @Override
    public List<Pupitre> findByFanfaron(String nomFanfaron) {
        List<Pupitre> pupitres = new ArrayList<>();
        String sql = """
        SELECT p.id_pupitre, p.nom_pupitre
        FROM pupitre p
        JOIN fanfaron_pupitre fp ON p.id_pupitre = fp.id_pupitre
        WHERE fp.nom_fanfaron = ?
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pupitre p = new Pupitre();
                    p.setIdPupitre(rs.getInt("id_pupitre"));
                    p.setNomPupitre(rs.getString("nom_pupitre"));
                    pupitres.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Utilise un logger en production
        }

        return pupitres;
    }

    @Override
    public void deletePupitre(int idPupitre) {
        String sqlDeleteFanfaronInscription = "DELETE FROM inscription_evenement WHERE id_pupitre = ?";
        String sqlDeleteFanfaronPupitre = "DELETE FROM fanfaron_pupitre WHERE id_pupitre = ?";
        String sqlDeletePupitre = "DELETE FROM pupitre WHERE id_pupitre = ?";


        try {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(sqlDeleteFanfaronInscription)) {
                stmt.setInt(1, idPupitre);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt1 = connection.prepareStatement(sqlDeleteFanfaronPupitre)) {
                stmt1.setInt(1, idPupitre);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = connection.prepareStatement(sqlDeletePupitre)) {
                stmt2.setInt(1, idPupitre);
                stmt2.executeUpdate();
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
    public void updatePupitre(int idPupitre, Pupitre pupitre) {
        String sql = "UPDATE pupitre SET nom_pupitre = ? WHERE id_pupitre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pupitre.getNomPupitre());
            stmt.setInt(2, idPupitre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertPupitre(Pupitre pupitre) {
        String sql = "INSERT INTO pupitre (nom_pupitre) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pupitre.getNomPupitre());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pupitre.setIdPupitre(generatedKeys.getInt(1)); // récupère l’ID auto-généré
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFanfaronPupitreByPupitreId(int idPupitre) {
        String sql = "DELETE FROM fanfaron_pupitre WHERE id_pupitre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPupitre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
