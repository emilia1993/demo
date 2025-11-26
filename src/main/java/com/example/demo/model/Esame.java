package com.example.demo.model;

public class Esame {


    private Long id;
    private String nomeEsame;
    private String voto;
    private String dataEsame;
    private String matricola;

    private Studente studente;  // Riferimento allo Studente

    // Costruttore, getter e setter
    public Esame() {}

    public Esame(Long id, String nomeEsame, String voto, String dataEsame, Studente studente) {
        this.id = id;
        this.nomeEsame = nomeEsame;
        this.voto = voto;
        this.dataEsame = dataEsame;
        this.studente = studente;
        this.matricola = matricola;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEsame() {
        return nomeEsame;
    }

    public void setNomeEsame(String nomeEsame) {
        this.nomeEsame = nomeEsame;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public String getDataEsame() {
        return dataEsame;
    }

    public void setDataEsame(String dataEsame) {
        this.dataEsame = dataEsame;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }
}

