package com.example.demo.controller;

import com.example.demo.entity.Esame;
import com.example.demo.entity.Studente;
import com.example.demo.repository.EsameRepository;
import com.example.demo.repository.StudenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudenteController {

    private static final Logger logger = LoggerFactory.getLogger(StudenteController.class);

    @Autowired
    private StudenteRepository studenteRepository;

    @Autowired
    private EsameRepository esameRepository;

    // Recupera uno studente tramite la matricola
    @GetMapping("/studenti/{matricola}")
    public Studente getStudente(@PathVariable String matricola) {
        logger.info("GET /studenti/{}", matricola);

        Studente studente = studenteRepository.findByMatricola(matricola);
        if (studente == null) {
            logger.error("Studente con matricola {} non trovato", matricola);
        } else {
            logger.debug("Studente trovato: {}", studente);
        }

        return studente;
    }

    // Recupera studenti con matricola maggiore di un certo valore
    @GetMapping("/studenti")
    public ResponseEntity<?> getStudentiConMatricolaMaggiore(@RequestParam String matricola) {
        logger.info("GET /studenti?matricola={}", matricola);

        List<Studente> studenti = studenteRepository.findByMatricolaGreaterThan(matricola);
        logger.debug("Numero di studenti trovati: {}", studenti.size());

        return ResponseEntity.ok(studenti);
    }

    // Crea un nuovo studente
    @PostMapping("/studenti")
    public Studente creaStudente(@RequestBody Studente studente) {
        logger.info("POST /studenti - Creazione di nuovo studente con matricola {}", studente.getMatricola());
        Studente saved = studenteRepository.save(studente);
        logger.debug("Studente salvato: {}", saved);
        return saved;
    }


    // Crea un nuovo esame per uno studente
    @PostMapping(value = "/studenti", headers = "Esame")
    public ResponseEntity<Esame> creaEsame(@RequestHeader("Esame") String nomeEsame,
                                           @RequestBody Esame esame) {
        logger.info("POST /studenti - Creazione esame '{}' per lo studente {}", nomeEsame,
                esame.getStudente() != null ? esame.getStudente().getMatricola() : "null");

        if (esame.getStudente() == null || esame.getStudente().getMatricola() == null) {
            logger.warn("Studente o matricola mancante nell'esame");
            return ResponseEntity.badRequest().build();
        }

        Studente studente = studenteRepository.findByMatricola(esame.getStudente().getMatricola());
        if (studente == null) {
            logger.error("Studente {} non trovato per l'esame", esame.getStudente().getMatricola());
            return ResponseEntity.notFound().build();
        }

        esame.setNomeEsame(nomeEsame);
        esame.setStudente(studente);

        Esame saved = esameRepository.save(esame);
        logger.debug("Esame salvato: {}", saved);
        return ResponseEntity.ok(saved);
    }

    // Aggiorna il corso di studio degli studenti con matricola maggiore di un certo valore
    @PostMapping("/studenti/aggiorna-corso-maggiori")
    public ResponseEntity<?> aggiornaCorsoStudentiMaggiori(@RequestParam String matricola,
                                                           @RequestBody Studente aggiornamenti) {
        logger.info("POST /studenti/aggiorna-corso-maggiori?matricola={}", matricola);

        if (aggiornamenti.getCorsoDiStudio() == null) {
            logger.warn("Campo 'corsoDiStudio' mancante nel body");
            return ResponseEntity.badRequest().body("Devi specificare il campo 'corsoDiStudio'.");
        }

        List<Studente> studenti = studenteRepository.findByMatricolaGreaterThanNumeric(matricola);

        if (studenti.isEmpty()) {
            logger.warn("Nessuno studente trovato con matricola maggiore di {}", matricola);
            return ResponseEntity.notFound().build();
        }

        for (Studente s : studenti) {
            s.setCorsoDiStudio(aggiornamenti.getCorsoDiStudio());
        }

        studenteRepository.saveAll(studenti);
        logger.debug("Corso di studio aggiornato per {} studenti", studenti.size());
        return ResponseEntity.ok(studenti);
    }

    // Elimina uno studente
    @DeleteMapping("/studenti/{matricola}")
    public ResponseEntity<Void> eliminaStudente(@PathVariable String matricola) {
        logger.info("DELETE /studenti/{}", matricola);

        if (!studenteRepository.existsById(matricola)) {
            logger.warn("Tentativo di eliminazione studente non esistente: {}", matricola);
            return ResponseEntity.notFound().build();
        }

        studenteRepository.deleteById(matricola);
        logger.info("Studente {} eliminato con successo", matricola);
        return ResponseEntity.noContent().build();
    }

    // Aggiornamento parziale di uno studente
    @PatchMapping("/studenti/{matricola}")
    public ResponseEntity<Studente> aggiornaParzialeStudente(@PathVariable String matricola,
                                                             @RequestBody Studente aggiornamenti) {
        logger.info("PATCH /studenti/{} - Aggiornamento parziale", matricola);

        Studente studente = studenteRepository.findByMatricola(matricola);
        if (studente == null) {
            logger.error("Studente {} non trovato per aggiornamento", matricola);
            return ResponseEntity.notFound().build();
        }

        if (aggiornamenti.getNome() != null) studente.setNome(aggiornamenti.getNome());
        if (aggiornamenti.getCognome() != null) studente.setCognome(aggiornamenti.getCognome());
        if (aggiornamenti.getCorsoDiStudio() != null) studente.setCorsoDiStudio(aggiornamenti.getCorsoDiStudio());

        Studente aggiornato = studenteRepository.save(studente);
        logger.debug("Studente aggiornato: {}", aggiornato);

        return ResponseEntity.ok(aggiornato);
    }

}


