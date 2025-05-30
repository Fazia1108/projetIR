package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Instrument;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrumentDaoImpl implements InstrumentDao {

    private final Connection connection;

    public InstrumentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Instrument> findAll() {
        List<Instrument> instruments = new ArrayList<>();
        String sql = "SELECT id_instrument, nom_instrument FROM instrument";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                instruments.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instruments;
    }

    @Override
    public List<Instrument> findInstrumentsByFanfaron(String nomFanfaron) {
        List<Instrument> instruments = new ArrayList<>();

        String sql = """
            SELECT DISTINCT i.id_instrument, i.nom_instrument
            FROM fanfaron_pupitre fp
            JOIN pupitre p ON fp.id_pupitre = p.id_pupitre
            JOIN instrument i ON p.nom_pupitre = i.nom_instrument
            WHERE fp.nom_fanfaron = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                instruments.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instruments;
    }

    private Instrument map(ResultSet rs) throws SQLException {
        Instrument i = new Instrument();
        i.setIdInstrument(rs.getInt("id_instrument"));
        i.setNomInstrument(rs.getString("nom_instrument"));
        return i;
    }
}
