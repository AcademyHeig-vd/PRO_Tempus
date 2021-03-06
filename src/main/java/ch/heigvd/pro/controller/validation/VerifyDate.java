/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : VerifyDate.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 19.05.2021
 But         : Classe vérifiant qu'une date est valide
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.controller.validation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class VerifyDate {
    private final DateTimeFormatter dateFormatter;

    /**
     * Constructeur
     * @param dateFormatter - format de la date à avoir
     */
    public VerifyDate(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    /**
     * Verification que la date est valide
     * @param dateStr - date au format string
     * @return - si la date est valide
     */
    public boolean isValid(String dateStr) {
        try {
            this.dateFormatter.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
