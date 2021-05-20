/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : PrimaryController.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Controlleur pour la page principale de l'application
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import javafx.fxml.FXML;

import java.io.IOException;

public class PrimaryController {


    @FXML
    private void switchToPeriode() throws IOException {
        Tempus.setRoot("view/periodeAdd");
    }

    @FXML
    private void switchToRappel() throws IOException {
        Tempus.setRoot("view/rappelAdd");
    }

    @FXML
    private void switchToProf() throws IOException {
        Tempus.setRoot("view/profAdd");
    }

    @FXML
    private void switchToCours() throws IOException {
        Tempus.setRoot("view/coursAdd");
    }

    public void switchToCalendar() throws IOException {
       // Tempus.getScene().setRoot(CalendarPageControler.loadFromFXMLDocument());
    }

}
