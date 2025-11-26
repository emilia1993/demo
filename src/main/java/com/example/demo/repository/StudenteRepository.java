package com.example.demo.repository;

import com.example.demo.entity.Studente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudenteRepository extends JpaRepository<Studente, String> {

    // Restituisce uno studente tramite la matricola
    Studente findByMatricola(String matricola);

    // Restituisce tutti gli studenti con matricola maggiore di quella indicata
    List<Studente> findByMatricolaGreaterThan(String matricola);

    // Versione numeric-safe
    @Query("SELECT s FROM Studente s WHERE CAST(s.matricola AS integer) > CAST(:matricola AS integer)")
    List<Studente> findByMatricolaGreaterThanNumeric(@Param("matricola") String matricola);




}
