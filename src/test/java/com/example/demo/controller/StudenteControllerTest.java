package com.example.demo.controller;

import com.example.demo.entity.Esame;
import com.example.demo.entity.Studente;
import com.example.demo.repository.EsameRepository;
import com.example.demo.repository.StudenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudenteRepository studenteRepository;

    @Autowired
    private EsameRepository esameRepository;

    @BeforeEach
    void setup() {
        // Pulisce database prima di ogni test
        esameRepository.deleteAll();
        studenteRepository.deleteAll();
    }

    @Test
    void testCRUDStudente() throws Exception {
        String studenteJson = """
            {
                "matricola": "999",
                "nome": "Test",
                "cognome": "User",
                "corsoDiStudio": "Testing"
            }
        """;

        // CREAZIONE
        mockMvc.perform(post("/studenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studenteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matricola").value("999"))
                .andExpect(jsonPath("$.nome").value("Test"));

        // LETTURA
        mockMvc.perform(get("/studenti/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cognome").value("User"));

        // AGGIORNAMENTO PARZIALE
        String aggiornamentoJson = """
            {
                "nome": "TestMod",
                "corsoDiStudio": "Ingegneria"
            }
        """;

        mockMvc.perform(patch("/studenti/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aggiornamentoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("TestMod"))
                .andExpect(jsonPath("$.corsoDiStudio").value("Ingegneria"));

        // ELIMINAZIONE
        mockMvc.perform(delete("/studenti/999"))
                .andExpect(status().isNoContent());

        // VERIFICA ELIMINAZIONE
        mockMvc.perform(get("/studenti/999"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testCreaEsame() throws Exception {
        // 1️⃣ Creiamo uno studente per associare l'esame
        Studente studente = new Studente("123", "Luigi", "Bianchi", "Fisica");
        studenteRepository.save(studente);

        String esameJson = """
            {
                "studente": {"matricola": "123"},
                "voto": "28",
                "dataEsame": "2025-06-15"
            }
        """;

        // CREAZIONE ESAME
        mockMvc.perform(post("/studenti")
                        .header("Esame", "Analisi 1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(esameJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeEsame").value("Analisi 1"))
                .andExpect(jsonPath("$.voto").value("28"))
                .andExpect(jsonPath("$.dataEsame").value("2025-06-15"));

        // LETTURA ESAME DAL DATABASE
        Esame saved = esameRepository.findAll().get(0);
        assert saved.getStudente().getMatricola().equals("123");
    }
}
