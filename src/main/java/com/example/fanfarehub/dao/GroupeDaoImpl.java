package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Groupe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupeDaoImpl implements GroupeDao {

    private final Connection connection;

    public GroupeDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Groupe> findAll() {
        List<Groupe> liste = new ArrayList<>();
        String sql = "SELECT id_groupe, nom_groupe FROM groupe";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Groupe g = new Groupe();
                g.setIdGroupe(rs.getInt("id_groupe"));
                g.setNomGroupe(rs.getString("nom_groupe"));
                liste.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public void insertFanfaronGroupe(String nomFanfaron, int idGroupe) {
        String sql = "INSERT INTO appartenance (nom_fanfaron, id_groupe) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            stmt.setInt(2, idGroupe);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFanfaronGroupes(String nomFanfaron) {
        String sql = "DELETE FROM appartenance WHERE nom_fanfaron = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> findGroupeIdsByFanfaron(String nomFanfaron) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_groupe FROM appartenance WHERE nom_fanfaron = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("id_groupe"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
