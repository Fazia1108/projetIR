package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Fanfaron;

import java.sql.*;
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
        String sql = "INSERT INTO fanfaron (id_fanfaron, nom_fanfaron, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fanfaron.getIdFanfaron());
            stmt.setString(2, fanfaron.getNomFanfaron());
            stmt.setString(3, fanfaron.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Fanfaron fanfaron) {
        String sql = "UPDATE fanfaron SET nom_fanfaron = ?, email = ? WHERE id_fanfaron = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fanfaron.getNomFanfaron());
            stmt.setString(2, fanfaron.getEmail());
            stmt.setString(3, fanfaron.getIdFanfaron());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Fanfaron> findById(String id) {
        String sql = "SELECT * FROM fanfaron WHERE id_fanfaron = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
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
    public void delete(Fanfaron fanfaron) {
        String sql = "DELETE FROM fanfaron WHERE id_fanfaron = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fanfaron.getIdFanfaron());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Fanfaron map(ResultSet rs) throws SQLException {
        Fanfaron f = new Fanfaron();
        f.setIdFanfaron(rs.getString("id_fanfaron"));
        f.setNomFanfaron(rs.getString("nom_fanfaron"));
        f.setEmail(rs.getString("email"));
        return f;
    }
}
