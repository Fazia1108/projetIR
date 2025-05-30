package com.example.fanfarehub.model;

public class Instrument {
    private int idInstrument;
    private String nomInstrument;

    public Instrument() {}

    //Getters et Setters
    public Instrument(int idInstrument, String nomInstrument) {
        this.idInstrument = idInstrument;
        this.nomInstrument = nomInstrument;
    }
    public int getIdInstrument() { return idInstrument; }
    public String getNomInstrument() { return nomInstrument; }
    public void setIdInstrument(int idInstrument) { this.idInstrument = idInstrument; }
    public void setNomInstrument(String nomInstrument) { this.nomInstrument = nomInstrument; }
}
