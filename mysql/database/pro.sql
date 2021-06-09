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
                            acronyme VARCHAR(3) NOT NULL,
                            nom VARCHAR(50) NOT NULL,
                            prenom VARCHAR(50) NOT NULL,
                            mail VARCHAR(255) NOT NULL,
                            CONSTRAINT PK_Professeur PRIMARY KEY (acronyme)
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
                       acronyme VARCHAR(3),
                       CONSTRAINT PK_Cours PRIMARY KEY (idEvenement)
)ENGINE = InnoDB;

CREATE TABLE Periode (
                         idPeriode INTEGER UNSIGNED AUTO_INCREMENT,
                         idCours INTEGER UNSIGNED,
                         jourSemaine VARCHAR(8) NOT NULL,
                         heureDebut TIME NOT NULL,
                         heureFin TIME NOT NULL,
                         salle VARCHAR(10) NOT NULL,
                         CONSTRAINT PK_Periode PRIMARY KEY (idPeriode)
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
		FOREIGN KEY (acronyme)
		REFERENCES Professeur (acronyme)
		ON DELETE SET NULL
		ON UPDATE CASCADE;


ALTER TABLE Periode
	ADD CONSTRAINT FK_Periode_idCours
		FOREIGN KEY (idCours)
		REFERENCES Cours (idEvenement)
		ON DELETE CASCADE
		ON UPDATE CASCADE;