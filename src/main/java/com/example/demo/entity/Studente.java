package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Studente {

    @Id
    private String matricola;
    private String nome;
    private String cognome;
    private String corsoDiStudio;

    @JsonManagedReference
    @OneToMany(mappedBy = "studente")  // Relazione inversa con Esame
    private List<Esame> esami;  // Lista di esami associati allo studente

    // Costruttore, getter e setter
    public Studente() {}

    public Studente(String matricola, String nome, String cognome, String corsoDiStudio) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.corsoDiStudio = corsoDiStudio;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCorsoDiStudio() {
        return corsoDiStudio;
    }

    public void setCorsoDiStudio(String corsoDiStudio) {
        this.corsoDiStudio = corsoDiStudio;
    }

    public List<Esame> getEsami() {
        return esami;
    }

    public void setEsami(List<Esame> esami) {
        this.esami = esami;
    }
}
