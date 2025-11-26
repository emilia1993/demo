package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Esame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Se vuoi l'auto-incremento per l'ID
    private Long id;
    private String nomeEsame;
    private String voto;
    private String dataEsame;


    @ManyToOne
    @JoinColumn(name = "matricola_studente", referencedColumnName = "matricola")
    @JsonBackReference
    private Studente studente;  // Relazione many-to-one con lo Studente (via matricola)

    // Costruttore senza parametri (necessario per JPA)
    public Esame() {}

    // Costruttore con parametri
    public Esame(Long id, String nomeEsame, String voto, String dataEsame, Studente studente) {
        this.id = id;
        this.nomeEsame = nomeEsame;
        this.voto = voto;
        this.dataEsame = dataEsame;
        this.studente = studente;
    }

    // Getter e Setter
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

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }
}
