/*SQL NON A JOUR MAIS FONCTIONNEL POUR L'INSTANT*/
DROP SCHEMA IF EXISTS PRO;
CREATE SCHEMA PRO DEFAULT CHARSET = utf8mb4;
USE PRO;

CREATE TABLE Evenement (
                           idEvenement INTEGER UNSIGNED AUTO_INCREMENT,
                           titre VARCHAR(50) NOT NULL,
                           dateDebut DATE,
                           dateEcheance DATE NOT NULL,
                           description VARCHAR(255),
                           CONSTRAINT PK_Evenement PRIMARY KEY (idEvenement)
)ENGINE = InnoDB;

CREATE TABLE Professeur (
                            idProfesseur INTEGER UNSIGNED AUTO_INCREMENT,
                            nom VARCHAR(50) NOT NULL,
                            prenom VARCHAR(50) NOT NULL,
                            mail VARCHAR(255) NOT NULL,
                            CONSTRAINT PK_Professeur PRIMARY KEY (idProfesseur)
)ENGINE = InnoDB;

CREATE TABLE Rappel (
                        idEvenement INTEGER UNSIGNED,
                        contenu VARCHAR(255) NOT NULL,
                        lien VARCHAR(255),
                        heure TIME NOT NULL,
                        CONSTRAINT PK_Rappel PRIMARY KEY (idEvenement)
)ENGINE = InnoDB;

CREATE TABLE Cours (
                       idEvenement INTEGER UNSIGNED,
                       idProfesseur INTEGER UNSIGNED,
                       CONSTRAINT PK_Cours PRIMARY KEY (idEvenement)
)ENGINE = InnoDB;

CREATE TABLE Periode (
                         idPeriode INTEGER UNSIGNED AUTO_INCREMENT,
                         idCours INTEGER UNSIGNED,
                         nom VARCHAR(50) NOT NULL,
                         jourSemaine VARCHAR(8) NOT NULL,
                         heureDebut TIME NOT NULL,
                         heureFin TIME NOT NULL,
                         salle VARCHAR(10) NOT NULL,
                         CONSTRAINT PK_Periode PRIMARY KEY (idPeriode)
)ENGINE = InnoDB;

CREATE TABLE Projet (
                        idEvenement INTEGER UNSIGNED,
                        contenu VARCHAR(255),
                        CONSTRAINT PK_Projet PRIMARY KEY (idEvenement)
)ENGINE = InnoDB;

CREATE TABLE TodoListe (
                           idTodoListe INTEGER UNSIGNED AUTO_INCREMENT,
                           idProjet INTEGER UNSIGNED,
                           titre VARCHAR(50) NOT NULL,
                           description VARCHAR(255),
                           CONSTRAINT PK_TodoListe PRIMARY KEY (idTodoListe)
)ENGINE = InnoDB;

/* Contraintes */
ALTER TABLE Rappel
    ADD CONSTRAINT FK_Rappel_idEvenement
        FOREIGN KEY (idEvenement)
            REFERENCES Evenement (idEvenement)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

ALTER TABLE Cours
    ADD CONSTRAINT FK_Cours_idEvenement
        FOREIGN KEY (idEvenement)
            REFERENCES Evenement (idEvenement)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
	ADD CONSTRAINT FK_Cours_idProfesseur
		FOREIGN KEY (idProfesseur)
		REFERENCES Professeur (idProfesseur)
		ON DELETE SET NULL
		ON UPDATE SET NULL;

ALTER TABLE Projet
    ADD CONSTRAINT FK_Projet_idEvenement
        FOREIGN KEY (idEvenement)
            REFERENCES Evenement (idEvenement)
            ON DELETE CASCADE
            ON UPDATE CASCADE;
/*
ALTER TABLE Periode
	ADD CONSTRAINT FK_Periode_idCours
		FOREIGN KEY (idCours)
		REFERENCES Cours (idEvenement)
		ON DELETE CASCADE
		ON UPDATE CASCADE;
	*/
ALTER TABLE TodoListe
    ADD CONSTRAINT FK_TodoListe_idProjet
        FOREIGN KEY (idProjet)
            REFERENCES Projet (idEvenement)
            ON DELETE CASCADE
            ON UPDATE CASCADE;