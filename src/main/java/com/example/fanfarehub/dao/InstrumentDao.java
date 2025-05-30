package com.example.fanfarehub.dao;

import com.example.fanfarehub.model.Instrument;

import java.util.List;

public interface InstrumentDao {
    List<Instrument> findAll();
    List<Instrument> findInstrumentsByFanfaron(String nomFanfaron);
}
