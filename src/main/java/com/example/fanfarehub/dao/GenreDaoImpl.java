package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreDaoImpl implements GenreDao {

    private Connection connection;

    public GenreDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        String sql = "SELECT * FROM genre WHERE id = ?";
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
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genre";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                genres.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    private Genre map(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setLibelle(rs.getString("libelle"));
        return genre;
    }
}
