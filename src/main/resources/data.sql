CREATE TABLE IF NOT EXISTS studente (
    matricola VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    corso_di_studio VARCHAR(100)
);
INSERT INTO studente (matricola, nome, cognome, corso_di_studio) VALUES ('12345', 'Mario', 'Rossi', 'Ingegneria Informatica');
INSERT INTO studente (matricola, nome, cognome, corso_di_studio) VALUES ('67890', 'Luigi', 'Bianchi', 'Economia');

-- Creazione della tabella Esami con una chiave esterna che collega allo studente
CREATE TABLE IF NOT EXISTS esame (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,        -- id come chiave primaria
    nome_esame VARCHAR(255),                     -- nome dell'esame
    voto VARCHAR(5),                             -- voto dell'esame
    data_esame DATE,                             -- data dell'esame
    matricola_studente VARCHAR(20),              -- chiave esterna che collega all'utente
    FOREIGN KEY (matricola_studente) REFERENCES studente(matricola)  -- relazione con la tabella studente
);

INSERT INTO esame (nome_esame, voto, data_esame, matricola_studente) VALUES ('Matematica I', '30', '2025-06-15', '12345');
